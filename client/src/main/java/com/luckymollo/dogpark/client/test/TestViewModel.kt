package com.luckymollo.dogpark.client.test

import com.luckymollo.dogpark.client.data.Repository
import com.luckymollo.dogpark.core.base.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestViewModel(repository: Repository) : MviViewModel<Repository, State, BaseAction, BaseResult, BaseEvent>(repository, State()) {
    override fun handle(action: BaseAction): Flow<BaseResult> = flow {
    }

    override fun reduce(result: BaseResult): State {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
