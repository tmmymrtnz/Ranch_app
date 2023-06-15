package com.example.myapplication.devices

import android.util.Log
import com.example.myapplication.data.network.RetrofitClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch

class DeviceViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DeviceUiState())
    val uiState: StateFlow<DeviceUiState> = _uiState.asStateFlow()
    private var fetchJob: Job? = null

    fun dismissMessage() {
        _uiState.update { it.copy(message = null) }
    }

    fun fetchDevices() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            runCatching {
                Log.d("homehivestatus", " Llegue hasta aca")
                val apiService = RetrofitClient.getApiService()
                Log.d("homehivestatus", " Nunca llego hasta aca!")
                apiService?.getDevices() ?: throw Exception("API Service is null")
            }.onSuccess { response ->
                Log.d("homehivestatus", "Success: Inside Success Block")
                _uiState.update { it.copy(
                    devices = response.body(),
                    isLoading = false
                ) }
            }.onFailure { e ->
                Log.d("homehivestatus", "Failure: Inside Failure Block \nError message  ${e.message}")
                _uiState.update { it.copy(
                    message = e.message,
                    isLoading = false
                ) }
            }
        }
    }
}