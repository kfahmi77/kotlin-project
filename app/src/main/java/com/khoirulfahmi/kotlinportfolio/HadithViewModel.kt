package com.khoirulfahmi.kotlinportfolio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HadithViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<HadithUiState>(HadithUiState.Loading)
    val uiState: StateFlow<HadithUiState> = _uiState

    private val hadisList = listOf(
        "muslim",
        "bukhari",
        "tirmidzi",
        "nasai",
        "abu-daud",
        "ibnu-majah",
        "ahmad",
        "darimi",
        "malik"
    )
    private var currentHadisIndex = 0

    fun loadRandomHadith() {
        viewModelScope.launch {
            try {
                val hadisName = hadisList[currentHadisIndex]
                val randomNumber = when (hadisName) {
                    "muslim" -> (1..5329).random()
                    "bukhari" -> (1..7000).random()
                    "tirmidzi" -> (1..3890).random()
                    "nasai" -> (1..5490).random()
                    "abu-daud" -> (1..4590).random()
                    "ibnu-majah" -> (1..4331).random()
                    "ahmad" -> (1..12).random()
                    "darimi" -> (1..3180).random()
                    "malik" -> (1..1594).random()
                    else -> 1 // Default range
                }
                val response = RetrofitClient.instance.getHadith(hadisName, randomNumber)
                _uiState.value = HadithUiState.Success(response.data.contents, hadisName)
            } catch (e: Exception) {
                _uiState.value = HadithUiState.Error("Failed to load hadith: ${e.message}")
            }
        }
    }

    fun changeHadisCollection() {
        currentHadisIndex = (currentHadisIndex + 1) % hadisList.size
        loadRandomHadith()
    }
}

sealed class HadithUiState {
    data object Loading : HadithUiState()
    data class Success(val content: HadithContent, val hadisName: String) : HadithUiState()
    data class Error(val message: String) : HadithUiState()
}