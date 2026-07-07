package com.workshop.kmp.android.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun WorkshopNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "item-list") {
        composable("item-list") {
            ItemListScreen(onItemClick = { navController.navigate("item-detail/$it") })
        }
        composable(
            route = "item-detail/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.StringType }),
        ) { back ->
            val itemId = back.arguments?.getString("itemId") ?: return@composable
            ItemDetailScreen(itemId = itemId, onBack = { navController.popBackStack() })
        }
    }
}
