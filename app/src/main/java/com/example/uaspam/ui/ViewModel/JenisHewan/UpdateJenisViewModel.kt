package com.example.uaspam.ui.ViewModel.JenisHewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.repository.JenisHewanRepository
import com.example.uaspam.ui.navigation.DestinasiJenisUpdate
import kotlinx.coroutines.launch

class UpdateJenisViewModel(
    savedStateHandle: SavedStateHandle,
    private val jenisHewanRepository: JenisHewanRepository
): ViewModel(){
    var updateJenisUiState by mutableStateOf(InsertJenisUiState())
        private set

    private val _ID: Int = checkNotNull(savedStateHandle[DestinasiJenisUpdate.ID])

    init {
        viewModelScope.launch {
            updateJenisUiState = jenisHewanRepository.getJenisHewanById(_ID).data
                .toUiStateJns()
        }
    }

    fun updateInsertJnsState(insertJenisUiEvent: InsertJenisUiEvent){
        updateJenisUiState = InsertJenisUiState(insertJenisUiEvent = insertJenisUiEvent)
    }

    suspend fun updateJns(){
        viewModelScope.launch {
            try {
                jenisHewanRepository.updateJenisHewan(_ID, updateJenisUiState.insertJenisUiEvent.toJns())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}