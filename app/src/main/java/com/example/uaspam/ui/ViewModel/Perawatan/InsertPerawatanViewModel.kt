package com.example.uaspam.ui.ViewModel.Perawatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Dokter
import com.example.uaspam.model.PasienHewan
import com.example.uaspam.model.Perawatan
import com.example.uaspam.repository.DokterRepository
import com.example.uaspam.repository.PasienHewanRepository
import com.example.uaspam.repository.PerawatanRepository
import kotlinx.coroutines.launch

class InsertPrwViewModel (
    private val prw: PerawatanRepository,
    private val psn : PasienHewanRepository,
    private val dkt : DokterRepository


) : ViewModel() {

    var uiState by mutableStateOf(InsertPrwUiState())
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

    fun updateInsertPrwState(insertPrwUiEvent: InsertPrwUiEvent) {
        uiState = InsertPrwUiState(insertPrwUiEvent = insertPrwUiEvent)
    }

    suspend fun insertPrw(){
        viewModelScope.launch {
            try {
                prw.insertPerawatan(uiState.insertPrwUiEvent.toPrw())
            } catch (e:Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertPrwUiState(
    val insertPrwUiEvent: InsertPrwUiEvent = InsertPrwUiEvent()
)


fun InsertPrwUiEvent.toPrw(): Perawatan = Perawatan(
    id_perawatan = id_perawatan ?: 0,
    id_hewan = id_hewan ?: 0,
    id_dokter = id_dokter ?: 0,
    tanggal_perawatan = tanggal_perawatan,
    detail_perawatan = detail_perawatan

)

fun Perawatan.toUiStatePrw(): InsertPrwUiState = InsertPrwUiState(
    insertPrwUiEvent = toInsertPrwEvent()
)

fun Perawatan.toInsertPrwEvent(): InsertPrwUiEvent = InsertPrwUiEvent (
    id_perawatan = id_perawatan,
    id_hewan = id_hewan,
    id_dokter = id_dokter,
    tanggal_perawatan = tanggal_perawatan,
    detail_perawatan = detail_perawatan

)
data class InsertPrwUiEvent(
    val id_perawatan: Int? = null,
    val id_hewan: Int? = null,
    val id_dokter: Int? = null,
    val tanggal_perawatan: String = "",
    val detail_perawatan: String = ""

)