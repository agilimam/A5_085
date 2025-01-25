package com.example.uaspam.model


import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class PasienHewan(
    @PrimaryKey(autoGenerate = true)
    val id_hewan : Int = 0 ,
    val nama_hewan : String,
    val jenis_hewan_id : Int,
    val pemilik : String,
    val kontak_pemilik : String,
    val tanggal_lahir : String,
    val catatan_kesehatan: String,
)

@Serializable
data class PasienResponse(
    val status : Boolean,
    val message: String,
    val data: List<PasienHewan>
)

@Serializable
data class PasienResponseDetail(
    val status: Boolean,
    val message: String,
    val data: PasienHewan
)

