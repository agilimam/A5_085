package com.example.uaspam.ui.View.PasienHewan

import Dropdown
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.model.Jenishewan
import com.example.uaspam.ui.ViewModel.PasienHewan.InsertUiEvent
import com.example.uaspam.ui.ViewModel.PasienHewan.InsertUiState
import com.example.uaspam.ui.ViewModel.PasienHewan.InsertViewModel
import com.example.uaspam.ui.ViewModel.PenyediaViewModel
import com.example.uaspam.ui.customwidget.CostumeTopAppBar
import com.example.uaspam.ui.navigation.DestinasiEntry

import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertUiState: InsertUiState,
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    jnsList: List<Jenishewan>,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.nama_hewan,
            onValueChange = { onValueChange(insertUiEvent.copy(nama_hewan= it))},
            label = { Text("Nama hewan Anda") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Dropdown(
            // untuk memanggil sesuai id nya forenb key = //insertUiState.insertUiEvent.jenis_hewan_id.toString(),//

            selectedValue = jnsList.find { it.id_jenis_hewan == insertUiState.insertUiEvent.jenis_hewan_id }?.nama_jenis_hewan ?: "",
            options = jnsList.map { it.nama_jenis_hewan },
            label = "Jenis Hewan",
            onValueChangedEvent = { jenis ->
                val selectedJenis = jnsList.find { it.nama_jenis_hewan == jenis }
                selectedJenis?.let {
                    onValueChange(insertUiState.insertUiEvent.copy(jenis_hewan_id = it.id_jenis_hewan))
                }

            }

        )
        OutlinedTextField(
            value = insertUiEvent.pemilik,
            onValueChange = { onValueChange(insertUiEvent.copy(pemilik = it))},
            label = { Text(" Nama Pemilik Hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,

        )
        OutlinedTextField(
            value = insertUiEvent.kontak_pemilik,
            onValueChange = { onValueChange(insertUiEvent.copy(kontak_pemilik = it))},
            label = { Text(" Kontak Pemilik Hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.tanggal_lahir,
            onValueChange = { onValueChange(insertUiEvent.copy(tanggal_lahir = it))},
            label = { Text("Tanggal Lahir Hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.catatan_kesehatan,
            onValueChange = { onValueChange(insertUiEvent.copy(catatan_kesehatan = it))},
            label = { Text("catatan Kesehatan Hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if(enabled){
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}