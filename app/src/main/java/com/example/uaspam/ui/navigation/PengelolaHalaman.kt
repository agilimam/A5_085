package com.example.uaspam.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uaspam.ui.View.Dokter.EntryDkScreen
import com.example.uaspam.ui.View.Dokter.HomeDkScreen
import com.example.uaspam.ui.View.Dokter.UpdateDkScreen
import com.example.uaspam.ui.View.JenisHewan.EntryJnsScreen
import com.example.uaspam.ui.View.JenisHewan.HomeJenisScreen
import com.example.uaspam.ui.View.JenisHewan.UpdateJenisScreen
import com.example.uaspam.ui.View.PasienHewan.DetailScreen
import com.example.uaspam.ui.View.PasienHewan.EntryPsnScreen
import com.example.uaspam.ui.View.PasienHewan.HomeScreen
import com.example.uaspam.ui.View.PasienHewan.UpdateScreen
import com.example.uaspam.ui.View.Perawatan.DetailPrwScreen
import com.example.uaspam.ui.View.Perawatan.EntryPrwScreen
import com.example.uaspam.ui.View.Perawatan.HomePrwScreen
import com.example.uaspam.ui.View.Perawatan.UpdateScreenPrw

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ) {
        // Home Screen
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { ID ->
                    navController.navigate("${DestinasiDetail.route}/$ID")
                },
                onEditClick = { pasienHewan ->
                    val route = DestinasiUpdate.routeWithArgs.replace(
                        "{${DestinasiUpdate.ID}}", pasienHewan.id_hewan.toString()
                    )
                    navController.navigate(route)
                },
                navigateToJenisHewanManagement = {
                    navController.navigate(DestinasiHomeJenis.route)
                },
                navigateToDokterManagement = {
                    navController.navigate(DestinasiHomeDokter.route)

                },
                navigateToPerawatanManagement = {
                    navController.navigate(DestinasiHomePerawatan.route)

                }
            )
        }

        // Entry Screen Pasien Hewan
        composable(DestinasiEntry.route) {
            EntryPsnScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) { inclusive = true }
                    }
                }
            )
        }

        // Detail Screen pasien Hewan
        composable(
            DestinasiDetail.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetail.ID) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val ID = backStackEntry.arguments?.getInt(DestinasiDetail.ID)
            ID?.let {
                DetailScreen(
                    navigateBack = {
                        navController.navigate(DestinasiHome.route) {
                            popUpTo(DestinasiHome.route) { inclusive = true }
                        }
                    },
                    onEditClick = {
                        navController.navigate(DestinasiEntryPerawatan.route){
                            popUpTo(DestinasiHome.route) { inclusive = true }
                        }
                    }
                )
            }
        }

        // Update Screen Pasien Hewan
        composable(
            DestinasiUpdate.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdate.ID) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val ID = backStackEntry.arguments?.getInt(DestinasiUpdate.ID)
            ID?.let {
                UpdateScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            } ?: Log.e("Navigasi", "ID null di DestinasiUpdate")
        }


        //HOME SCRENN JENIS HEWAN
        composable(
            DestinasiHomeJenis.route
        ) {
            HomeJenisScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryJenis.route)
                },
                onEditJenisClick = { jenisHewan ->
                    val route = DestinasiJenisUpdate.routeWithArgs.replace(
                        "{${DestinasiJenisUpdate.ID}}", jenisHewan.id_jenis_hewan.toString()
                    )
                    navController.navigate(route)
                },
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        // Insert Screen Jenis Hewan
        composable(
            DestinasiEntryJenis.route
        ) {
            EntryJnsScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeJenis.route) {
                        popUpTo(DestinasiHomeJenis.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        // Update Screen Jenis Hewan
        composable(
            DestinasiJenisUpdate.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiJenisUpdate.ID) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val ID = backStackEntry.arguments?.getInt(DestinasiJenisUpdate.ID)
            ID?.let {
                UpdateJenisScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            } ?: Log.e("Navigasi", "ID null di DestinasiJenisUpdate")
        }


        //HOME SCRENN DOKTER
        composable(
            DestinasiHomeDokter.route
        ) {
            HomeDkScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryDokter.route)
                },
                onEditDkClick = { dokter ->
                    val route = DestinasiDokterUpdate.routeWithArgs.replace(
                        "{${DestinasiDokterUpdate.ID}}", dokter.id_dokter.toString()
                    )
                    navController.navigate(route)
                },
                navigateToitemPerawatan = { navController.navigate(DestinasiEntryPerawatan.route)
                },
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHomeDokter.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        // Insert Screen Dokter
        composable(
            DestinasiEntryDokter.route
        ) {
            EntryDkScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeDokter.route) {
                        popUpTo(DestinasiHomeDokter.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        //Update Screen Dokter
        composable(
            DestinasiDokterUpdate.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDokterUpdate.ID) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val ID = backStackEntry.arguments?.getInt(DestinasiDokterUpdate.ID)
            ID?.let {
                UpdateDkScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            } ?: Log.e("Navigasi", "ID null di DestinasiDokterUpdate")
        }

        //HOME SCRENN PERAWATAN
        composable(
            DestinasiHomePerawatan.route
        ) {
            HomePrwScreen(
                navigateToItemEntryPrw = { navController.navigate(DestinasiEntryPerawatan.route)
                },
                navigateToItemEntryDk = {
                    navController.navigate(DestinasiEntryDokter.route)
                },
                onDetailPrwClick = { ID ->
                    navController.navigate("${DestinasiDetailPerawatan.route}/$ID")
                },
                onEditPrwClick = { rawat ->
                    val route = DestinasiUpdatePerawatan.routeWithArgs.replace(
                        "{${DestinasiUpdatePerawatan.ID}}", rawat.id_perawatan.toString()
                    )
                    navController.navigate(route)
                },
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHomePerawatan.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Insert Screen Insert Perawatan
        composable(
            DestinasiEntryPerawatan.route
        ) {
            EntryPrwScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomePerawatan.route) {
                        popUpTo(DestinasiHomeDokter.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        // Detail Screen Perawatan
        composable(
            DestinasiDetailPerawatan.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailPerawatan.ID) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val ID = backStackEntry.arguments?.getInt(DestinasiDetailPerawatan.ID)
            ID?.let {
                DetailPrwScreen(
                    navigateBack = {
                        navController.navigate(DestinasiHomePerawatan.route) {
                            popUpTo(DestinasiHomePerawatan.route) { inclusive = true }
                        }
                    }
                )
            }
        }
        //Update Screen Perawatan
        composable(
            DestinasiUpdatePerawatan.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdatePerawatan.ID) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val ID = backStackEntry.arguments?.getInt(DestinasiUpdatePerawatan.ID)
            ID?.let {
                UpdateScreenPrw(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            } ?: Log.e("Navigasi", "ID null di DestinasiUpdatePerawatan")
        }
    }
}

