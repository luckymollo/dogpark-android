package com.luckymollo.dogpark.core.base

import com.luckymollo.dogpark.core.data.BaseRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
abstract class MviViewModel<T : BaseRepository, S : BaseState, A : BaseAction, R : BaseResult, E : BaseEvent>(
    repository: T,
    initialState: S
) : BaseViewModel<T>(repository), CoroutineScope {

    abstract fun handle(action: A): Flow<R>
    abstract fun reduce(result: R): S

    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.IO
    private val actions = Channel<A>(Channel.CONFLATED)

    private val resultFlows = Channel<Flow<R>>(Channel.UNLIMITED)

    private val events = BroadcastChannel<E>(1)

    protected val mStates = ConflatedBroadcastChannel(initialState)
    val states: ReceiveChannel<S> get() = mStates.openSubscription()

    val state get() = mStates.value

    init {
        launch {
            actions.consumeEach { action ->
                resultFlows.send(handle(action))
            }
        }

        launch {
            resultFlows.consumeEach { results ->
                launch {
                    results.collect { result ->
                        mStates.send(reduce(result))
                    }
                }
            }
        }
    }

    fun dispatch(action: A) = actions.offer(action)

    protected fun sendEvent(event: E) {
        launch {
            events.send(event)
        }
    }

    private fun subscribeToEvents(): ReceiveChannel<E> = events.openSubscription()

    fun observeEvents(coroutineScope: CoroutineScope, onEvent: suspend (E) -> Unit) {
        coroutineScope.launch {
            subscribeToEvents().consumeEach { onEvent(it) }
        }
    }

    fun observeState(coroutineScope: CoroutineScope, onStateChanged: suspend (S) -> Unit) {
        coroutineScope.launch {
            states.consumeEach { onStateChanged(it) }
        }
    }

    override fun onCleared() {
        coroutineContext.cancel()
        super.onCleared()
    }
}
