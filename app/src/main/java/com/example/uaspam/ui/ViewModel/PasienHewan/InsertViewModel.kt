package com.example.uaspam.ui.ViewModel.PasienHewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Jenishewan
import com.example.uaspam.model.PasienHewan
import com.example.uaspam.repository.JenisHewanRepository
import com.example.uaspam.repository.PasienHewanRepository
import kotlinx.coroutines.launch

class InsertViewModel(
    private val psn: PasienHewanRepository,
    private val jns: JenisHewanRepository

) : ViewModel() {

    var uiState by mutableStateOf(InsertUiState())
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

    fun updateInsertPsnState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertPsn() {
        viewModelScope.launch {
            try {
                psn.insertPasienHewan(uiState.insertUiEvent.toPsn())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)


fun InsertUiEvent.toPsn(): PasienHewan = PasienHewan(
    id_hewan = id_hewan ?: 0,
    nama_hewan = nama_hewan,
    jenis_hewan_id = jenis_hewan_id ?: 0,
    pemilik = pemilik,
    kontak_pemilik = kontak_pemilik,
    tanggal_lahir = tanggal_lahir,
    catatan_kesehatan = catatan_kesehatan
)

fun PasienHewan.toUiStatePsn(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun PasienHewan.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id_hewan = id_hewan,
    nama_hewan = nama_hewan,
    jenis_hewan_id = jenis_hewan_id,
    pemilik = pemilik,
    kontak_pemilik = kontak_pemilik,
    tanggal_lahir = tanggal_lahir,
    catatan_kesehatan = catatan_kesehatan
)

data class InsertUiEvent(
    val id_hewan: Int? = null,
    val nama_hewan: String = "",
    val jenis_hewan_id: Int? = null,
    val pemilik: String = "",
    val kontak_pemilik: String = "",
    val tanggal_lahir: String = "",
    val catatan_kesehatan: String = ""
)

