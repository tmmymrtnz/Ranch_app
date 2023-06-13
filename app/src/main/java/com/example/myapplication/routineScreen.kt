package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
@Preview
fun RoutinesScreen(
    modifier: Modifier = Modifier,
    routineViewModel:RoutineViewModel=viewModel()
) {
    val routineUi by routineViewModel.uiState.collectAsState()
    val routines = listOf(
        Routine(
            title = "Rutina 1",
            rooms = mapOf(
                "Sala" to Room(listOf(Device("Luz", "Encender"), Device("TV", "Encender"))),
                "Dormitorio" to Room(listOf(Device("Luz", "Apagar"), Device("Aire Acondicionado", "Encender")))
            )
        ),
        Routine(
            title = "Rutina 2",
            rooms = mapOf(
                "Cocina" to Room(listOf(Device("Horno", "Prender"), Device("Lavaplatos", "Encender")))
            )
        )
    )
    MyApplicationTheme {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ){
        Column {
            Text(text = "My Routines", style = MaterialTheme.typography.headlineMedium, color = Color.White, modifier = Modifier.padding(16.dp))
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 170.dp)
            ){
                items(routines.size) { index ->
                    RoutineCardComponent(
                        routine = routines[index],
                        modifier = modifier
                    )
                }
            }
        }

    }
    }
}

@Composable
fun RoutineCardComponent(
    routine: Routine,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(10.dp)
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = routine.title,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = { /* Acción de reproducción */ },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.play_circle),
                        contentDescription = "Play Button",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            routine.rooms.forEach { (roomName, room) ->
                Text(
                    text = roomName,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                room.devices.forEach { device ->
                    Text(
                        text = "- ${device.name} ${device.action}",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.White
                        ),
                        modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
                    )
                }
            }
        }
    }
}


