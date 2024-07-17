package com.tdcolvin.flowsworkshop

import android.icu.text.DateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tdcolvin.flowsworkshop.datasource.ServerTimeDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@Composable
fun SingleClockScreen(
    modifier: Modifier = Modifier,
    viewModel: SingleClockViewModel = viewModel()
) {
    val dateString by viewModel.serverTime.collectAsStateWithLifecycle("")

    Column(modifier = modifier) {
        Text("Date and time", style = MaterialTheme.typography.labelMedium)
        Text(dateString, style = MaterialTheme.typography.displayMedium)
    }
}


class SingleClockViewModel: ViewModel() {
    private val serverTimeDataSource = ServerTimeDataSource()

    val serverTime = flow {
        while (true) {
            emit(serverTimeDataSource.getLatestTimeFromServer())
            delay(1000)
        }
    }
        .map { date -> DateFormat.getDateTimeInstance().format(date) }
}