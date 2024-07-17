package com.tdcolvin.flowsworkshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.tdcolvin.flowsworkshop.ui.theme.FlowsWorkshopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FlowsWorkshopTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // This is for the first hands-on
                    FlowCreationScreen(modifier = Modifier.padding(innerPadding))

                    // Uncomment this, and comment out the above, for the second hands-on
                    //SingleClockScreen(modifier = Modifier.padding(innerPadding))

                    // Uncomment this, and comment out the above, for the third hands-on
                    //MultiClocksScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}