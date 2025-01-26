package com.example.uaspam.ui.View.JenisHewan

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
import com.example.uaspam.ui.ViewModel.JenisHewan.InsertJenisUiEvent
import com.example.uaspam.ui.ViewModel.JenisHewan.InsertJenisUiState
import com.example.uaspam.ui.ViewModel.JenisHewan.InsertJenisViewModel
import com.example.uaspam.ui.ViewModel.PenyediaViewModel
import com.example.uaspam.ui.customwidget.CostumeTopAppBar
import com.example.uaspam.ui.navigation.DestinasiEntryJenis
import kotlinx.coroutines.launch



@Composable
fun EntryBodyJenis(
    insertJenisUiState: InsertJenisUiState,
    onJenisValueChange: (InsertJenisUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertJenisUiEvent = insertJenisUiState.insertJenisUiEvent,
            onValueChange = onJenisValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertJenisUiEvent: InsertJenisUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertJenisUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertJenisUiEvent.nama_jenis_hewan,
            onValueChange = { onValueChange(insertJenisUiEvent.copy(nama_jenis_hewan= it))},
            label = { Text("Nama Jenis hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertJenisUiEvent.deskripsi,
            onValueChange = { onValueChange(insertJenisUiEvent.copy(deskripsi = it))},
            label = { Text(" Deskripsi Jenis  Hewan") },
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