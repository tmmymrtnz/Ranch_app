package com.example.myapplication.oven

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.network.RetrofitClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OvenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(OvenUiState())
    val uiState: StateFlow<OvenUiState> = _uiState.asStateFlow()
    private var fetchJob: Job? = null


    private var ovenId: String = "XD"

    fun setId(id: String) {
        ovenId = id
    }

    fun dismissMessage() {
        _uiState.update { it.copy(message = null) }
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


    fun changeGrillMode( chosen : String){
        _uiState.update { currentState -> currentState.copy(selectedGrillMode = chosen) }
    }
    fun changeHeatMode( chosen : String){
        _uiState.update { currentState -> currentState.copy(selectedHeatMode = chosen) }
    }
    fun changeConvMode( chosen : String){
        _uiState.update { currentState -> currentState.copy(selectedConvMode = chosen) }
    }

    fun setTemp( new : Int){
        _uiState.update { currentState -> currentState.copy(Oventempeture = new) }
    }

    fun switchOven(state : Boolean){
        _uiState.update { currentState -> currentState.copy(OvenOn = state) }
    }


}