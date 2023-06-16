package com.example.myapplication.blind

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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.SwitchWithLabels
import com.example.myapplication.TemperatureSlider
import com.example.myapplication.blind.BlindViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun BlindScreen(id: String, blindViewModel: BlindViewModel = viewModel()) {

    val blindUi by blindViewModel.uiState.collectAsState()

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
                    modifier = Modifier.size(88.dp)
                )
                Text(
                    text = "Blind",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Color.White)

            SwitchWithLabels(
                checked = blindUi.blindOn,
                onCheckedChange = { checked ->
                    blindViewModel.switchBlind(checked)
                },
                labelOn = stringResource(id = R.string.on),
                labelOff = stringResource(id = R.string.off)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TemperatureSlider(
                title =  "Set level",
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


@Preview
@Composable
fun BlindPreview() {
    MyApplicationTheme {
        BlindScreen("aaa")
    }
}