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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.lamp.SwitchWithLabels
import com.example.myapplication.oven.OvenViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

@Composable
fun ovenScreen(id: String,ovenViewModel: OvenViewModel = viewModel()) {

    val ovenUi by ovenViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ovenViewModel.setId(id)

    val isOn = stringResource(id = R.string.is_on)
    val isOff = stringResource(id = R.string.is_off)
    val changedTemperature = stringResource(id = R.string.changedTemp)
    val changedGrillMode = stringResource(id = R.string.changedGrillMode)
    val changedConvectionMode = stringResource(id = R.string.changedConvectionMode)
    val changedHeatMode = stringResource(id = R.string.changedHeatMode)
    val complete = stringResource(id = R.string.complete)
    val economic = stringResource(id = R.string.econmic)
    val off = stringResource(id = R.string.offf)
    val normal = stringResource(id = R.string.normal)
    val conventional = stringResource(id = R.string.conventional)
    val top = stringResource(id = R.string.top)
    val bottom = stringResource(id = R.string.bottom)



    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(paddingValues) // Use the padding here
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
                        modifier = Modifier.size(88.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                    )
                    Text(
                        text = ovenUi.device?.result?.name.toString(), //stringResource(id = R.string.stove),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.scrim,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = MaterialTheme.colorScheme.secondary)

                SwitchWithLabels(
                    checked = ovenUi.OvenOn,
                    onCheckedChange = { checked ->
                        ovenViewModel.switchOven(checked)
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = if (checked) {
                                    "${ovenUi.device?.result?.name} $isOn"
                                } else {
                                    "${ovenUi.device?.result?.name} $isOff"
                                }
                            )
                        }
                    },
                    labelOn = stringResource(id = R.string.onn),
                    labelOff = stringResource(id = R.string.offf)
                )

                Spacer(modifier = Modifier.height(16.dp))

                TemperatureSlider(
                    title = stringResource(id = R.string.setTemmp),
                    minValue = 90,
                    maxValue = 230,
                    currentTemperature = ovenUi.Oventempeture,
                    onTemperatureChange = { value ->
                        if (value != ovenUi.Oventempeture) {
                            ovenViewModel.setTemp(value)
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "$changedTemperature $value°C"
                                )
                            }
                        }
                    },
                    unit = "°C"
                )

                Spacer(modifier = Modifier.height(16.dp))

                val Gmodes = listOf(
                    stringResource(id = R.string.complete),
                    stringResource(id = R.string.econmic),
                    stringResource(id = R.string.offf)
                )


                val translateGrill = when (ovenUi.selectedGrillMode) {
                    "complete" -> stringResource(id = R.string.complete)
                    "economic" -> stringResource(id = R.string.econmic)
                    "off" -> stringResource(id = R.string.offf)
                    else -> ""
                }

                ModeSelector(
                    title = stringResource(id = R.string.grillMode),
                    selectedMode = translateGrill,
                    modes = Gmodes,
                    onModeSelected = { mode ->
                        if (mode != translateGrill) {
                            when (mode) {
                                Gmodes[0] -> {
                                    ovenViewModel.changeGrillMode("complete")
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "$changedGrillMode $complete"
                                        )
                                    }
                                }

                                Gmodes[1] -> {
                                    ovenViewModel.changeGrillMode("economic")
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "$changedGrillMode $economic"
                                        )
                                    }
                                }

                                Gmodes[2] -> {
                                    ovenViewModel.changeGrillMode("off")
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "$changedGrillMode $off"
                                        )
                                    }
                                }

                                else -> {
                                    // Handle other cases
                                }
                            }
                        }

                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                val Cmodes = listOf(
                    stringResource(id = R.string.normal),
                    stringResource(id = R.string.econmic),
                    stringResource(id = R.string.offf)
                )

                val transalteConv = when (ovenUi.selectedConvMode) {
                    "normal" -> stringResource(id = R.string.normal)
                    "economic" -> stringResource(id = R.string.econmic)
                    "off" -> stringResource(id = R.string.offf)
                    else -> ""
                }

                ModeSelector(
                    title = stringResource(id = R.string.convMode),
                    selectedMode = transalteConv,
                    modes = Cmodes,
                    onModeSelected = { mode ->
                        if (mode != transalteConv) {
                            when (mode) {
                                Cmodes[0] -> {
                                    ovenViewModel.changeConvMode("normal")
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "$changedConvectionMode $normal"
                                        )
                                    }
                                }

                                Cmodes[1] -> {
                                    ovenViewModel.changeConvMode("economic")
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "$changedConvectionMode $economic"
                                        )
                                    }
                                }

                                Cmodes[2] -> {
                                    ovenViewModel.changeConvMode("off")
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "$changedConvectionMode $off"
                                        )
                                    }
                                }

                                else -> {
                                    // Handle other cases
                                }
                            }

                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                val Hmodes = listOf(
                    stringResource(id = R.string.conventional),
                    stringResource(id = R.string.top),
                    stringResource(id = R.string.bottom)
                )

                val translateHeat = when (ovenUi.selectedHeatMode) {
                    "conventional" -> stringResource(id = R.string.conventional)
                    "top" -> stringResource(id = R.string.top)
                    "bottom" -> stringResource(id = R.string.bottom)
                    else -> ""
                }

                ModeSelector(
                    title = stringResource(id = R.string.heatMode),
                    selectedMode = translateHeat,
                    modes = Hmodes,
                    onModeSelected = { mode ->
                        if (mode != translateHeat) {
                            when (mode) {
                                Hmodes[0] -> {
                                    ovenViewModel.changeHeatMode("conventional")
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "$changedHeatMode $conventional"
                                        )
                                    }
                                }

                                Hmodes[1] -> {
                                    ovenViewModel.changeHeatMode("top")
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "$changedHeatMode $top"
                                        )
                                    }
                                }

                                Hmodes[2] -> {
                                    ovenViewModel.changeHeatMode("bottom")
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "$changedHeatMode $bottom"
                                        )
                                    }
                                }

                                else -> {
                                    // Handle other cases
                                }
                            }

                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))


            }
        }
    }
}


@Composable
fun ovenscreenPrev() {
    MyApplicationTheme {
        ovenScreen(id="2")
    }
}
