package com.example.uaspam.di

import com.example.uaspam.repository.DokterRepository
import com.example.uaspam.repository.JenisHewanRepository
import com.example.uaspam.repository.NetworkDokterRepository
import com.example.uaspam.repository.NetworkJenisHewanRepository
import com.example.uaspam.repository.NetworkPasienHewanRepository
import com.example.uaspam.repository.NetworkPerawatanRepository
import com.example.uaspam.repository.PasienHewanRepository
import com.example.uaspam.repository.PerawatanRepository
import com.example.uaspam.service.DokterService
import com.example.uaspam.service.JenisService
import com.example.uaspam.service.PasienHewanService
import com.example.uaspam.service.PerawatanService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val pasienHewanRepository: PasienHewanRepository
    val jenisHewanRepository: JenisHewanRepository
    val dokterRepository: DokterRepository
    val perawatanRepository: PerawatanRepository
}

class PetShopContainer : AppContainer {

    private val json = Json { ignoreUnknownKeys = true }

    // Base URL untuk masing-masing API
    private val baseUrlPasienHewan = "http://192.168.1.4:3000/api/pasienhewan/"
    private val baseUrlJenisHewan = "http://192.168.1.4:3000/api/jenishewan/"
    private val baseUrlDokter = "http://192.168.1.4:3000/api/dokter/"
    private val baseUrlPerawatan = "http://192.168.1.4:3000/api/perawatan/"

    // Retrofit untuk API Pasien Hewan
    private val retrofitPasienHewan: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrlPasienHewan)
        .build()

    // Retrofit untuk API Jenis Hewan
    private val retrofitJenisHewan: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrlJenisHewan)
        .build()

    // Retrofit untuk API Dokter
    private val retrofitDokter: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrlDokter)
        .build()

    // Retrofit untuk API Perawatan
    private val retrofitPerawatan: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrlPerawatan)
        .build()

    // Service untuk API Pasien Hewan
    private val pasienHewanService: PasienHewanService by lazy {
        retrofitPasienHewan.create(PasienHewanService::class.java)
    }

    // Repository untuk API Pasien Hewan
    override val pasienHewanRepository: PasienHewanRepository by lazy {
        NetworkPasienHewanRepository(pasienHewanService)
    }



    // Service untuk API Jenis Hewan
    private val jenisService: JenisService by lazy {
        retrofitJenisHewan.create(JenisService::class.java)
    }

    // Repository untuk API Jenis Hewan
    override val jenisHewanRepository: JenisHewanRepository by lazy {
        NetworkJenisHewanRepository(jenisService)
    }



    // Service untuk API Dokter
    private val dokterService: DokterService by lazy {
        retrofitDokter.create(DokterService::class.java)
    }
    // Repository untuk API Dokter
    override val dokterRepository: DokterRepository by lazy {
        NetworkDokterRepository(dokterService)
    }



    // Service untuk API Perawatan
    private val perawatanService: PerawatanService by lazy {
        retrofitPerawatan.create(PerawatanService::class.java)
    }
    // Repository untuk API Perawatan
    override val perawatanRepository: PerawatanRepository by lazy {
        NetworkPerawatanRepository(perawatanService)
    }

}
