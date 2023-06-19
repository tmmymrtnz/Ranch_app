package com.example.myapplication.speaker

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.myapplication.data.network.RetrofitClient
import com.example.myapplication.data.network.model.Device
import com.example.myapplication.data.network.model.Song
import com.example.myapplication.data.network.model.SongAux
import com.google.gson.Gson
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SpeakerViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(SpeakerUiState())
    val uiState: StateFlow<SpeakerUiState> = _uiState.asStateFlow()
    private var fetchJob: Job? = null


    private var speakerId: String = "no id"
    fun setId(id: String) {
        speakerId = id
    }

    fun fetchADevice(id: String) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            viewModelScope.launch {
                runCatching {
                    RetrofitClient.getApiService()?.getADevice(id)
                        ?: throw Exception("API Service is null")
                }.onSuccess { response ->
                    val state = response.body()?.result?.state
                    val songResponse = state?.song

                    val currentSong = if (state?.status == "playing" && songResponse != null) {
                        val durationInSeconds = convertTimeToSeconds(songResponse.duration)
                        val progressInSeconds = convertTimeToSeconds(songResponse.progress)

                        Song(
                            title = songResponse.title ?: "",
                            duration = durationInSeconds,
                            artist = songResponse.artist ?: "",
                            album = songResponse.album ?: "",
                            progress = progressInSeconds
                        )
                    } else {
                        null
                    }

                    _uiState.update {
                        it.copy(
                            device = response.body(),
                            state = state?.status ?: "",
                            currentSong = currentSong,
                            currentGenre = state?.genre ?: "",
                            isLoading = false
                        )
                    }
                }.onFailure { e ->
                    _uiState.update {
                        it.copy(
                            message = e.message,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }



    fun doAction(id: String, actionName: String, actionParams: List<String>?) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            viewModelScope.launch {
                runCatching {
                    RetrofitClient.getApiService()?.makeAction(id, actionName, actionParams)
                        ?: throw Exception("API Service is null")
                }.onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            device = response.body(),
                            isLoading = false
                        )
                    }
                }.onFailure { e ->
                    _uiState.update {
                        it.copy(
                            message = e.message,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    suspend fun getPlaylistAction(id: String) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            viewModelScope.launch {
                runCatching {
                    RetrofitClient.getApiService()?.getPlaylist(id, "getPlaylist")
                        ?: throw Exception("API Service is null")
                }.onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            playlistAux = response.body(),
                            isLoading = false
                        )
                    }
                }.onFailure { e ->
                    _uiState.update {
                        it.copy(
                            message = e.message,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    suspend fun getPlayList(id: String) {
        getPlaylistAction(id)
        val playlistAux = uiState.value.playlistAux
        val playlist = ArrayList<Song>()
        playlistAux?.forEach { songAux ->
            val song = Song(
                title = songAux.title,
                artist = songAux.artist,
                album = songAux.album,
                duration = songAux.duration?.toInt(),
                progress = songAux.progress?.toInt()
            )
            playlist.add(song)
        }
        _uiState.update { it.copy(playlist = playlist) }
    }


    fun doActionInt(id: String,actionName: String, actionParams: List<Int>?){
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            viewModelScope.launch {
                runCatching {
                    RetrofitClient.getApiService()?.makeActionInt(id,actionName,actionParams)
                        ?: throw Exception("API Service is null")
                }.onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            device = response.body(),
                            isLoading = false
                        )
                    }
                }.onFailure { e ->
                    _uiState.update {
                        it.copy(
                            message = e.message,
                            isLoading = false
                        )
                    }
                }

            }

        }
    }



    fun setVolume(id: String, volume: Int) {
        val actionName = "setVolume"
        val actionParams = listOf(volume)
        doActionInt(id, actionName, actionParams)
    }

    fun play(id: String) {
        val actionName = "play"
        doAction(id, actionName, null)
    }

    fun stop(id: String) {
        val actionName = "stop"
        doAction(id, actionName, null)
    }

    fun pause(id: String) {
        val actionName = "pause"
        doAction(id, actionName, null)
    }

    fun resume(id: String) {
        val actionName = "resume"
        doAction(id, actionName, null)
    }

    fun nextSong(id: String) {
        val actionName = "nextSong"
        doAction(id, actionName, null)
    }

    fun previousSong(id: String) {
        val actionName = "previousSong"
        doAction(id, actionName, null)
    }

    fun setGenre(id: String, genre: String) {
        val actionName = "setGenre"
        val actionParams = listOf(genre)
        doAction(id, actionName, actionParams)
    }




    private fun convertTimeToSeconds(time: String?): Int {
        if (time.isNullOrEmpty()) return 0

        val parts = time.split(":")
        if (parts.size != 2) return 0

        val minutes = parts[0].toIntOrNull()
        val seconds = parts[1].toIntOrNull()

        return if (minutes != null && seconds != null) {
            minutes * 60 + seconds
        } else {
            0
        }
    }

    fun setGenre(mode: String) {

    }


}