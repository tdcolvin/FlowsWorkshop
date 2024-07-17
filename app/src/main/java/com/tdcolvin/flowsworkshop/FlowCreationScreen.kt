package com.tdcolvin.flowsworkshop

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tdcolvin.flowsworkshop.datasource.ServerTimeDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException


@Composable
fun FlowCreationScreen(
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var flowEmissions by remember { mutableStateOf("") }

    Column(modifier) {
        Row {
            Button(onClick = {
                scope.launch {
                    partyGuestsFlow.collect {
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
    emit(1)
    delay(500)
    emit(2)
}

val partyGuestsFlow = flow {
    for(guest in guests) {
        emit(guest)
        delay(200)
    }
}
    .filter { it.age >= 18 }
    .map { it.name.substring(0, 1) }


data class PartyGuest(val name: String, val age: Int)

val guests = listOf(
    PartyGuest("Ava", 33),
    PartyGuest("Benjamin", 43),
    PartyGuest("Charlotte", 83),
    PartyGuest("Dev", 12),
    PartyGuest("Elijah", 39),
    PartyGuest("Finn", 52),
    PartyGuest("Grayson", 10),
    PartyGuest("Henry", 44),
    PartyGuest("Isaac", 64),
    PartyGuest("James", 97),
    PartyGuest("Kai", 12),
    PartyGuest("Luna", 61),
    PartyGuest("Mia", 31),
    PartyGuest("Noah", 28),
    PartyGuest("Oli", 19),
    PartyGuest("Parker", 54),
    PartyGuest("Quillam", 108),
    PartyGuest("Rice", 20),
    PartyGuest("Sophia", 49),
    PartyGuest("Theo", 72),
    PartyGuest("Uriel", 15),
    PartyGuest("Victoria", 71),
    PartyGuest("Willow", 39),
    PartyGuest("Xavier", 47),
    PartyGuest("Yusuf", 17),
    PartyGuest("Zoey", 37),
)

fun CoroutineScope.cancelChildren(cause: CancellationException? = null) {
    val job = coroutineContext[Job] ?: error("Scope cannot be cancelled because it does not have a job: $this")
    job.cancelChildren(cause)
}