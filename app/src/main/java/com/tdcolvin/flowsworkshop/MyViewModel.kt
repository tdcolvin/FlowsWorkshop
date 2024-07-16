package com.tdcolvin.flowsworkshop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tdcolvin.flowsworkshop.datasource.ServerTimeDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn

class MyViewModel: ViewModel() {
    private val serverTimeDataSource = ServerTimeDataSource()

    val serverTime = flow {
        while (true) {
            emit(serverTimeDataSource.getLatestTimeFromServer())
            delay(5000)
        }
    }.shareIn(viewModelScope, SharingStarted.Lazily)
}

