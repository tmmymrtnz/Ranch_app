package com.example.myapplication.speaker


import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Slider

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.myapplication.R
import com.example.myapplication.data.network.model.Song
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch


@Composable
fun speakerScreen(id: String, speakerViewModel: SpeakerViewModel = viewModel()) {
    val speakerUi by speakerViewModel.uiState.collectAsState()
    speakerViewModel.setId(id)
    Log.d("status",speakerUi.status.toString())
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .padding(2.dp)
                .verticalScroll(rememberScrollState())
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.speaker),
                    contentDescription = null,
                    modifier = Modifier.size(88.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                )
                Text(
                    text = speakerUi.name.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.scrim,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = MaterialTheme.colorScheme.secondary)

            SpeakerCard(speakerUi = speakerUi, speakerViewModel = speakerViewModel,
            )

            GenreSelector(
                selectedGenre = speakerUi.currentGenre,
                genres = speakerUi.genres,
                onModeSelected = { genre ->
                    speakerViewModel.setGenre(genre)
                },
                speakerUi = speakerUi
            )

            PlaylistCard(playlist = speakerUi.playlist ?: emptyList())
            // Otros componentes o contenido aquí si es necesario
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
            .height(150.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(painter = painterResource(R.drawable.baseline_queue_music_white_24dp),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(24.dp))
            Text(
                text = stringResource(R.string.Playlist),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.scrim,
            )
        }

        Column(
           modifier = Modifier
               .padding(2.dp)
               .verticalScroll(rememberScrollState())
        ) {
            playlist.forEach{song ->
                SongItem(song)

            }
        }
    }
}

@Composable
fun SongItem(song: Song) {
    Column(
        modifier = Modifier.padding(5.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(painter = painterResource(R.drawable.baseline_music_note_white_24dp),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(24.dp))
            Text(text = song.title ?: "", style = MaterialTheme.typography.headlineSmall)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(painter = painterResource(R.drawable.baseline_person_white_24dp),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(24.dp))
            Text(text = song.artist ?: "", style = MaterialTheme.typography.bodySmall)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(painter = painterResource(R.drawable.baseline_album_white_24dp),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(24.dp))
            Text(text = (song.album + " - ") , style = MaterialTheme.typography.bodySmall)
            Text(text = formatTime(song.duration ?: 0), style = MaterialTheme.typography.bodySmall)
        }


    }
}



@Composable
fun SpeakerCard(speakerUi: SpeakerUiState, speakerViewModel: SpeakerViewModel) {
    Column(modifier = Modifier.padding(16.dp) ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(painter = painterResource(R.drawable.baseline_music_note_white_24dp),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(24.dp))
            Text(text = speakerUi.currentSong?.title ?: "No song playing", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            if(speakerUi.status != "stopped"){ Icon(painter = painterResource(R.drawable.baseline_person_white_24dp),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(24.dp))
            Text(text = speakerUi.currentSong?.artist ?: "", fontSize = 16.sp)}
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            if(speakerUi.status != "stopped"){
                Icon(painter = painterResource(R.drawable.baseline_album_white_24dp),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(24.dp))
                Text(text = speakerUi.currentSong?.album +" - " , fontSize = 16.sp)
                Text(text = formatTime(speakerUi.currentSong?.duration ?: 0), fontSize = 16.sp, style = MaterialTheme.typography.bodySmall)

            }
             }

        // Estado (playing o paused)
        Text(text = speakerUi.status ?: "", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(8.dp))

        // Barra de reproducción
        val progress = speakerUi.currentSong?.progress ?: 0
        val duration = speakerUi.currentSong?.duration ?: 0
        val progressText = formatTime(progress)
        val durationText = formatTime(duration)
        val progressPercentage = if (duration != 0) (progress.toFloat() / duration.toFloat()) else 0f

        LinearProgressIndicator(progress = progressPercentage, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = progressText, fontSize = 14.sp)
            Text(text = durationText, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Botones
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {

            IconButton(
                onClick = { speakerViewModel.previousSong() },
                enabled = speakerUi.status != "stopped",
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_fast_rewind_white_24dp),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(24.dp)
                )
            }

            IconButton(
                onClick = {
                    if (speakerUi.status == "playing") {
                        speakerViewModel.pause()
                    } else if (speakerUi.status == "paused") {
                        speakerViewModel.resume()
                    }
                },
                enabled = speakerUi.status != "stopped",
                modifier = Modifier.size(40.dp)
            ) {
                Icon(

                    painter = if (speakerUi.status == "playing") painterResource(R.drawable.baseline_pause_white_24dp) else painterResource(R.drawable.baseline_play_arrow_white_24dp),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(24.dp)
                )
            }

            IconButton(
                onClick = { speakerViewModel.nextSong() },
                enabled = speakerUi.status != "stopped",
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_fast_forward_white_24dp),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(24.dp)
                )
            }

            IconButton(
                onClick = { if (speakerUi.status != "stopped") {
                    speakerViewModel.stop()
                } else  {
                    speakerViewModel.play()
                }
                },
                enabled = true,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(

                    painter =  painterResource(R.drawable.baseline_stop_white_24dp),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(24.dp)
                )
            }

        }


        Spacer(modifier = Modifier.height(8.dp))

        // Barra de volumen

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {


            IconButton(
                onClick = { speakerViewModel.setVolume(speakerUi.volume?.minus(1) ?: 0 ) },
                enabled = speakerUi.status != "stopped",
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_volume_down_white_24dp),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(24.dp)
                )
            }
            Slider(
                value = speakerUi.volume?.toFloat() ?: 0f,
                onValueChange = { value ->
                    speakerViewModel.setVolume(value.toInt())
                },
                valueRange = 0f..10f,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick =  { speakerViewModel.setVolume(speakerUi.volume?.plus(1) ?: 10) },
                    enabled = speakerUi.status != "stopped",
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_volume_up_white_24dp),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(24.dp)
                )
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
fun GenreSelector(speakerUi: SpeakerUiState,
    selectedGenre: String?,
    genres: List<String>,
    onModeSelected: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.tertiary, RoundedCornerShape(20.dp))
            .height(200.dp)
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
                        onModeSelected = { onModeSelected(it) },
                        speakerUi = speakerUi
                    )
                }
            }
        }
    }
}



@Composable
fun GenreButton(
    speakerUi: SpeakerUiState,
    genre: String,
    isSelected: Boolean,
    onModeSelected: (String) -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
    val textColor = if (isSelected) MaterialTheme.colorScheme.scrim else Color.Gray

    Button(
        enabled = speakerUi.status=="stopped",
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
    MyApplicationTheme() {
        speakerScreen(id="1a13c0ffa7537e06")
    }
}