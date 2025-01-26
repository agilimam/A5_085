package com.example.uaspam.ui.View.JenisHewan

import com.example.uaspam.model.Jenishewan
import com.example.uaspam.ui.ViewModel.JenisHewan.HomeJenisViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.example.uaspam.ui.ViewModel.JenisHewan.HomeJenisUiState
import com.example.uaspam.ui.customwidget.CostumeTopAppBar
import com.example.uaspam.ui.navigation.DestinasiHomeJenis


@Composable
fun OnLoading(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier.size(150.dp),
                painter = painterResource(R.drawable.loading),
                contentDescription = stringResource(R.string.loading)
            )
            Text(text = "Loading...", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun OnError(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.eror), contentDescription = ""
            )
            Text(
                text = stringResource(R.string.loading_failed),
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
            Button(onClick = retryAction) {
                Text(stringResource(R.string.retry))
            }
        }
    }
}

@Composable
fun JnsLayout(
    jenishewan: List<Jenishewan>,
    modifier: Modifier = Modifier,
    onEditJenisClick: (Jenishewan) -> Unit,
    onDeleteJenisClick: (Jenishewan) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(jenishewan) { jenis ->
            JnsCard(
                jenishewan = jenis,
                modifier = Modifier.fillMaxWidth(),
                onEditJenisClick = { onEditJenisClick(it) },
                onDeleteJenisClick = { onDeleteJenisClick(it) }
            )
        }
    }
}

@Composable
fun JnsCard(
    jenishewan: Jenishewan,
    modifier: Modifier = Modifier,
    onEditJenisClick: (Jenishewan) -> Unit = {},
    onDeleteJenisClick: (Jenishewan) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                showDialog = false
                onDeleteJenisClick(jenishewan)
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
                text = "Jenis Hewan: ${jenishewan.nama_jenis_hewan}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Deskripsi hewan : ${jenishewan.deskripsi}",
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(onClick = { onEditJenisClick(jenishewan) }) {
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
