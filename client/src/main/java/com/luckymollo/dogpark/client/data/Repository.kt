package com.luckymollo.dogpark.client.data

import com.luckymollo.dogpark.core.data.BaseRepository
import com.luckymollo.dogpark.core.data.CoreService

class Repository(clientService: ClientService,
                 coreService: CoreService) : BaseRepository(coreService) {

}
