package com.example.uaspam.service

import com.example.uaspam.model.Dokter
import com.example.uaspam.model.DokterResponse
import com.example.uaspam.model.DokterResponseDetail
import com.example.uaspam.model.JenisResponse
import com.example.uaspam.model.JenisResponseDetail
import com.example.uaspam.model.Jenishewan
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DokterService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET(".")
    suspend fun getDokter() : DokterResponse

    @GET("{id_dokter}")
    suspend fun  getDokterById(@Path("id_dokter") id_dokter:Int): DokterResponseDetail

    @POST("store")
    suspend fun insertDokter(@Body dokter: Dokter)

    @PUT("{id_dokter}")
    suspend fun updateDokter(@Path("id_dokter") id_dokter: Int, @Body dokter: Dokter)

    @DELETE("{id_dokter}")
    suspend fun deletedokter(@Path("id_dokter") id_dokter: Int) : Response<Void>
}