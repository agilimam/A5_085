package com.example.uaspam.ui.View.Perawatan

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.model.Dokter
import com.example.uaspam.model.PasienHewan
import com.example.uaspam.model.Perawatan
import com.example.uaspam.ui.ViewModel.PenyediaViewModel
import com.example.uaspam.ui.ViewModel.Perawatan.DetailPrwUiState
import com.example.uaspam.ui.ViewModel.Perawatan.DetailPrwViewModel
import com.example.uaspam.ui.customwidget.CostumeTopAppBar
import com.example.uaspam.ui.navigation.DestinasiDetailPerawatan


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPrwScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailPrwViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailPerawatan.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getPerawatanById()
                }
            )
        }
    ) { innerPadding ->
        DetailPrwStatus(
            modifier = Modifier.padding(innerPadding),
            detailPrwUiState = viewModel.detailPrwUiState,
            retryAction = { viewModel.getPerawatanById() }
        )
    }
}


@Composable
fun DetailPrwStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailPrwUiState: DetailPrwUiState,
    viewModel: DetailPrwViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    when (detailPrwUiState) {
        is DetailPrwUiState.Loading ->OnLoading(modifier = modifier.fillMaxSize())

        is DetailPrwUiState.Success -> {
            if (detailPrwUiState.perawatan.data.id_perawatan == 0) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailPrw(
                    perawatan = detailPrwUiState.perawatan.data,
                    modifier = modifier.fillMaxWidth(),
                    psnList = viewModel.psnList,
                    dkList = viewModel.dktList
                )
            }
        }

        is DetailPrwUiState.Error ->OnError(retryAction, modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailPrw(
    psnList: List<PasienHewan>,
    dkList : List<Dokter>,
    modifier: Modifier = Modifier,
    perawatan: Perawatan
) {
    var pasienHewan= psnList.find { it.id_hewan == perawatan. id_dokter}?.nama_hewan ?: "Tidak ditemukan Pasien Hewan"
    var dokterHewan= dkList.find { it.id_dokter == perawatan. id_hewan}?.nama_dokter ?: "Tidak ditemukan Jenis Hewan"
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        )
        {
            DetailPrw(judul = "Hewan", isinya = pasienHewan)
            DetailPrw(judul = "Dokter", isinya = dokterHewan)
            DetailPrw(judul = "Tanggal Rawat", isinya = perawatan.tanggal_perawatan)
            DetailPrw(judul = "Detail Perawatan", isinya = perawatan.detail_perawatan)
        }
    }
}

@Composable
fun DetailPrw(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
){
    Column(modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start)
    {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}