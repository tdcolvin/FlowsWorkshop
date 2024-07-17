package com.tdcolvin.flowsworkshop

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@Composable
fun UIStateFlowScreen(
    modifier: Modifier = Modifier,
    viewModel: UIStateFlowScreenViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = modifier) {
        Row {
            Button(onClick = { viewModel.setColor(Color.Red) }) {
                Text("Red")
            }
            Button(onClick = { viewModel.setColor(Color.Green) }) {
                Text("Green")
            }
            Button(onClick = { viewModel.setColor(Color.Blue) }) {
                Text("Blue")
            }
        }
        Row(modifier = Modifier.padding(top = 10.dp)) {
            Button(onClick = { viewModel.setNumber(1) }) {
                Text("1")
            }
            Button(onClick = { viewModel.setNumber(2) }) {
                Text("2")
            }
            Button(onClick = { viewModel.setNumber(42) }) {
                Text("42")
            }
        }
        Text(
            modifier = Modifier.padding(top = 20.dp),
            fontSize = 200.sp,
            color = uiState.color,
            text = uiState.number.toString()
        )
    }
}

data class MyUIState(
    val color: Color = Color.Red,
    val number: Int = 1
)

class UIStateFlowScreenViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(MyUIState())
    val uiState: StateFlow<MyUIState> = _uiState

    fun setColor(color: Color) {
        _uiState.update { it.copy(color = color) }
    }

    fun setNumber(number: Int) {
        _uiState.update { it.copy(number = number) }
    }
}