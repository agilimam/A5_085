package com.example.uaspam.service

import com.example.uaspam.model.Dokter
import com.example.uaspam.model.DokterResponse
import com.example.uaspam.model.DokterResponseDetail
import com.example.uaspam.model.Perawatan
import com.example.uaspam.model.PerawatanResponse
import com.example.uaspam.model.PerawatanResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PerawatanService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET(".")
    suspend fun getPerawatan() : PerawatanResponse

    @GET("{id_perawatan}")
    suspend fun getPerawatanById(@Path("id_perawatan") id_perawatan:Int): PerawatanResponseDetail

    @POST("store")
    suspend fun insertPerawatan(@Body perawatan: Perawatan)

    @PUT("{id_perawatan}")
    suspend fun updatePerawatan(@Path("id_perawatan") id_perawatan: Int, @Body perawatan: Perawatan)

    @DELETE("{id_perawatan}")
    suspend fun deleteperawatan(@Path("id_perawatan") id_perawatan: Int) : Response<Void>
}