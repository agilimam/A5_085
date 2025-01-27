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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePrwScreen(
    navigateToItemEntryPrw: () -> Unit,
    navigateToItemEntryDk: () ->Unit,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onEditPrwClick: (Perawatan) -> Unit = {},
    onDetailPrwClick: (Int) -> Unit = {},
    viewModel: HomePerawatanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    var selectedIndex by remember { mutableStateOf(0) }
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePerawatan.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getPrw() }
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
                        navigateToItemEntryPrw()
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
                                contentDescription = "Tambah Perawatan",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    },
                )
                NavigationBarItem(
                    selected = selectedIndex == 1,
                    onClick = {
                        selectedIndex = 1
                        navigateToItemEntryDk()
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
                            colors = listOf(Color(0xFFE3F2FD), Color(0xFFBBDEFB)) // Gradasi biru muda
                        ),
                        shape = RoundedCornerShape(16.dp) // Sudut membulat
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Perbesar ukuran logo
                    Image(
                        painter = painterResource(id = R.drawable.perawatan), // Logo klinik
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(100.dp) // Ukuran logo lebih besar
                    )
                    Text(
                        text = "Selamat Datang di Daftar Perawatan",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        color = Color(0xFF0D47A1) // Warna teks biru gelap
                    )
                }
            }

            HomeStatusPrw(
                homePrwUiState = viewModel.prwUIState,
                retryAction = { viewModel.getPrw() },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                onEditPrwClick = onEditPrwClick,
                onDetailPrwClick = onDetailPrwClick,
                onDeletePrwClick = {
                    viewModel.deletePrw(it.id_perawatan)
                    viewModel.getPrw()
                }
            )
        }
    }
}



@Composable
fun HomeStatusPrw(
    homePrwUiState: HomePrwUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailPrwClick: (Int) -> Unit,
    onDeletePrwClick: (Perawatan) -> Unit,
    onEditPrwClick: (Perawatan) -> Unit
) {
    when (homePrwUiState) {
        is HomePrwUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomePrwUiState.Success -> {
            if (homePrwUiState.perawatan.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Tidak ada data Perawatan",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                PrwLayout(
                    perawatan = homePrwUiState.perawatan,
                    modifier = modifier,
                    onDeletePrwClick = {onDeletePrwClick(it)},
                    onDetailPrwClick = {onDetailPrwClick(it.id_perawatan)},
                    onEditPrwClick = {onEditPrwClick(it)}
                )
            }
        }
        is HomePrwUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
