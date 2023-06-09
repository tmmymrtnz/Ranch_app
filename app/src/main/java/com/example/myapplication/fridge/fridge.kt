package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.fridge.FridgeViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

@Composable
fun fridgeScreen(id: String ,fridgeViewModel: FridgeViewModel = viewModel()) {

    val fridgeUi by fridgeViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    fridgeViewModel.setId(id)

    val defaultMode = stringResource(id = R.string.defaultMode)
    val vacationMode = stringResource(id = R.string.vacationMode)
    val partyMode = stringResource(id = R.string.partyMode)
    val changedTemp = stringResource(id = R.string.changedTemp)
    val changedFreezerTemp = stringResource(id = R.string.changedTempF)

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
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
                        painter = painterResource(R.drawable.fridge),
                        contentDescription = null,
                        modifier = Modifier.size(88.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                    )
                    Text(
                        text = fridgeUi.device?.result?.name.toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.scrim,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = MaterialTheme.colorScheme.secondary)

                Spacer(modifier = Modifier.height(16.dp))

                val modes = listOf(
                    stringResource(id = R.string.defaul),
                    stringResource(id = R.string.vacation),
                    stringResource(id = R.string.party))

                val transalteModes= when(fridgeUi.selectedFridgeMode){
                    "default"-> stringResource(id = R.string.defaul)
                    "vacation"->stringResource(id = R.string.vacation)
                    "party"-> stringResource(id = R.string.party)
                    else->""
                }

                ModeSelector(
                    title = stringResource(id = R.string.Fmode),
                    selectedMode = transalteModes,
                    modes = modes,
                    onModeSelected = { mode ->
                        if(mode !=transalteModes ){
                            when (mode) {
                                modes[0] -> {
                                    fridgeViewModel.changeMode("default")
                                    scope.launch {
                                        snackbarHostState.showSnackbar(defaultMode)
                                    }
                                }
                                modes[1] -> {
                                    fridgeViewModel.changeMode("vacation")
                                    scope.launch {
                                        snackbarHostState.showSnackbar(vacationMode)
                                    }
                                }
                                modes[2]-> {
                                    fridgeViewModel.changeMode("party")
                                    scope.launch {
                                        snackbarHostState.showSnackbar(partyMode)
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

                TemperatureSlider(
                    title = stringResource(id = R.string.setTemmp),
                    minValue = 2,
                    maxValue = 8,
                    currentTemperature = fridgeUi.fridgeTemp,
                    onTemperatureChange = { value ->
                        if(value != fridgeUi.fridgeTemp){
                            fridgeViewModel.setFridgeTemp(value)
                            scope.launch {
                                snackbarHostState.showSnackbar("$changedTemp $value°C")
                            }
                        }
                    },
                    unit = "°C"
                )

                Spacer(modifier = Modifier.height(16.dp))

                TemperatureSlider(
                    title =  stringResource(id = R.string.setFTemmp),
                    minValue = -20,
                    maxValue = -8,
                    currentTemperature = fridgeUi.freezerTemp,
                    onTemperatureChange = { value ->
                        if(value != fridgeUi.fridgeTemp){
                            fridgeViewModel.setFreezerTemp(value)
                            scope.launch {
                                snackbarHostState.showSnackbar("$changedFreezerTemp $value°C")
                            }
                        }
                    },
                    unit = "°C"
                )

            }
        }
    }

}



@Composable
fun fridescreenPrev() {
    MyApplicationTheme {
        fridgeScreen("XD")
    }
}

@Composable
fun ModeSelector(
    title: String,
    selectedMode: String,
    modes: List<String>,
    onModeSelected: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.tertiary, RoundedCornerShape(20.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.scrim,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                modes.forEach { mode ->
                    FridgeModeButton(
                        mode = mode,
                        isSelected = mode == selectedMode,
                        onModeSelected = { onModeSelected(it) }
                    )
                }
            }
        }
    }
}


@Composable
fun FridgeModeButton(
    mode: String,
    isSelected: Boolean,
    onModeSelected: (String) -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
    val textColor = if (isSelected) MaterialTheme.colorScheme.scrim else Color.Gray

    Button(
        onClick = { onModeSelected(mode) },
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = mode,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    }
}



@Composable
fun TemperatureSlider(
    title: String,
    minValue: Int,
    maxValue: Int,
    currentTemperature: Int,
    onTemperatureChange: (Int) -> Unit,
    unit: String
) {
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.tertiary, RoundedCornerShape(20.dp))
            .padding(10.dp)
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.scrim,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Slider(
                value = currentTemperature.toFloat(),
                onValueChange = { value ->
                    onTemperatureChange(value.toInt())
                },
                valueRange = minValue.toFloat()..maxValue.toFloat(),
                onValueChangeFinished = null,
                steps = 100,
                modifier = Modifier.padding(horizontal = 16.dp),

            )
            Text(
                text = "$currentTemperature$unit",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.scrim,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}


