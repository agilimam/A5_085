package com.example.uaspam.ui.View.Perawatan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.R
import com.example.uaspam.model.Dokter
import com.example.uaspam.model.PasienHewan
import com.example.uaspam.model.Perawatan
import com.example.uaspam.ui.ViewModel.PenyediaViewModel
import com.example.uaspam.ui.ViewModel.Perawatan.HomePerawatanViewModel
import com.example.uaspam.ui.ViewModel.Perawatan.HomePrwUiState
import com.example.uaspam.ui.customwidget.CostumeTopAppBar
import com.example.uaspam.ui.navigation.DestinasiHomePerawatan


@Composable
fun PrwLayout(
    perawatan: List<Perawatan>,
    modifier: Modifier = Modifier,
    onEditPrwClick: (Perawatan) -> Unit,
    onDetailPrwClick: (Perawatan) -> Unit,
    onDeletePrwClick: (Perawatan) -> Unit = {},
    viewModel: HomePerawatanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(perawatan) { rawat ->
            PrwCard(
                perawatan = rawat,
                modifier = Modifier.fillMaxWidth(),
                onDetailPrwClick= { onDetailPrwClick(it) },
                onEditPrwClick = { onEditPrwClick(it) },
                onDeletePrwClick = { onDeletePrwClick(it) },
                psnList = viewModel.psnList,
                dkList = viewModel.dktList

            )
        }
    }
}

@Composable
fun PrwCard(
    psnList: List<PasienHewan>,
    dkList : List<Dokter>,
    perawatan: Perawatan,
    modifier: Modifier = Modifier,
    onEditPrwClick: (Perawatan) -> Unit = {},
    onDetailPrwClick: (Perawatan) -> Unit,
    onDeletePrwClick: (Perawatan) -> Unit = {}
) {
    var pasienHewan= psnList.find { it.id_hewan == perawatan. id_hewan}?.nama_hewan ?: "Tidak ditemukan Pasien Hewan"
    var dokterHewan= dkList.find { it.id_dokter == perawatan. id_dokter}?.nama_dokter ?: "Tidak ditemukan Jenis Hewan"
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                showDialog = false
                onDeletePrwClick(perawatan)
            },
            onDeleteCancel = {
                showDialog = false
            }
        )
    }

    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .heightIn(min = 80.dp, max = 120.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Hewan rawat : ${pasienHewan}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Dokter  rawat: ${dokterHewan}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Tanggal Rawat  : ${perawatan.tanggal_perawatan}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Detail Rawat  : ${perawatan.detail_perawatan}",
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(onClick = { onDetailPrwClick(perawatan) }) {
                    Text("Detail", style = MaterialTheme.typography.labelSmall)
                }
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(onClick = { onEditPrwClick(perawatan) }) {
                    Text("Edit", style = MaterialTheme.typography.labelSmall)
                }
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(onClick = { showDialog = true }) {
                    Text("Hapus", style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /*Do nothing*/ },
        title = { Text("Hapus Data") },
        text = { Text("Apakah Anda yakin ingin menghapus data ini?") },
        dismissButton = {
            TextButton(onClick = { onDeleteCancel() }) {
                Text(text = "Batal")
            }
        },
        confirmButton = {
            TextButton(onClick = { onDeleteConfirm() }) {
                Text(text = "Ya")
            }
        }
    )
}
