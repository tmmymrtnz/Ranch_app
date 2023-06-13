package com.example.myapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RoutineViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(RoutineUiState(emptyList()))
    val uiState: StateFlow<RoutineUiState> = _uiState.asStateFlow()
}