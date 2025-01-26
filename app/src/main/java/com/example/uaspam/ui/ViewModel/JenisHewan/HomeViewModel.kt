package com.example.uaspam.ui.ViewModel.JenisHewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uaspam.model.Jenishewan
import com.example.uaspam.repository.JenisHewanRepository
import kotlinx.coroutines.launch
import java.io.IOException


sealed class HomeJenisUiState {
    data class Success(val jenishewan: List<Jenishewan>) : HomeJenisUiState()
    object Error : HomeJenisUiState()
    object Loading : HomeJenisUiState()
}

class HomeJenisViewModel (private val jns : JenisHewanRepository) : ViewModel(){
    var jnsUIState: HomeJenisUiState by mutableStateOf(HomeJenisUiState.Loading)
        private set

    init {
        getJns()
    }

    fun getJns(){
        viewModelScope.launch {
            jnsUIState = HomeJenisUiState.Loading
            jnsUIState = try {
                HomeJenisUiState.Success(jns.getJenisHewan().data)
            } catch (e: IOException) {
                HomeJenisUiState.Error
            } catch (e: HttpException){
                HomeJenisUiState.Error
            }
        }
    }

    fun deleteJns(id_jenis_hewan : Int) {
        viewModelScope.launch {
            try {
                jns.deleteJenisHewan(id_jenis_hewan)
            } catch (e: IOException) {
                HomeJenisUiState.Error
            } catch (e: HttpException) {
                HomeJenisUiState.Error
            }
        }
    }
}