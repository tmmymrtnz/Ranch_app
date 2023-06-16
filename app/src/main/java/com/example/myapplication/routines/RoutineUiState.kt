package com.example.myapplication.routines

import com.example.myapplication.data.network.model.Routines

data class RoutineUiState (
    val routines: Routines? = null,
    val isLoading: Boolean = false,
    val message: String? = null
)

val RoutineUiState.hasError: Boolean get() = message != null
