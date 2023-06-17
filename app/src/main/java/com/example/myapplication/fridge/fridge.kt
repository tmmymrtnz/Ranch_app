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
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.fridge.FridgeViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun fridgeScreen(id: String ,fridgeViewModel: FridgeViewModel = viewModel()) {

    val fridgeUi by fridgeViewModel.uiState.collectAsState()

    fridgeViewModel.setId(id)


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
                    painter = painterResource(R.drawable.fridge),
                    contentDescription = null,
                    modifier = Modifier.size(88.dp)
                )
                Text(
                    text = stringResource(id = R.string.fridge),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Color.White)

            Spacer(modifier = Modifier.height(16.dp))

            val modes = listOf(
                stringResource(id = R.string.defaul),
                stringResource(id = R.string.vacation),
                stringResource(id = R.string.party))

            ModeSelector(
                title = stringResource(id = R.string.Fmode),
                selectedMode = fridgeUi.selectedFridgeMode,
                modes = modes,
                onModeSelected = { mode ->
                    if(mode !=fridgeUi.selectedFridgeMode ){
                        fridgeViewModel.changeMode(mode)
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
                    }
                },
                unit = "°C"
            )

        }
    }
}


@Preview
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
                color = Color.White,
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
    val textColor = if (isSelected) Color.White else Color.Gray

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
                color = Color.White,
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
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(
                text = "$currentTemperature$unit",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}


