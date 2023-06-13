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
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun ovenScreen(id: Int,ovenViewModel: OvenViewModel = viewModel()) {

    val ovenUi by ovenViewModel.uiState.collectAsState()

    ovenViewModel.setId(id)

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
                    text =  id.toString(), //stringResource(id = R.string.stove),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Color.White)

            SwitchWithLabels(
                checked = ovenUi.OvenOn,
                onCheckedChange = { checked ->
                    ovenViewModel.switchOven(checked)
                },
                labelOn = stringResource(id = R.string.on),
                labelOff = stringResource(id = R.string.off)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TemperatureSlider(
                title =  stringResource(id = R.string.setTemmp),
                minValue = 90,
                maxValue = 230,
                currentTemperature = ovenUi.Oventempeture,
                onTemperatureChange = { value ->
                    ovenViewModel.setTemp(value)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            val Gmodes = listOf(stringResource(id = R.string.complete),
                stringResource(id = R.string.econmic),
                stringResource(id = R.string.off))

            ModeSelector(
                title = stringResource(id = R.string.grillMode),
                selectedMode = ovenUi.selectedGrillMode,
                modes = Gmodes,
                onModeSelected = { mode -> ovenViewModel.changeGrillMode(mode)}
            )

            Spacer(modifier = Modifier.height(16.dp))

            val Cmodes = listOf(stringResource(id = R.string.normal),
                stringResource(id = R.string.econmic),
                stringResource(id = R.string.off))

            ModeSelector(
                title = stringResource(id = R.string.convMode),
                selectedMode = ovenUi.selectedConvMode,
                modes = Cmodes,
                onModeSelected = { mode -> ovenViewModel.changeConvMode(mode)}
            )

            Spacer(modifier = Modifier.height(16.dp))
            val Hmodes = listOf(
                stringResource(id = R.string.conventional),
                stringResource(id = R.string.top),
                stringResource(id = R.string.bottom)
            )

            ModeSelector(
                title =  stringResource(id = R.string.heatMode),
                selectedMode = ovenUi.selectedHeatMode,
                modes = Hmodes,
                onModeSelected = { mode -> ovenViewModel.changeHeatMode(mode) }
            )

            Spacer(modifier = Modifier.height(16.dp))


        }
    }
}


@Preview
@Composable
fun ovenscreenPrev() {
    MyApplicationTheme {
        ovenScreen(id=2)
    }
}
