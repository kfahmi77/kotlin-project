package com.khoirulfahmi.kotlinportfolio

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HadithViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _uiState = MutableStateFlow<HadithUiState>(HadithUiState.Loading)
    val uiState: StateFlow<HadithUiState> = _uiState

    private val hadisList = listOf(
        "muslim", "bukhari", "tirmidzi", "nasai", "abu-daud",
        "ibnu-majah", "ahmad", "darimi", "malik"
    )

    init {
        loadSavedStateOrFetchNew()
    }

    private fun loadSavedStateOrFetchNew() {
        val savedNumber = savedStateHandle.get<Int>(KEY_HADITH_NUMBER)
        val savedArabic = savedStateHandle.get<String>(KEY_HADITH_ARABIC)
        val savedTranslation = savedStateHandle.get<String>(KEY_HADITH_TRANSLATION)
        val savedHadisName = savedStateHandle.get<String>(KEY_HADIS_NAME)
        val savedIndex = savedStateHandle.get<Int>(KEY_CURRENT_INDEX) ?: 0

        if (savedNumber != null && savedArabic != null && savedTranslation != null && savedHadisName != null) {
            val savedContent = HadithContent(savedNumber, savedArabic, savedTranslation)
            _uiState.value = HadithUiState.Success(savedContent, savedHadisName)
        } else {
            currentHadisIndex = savedIndex
            loadRandomHadith()
        }
    }

    private var currentHadisIndex: Int
        get() = savedStateHandle.get<Int>(KEY_CURRENT_INDEX) ?: 0
        set(value) = savedStateHandle.set(KEY_CURRENT_INDEX, value)

    fun loadRandomHadith() {
        viewModelScope.launch {
            _uiState.value = HadithUiState.Loading
            try {
                val hadisName = hadisList[currentHadisIndex]
                val randomNumber = getRandomNumberForHadis(hadisName)
                val response = RetrofitClient.instance.getHadith(hadisName, randomNumber)
                val content = response.data.contents
                _uiState.value = HadithUiState.Success(content, hadisName)
                // Save the fetched data
                savedStateHandle.set(KEY_HADITH_NUMBER, content.number)
                savedStateHandle.set(KEY_HADITH_ARABIC, content.arab)
                savedStateHandle.set(KEY_HADITH_TRANSLATION, content.id)
                savedStateHandle.set(KEY_HADIS_NAME, hadisName)
            } catch (e: Exception) {
                _uiState.value = HadithUiState.Error("Failed to load hadith: ${e.message}")
            }
        }
    }

    fun changeHadisCollection() {
        currentHadisIndex = (currentHadisIndex + 1) % hadisList.size
        // Clear saved state when changing collection
        savedStateHandle.remove<Int>(KEY_HADITH_NUMBER)
        savedStateHandle.remove<String>(KEY_HADITH_ARABIC)
        savedStateHandle.remove<String>(KEY_HADITH_TRANSLATION)
        savedStateHandle.remove<String>(KEY_HADIS_NAME)
        loadRandomHadith()
    }

    private fun getRandomNumberForHadis(hadisName: String): Int {
        return when (hadisName) {
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
    }

    companion object {
        private const val KEY_HADITH_NUMBER = "hadith_number"
        private const val KEY_HADITH_ARABIC = "hadith_arabic"
        private const val KEY_HADITH_TRANSLATION = "hadith_translation"
        private const val KEY_HADIS_NAME = "hadis_name"
        private const val KEY_CURRENT_INDEX = "current_index"
    }
}

sealed class HadithUiState {
    data object Loading : HadithUiState()
    data class Success(val content: HadithContent, val hadisName: String) : HadithUiState()
    data class Error(val message: String) : HadithUiState()
}