package com.example.uaspam.model

import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class Perawatan(
    @PrimaryKey(autoGenerate = true)
    val id_perawatan : Int = 0,
    val id_hewan : Int = 0,
    val id_dokter : Int = 0,
    val tanggal_perawatan : String,
    val detail_perawatan : String,
)

@Serializable
data class PerawatanResponse(
    val status : Boolean,
    val message : String,
    val data: List<Perawatan>
)
@Serializable
data class PerawatanResponseDetail(
    val status: Boolean,
    val message: String,
    val data : Perawatan
)