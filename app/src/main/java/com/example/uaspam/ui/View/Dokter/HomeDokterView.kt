package com.example.uaspam.ui.View.Dokter

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
import com.example.uaspam.ui.ViewModel.Dokter.HomeDokterUiState
import com.example.uaspam.ui.ViewModel.Dokter.HomeDokterViewModel
import com.example.uaspam.ui.ViewModel.PenyediaViewModel
import com.example.uaspam.ui.customwidget.CostumeTopAppBar
import com.example.uaspam.ui.navigation.DestinasiHomeDokter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDkScreen(
    navigateToitemPerawatan: () -> Unit,
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    navigateBack: () ->Unit,
    onEditDkClick: (Dokter) -> Unit,
    viewModel: HomeDokterViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeDokter.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getDk() }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFFBBDEFB),
            ) {
                NavigationBarItem(
                    selected = selectedIndex == 0,
                    onClick = {
                        selectedIndex = 0
                        navigateToItemEntry()
                    },
                    icon = {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = Color(0xFFE3F2FD),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(8.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.dokter),
                                contentDescription = "Tambah Dokter",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    },
                )
                NavigationBarItem(
                    selected = selectedIndex == 1,
                    onClick = {
                        selectedIndex = 1
                        navigateToitemPerawatan()
                    },
                    icon = {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = Color(0xFFE3F2FD),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(8.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.perawatan),
                                contentDescription = "Perawatan",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    },
                )
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(150.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFE3F2FD), Color(0xFFBBDEFB))
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dokter),
                        contentDescription = "Logo",
                        modifier = Modifier.size(80.dp)
                    )
                    Text(
                        text = "Selamat Datang di Daftar Dokter",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        color = Color(0xFF0D47A1)
                    )
                }
            }


            HomeDkStatus(
                homeDokterUiState = viewModel.DkUIState,
                retryAction = { viewModel.getDk() },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                onEditDkClick = onEditDkClick,
                onDeleteDkClick = {
                    viewModel.deleteDk(it.id_dokter)
                    viewModel.getDk()
                }
            )

        }
    }
}


@Composable
fun HomeDkStatus(
    homeDokterUiState: HomeDokterUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteDkClick: (Dokter) -> Unit,
    onEditDkClick: (Dokter) -> Unit
) {
    when (homeDokterUiState) {
        is HomeDokterUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeDokterUiState.Success -> {
            if (homeDokterUiState.dokter.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Tidak ada data Jenis Hewan",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                DkLayout(
                    dokter = homeDokterUiState.dokter,
                    modifier = modifier,
                    onDeleteDkClick = {onDeleteDkClick(it)},
                    onEditDkClick = {onEditDkClick(it)}
                )
            }
        }
        is HomeDokterUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}


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
fun DkLayout(
    dokter: List<Dokter>,
    modifier: Modifier = Modifier,
    onEditDkClick: (Dokter) -> Unit,
    onDeleteDkClick: (Dokter) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(dokter) { dk ->
            DkCard(
                dokter = dk,
                modifier = Modifier.fillMaxWidth(),
                onEditDkClick = { onEditDkClick(it) },
                onDeleteDkClick = { onDeleteDkClick(it) }
            )
        }
    }
}

@Composable
fun DkCard(
    dokter: Dokter,
    modifier: Modifier = Modifier,
    onEditDkClick: (Dokter) -> Unit = {},
    onDeleteDkClick: (Dokter) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                showDialog = false
                onDeleteDkClick(dokter)
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
                text = "Nama Dokter: ${dokter.nama_dokter}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Spesialisasi Dokter : ${dokter.spesialisasi}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Kontak Dokter : ${dokter.kontak}",
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(onClick = { onEditDkClick(dokter) }) {
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