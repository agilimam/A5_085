package com.example.uaspam.ui.ViewModel.Perawatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uaspam.model.Dokter
import com.example.uaspam.model.PasienHewan
import com.example.uaspam.model.Perawatan
import com.example.uaspam.repository.DokterRepository
import com.example.uaspam.repository.PasienHewanRepository
import com.example.uaspam.repository.PerawatanRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomePrwUiState {
    data class Success(val perawatan: List<Perawatan>) : HomePrwUiState()
    object Error : HomePrwUiState()
    object Loading : HomePrwUiState()
}

class HomePerawatanViewModel (
    private val Prw : PerawatanRepository,
    private val psn : PasienHewanRepository,
    private val dkt : DokterRepository
): ViewModel(){

    var prwUIState: HomePrwUiState by mutableStateOf(HomePrwUiState.Loading)
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


    init {
        getPrw()

    }

    fun getPrw(){
        viewModelScope.launch {
            prwUIState = HomePrwUiState.Loading
            prwUIState = try {
                HomePrwUiState.Success(Prw.getPerawatan().data)
            } catch (e: IOException) {
                HomePrwUiState.Error
            } catch (e: HttpException){
                HomePrwUiState.Error
            }
        }
    }

    fun deletePrw(id_perawatan : Int) {
        viewModelScope.launch {
            try {
                Prw.deletePerawatan(id_perawatan)
            } catch (e: IOException) {
                HomePrwUiState.Error
            } catch (e: HttpException) {
                HomePrwUiState.Error
            }
        }
    }
}