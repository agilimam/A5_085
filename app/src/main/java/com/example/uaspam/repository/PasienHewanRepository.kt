package com.example.uaspam.repository

import com.example.uaspam.model.PasienHewan
import com.example.uaspam.model.PasienResponse
import com.example.uaspam.model.PasienResponseDetail
import com.example.uaspam.service.PasienHewanService
import okio.IOException

interface PasienHewanRepository {
    suspend fun getPasienHewan() : PasienResponse
    suspend fun insertPasienHewan(pasienHewan: PasienHewan)
    suspend fun updatePasienHewan(id_hewan: Int, pasienHewan: PasienHewan)
    suspend fun deletePasienHewan(id_hewan: Int)
    suspend fun getPasienHewanById(id_hewan: Int):PasienResponseDetail
}

class NetworkPasienHewanRepository(
    private val pasienApiService: PasienHewanService
) : PasienHewanRepository{

    override suspend fun insertPasienHewan(pasienHewan: PasienHewan) {
        pasienApiService.insertPasienHewan(pasienHewan)
    }

    override suspend fun updatePasienHewan(id_hewan: Int, pasienHewan: PasienHewan) {
        pasienApiService.updatePasienHewan(id_hewan,pasienHewan)
    }

    override suspend fun deletePasienHewan(id_hewan: Int) {
        val response = pasienApiService.deletePasienHewan(id_hewan)
        if (!response.isSuccessful) {
            throw IOException("Failed to delete pasienHewan. HTTP Status Code:" + "${response.code()}")
        } else {
            println(response.message())
        }
    }


    override suspend fun getPasienHewan(): PasienResponse {
        return pasienApiService.getPasienHewan()
    }

    override suspend fun getPasienHewanById(id_hewan: Int): PasienResponseDetail {
        return pasienApiService.getPasienHewanById(id_hewan)
    }
}