package com.example.myapplication.blind

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.network.RetrofitClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BlindViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BlindUiState())
    val uiState: StateFlow<BlindUiState> = _uiState.asStateFlow()
    private var fetchJob: Job? = null

    private var blindId: String = "44"

    fun setId(id: String) {
        blindId = id
    }

    fun fetchADevice(id: String){
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            viewModelScope.launch {
                runCatching {
                    RetrofitClient.getApiService()?.getADevice(id)
                        ?: throw Exception("API Service is null")
                }.onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            device = response.body(),
                            blindOn = response.body()?.result?.state?.status.equals("on"),
                            blindLevel = response.body()?.result?.state?.level ?:0,
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


    fun switchBlind(state: Boolean) {
        _uiState.update { currentState -> currentState.copy(blindOn = state)}
    }

    fun setLevel( new: Int) {
        val chosenList: List<Int> = listOf(new)
        doActionInt(blindId, "setLevel", chosenList)
        _uiState.update { currentState -> currentState.copy(blindLevel = new) }
    }
}