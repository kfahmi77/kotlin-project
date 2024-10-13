package com.khoirulfahmi.kotlinportfolio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HadithViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<HadithUiState>(HadithUiState.Loading)
    val uiState: StateFlow<HadithUiState> = _uiState

    fun loadHadith(name: String = "muslim", number: Int = 5) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getHadith(name, number)
                _uiState.value = HadithUiState.Success(response.data.contents)
            } catch (e: Exception) {
                _uiState.value = HadithUiState.Error("Failed to load hadith: ${e.message}")
            }
        }
    }
}

sealed class HadithUiState {
    object Loading : HadithUiState()
    data class Success(val content: HadithContent) : HadithUiState()
    data class Error(val message: String) : HadithUiState()
}