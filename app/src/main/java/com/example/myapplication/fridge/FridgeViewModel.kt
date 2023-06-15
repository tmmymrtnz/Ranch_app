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


    private var fridgeId: String = "no id"

    fun setId(id: String) {
        fridgeId = id
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
                           selectedFridgeMode = response.body()?.result?.state?.mode.toString(),
                            fridgeTemp = response.body()?.result?.state?.temperature ?:0,
                            freezerTemp= response.body()?.result?.state?.freezerTemperature ?:0,
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

    fun changeMode( chosen : String){
        val chosenList: List<String> = listOf(chosen)
        doAction(fridgeId, "setMode", chosenList)
        _uiState.update { currentState -> currentState.copy(selectedFridgeMode = chosen) }
    }

    fun setFreezerTemp( new : Int){
        val chosenList: List<Int> = listOf(new)
        doActionInt(fridgeId, "setFreezerTemperature", chosenList)
        _uiState.update { currentState -> currentState.copy(freezerTemp = new) }
    }

    fun setFridgeTemp( new : Int){
        val chosenList: List<Int> = listOf(new)
        doActionInt(fridgeId, "setTemperature", chosenList)
        _uiState.update { currentState -> currentState.copy( fridgeTemp = new) }
    }
}