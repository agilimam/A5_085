package com.example.uaspam.ui.ViewModel.PasienHewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Jenishewan
import com.example.uaspam.model.PasienResponseDetail
import com.example.uaspam.repository.JenisHewanRepository
import com.example.uaspam.repository.PasienHewanRepository
import com.example.uaspam.ui.navigation.DestinasiDetail
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed class DetailUiState {
    data class Success(val pasienHewan: PasienResponseDetail) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val psn : PasienHewanRepository,
    private val jns: JenisHewanRepository

) : ViewModel() {

    var detailUiState: DetailUiState by mutableStateOf(DetailUiState.Loading)
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

    private val _ID: Int = checkNotNull(savedStateHandle[DestinasiDetail.ID])

    init {
        getPasienHewanById()
    }

    fun getPasienHewanById() {
        viewModelScope.launch {
            detailUiState = DetailUiState.Loading
            detailUiState = try {
                val pasienHewan = psn.getPasienHewanById(_ID)
                DetailUiState.Success(pasienHewan)
            } catch (e: IOException) {
                DetailUiState.Error
            } catch (e: HttpException) {
                DetailUiState.Error
            }
        }
    }
}