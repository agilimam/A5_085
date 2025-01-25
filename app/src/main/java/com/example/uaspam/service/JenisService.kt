package com.example.uaspam.service

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

interface JenisService{
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET(".")
    suspend fun getJenisHewan() : JenisResponse

    @GET("{id_jenis_hewan}")
    suspend fun  getJenisHewanById(@Path("id_jenis_hewan") id_jenis_hewan:Int): JenisResponseDetail

    @POST("store")
    suspend fun insertJenisHewan(@Body jenishewan: Jenishewan)

    @PUT("{id_jenis_hewan}")
    suspend fun updateJenisHewan(@Path("id_jenis_hewan") id_jenis_hewan: Int, @Body jenishewan: Jenishewan)

    @DELETE("{id_jenis_hewan}")
    suspend fun deleteJenisHewan(@Path("id_jenis_hewan") id_jenis_hewan: Int) : Response<Void>
}
