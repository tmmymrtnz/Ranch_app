package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource




@Composable
fun DevicesScreen(
    modifier: Modifier = Modifier
) {
    val cardList = listOf(
        CardItem(stringResource(id = R.string.fridge), painterResource(id = R.drawable.fridge)),
        CardItem(stringResource(id = R.string.speaker), painterResource(id = R.drawable.speaker)),
        CardItem(stringResource(id = R.string.stove), painterResource(id = R.drawable.stove)),
        CardItem(stringResource(id = R.string.lightBulb), painterResource(id = R.drawable.lightbulb)),
        CardItem(stringResource(id = R.string.curtain), painterResource(id = R.drawable.curtains))
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ){
        Column {
            Text(text = "My Devices", style = MaterialTheme.typography.headlineMedium, color = Color.White, modifier = Modifier.padding(16.dp))
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 170.dp)
            ){
                items(cardList.size) { index ->
                    RoundedCardComponent(title = cardList[index].title, icon = cardList[index].icon)
                }
            }
        }

    }
}


