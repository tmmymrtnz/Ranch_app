package com.example.myapplication.speaker

import com.example.myapplication.R
import com.example.myapplication.data.network.model.Device
import com.example.myapplication.data.network.model.Song
import com.example.myapplication.data.network.model.SongAux


data class SpeakerUiState(
    val playlist: ArrayList<Song>? = null,
    val playlistAux: ArrayList<SongAux>? = null,
    val id: String? = null,
    val state: String? = null,
    val message: String? = null,
    val currentSong: Song? = null,
    val currentGenre: String? = null,
    val isLoading: Boolean = false,
    val device: Device? = null,
    val volume: Int? = null,
    val genres: List<String> = listOf(
        "classical",
        "country",
        "dance",
        "latina",
        "pop",
        "rock"
    ),
    val actions: List<String> = listOf(
        "setVolume",
        "play",
        "stop",
        "pause",
        "resume",
        "nextSong",
        "previousSong",
        "setGenre",
        "getPlaylist"
    )
)

data class SpeakerIcons(
    val speaker: Int = R.drawable.speaker,
    val prev: Int = R.drawable.ic_previous,
    val play: Int = R.drawable.ic_play,
    val next: Int = R.drawable.ic_next,

)

