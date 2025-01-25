package com.example.uaspam.repository

import com.example.uaspam.model.JenisResponse
import com.example.uaspam.model.JenisResponseDetail
import com.example.uaspam.model.Jenishewan


import com.example.uaspam.service.JenisService
import java.io.IOException

interface JenisHewanRepository {
    suspend fun getJenisHewan() : JenisResponse
    suspend fun insertJenisHewan( jenishewan: Jenishewan)
    suspend fun updateJenisHewan(id_jenis_hewan: Int, jenishewan: Jenishewan)
    suspend fun deleteJenisHewan(id_jenis_hewan: Int)
    suspend fun getJenisHewanById(id_jenis_hewan: Int): JenisResponseDetail
}

class NetworkJenisHewanRepository(
    private val jenisApiService: JenisService
) : JenisHewanRepository{

    override suspend fun getJenisHewan(): JenisResponse {
        return jenisApiService.getJenisHewan()
    }

    override suspend fun getJenisHewanById(id_jenis_hewan: Int): JenisResponseDetail {
         return jenisApiService.getJenisHewanById(id_jenis_hewan)
    }

    override suspend fun deleteJenisHewan(id_jenis_hewan: Int) {
        val response = jenisApiService.deleteJenisHewan(id_jenis_hewan)
        if (!response.isSuccessful) {
            throw IOException("Failed to delete pasienHewan. HTTP Status Code:" + "${response.code()}")
        } else {
            println(response.message())
        }
    }

    override suspend fun insertJenisHewan(jenishewan: Jenishewan) {
        return jenisApiService.insertJenisHewan(jenishewan)
    }

    override suspend fun updateJenisHewan(id_jenis_hewan: Int, jenishewan: Jenishewan) {
       return jenisApiService.updateJenisHewan(id_jenis_hewan,jenishewan)
    }
}