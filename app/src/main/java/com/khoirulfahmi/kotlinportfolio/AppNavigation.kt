package com.khoirulfahmi.kotlinportfolio

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation(viewModel: HadithViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "hadith") {
        composable("hadith") {
            HadithScreen(
                onNavigateToFavorites = { navController.navigate("favorites") }
            )
        }
        composable("favorites") {
            FavoriteHadithScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDetail = { favoriteHadith ->
                    // Encode the favoriteHadith id to pass it safely through the URL
                    val encodedId = java.net.URLEncoder.encode(favoriteHadith.id, "UTF-8")
                    navController.navigate("detail/$encodedId")
                }
            )
        }
        composable(
            route = "detail/{hadithId}",
            arguments = listOf(navArgument("hadithId") { type = NavType.StringType })
        ) { backStackEntry ->
            val hadithId = backStackEntry.arguments?.getString("hadithId")
            // Decode the URL-encoded id
            val decodedId = java.net.URLDecoder.decode(hadithId, "UTF-8")
            val favoriteHadith = viewModel.getFavoriteHadithById(decodedId)
            if (favoriteHadith != null) {
                HadithDetailScreen(
                    favoriteHadith = favoriteHadith,
                    onNavigateBack = { navController.popBackStack() }
                )
            } else {
                // Handle case when favoriteHadith is null
                // You might want to show an error message or navigate back
            }
        }
    }
}