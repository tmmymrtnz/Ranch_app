package com.example.myapplication.speaker

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.network.RetrofitClient
import com.example.myapplication.data.network.model.Song
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.log

class SpeakerViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(SpeakerUiState())
    val uiState: StateFlow<SpeakerUiState> = _uiState.asStateFlow()
    private var fetchJob: Job? = null


    private var speakerId: String = "no id"
    fun setId(id: String) {
        speakerId = id
    }


    init {
        polling()
    }

    private fun polling() {
        viewModelScope.launch {
            while (true) {
                delay(1000L) // Delay for 1 second
                fetchADevice(speakerId)

                getPlayList(speakerId)
            }
        }
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
                    val songResponse = response.body()?.result?.state?.song

                    val currentSong = if (response.body()?.result?.status != "stopped" && songResponse != null) {
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
                            id = response.body()?.result?.id,
                            name =  response.body()?.result?.name ,
                            currentGenre = response.body()?.result?.state?.genre,
                            status = response.body()?.result?.state?.status,
                            currentSong = currentSong,
                            isLoading = false
                        )
                    }
                    Log.d("AAAA",response.body()?.result?.toString()!!)
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




    fun doAction(id: String,actionName: String, actionParams: List<String>?){
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            viewModelScope.launch {
                runCatching {
                    RetrofitClient.getApiService()?.makeAction(id,actionName,actionParams)
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


    private fun getPlaylistAction(id: String) {
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

    fun getPlayList(id: String) {
        getPlaylistAction(id)
        val playlistAux = uiState.value.playlistAux
        val playlist = ArrayList<Song>()
        playlistAux?.result?.forEach { songAux ->
            val song = Song(
                title = songAux.title,
                artist = songAux.artist,
                album = songAux.album,
                duration = convertTimeToSeconds(songAux.duration),
                progress = convertTimeToSeconds(songAux.progress)
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


    fun setVolume(volume: Int) {
        val actionName = "setVolume"
        val actionParams = listOf(volume)
        doActionInt(speakerId, actionName, actionParams)
        _uiState.update { currentState ->
            currentState.copy(volume = actionParams[0])
        }
    }

    fun play() {
        val actionName = "play"
        doAction(speakerId, actionName, emptyList())
        if(_uiState.value.status == "stopped"){
            _uiState.update { currentState ->
                currentState.copy(status = "playing")
            }
        }
    }

    fun stop() {
        val actionName = "stop"
        doAction(speakerId, actionName, emptyList())
        if(_uiState.value.status != "stopped"){
            _uiState.update { currentState ->
                currentState.copy(status = "stopped")
            }
        }
    }

    fun pause() {
        val actionName = "pause"
        doAction(speakerId, actionName, emptyList())
        if(_uiState.value.status == "playing"){
            _uiState.update { currentState ->
                currentState.copy(status = "paused")
            }
        }
    }

    fun resume() {
        val actionName = "resume"
        doAction(speakerId, actionName, emptyList())
        if(_uiState.value.status == "paused"){
            _uiState.update { currentState ->
                currentState.copy(status = "playing")
            }
        }
    }

    fun nextSong() {
        val actionName = "nextSong"
        doAction(speakerId, actionName, emptyList())
        fetchADevice(speakerId)
        _uiState.update { currentState ->
            currentState.copy(status = "playing")
        }
    }

    fun previousSong() {
        val actionName = "previousSong"
        doAction(speakerId, actionName, emptyList())
        fetchADevice(speakerId)
        _uiState.update { currentState ->
            currentState.copy(status = "playing")
        }
    }

    fun setGenre(genre: String) {
        val actionName = "setGenre"
        val actionParams = listOf(genre)
        doAction(speakerId, actionName, actionParams)
        if(_uiState.value.currentGenre != genre){
            _uiState.update { currentState ->
                currentState.copy(currentGenre = genre)
            }

        }
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



}