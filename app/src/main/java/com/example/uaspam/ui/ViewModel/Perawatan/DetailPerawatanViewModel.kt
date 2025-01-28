package com.example.uaspam.ui.ViewModel.Perawatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Dokter
import com.example.uaspam.model.PasienHewan
import com.example.uaspam.model.PerawatanResponseDetail
import com.example.uaspam.repository.DokterRepository
import com.example.uaspam.repository.PasienHewanRepository
import com.example.uaspam.repository.PerawatanRepository
import com.example.uaspam.ui.navigation.DestinasiDetailPerawatan
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailPrwUiState {
    data class Success(val perawatan: PerawatanResponseDetail) : DetailPrwUiState()
    object Error : DetailPrwUiState()
    object Loading : DetailPrwUiState()
}

class DetailPrwViewModel(
    savedStateHandle: SavedStateHandle,
    private val prw : PerawatanRepository,
    private val psn : PasienHewanRepository,
    private val dkt : DokterRepository
) : ViewModel() {

    var detailPrwUiState: DetailPrwUiState by mutableStateOf(DetailPrwUiState.Loading)
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

    private val _ID: Int = checkNotNull(savedStateHandle[DestinasiDetailPerawatan.ID])

    init {
        getPerawatanById()
    }

    fun getPerawatanById() {
        viewModelScope.launch {
            detailPrwUiState = DetailPrwUiState.Loading
            detailPrwUiState = try {
                val perawatan = prw.getPerawatanById(_ID)
                DetailPrwUiState.Success(perawatan)
            } catch (e: IOException) {
                DetailPrwUiState.Error
            } catch (e: HttpException) {
                DetailPrwUiState.Error
            }
        }
    }
}