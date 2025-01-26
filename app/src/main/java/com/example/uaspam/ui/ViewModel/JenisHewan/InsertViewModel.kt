package com.example.uaspam.ui.ViewModel.JenisHewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Jenishewan
import com.example.uaspam.repository.JenisHewanRepository
import kotlinx.coroutines.launch

class InsertJenisViewModel (private val jns : JenisHewanRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertJenisUiState())
        private set

    fun updateInsertJnsState(insertJenisUiEvent: InsertJenisUiEvent) {
        uiState = InsertJenisUiState(insertJenisUiEvent = insertJenisUiEvent)
    }

    suspend fun insertJns(){
        viewModelScope.launch {
            try {
                jns.insertJenisHewan(uiState.insertJenisUiEvent.toJns())
            } catch (e:Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertJenisUiState(
     val insertJenisUiEvent: InsertJenisUiEvent = InsertJenisUiEvent()
)

data class InsertJenisUiEvent(
    val id_jenis_hewan: Int? = null,
    val nama_jenis_hewan : String = "",
    val deskripsi : String = "",

)

fun InsertJenisUiEvent.toJns(): Jenishewan = Jenishewan (
    id_jenis_hewan = id_jenis_hewan ?: 0,
    nama_jenis_hewan = nama_jenis_hewan,
    deskripsi = deskripsi,

)

fun Jenishewan.toUiStateJns(): InsertJenisUiState = InsertJenisUiState(
    insertJenisUiEvent = toInsertJenisUiEvent()
)

fun Jenishewan.toInsertJenisUiEvent(): InsertJenisUiEvent = InsertJenisUiEvent (
    nama_jenis_hewan = nama_jenis_hewan,
    deskripsi = deskripsi,
)

