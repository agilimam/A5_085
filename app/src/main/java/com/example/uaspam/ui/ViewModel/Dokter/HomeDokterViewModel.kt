package com.example.uaspam.ui.ViewModel.Dokter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uaspam.model.Dokter
import com.example.uaspam.repository.DokterRepository
import com.example.uaspam.repository.JenisHewanRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeDokterUiState {
    data class Success(val dokter: List<Dokter>) : HomeDokterUiState()
    object Error : HomeDokterUiState()
    object Loading : HomeDokterUiState()
}

class HomeDokterViewModel (private val Dk : DokterRepository) : ViewModel(){
    var DkUIState: HomeDokterUiState by mutableStateOf(HomeDokterUiState.Loading)
        private set

    init {
        getDk()
    }

    fun getDk(){
        viewModelScope.launch {
            DkUIState = HomeDokterUiState.Loading
            DkUIState = try {
                HomeDokterUiState.Success(Dk.getDokter().data)
            } catch (e: IOException) {
                HomeDokterUiState.Error
            } catch (e: HttpException){
                HomeDokterUiState.Error
            }
        }
    }

    fun deleteDk(id_dokter : Int) {
        viewModelScope.launch {
            try {
                Dk.deleteDokter(id_dokter)
            } catch (e: IOException) {
                HomeDokterUiState.Error
            } catch (e: HttpException) {
                HomeDokterUiState.Error
            }
        }
    }
}