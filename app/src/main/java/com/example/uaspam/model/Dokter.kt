package com.example.uaspam.model

import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class Dokter(
    @PrimaryKey(autoGenerate = true)
    val id_dokter : Int = 0,
    val nama_dokter : String,
    val spesialisasi : String,
    val kontak : String,
)
@Serializable
data class DokterResponse(
    val status : Boolean,
    val message : String,
    val data: List<Dokter>
)

@Serializable
data class DokterResponseDetail(
    val status: Boolean,
    val message: String,
    val data : Dokter
)