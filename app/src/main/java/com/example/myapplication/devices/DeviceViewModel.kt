package com.example.myapplication.devices

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
                val apiService = RetrofitClient.getApiService()
                apiService?.getDevices() ?: throw Exception("API Service is null")
            }.onSuccess { response ->
                _uiState.update { it.copy(
                    devices = response.body(),
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
}