package com.luckymollo.dogpark.core.data

import com.luckymollo.dogpark.core.session.SessionManager
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class BaseRepository(private val coreService: CoreService) : KoinComponent {

    private val sessionManager: SessionManager by inject()
}
