package com.example.uaspam.ui.View.Perawatan

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
import com.example.uaspam.model.Dokter
import com.example.uaspam.model.PasienHewan
import com.example.uaspam.ui.ViewModel.PenyediaViewModel
import com.example.uaspam.ui.ViewModel.Perawatan.InsertPrwUiEvent
import com.example.uaspam.ui.ViewModel.Perawatan.InsertPrwUiState
import com.example.uaspam.ui.ViewModel.Perawatan.InsertPrwViewModel
import com.example.uaspam.ui.customwidget.CostumeTopAppBar
import com.example.uaspam.ui.navigation.DestinasiEntryPerawatan
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPrwScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPrwViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryPerawatan.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyPrw(
            insertPrwUiState = viewModel.uiState,
            psnList = viewModel.psnList,
            dkList = viewModel.dktList,
            onPrwValueChange = viewModel::updateInsertPrwState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPrw()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )

    }
}
@Composable
fun EntryBodyPrw(
    insertPrwUiState: InsertPrwUiState,
    onPrwValueChange: (InsertPrwUiEvent) -> Unit,
    psnList: List<PasienHewan>,
    dkList : List<Dokter>,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPrw(
            insertPrwUiEvent = insertPrwUiState.insertPrwUiEvent,
            insertPrwUiState = insertPrwUiState,
            onValuePrwChange = onPrwValueChange,
            modifier = Modifier.fillMaxWidth(),
            psnList = psnList,
            dkList = dkList,
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
fun FormInputPrw(
    insertPrwUiState: InsertPrwUiState,
    insertPrwUiEvent: InsertPrwUiEvent,
    modifier: Modifier = Modifier,
    onValuePrwChange: (InsertPrwUiEvent) -> Unit = {},
    psnList: List<PasienHewan>,
    dkList : List<Dokter>,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Dropdown(
            selectedValue = psnList.find { it.id_hewan == insertPrwUiState.insertPrwUiEvent.id_hewan }?.nama_hewan ?: "",
            options = psnList.map { it.nama_hewan },
            label = "Nama Hewan",
            onValueChangedEvent = { hewan ->
                val selectedHewan = psnList.find { it.nama_hewan == hewan }
                selectedHewan?.let {
                    onValuePrwChange(insertPrwUiState.insertPrwUiEvent.copy(id_hewan = it.id_hewan))
                }

            }

        )
        Dropdown(
            selectedValue = dkList.find { it.id_dokter == insertPrwUiState.insertPrwUiEvent.id_dokter }?.nama_dokter ?: "",
            options = dkList.map { it.nama_dokter },
            label = "Jenis Hewan",
            onValueChangedEvent = { dokter ->
                val selectedDokter = dkList.find { it.nama_dokter == dokter }
                selectedDokter?.let {
                    onValuePrwChange(insertPrwUiState.insertPrwUiEvent.copy(id_dokter = it.id_dokter))
                }

            }

        )
        OutlinedTextField(
            value = insertPrwUiEvent.tanggal_perawatan,
            onValueChange = { onValuePrwChange(insertPrwUiEvent.copy(tanggal_perawatan = it))},
            label = { Text(" Tanggal Rawat") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertPrwUiEvent.detail_perawatan,
            onValueChange = { onValuePrwChange(insertPrwUiEvent.copy(detail_perawatan = it))},
            label = { Text(" Detail Perawatan Hewan") },
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