package com.example.myapplication.speaker


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Slider

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.myapplication.R
import com.example.myapplication.data.network.model.Song
import com.example.myapplication.ui.theme.MyApplicationTheme


@Composable
fun speakerScreen(id: String, speakerViewModel: SpeakerViewModel = viewModel()) {
    val speakerUi by speakerViewModel.uiState.collectAsState()
    speakerViewModel.setId(id)
    Log.d("nameeee",speakerUi.name.toString())
    Log.d("genre",speakerUi.currentGenre.toString())
    speakerUi.playlist
    for (i in 0 until (speakerUi.playlist?.size?.toInt() ?: 0)) {
        Log.d("gsong", speakerUi.playlist?.get(i)?.title.toString())
    }
    MyApplicationTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(16.dp)

        ) {
            Column {
                Text(
                    text = speakerUi.name.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.scrim,
                    modifier = Modifier.padding(16.dp)
                )

                SpeakerCard(
                    speakerUi = speakerUi, speakerViewModel=speakerViewModel,

                )

                GenreSelector(
                    selectedGenre = speakerUi.currentGenre,
                    genres = speakerUi.genres,
                    onModeSelected = { mode ->
                        speakerViewModel.setGenre(mode)
                    }
                )


                PlaylistCard(playlist = speakerUi.playlist ?: emptyList())
                // Otros componentes o contenido aquí si es necesario
            }
        }
    }
}


@Composable
fun PlaylistCard(playlist: List<Song>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = "Playlist",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp),
            color = MaterialTheme.colorScheme.scrim,
        )
        LazyColumn {
            items(playlist.size) { song ->
                SongItem(playlist[song])
            }
        }
    }
}

@Composable
fun SongItem(song: Song) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(text = song.title ?: "", style = MaterialTheme.typography.headlineSmall)
        Text(text = song.artist ?: "", style = MaterialTheme.typography.displayMedium)
        Text(text = song.album ?: "", style = MaterialTheme.typography.displayMedium)
        Text(text = formatTime(song.duration ?: 0), style = MaterialTheme.typography.displayMedium)
    }
}



@Composable
fun SpeakerCard(speakerUi: SpeakerUiState, speakerViewModel: SpeakerViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Título de la canción
        Text(text = speakerUi.currentSong?.title ?: "No song playing", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        // Nombre del artista y álbum
        Text(text = speakerUi.currentSong?.artist ?: "", fontSize = 16.sp)
        Text(text = speakerUi.currentSong?.album ?: "", fontSize = 16.sp)

        // Estado (playing o paused)
        Text(text = speakerUi.status ?: "", fontSize = 16.sp)

        // Barra de reproducción
        val progress = speakerUi.currentSong?.progress ?: 0
        val duration = speakerUi.currentSong?.duration ?: 0
        val progressText = formatTime(progress)
        val durationText = formatTime(duration)
        val progressPercentage = if (duration != 0) (progress.toFloat() / duration.toFloat()) else 0f

        LinearProgressIndicator(progress = progressPercentage, modifier = Modifier.fillMaxWidth())

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = progressText, fontSize = 14.sp)
            Text(text = durationText, fontSize = 14.sp)
        }

        // Botones
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Button(onClick = { speakerViewModel.previousSong(speakerUi.id.toString()) }, enabled = speakerUi.currentSong != null) {
                Text(text = "Previous")
            }

            Button(onClick = { if (speakerUi.status == "playing"){
                speakerViewModel.pause(speakerUi.id.toString())
            }else{speakerViewModel.play(speakerUi.id.toString())} }, enabled = speakerUi.currentSong != null) {
                Text(text = if (speakerUi.status == "playing") "Pause" else "Play")
            }

            Button(onClick = {  speakerViewModel.nextSong(speakerUi.id.toString()) }, enabled = speakerUi.currentSong != null) {
                Text(text = "Next")
            }

            Button(onClick = { speakerViewModel.stop(speakerUi.id.toString()) }) {
                Text(text = "Stop")
            }
        }

        // Selector de opciones
        // ...

        // Barra de volumen
        val volume = speakerUi.volume ?: 0

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = {  }) {
                Text(text = "-")
            }

            Slider(value = volume.toFloat(), onValueChange = {  }, modifier = Modifier.weight(1f))

            Button(onClick = {  }) {
                Text(text = "+")
            }
        }
    }
}

@Composable
fun formatTime(timeInSeconds: Int): String {
    val minutes = timeInSeconds / 60
    val seconds = timeInSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}


@Composable
fun GenreSelector(
    selectedGenre: String?,
    genres: List<String>,
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
                text = stringResource(R.string.GenreSelector),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.scrim,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
            ) {
                items(genres.size) { genre ->
                    GenreButton(
                        genre = genres[genre],
                        isSelected = genres[genre] == selectedGenre,
                        onModeSelected = { onModeSelected(it) }
                    )
                }
            }
        }
    }
}



@Composable
fun GenreButton(
    genre: String,
    isSelected: Boolean,
    onModeSelected: (String) -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
    val textColor = if (isSelected) MaterialTheme.colorScheme.scrim else Color.Gray

    Button(
        onClick = { onModeSelected(genre) },
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = genre,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    }
}





@Preview
@Composable
fun speakerScreenPrev() {
    speakerScreen(id="1a13c0ffa7537e06")
}