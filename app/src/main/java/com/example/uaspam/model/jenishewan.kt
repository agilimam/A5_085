package com.example.uaspam.model

import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable
data class Jenishewan(
    @PrimaryKey(autoGenerate = true)
    val id_jenis_hewan : Int = 0,
    val nama_jenis_hewan : String,
    val deskripsi : String,
)

@Serializable
data class JenisResponse(
    val status : Boolean,
    val message : String,
    val data: List<Jenishewan>
)

@Serializable
data class JenisResponseDetail(
    val status: Boolean,
    val message: String,
    val data : Jenishewan
)