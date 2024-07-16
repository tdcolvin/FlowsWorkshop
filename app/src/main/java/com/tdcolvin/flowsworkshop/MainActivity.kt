package com.tdcolvin.flowsworkshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tdcolvin.flowsworkshop.datasource.ServerTimeDataSource
import com.tdcolvin.flowsworkshop.ui.theme.FlowsWorkshopTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Date
import kotlin.coroutines.cancellation.CancellationException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FlowsWorkshopTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FlowsWorkshopScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun FlowsWorkshopScreen(
    modifier: Modifier = Modifier,
    viewModel: MyViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    var flowEmissions by remember { mutableStateOf("") }

    Column(modifier) {
        Row {
            Button(onClick = {
                scope.launch {
                    viewModel.serverTime.collect {
                        flowEmissions += "\n" + it
                    }
                }
            }) {
                Text("Start Collecting")
            }
            Button(onClick = {
                scope.cancelChildren()
                flowEmissions = ""
            }) {
                Text("Stop Collecting")

            }
        }

        Text(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
                .fillMaxWidth(),
            text = flowEmissions
        )
    }
}

val myFlow = flow {
    val dataSource = ServerTimeDataSource()
    while (true) {
        emit(dataSource.getLatestTimeFromServer())
        delay(5000)
    }
}

fun CoroutineScope.cancelChildren(cause: CancellationException? = null) {
    val job = coroutineContext[Job] ?: error("Scope cannot be cancelled because it does not have a job: $this")
    job.cancelChildren(cause)
}