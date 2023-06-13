package com.example.myapplication

data class RoutineUiState (
    val Routines:List<Routine>
)

class Routine(
    val title: String="",
    val rooms: Map<String, Room>
)