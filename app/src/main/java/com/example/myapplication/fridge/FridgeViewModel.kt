package com.example.myapplication.fridge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.network.RetrofitClient
import com.example.myapplication.fridge.FridgeUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FridgeViewModel : ViewModel(){

    private val _uiState = MutableStateFlow(FridgeUiState());
    val uiState: StateFlow<FridgeUiState> = _uiState.asStateFlow();

    private var fetchJob: Job? = null

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

    fun changeMode( chosen : String){
        _uiState.update { currentState -> currentState.copy(selectedFridgeMode = chosen) }
    }

    fun setFreezerTemp( new : Int){
        _uiState.update { currentState -> currentState.copy(freezerTemp = new) }
    }

    fun setFridgeTemp( new : Int){
        _uiState.update { currentState -> currentState.copy( fridgeTemp = new) }
    }
}