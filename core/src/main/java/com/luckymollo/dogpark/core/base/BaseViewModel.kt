package com.luckymollo.dogpark.core.base

import androidx.lifecycle.ViewModel
import com.luckymollo.dogpark.core.data.BaseRepository
import org.koin.core.KoinComponent

abstract class BaseViewModel<T : BaseRepository>(val repository: T) : ViewModel(), KoinComponent
