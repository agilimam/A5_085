package com.example.uaspam.repository

import com.example.uaspam.model.Perawatan
import com.example.uaspam.model.PerawatanResponse
import com.example.uaspam.model.PerawatanResponseDetail
import com.example.uaspam.service.PerawatanService
import java.io.IOException

interface PerawatanRepository {
    suspend fun getPerawatan() : PerawatanResponse
    suspend fun getPerawatanById(id_perawatan: Int): PerawatanResponseDetail
    suspend fun insertPerawatan(perawatan: Perawatan)
    suspend fun updatePerawatan(id_perawatan: Int, perawatan: Perawatan)
    suspend fun deletePerawatan(id_perawatan: Int)
}

class NetworkPerawatanRepository(
    private val perawatanApiService: PerawatanService
): PerawatanRepository{
    override suspend fun getPerawatan(): PerawatanResponse {
        return perawatanApiService.getPerawatan()
    }
    override suspend fun insertPerawatan(perawatan: Perawatan) {
        return perawatanApiService.insertPerawatan(perawatan)
    }
    override suspend fun updatePerawatan(id_perawatan: Int, perawatan: Perawatan) {
        return perawatanApiService.updatePerawatan(id_perawatan,perawatan)
    }
    override suspend fun deletePerawatan(id_perawatan: Int) {
        val response = perawatanApiService.deleteperawatan(id_perawatan)
        if (!response.isSuccessful) {
            throw IOException("Failed to delete dokter. HTTP Status Code:" + "${response.code()}")
        } else {
            println(response.message())
        }
    }
    override suspend fun getPerawatanById(id_perawatan: Int): PerawatanResponseDetail {
        return perawatanApiService.getPerawatanById(id_perawatan)
    }

}
