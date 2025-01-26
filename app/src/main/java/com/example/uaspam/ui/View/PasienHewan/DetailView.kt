package com.example.uaspam.ui.View.PasienHewan

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.example.uaspam.model.Jenishewan
import com.example.uaspam.model.PasienHewan
import com.example.uaspam.ui.ViewModel.PasienHewan.DetailUiState
import com.example.uaspam.ui.ViewModel.PasienHewan.DetailViewModel
import com.example.uaspam.ui.ViewModel.PenyediaViewModel
import com.example.uaspam.ui.customwidget.CostumeTopAppBar
import com.example.uaspam.ui.navigation.DestinasiDetail


@Composable
fun DetailStatus(
    retryAction: () -> Unit,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory),

    modifier: Modifier = Modifier,
    detailUiState: DetailUiState
) {
    when (detailUiState) {
        is DetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DetailUiState.Success -> {
            if (detailUiState.pasienHewan.data.id_hewan == 0) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailPsn(
                    pasienHewan = detailUiState.pasienHewan.data,
                    modifier = modifier.fillMaxWidth(),
                    jnsList = viewModel.jnsList
                )
            }
        }

        is DetailUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailPsn(
    jnsList: List<Jenishewan>,
    modifier: Modifier = Modifier,
    pasienHewan: PasienHewan
) {
    var jenishewan = jnsList.find { it.id_jenis_hewan == pasienHewan.jenis_hewan_id }?.nama_jenis_hewan ?: "Tidak ditemukan Jenis Hewan"
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        )
        {
            DetailPsn(judul = "Nama Hewan", isinya = pasienHewan.nama_hewan)
            DetailPsn(judul = "jenis hewan id", isinya = jenishewan)
            DetailPsn(judul = "Pemilik", isinya = pasienHewan.pemilik)
            DetailPsn(judul = "Kontak Pemilik", isinya = pasienHewan.kontak_pemilik)
            DetailPsn(judul = "Tanggal Lahir Hewan", isinya = pasienHewan.tanggal_lahir)
            DetailPsn(judul = "Catatan Kesehatan", isinya = pasienHewan.catatan_kesehatan)

        }

    }
}

@Composable
fun DetailPsn(
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