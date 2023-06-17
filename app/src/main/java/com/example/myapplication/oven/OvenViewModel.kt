package com.example.myapplication.oven

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.network.RetrofitClient
import com.example.myapplication.data.network.model.Device
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OvenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(OvenUiState())
    val uiState: StateFlow<OvenUiState> = _uiState.asStateFlow()
    private var fetchJob: Job? = null


    private var ovenId: String = "no id"

    fun setId(id: String) {
        ovenId = id
    }

    init {
        polling()
    }

    private fun polling() {
        viewModelScope.launch {
            while (true) {
                delay(1000L) // Delay for 1 second
                fetchADevice(ovenId)
            }
        }
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
                            selectedGrillMode =  response.body()?.result?.state?.grill.toString()  ,
                            selectedConvMode = response.body()?.result?.state?.convection.toString(),
                            selectedHeatMode = response.body()?.result?.state?.heat.toString(),
                            Oventempeture = response.body()?.result?.state?.temperature ?:0,
                            OvenOn = response.body()?.result?.state?.status.equals("on"),
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


    fun changeGrillMode( chosen : String){

        val chosenList: List<String> = listOf(chosen)
        doAction(ovenId, "setGrill", chosenList)

        _uiState.update { currentState -> currentState.copy(selectedGrillMode = chosen) }

    }
    fun changeHeatMode( chosen : String){
        val chosenList: List<String> = listOf(chosen)
        doAction(ovenId, "setHeat", chosenList)
        _uiState.update { currentState -> currentState.copy(selectedHeatMode = chosen) }
    }
    fun changeConvMode( chosen : String){
        val chosenList: List<String> = listOf(chosen)
        doAction(ovenId, "setConvection", chosenList)
        _uiState.update { currentState -> currentState.copy(selectedConvMode = chosen) }
    }

    fun setTemp( new : Int){
        val chosenList: List<Int> = listOf(new)
        doActionInt(ovenId, "setTemperature", chosenList)
        _uiState.update { currentState -> currentState.copy(Oventempeture = new) }
    }

    fun switchOven(state : Boolean){
        if(!state){
            doAction(ovenId,"turnOff", listOf())
        }else{
            doAction(ovenId,"turnOn", listOf())
        }
        _uiState.update { currentState -> currentState.copy(OvenOn = state) }

    }

}