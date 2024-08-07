package com.tdcolvin.flowsworkshop.datasource

import android.util.Log
import java.util.Date

class ServerTimeDataSource {
    suspend fun getLatestTimeFromServer(): Date {
        Log.v("timeserver", "Getting time from time server...")
        return Date()
    }
}