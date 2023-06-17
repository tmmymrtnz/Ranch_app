package com.example.myapplication.devices

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.RoundedCardComponent
import com.example.myapplication.deviceTypes.DeviceTypesViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun DevicesScreen(
    modifier: Modifier = Modifier,
    viewModel: DeviceViewModel = viewModel(),
    typesViewModel:  DeviceTypesViewModel = viewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    val typesUiState by typesViewModel.uiState.collectAsState()

    LaunchedEffect(Unit){
        viewModel.fetchDevices()
        typesViewModel.fetchDeviceTypes()
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(uiState.isLoading),
        onRefresh = { viewModel.fetchDevices() }
    ) {

        Column (
            modifier = modifier
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.devices),
                    contentDescription = null,
                    modifier = Modifier.size(88.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                )
                Text(
                    text = stringResource(id = R.string.mydevices),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(8.dp))

            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ){
                if (uiState.isLoading || typesUiState.isLoading)
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                    ) {
                        Text(
                            text = "Loading...",
                            fontSize = 16.sp
                        )
                    }
                else {
                    val list = uiState.devices?.result.orEmpty()
                    val typesList = typesUiState.deviceTypes?.result.orEmpty()
                    println(list)
                    println(typesList)
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 170.dp),
                        modifier = Modifier.padding(6.dp)
                    ){
                        items(
                            count = list.size,
                            key = { index -> list[index].id.toString() }
                        ) {
                            index ->  RoundedCardComponent(
                            title = list[index].name.toString(),
                            icon = painterResource(
                            id = when (list[index].type?.name.toString()) {
                                "fridge" -> R.drawable.fridge
                                "speaker" -> R.drawable.speaker
                                "oven" -> R.drawable.stove
                                "lamp" -> R.drawable.lightbulb
                                "blinds" -> R.drawable.curtains
                                else -> R.drawable.fridge
                            }),
                            navController = navController,
                            modifier = Modifier,
                            result = list[index]
                        )
                        }


                    }
                }
            }
            IconButton(onClick = { viewModel.fetchDevices() }) {
                Icon(Icons.Default.Refresh, contentDescription = "Refresh")
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun DeviceScreen() {
    MyApplicationTheme {
        DevicesScreen(viewModel = viewModel(), navController = NavController(LocalContext.current))
    }
}


