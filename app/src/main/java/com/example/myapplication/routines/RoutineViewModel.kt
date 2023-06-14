package com.example.myapplication.routines

import androidx.lifecycle.ViewModel
import com.example.myapplication.RoutineUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RoutineViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(RoutineUiState(emptyList()))
    val uiState: StateFlow<RoutineUiState> = _uiState.asStateFlow()
}