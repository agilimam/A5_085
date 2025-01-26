package com.example.uaspam.ui.View.PasienHewan


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.ui.ViewModel.PenyediaViewModel
import com.example.uaspam.R
import com.example.uaspam.model.Jenishewan
import com.example.uaspam.model.PasienHewan
import com.example.uaspam.ui.ViewModel.PasienHewan.HomeUiState
import com.example.uaspam.ui.ViewModel.PasienHewan.HomeViewModel
import com.example.uaspam.ui.customwidget.CostumeTopAppBar
import com.example.uaspam.ui.navigation.DestinasiHome

@Composable
fun PsnLayout(
    pasienHewan: List<PasienHewan>,
    modifier: Modifier = Modifier,
    onDetailClick: (PasienHewan) -> Unit,
    onEditClick: (PasienHewan) -> Unit = {},
    onDeleteClick: (PasienHewan) -> Unit = {} ,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory),
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pasienHewan) { hewan ->
            PsnCard(
                pasienHewan = hewan,
                modifier = Modifier
                    .fillMaxWidth(),
                onDetailClick = { onDetailClick(it) },
                onEditClick = { onEditClick(it) },
                jnsList = viewModel.jnsList,
                onDeleteClick = { onDeleteClick(it) }
            )
        }
    }
}

@Composable
fun PsnCard(
    jnsList: List<Jenishewan>,
    pasienHewan: PasienHewan,
    modifier: Modifier = Modifier,
    onDetailClick: (PasienHewan) -> Unit = {},
    onEditClick: (PasienHewan) -> Unit = {},
    onDeleteClick: (PasienHewan) -> Unit = {}
) {
    var jenishewan = jnsList.find { it.id_jenis_hewan == pasienHewan.jenis_hewan_id }?.nama_jenis_hewan ?: "Tidak ditemukan Jenis Hewan"

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                showDialog = false
                onDeleteClick(pasienHewan)
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
                text = "Nama Hewan: ${pasienHewan.nama_hewan}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Nama Pemilik : ${pasienHewan.pemilik}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Jenis Hewan : ${jenishewan}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Catatan Kesehatan : ${pasienHewan.catatan_kesehatan}",
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { onDetailClick(pasienHewan) }) {
                    Text("Detail", style = MaterialTheme.typography.labelSmall)
                }
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(onClick = { onEditClick(pasienHewan) }) {
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