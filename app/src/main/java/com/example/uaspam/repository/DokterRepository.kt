package com.example.uaspam.repository

import com.example.uaspam.model.Dokter
import com.example.uaspam.model.DokterResponse
import com.example.uaspam.model.DokterResponseDetail
import com.example.uaspam.service.DokterService
import java.io.IOException


interface DokterRepository {
    suspend fun getDokter() : DokterResponse
    suspend fun insertDokter( dokter: Dokter)
    suspend fun updateDokter(id_dokter: Int, dokter: Dokter)
    suspend fun deleteDokter(id_dokter: Int)
    suspend fun getDokterById(id_dokter: Int): DokterResponseDetail
}

class NetworkDokterRepository(
    private val dokterApiService: DokterService
) : DokterRepository{
    override suspend fun getDokter(): DokterResponse {
        return dokterApiService.getDokter()
    }
    override suspend fun getDokterById(id_dokter: Int): DokterResponseDetail {
        return dokterApiService.getDokterById(id_dokter)
    }
    override suspend fun deleteDokter(id_dokter: Int) {
        val response = dokterApiService.deletedokter(id_dokter)
        if (!response.isSuccessful) {
            throw IOException("Failed to delete dokter. HTTP Status Code:" + "${response.code()}")
        } else {
            println(response.message())
        }
    }
    override suspend fun insertDokter(dokter: Dokter) {
        return dokterApiService.insertDokter(dokter)
    }

    override suspend fun updateDokter(id_dokter: Int, dokter: Dokter) {
        return dokterApiService.updateDokter(id_dokter,dokter)
    }

}
