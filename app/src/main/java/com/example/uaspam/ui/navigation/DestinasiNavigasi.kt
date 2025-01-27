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

// JENIS HEWAN
object DestinasiHomeJenis: DestinasiNavigasi{
    override val route = "Home_Jenis"
    override val titleRes = "Daftar Jenis Hewan"
}

object DestinasiEntryJenis :DestinasiNavigasi{
    override val route = "Insert_Jenis"
    override val titleRes = "Masukkan Jenis Hewan"
}

object DestinasiJenisUpdate: DestinasiNavigasi{
    override val route = "update_Jenis"
    override val titleRes = "Edit Jenis Hewan"
    const val ID = "id_jenis_hewan"
    val routeWithArgs = "$route/{$ID}"
}

// DOKTER
object DestinasiHomeDokter: DestinasiNavigasi{
    override val route = "Home_Dokter"
    override val titleRes = "Daftar Dokter"
}

object DestinasiEntryDokter :DestinasiNavigasi{
    override val route = "Insert_Dokter"
    override val titleRes ="Masukkan Dokter"
}

object DestinasiDokterUpdate: DestinasiNavigasi{
    override val route = "update_Dokter"
    override val titleRes = "Edit Dokter"
    const val ID = "id_dokter"
    val routeWithArgs = "$route/{$ID}"
}
