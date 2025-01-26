package com.example.uaspam.ui.navigation


interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

//PASIEN HEWAN
object DestinasiHome : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Daftar Pasien Hewan"
}

object DestinasiEntry : DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = "Masukkan Data Hewan"
}

object DestinasiDetail : DestinasiNavigasi {
    override val route = "Detail"
    override val titleRes = "Detail Hewan"
    const val ID = "id_hewan"
    val routeWithArgs = "$route/{$ID}"
}

object DestinasiUpdate : DestinasiNavigasi {
    override val route = "update"
    override val titleRes = "Edit Hewan"
    const val ID = "id_hewan"
    val routeWithArgs = "$route/{$ID}"
}
