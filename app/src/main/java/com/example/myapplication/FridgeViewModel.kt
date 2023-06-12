package com.example.myapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FridgeViewModel : ViewModel(){

    private val _uiState = MutableStateFlow(FridgeUiState());
    val uiState: StateFlow<FridgeUiState> = _uiState.asStateFlow();

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