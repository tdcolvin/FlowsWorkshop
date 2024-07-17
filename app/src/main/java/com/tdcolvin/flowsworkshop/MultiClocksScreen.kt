package com.tdcolvin.flowsworkshop

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tdcolvin.flowsworkshop.datasource.ServerTimeDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import java.util.Locale

@Composable
fun MultiClocksScreen(
    modifier: Modifier = Modifier,
    viewModel: ClocksViewModel = viewModel()
) {
    val year by viewModel.year.collectAsStateWithLifecycle("")
    val month by viewModel.month.collectAsStateWithLifecycle("")
    val day by viewModel.day.collectAsStateWithLifecycle("")
    val hour by viewModel.hour.collectAsStateWithLifecycle("")
    val minute by viewModel.minute.collectAsStateWithLifecycle("")
    val second by viewModel.second.collectAsStateWithLifecycle("")

    Column(modifier = modifier) {
        Text("Year", style = MaterialTheme.typography.labelMedium)
        Text(year, style = MaterialTheme.typography.displayMedium)

        Text("Month", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(top = 20.dp))
        Text(month, style = MaterialTheme.typography.displayMedium)

        Text("Day", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(top = 20.dp))
        Text(day, style = MaterialTheme.typography.displayMedium)

        Text("Hour", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(top = 20.dp))
        Text(hour, style = MaterialTheme.typography.displayMedium)

        Text("Minute", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(top = 20.dp))
        Text(minute, style = MaterialTheme.typography.displayMedium)

        Text("Second", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(top = 20.dp))
        Text(second, style = MaterialTheme.typography.displayMedium)
    }
}


class ClocksViewModel: ViewModel() {
    private val serverTimeDataSource = ServerTimeDataSource()

    private val serverTime = flow {
        while (true) {
            emit(serverTimeDataSource.getLatestTimeFromServer())
            delay(1000)
        }
    }.shareIn(viewModelScope, SharingStarted.Lazily)

    val year = serverTime.map { SimpleDateFormat("yyyy", Locale.getDefault()).format(it) }
    val month = serverTime.map { SimpleDateFormat("MMMM", Locale.getDefault()).format(it) }
    val day = serverTime.map { SimpleDateFormat("dd", Locale.getDefault()).format(it) }
    val hour = serverTime.map { SimpleDateFormat("kk", Locale.getDefault()).format(it) }
    val minute = serverTime.map { SimpleDateFormat("mm", Locale.getDefault()).format(it) }
    val second = serverTime.map { SimpleDateFormat("ss", Locale.getDefault()).format(it) }
}