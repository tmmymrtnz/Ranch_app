package com.example.myapplication.blind

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.TemperatureSlider
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun BlindScreen(id: String, blindViewModel: BlindViewModel = viewModel()) {

    val blindUi by blindViewModel.uiState.collectAsState()
    blindViewModel.setId(id)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.curtains),
                    contentDescription = null,
                    modifier = Modifier.size(88.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                )
                Text(
                    text = blindUi.device?.result?.name.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.scrim,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = MaterialTheme.colorScheme.secondary)

            SwitchWithLabelsTogable(
                checked = blindUi.blindOn,
                onCheckedChange = { checked ->
                    blindViewModel.switchBlind(checked)
                },
                labelOn = stringResource(id = R.string.openLabel),
                labelOff = stringResource(id = R.string.closeLabel),
                blindUi = blindUi
            )

            Spacer(modifier = Modifier.height(16.dp))

            TemperatureSlider(
                title =  stringResource(id = R.string.blindSliderTitle),
                minValue = 0,
                maxValue = 100,
                currentTemperature = blindUi.blindLevel,
                onTemperatureChange = { value ->
                    if(value != blindUi.blindLevel){
                        blindViewModel.setLevel(value)
                    }
                },
                unit = "%"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Add your blinds controls here, such as buttons and sliders

        }
    }
}


@Composable
fun SwitchWithLabelsTogable(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    labelOn: String,
    labelOff: String,
    blindUi: BlindUiState
) {
    val changingState = blindUi.device?.result?.state?.status.toString().equals("opened") || blindUi.device?.result?.state?.status.toString().equals("closed")
    val textColor = if (changingState) {
        MaterialTheme.colorScheme.scrim
    } else {
        Color.DarkGray
    }

    val statusText = when (blindUi.device?.result?.state?.status.toString()) {
        "opened" -> stringResource(id = R.string.opened)
        "closed" -> stringResource(id = R.string.closed)
        "opening" -> stringResource(id = R.string.opening)
        "closing" -> stringResource(id = R.string.closing)
        else -> ""
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(text = labelOff, color = textColor)
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(vertical = 8.dp),
            enabled = changingState
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = labelOn, color = textColor)

        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .height(32.dp)
                .background(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = MaterialTheme.shapes.small
                )
        ) {
            Text(
                text = statusText,
                color = MaterialTheme.colorScheme.scrim,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}


@Preview
@Composable
fun BlindPreview() {
    MyApplicationTheme {
        BlindScreen("aaa")
    }
}