package com.example.uaspam.ui.ViewModel


import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uaspam.PetShopApplication
import com.example.uaspam.ui.ViewModel.Dokter.HomeDokterViewModel
import com.example.uaspam.ui.ViewModel.Dokter.InsertDokterViewModel
import com.example.uaspam.ui.ViewModel.Dokter.UpdateDokterViewModel
import com.example.uaspam.ui.ViewModel.JenisHewan.HomeJenisViewModel
import com.example.uaspam.ui.ViewModel.JenisHewan.InsertJenisViewModel
import com.example.uaspam.ui.ViewModel.JenisHewan.UpdateJenisViewModel
import com.example.uaspam.ui.ViewModel.PasienHewan.DetailViewModel
import com.example.uaspam.ui.ViewModel.PasienHewan.HomeViewModel
import com.example.uaspam.ui.ViewModel.PasienHewan.InsertViewModel
import com.example.uaspam.ui.ViewModel.PasienHewan.UpdateViewModel
import com.example.uaspam.ui.ViewModel.Perawatan.DetailPrwViewModel
import com.example.uaspam.ui.ViewModel.Perawatan.HomePerawatanViewModel
import com.example.uaspam.ui.ViewModel.Perawatan.InsertPrwViewModel
import com.example.uaspam.ui.ViewModel.Perawatan.UpdatePrwViewModel


object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiHewan().container.pasienHewanRepository,aplikasiHewan().container.jenisHewanRepository)}
        initializer { InsertViewModel(aplikasiHewan().container.pasienHewanRepository,aplikasiHewan().container.jenisHewanRepository) }
        initializer { DetailViewModel(createSavedStateHandle(),aplikasiHewan().container.pasienHewanRepository,aplikasiHewan().container.jenisHewanRepository) }
        initializer { UpdateViewModel(createSavedStateHandle(),aplikasiHewan().container.pasienHewanRepository,aplikasiHewan().container.jenisHewanRepository) }

        //JENIS HEWAN
        initializer { HomeJenisViewModel(aplikasiHewan().container.jenisHewanRepository) }
        initializer { InsertJenisViewModel(aplikasiHewan().container.jenisHewanRepository) }
        initializer { UpdateJenisViewModel(createSavedStateHandle(),aplikasiHewan().container.jenisHewanRepository) }

    }
}

fun CreationExtras.aplikasiHewan(): PetShopApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PetShopApplication)