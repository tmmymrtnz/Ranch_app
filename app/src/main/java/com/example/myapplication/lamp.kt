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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun LightBulbScreen(lightViewModel: LightViewModel = viewModel()) {

    val lightUi by lightViewModel.uiState.collectAsState()

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
                    painter = painterResource(R.drawable.lightbulb),
                    contentDescription = null,
                    modifier = Modifier.size(88.dp)
                )
                Text(
                    text = stringResource(id = R.string.lightBulb),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Color.White)
            SwitchWithLabels(
                checked = lightUi.lightOn,
                onCheckedChange = { checked ->
                   lightViewModel.switchLight(checked)
                },
                labelOn = "On",
                labelOff = "Off"
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Color Picker
            ColorPicker(initialColor = lightUi.lightColor){
                color ->  lightViewModel.changeColor(color)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Brightness Slider
            SetBrightness(
                lightUi.brightness,
                onBrightnessChange = { value -> lightViewModel.setBrightness(value) }
            )
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
        Text(text = labelOff, color = Color.White)
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = labelOn, color = Color.White)
    }
}



@Preview
@Composable
fun PreviewSwitchWithLabels() {
    MyApplicationTheme {
        LightBulbScreen()
    }
}




@Composable
fun SetBrightness(
    brightness: Float,
    onBrightnessChange: (Float) -> Unit
) {
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.tertiary, RoundedCornerShape(20.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Set Brightness",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            LabeledSliderBrightness(
                label = "Brightness Level",
                value = brightness,
                onValueChange = { value ->
                    onBrightnessChange(value)
                }
            )
            Text(
                text = "${(brightness * 100).toInt()}%",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
        }
    }

}



@Composable
fun LabeledSliderBrightness(label: String, value: Float, onValueChange: (Float) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            modifier = Modifier.width(100.dp),
            textAlign = TextAlign.Center
        )
        Slider(
            value = value,
            onValueChange = { onValueChange(it) },
            valueRange = 0f..1f,
            steps = 100,
            modifier = Modifier.weight(1f)
        )
    }
}




@Composable
fun ColorPicker(initialColor: Color, onColorChanged: (Color) -> Unit) {
    var color by remember { mutableStateOf(initialColor) }

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
                text = "Pick color",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
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
            LabeledSlider("Red", red.toFloat(), 0f, 255f) { value ->
                color = Color(value.toInt() / 255f, green / 255f, blue / 255f)
                onColorChanged(color) // Call the provided function with the updated color
            }
            LabeledSlider("Green", green.toFloat(), 0f, 255f) { value ->
                color = Color(red / 255f, value.toInt() / 255f, blue / 255f)
                onColorChanged(color) // Call the provided function with the updated color
            }
            LabeledSlider("Blue", blue.toFloat(), 0f, 255f) { value ->
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
            color = Color.White
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


