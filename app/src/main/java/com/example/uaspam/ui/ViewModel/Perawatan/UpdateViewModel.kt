package com.example.uaspam.ui.ViewModel.Perawatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Dokter
import com.example.uaspam.model.Jenishewan
import com.example.uaspam.model.PasienHewan
import com.example.uaspam.repository.DokterRepository
import com.example.uaspam.repository.PasienHewanRepository
import com.example.uaspam.repository.PerawatanRepository
import com.example.uaspam.ui.navigation.DestinasiUpdatePerawatan
import kotlinx.coroutines.launch

class UpdatePrwViewModel(
    savedStateHandle: SavedStateHandle,
    private val perawatanRepository: PerawatanRepository,
    private val psn : PasienHewanRepository,
    private val dkt : DokterRepository

): ViewModel(){
    var updatePrwUiState by mutableStateOf(InsertPrwUiState())
        private set

    var psnList by mutableStateOf<List<PasienHewan>>(emptyList())
    var dktList by mutableStateOf<List<Dokter>>(emptyList())

    init {
        //memuat data
        simpandataJenis()
    }

    private fun simpandataJenis() {
        viewModelScope.launch {
            try {
                psnList = psn.getPasienHewan().data
                dktList = dkt.getDokter().data
            } catch (e: Exception) {

            }
        }
    }

    private val _ID: Int = checkNotNull(savedStateHandle[DestinasiUpdatePerawatan.ID])

    init {
        viewModelScope.launch {
            updatePrwUiState = perawatanRepository.getPerawatanById(_ID).data
                .toUiStatePrw()
        }
    }

    fun updatePrwState(insertPrwUiEvent: InsertPrwUiEvent){
        updatePrwUiState = InsertPrwUiState(insertPrwUiEvent = insertPrwUiEvent)
    }

    suspend fun updatePrw(){
        viewModelScope.launch {
            try {
                perawatanRepository.updatePerawatan(_ID, updatePrwUiState.insertPrwUiEvent.toPrw())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}

