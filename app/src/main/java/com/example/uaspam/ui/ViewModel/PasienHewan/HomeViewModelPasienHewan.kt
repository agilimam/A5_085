package com.example.uaspam.ui.ViewModel.PasienHewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uaspam.model.Jenishewan
import com.example.uaspam.model.PasienHewan
import com.example.uaspam.repository.JenisHewanRepository
import com.example.uaspam.repository.PasienHewanRepository
import kotlinx.coroutines.launch
import java.io.IOException


sealed class HomeUiState {
    data class Success(val pasienHewan: List<PasienHewan>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class HomeViewModel (
    private val psn : PasienHewanRepository,
    private val jns: JenisHewanRepository
): ViewModel(){

    var psnUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
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

    init {
        getPsn()

    }

    fun getPsn(){
        viewModelScope.launch {
            psnUIState = HomeUiState.Loading
            psnUIState = try {
                HomeUiState.Success(psn.getPasienHewan().data)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }

    fun deletePsn(id_hewan : Int) {
        viewModelScope.launch {
            try {
                psn.deletePasienHewan(id_hewan)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }
}