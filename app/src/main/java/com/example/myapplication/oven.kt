package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun ovenScreen() {
    var selectedGrillMode by remember { mutableStateOf("Off") }
    var selectedConvMode by remember { mutableStateOf("Off") }
    var selectedHeatMode by remember { mutableStateOf("Top") }
    var Oventemperature by remember { mutableStateOf(180) }
    var OvenOn by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.stove),
                    contentDescription = null,
                    modifier = Modifier.size(88.dp)
                )
                Text(
                    text = stringResource(id = R.string.stove),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Color.White)

            SwitchWithLabels(
                checked = OvenOn,
                onCheckedChange = { checked ->
                    OvenOn = checked
                },
                labelOn = "On",
                labelOff = "Off"
            )

            Spacer(modifier = Modifier.height(16.dp))

            TemperatureSlider(
                title =  "Set temperature",
                minValue = 90,
                maxValue = 230,
                currentTemperature = Oventemperature,
                onTemperatureChange = { value ->
                    Oventemperature = value
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            val Gmodes = listOf("Complete", "Economic", "Off")

            ModeSelector(
                title = "Grill Mode",
                selectedMode = selectedGrillMode,
                modes = Gmodes,
                onModeSelected = { mode -> selectedGrillMode = mode }
            )

            Spacer(modifier = Modifier.height(16.dp))

            val Cmodes = listOf("Normal", "Economic", "Off")

            ModeSelector(
                title = "Covnection Mode",
                selectedMode = selectedConvMode,
                modes = Cmodes,
                onModeSelected = { mode -> selectedConvMode = mode }
            )

            Spacer(modifier = Modifier.height(16.dp))
            val Hmodes = listOf("COnventional", "Top", "Bottom")

            ModeSelector(
                title = "Heat Mode",
                selectedMode = selectedHeatMode,
                modes = Hmodes,
                onModeSelected = { mode -> selectedHeatMode = mode }
            )

            Spacer(modifier = Modifier.height(16.dp))


        }
    }
}


@Preview
@Composable
fun ovenscreenPrev() {
    MyApplicationTheme {
        ovenScreen()
    }
}
