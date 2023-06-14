package com.example.myapplication.lamp

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.myapplication.lamp.LightUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LightViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LightUiState());
    val uiState: StateFlow<LightUiState> = _uiState.asStateFlow();


    fun switchLight(state : Boolean){
        _uiState.update { currentState -> currentState.copy(lightOn = state) }
    }

    fun setBrightness( new : Float){
        _uiState.update { currentState -> currentState.copy(brightness = new) }
    }

    fun changeColor( chosen : Color){
        _uiState.update { currentState -> currentState.copy(lightColor = chosen) }
    }
}