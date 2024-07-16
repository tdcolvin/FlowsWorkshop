package com.tdcolvin.flowsworkshop.datasource

import android.util.Log
import kotlinx.coroutines.delay
import java.text.DateFormat
import java.util.Date

class ServerTimeDataSource {
    suspend fun getLatestTimeFromServer(): String {
        Log.v("timeserver", "Getting time from time server...")
        delay(500)
        return DateFormat.getDateTimeInstance().format(Date())
    }
}