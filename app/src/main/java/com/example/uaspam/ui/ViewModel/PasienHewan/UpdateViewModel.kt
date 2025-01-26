package com.example.uaspam.ui.ViewModel.PasienHewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Jenishewan
import com.example.uaspam.repository.JenisHewanRepository
import com.example.uaspam.repository.PasienHewanRepository
import com.example.uaspam.ui.navigation.DestinasiUpdate
import kotlinx.coroutines.launch

class UpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val pasienHewanRepository: PasienHewanRepository,
    private val jns: JenisHewanRepository
): ViewModel(){
    var updateUiState by mutableStateOf(InsertUiState())
        private set

    var jnsList by mutableStateOf<List<Jenishewan>>(emptyList())

    init {
        //memuat data jenis hewan
        simpandataJenis()
    }

    private fun simpandataJenis() {
        viewModelScope.launch {
            try {
                jnsList = jns.getJenisHewan().data
            } catch (e: Exception) {

            }
        }
    }

    private val _ID: Int = checkNotNull(savedStateHandle[DestinasiUpdate.ID])

    init {
        viewModelScope.launch {
            updateUiState = pasienHewanRepository.getPasienHewanById(_ID).data
                .toUiStatePsn()
        }
    }

    fun updateState(insertUiEvent: InsertUiEvent){
        updateUiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updatePsn(){
        viewModelScope.launch {
            try {
                pasienHewanRepository.updatePasienHewan(_ID, updateUiState.insertUiEvent.toPsn())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}