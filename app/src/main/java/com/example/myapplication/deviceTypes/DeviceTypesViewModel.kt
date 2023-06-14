package com.example.myapplication.deviceTypes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DeviceTypesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DeviceTypesUiState())
    val uiState: StateFlow<DeviceTypesUiState> = _uiState.asStateFlow()
    private var fetchJob: Job? = null

    fun dismissMessage() {
        _uiState.update { it.copy(message = null) }
    }

    fun fetchDeviceTypes() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            runCatching {
                val apiService = RetrofitClient.getApiService()
                apiService?.getDeviceTypes() ?: throw Exception("API Service is null")
            }.onSuccess { response ->
                Log.d("OnSuccessTypes", "OnSuccess: Inside OnSuccess types")
                _uiState.update { it.copy(
                    deviceTypes = response.body(),
                    isLoading = false
                ) }
            }.onFailure { e ->
                Log.d("OnFailureTypes", "OnFailure: Inside OnFailure types. Exception: ${e.message}")
                _uiState.update { it.copy(
                    message = e.message,
                    isLoading = false
                ) }
            }

        }
    }
}