package com.example.myapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OvenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(OvenUiState())
    val uiState: StateFlow<OvenUiState> = _uiState.asStateFlow()


    private var ovenId: Int = -1

    fun setId(id: Int) {
        ovenId = id
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