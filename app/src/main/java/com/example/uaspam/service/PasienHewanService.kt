package com.example.uaspam.service

import com.example.uaspam.model.PasienHewan
import com.example.uaspam.model.PasienResponse
import com.example.uaspam.model.PasienResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PasienHewanService{
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET(".")
    suspend fun getPasienHewan() : PasienResponse

    @GET("{id_hewan}")
    suspend fun  getPasienHewanById(@Path("id_hewan") id_hewan:Int): PasienResponseDetail

    @POST("store")
    suspend fun insertPasienHewan(@Body pasienHewan: PasienHewan)

    @PUT("{id_hewan}")
    suspend fun updatePasienHewan(@Path("id_hewan") id_hewan: Int, @Body pasienHewan: PasienHewan)

    @DELETE("{id_hewan}")
    suspend fun deletePasienHewan(@Path("id_hewan") id_hewan: Int) : Response<Void>
}

