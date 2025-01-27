package com.example.uaspam.ui.ViewModel.Dokter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Dokter
import com.example.uaspam.repository.DokterRepository
import kotlinx.coroutines.launch

class InsertDokterViewModel (private val Dk : DokterRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertDkUiState())
        private set

    fun updateInsertDkState(insertDkUiEvent: InsertDkUiEvent) {
        uiState = InsertDkUiState(insertDkUiEvent = insertDkUiEvent)
    }

    suspend fun insertDk(){
        viewModelScope.launch {
            try {
                Dk.insertDokter(uiState.insertDkUiEvent.toDk())
            } catch (e:Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertDkUiState(
    val insertDkUiEvent: InsertDkUiEvent = InsertDkUiEvent()
)

data class InsertDkUiEvent(
    val id_dokter: Int? = null,
    val nama_dokter : String = "",
    val spesialisasi : String = "",
    val kontak : String = "",

    )

fun InsertDkUiEvent.toDk(): Dokter = Dokter (
    id_dokter = id_dokter ?: 0,
    nama_dokter = nama_dokter,
    spesialisasi = spesialisasi,
    kontak = kontak,

    )



fun Dokter.toUiStateDk(): InsertDkUiState = InsertDkUiState(
    insertDkUiEvent = toInsertDkUiEvent()
)

fun Dokter.toInsertDkUiEvent(): InsertDkUiEvent = InsertDkUiEvent (
    nama_dokter = nama_dokter,
    spesialisasi = spesialisasi,
    kontak = kontak,
)
