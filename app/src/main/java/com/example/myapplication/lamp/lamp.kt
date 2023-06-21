package com.example.myapplication.lamp

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
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
import com.example.myapplication.R
import com.example.myapplication.TemperatureSlider
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LightBulbScreen(id : String ,lightViewModel: LightViewModel = viewModel()) {

    val lightUi by lightViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    lightViewModel.setId(id)

    val changedColor = stringResource(id = R.string.changed_color)
    val isOn = stringResource(id = R.string.is_on)
    val isOff = stringResource(id = R.string.is_off)
    val changedBrightness = stringResource(id = R.string.changed_brightness)

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
                        painter = painterResource(R.drawable.lightbulb),
                        contentDescription = null,
                        modifier = Modifier.size(88.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                    )
                    Text(
                        text = lightUi.device?.result?.name.toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.scrim,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = MaterialTheme.colorScheme.secondary)
                SwitchWithLabels(
                    checked = lightUi.lightOn,
                    onCheckedChange = { checked ->
                        lightViewModel.switchLight(checked)
                        scope.launch {
                            if (checked) {
                                snackbarHostState.showSnackbar("${lightUi.device?.result?.name} $isOn")
                            } else {
                                snackbarHostState.showSnackbar("${lightUi.device?.result?.name} $isOff")
                            }
                        }
                    },
                    labelOn = stringResource(id = R.string.onn),
                    labelOff = stringResource(id = R.string.offf)
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Color Picker
                ColorPicker(lightUi = lightUi){
                        color ->  lightViewModel.changeColor(color)
                    scope.launch {
                        snackbarHostState.showSnackbar(changedColor)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Brightness Slider
                TemperatureSlider(
                    title =  stringResource(id = R.string.bright),
                    minValue = 0,
                    maxValue = 100,
                    currentTemperature = lightUi.brightness,
                    onTemperatureChange = { value ->
                        if(value != lightUi.brightness){
                            lightViewModel.setBrightness(value)
                            scope.launch {
                                snackbarHostState.showSnackbar("$changedBrightness $value%")
                            }
                        }
                    },
                    unit = "%"
                )
            }
        }
    }

}




@Composable
fun SwitchWithLabels(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    labelOn: String,
    labelOff: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(text = labelOff, color = MaterialTheme.colorScheme.scrim)
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = labelOn, color = MaterialTheme.colorScheme.scrim)
    }
}
@Composable
fun RepeatedFunctionCall(intervalMillis: Long,lightViewModel: LightViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        while (true) {
            // Call your function here
            println("Function called at ${System.currentTimeMillis()}")
            delay(intervalMillis)
        }
    }
}


@Preview
@Composable
fun PreviewSwitchWithLabels() {
    MyApplicationTheme {
        LightBulbScreen("xd")
    }
}




@Composable
fun ColorPicker(lightUi: LightUiState, onColorChanged: (Color) -> Unit) {
    var color =Color(android.graphics.Color.parseColor(lightUi.lightColor))

    val red = remember(color) { color.red * 255 }.toInt()
    val green = remember(color) { color.green * 255 }.toInt()
    val blue = remember(color) { color.blue * 255 }.toInt()

    val selectedColor = remember(red, green, blue) {
        Color(red, green, blue)
    }

    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.tertiary, RoundedCornerShape(20.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.pickColor),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.scrim,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(color = selectedColor)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LabeledSlider(stringResource(id = R.string.r), red.toFloat(), 0f, 255f) { value ->
                color = Color(value.toInt() / 255f, green / 255f, blue / 255f)
                onColorChanged(color) // Call the provided function with the updated color
            }
            LabeledSlider(stringResource(id = R.string.g), green.toFloat(), 0f, 255f) { value ->
                color = Color(red / 255f, value.toInt() / 255f, blue / 255f)
                onColorChanged(color) // Call the provided function with the updated color
            }
            LabeledSlider(stringResource(id = R.string.b), blue.toFloat(), 0f, 255f) { value ->
                color = Color(red / 255f, green / 255f, value.toInt() / 255f)
                onColorChanged(color) // Call the provided function with the updated color
            }
        }
    }
}




@Composable
fun LabeledSlider(
    label: String,
    value: Float,
    minValue: Float,
    maxValue: Float,
    onValueChange: (Float) -> Unit
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.scrim
        )
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = minValue..maxValue,
            onValueChangeFinished = null,
            steps = 100,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

    }
}


