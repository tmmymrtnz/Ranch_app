package com.example.myapplication.lamp

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.network.RetrofitClient
import com.example.myapplication.lamp.LightUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LightViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LightUiState());
    val uiState: StateFlow<LightUiState> = _uiState.asStateFlow();

    private var fetchJob: Job? = null


    private var lampId: String = "no id"

    fun setId(id: String) {
        lampId = id
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
                            lightOn= response.body()?.result?.state?.status.equals("on"),
                            brightness = response.body()?.result?.state?.brightness ?: 0,
                            lightColor = "#FF" + response.body()?.result?.state?.color.toString(),
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


    fun switchLight(state : Boolean){
        if(!state){
            doAction(lampId,"turnOff", listOf())
        }else{
            doAction(lampId,"turnOn", listOf())
        }
        _uiState.update { currentState -> currentState.copy(lightOn = state) }
    }

    fun setBrightness( new : Int){
        val chosenList: List<Int> = listOf(new)
        doActionInt(lampId, "setBrightness", chosenList)
        _uiState.update { currentState -> currentState.copy(brightness = new) }
    }

    fun changeColor( chosen : Color){

        val colorStr = Integer.toHexString(chosen.toArgb()).uppercase().substring(2)
        val chosenList: List<String> = listOf(colorStr)
        doAction(lampId, "setColor", chosenList)

        _uiState.update { currentState -> currentState.copy(lightColor = "#FF$colorStr") }

    }
}