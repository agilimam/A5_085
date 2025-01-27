package com.example.uaspam.ui.ViewModel.Dokter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.repository.DokterRepository
import com.example.uaspam.ui.navigation.DestinasiDokterUpdate
import kotlinx.coroutines.launch

class UpdateDokterViewModel(
    savedStateHandle: SavedStateHandle,
    private val dokterRepository: DokterRepository

): ViewModel(){
    var updateDkUiState by mutableStateOf(InsertDkUiState())
        private set

    private val _ID: Int = checkNotNull(savedStateHandle[DestinasiDokterUpdate.ID])

    init {
        viewModelScope.launch {
            updateDkUiState = dokterRepository.getDokterById(_ID).data
                .toUiStateDk()
        }
    }

    fun updateInsertDkState(insertDkUiEvent: InsertDkUiEvent){
        updateDkUiState = InsertDkUiState(insertDkUiEvent = insertDkUiEvent)
    }

    suspend fun updateDk(){
        viewModelScope.launch {
            try {
                dokterRepository.updateDokter(_ID, updateDkUiState.insertDkUiEvent.toDk())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}
