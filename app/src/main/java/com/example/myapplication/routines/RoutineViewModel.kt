package com.example.myapplication.routines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.network.RetrofitClient
import com.example.myapplication.data.network.model.Routines
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoutineViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(RoutineUiState())
    val uiState: StateFlow<RoutineUiState> = _uiState.asStateFlow()
    private var fetchJob: Job? = null

    fun dismissMessage() {
        _uiState.update { it.copy(message = null) }
    }

    fun fetchRoutines() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            runCatching {
                val apiService = RetrofitClient.getApiService()
                apiService?.getRoutines() ?: throw Exception("API Service is null")
            }.onSuccess { response ->
                _uiState.update { it.copy(
                    routines = response.body(),
                    isLoading = false
                ) }
            }.onFailure { e ->
                _uiState.update { it.copy(
                    message = e.message,
                    isLoading = false
                ) }
            }
        }
    }

    fun execute(id: String){
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            runCatching {
                val apiService = RetrofitClient.getApiService()
                apiService?.executeRoutine(id) ?: throw Exception("Could not execute routine")
            }.onSuccess {
                _uiState.update { it.copy(
                    isLoading = false
                ) }
            }.onFailure { e ->
                _uiState.update { it.copy(
                    message = e.message,
                    isLoading = false
                ) }
            }
        }
    }

    fun convertRoutinesResponse(responseBody: Routines?): Map<RoutineAux, MutableMap<String, MutableList<DeviceAux>>> {
        val routinesMap = mutableMapOf<RoutineAux, MutableMap<String, MutableList<DeviceAux>>>()

        responseBody?.result?.forEach { routineType ->
            val routineId = routineType.id ?: ""
            val routineName = routineType.name ?: ""
            val roomMap = mutableMapOf<String, MutableList<DeviceAux>>()

            routineType.actions.forEach { routineAction ->
                val roomName = routineAction.device?.room?.name ?: ""
                val deviceName = routineAction.device?.name ?: ""
                val actionName = routineAction.actionName ?: ""

                val deviceList = roomMap.getOrPut(roomName) { mutableListOf() }
                deviceList.add(DeviceAux(deviceName, actionName))
            }

            routinesMap[RoutineAux(routineId, routineName)] = roomMap
        }

        return routinesMap
    }

    class DeviceAux(
        val deviceName: String,
        val actionName: String
    )

    class RoutineAux(
        val id: String,
        val name: String
    )


}