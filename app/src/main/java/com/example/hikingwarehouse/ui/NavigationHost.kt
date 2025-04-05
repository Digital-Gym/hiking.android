package com.example.hikingwarehouse.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.hikingwarehouse.ui.screens.HomeScreen
import com.example.hikingwarehouse.ui.screens.HomeViewModel
import com.example.hikingwarehouse.ui.screens.ProductCardScreen
import kotlinx.serialization.Serializable

@Composable
fun NavigationHost(modifier: Modifier){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ) {
        composable<HomeRoute> {
            val homeViewModel: HomeViewModel = viewModel()

            HomeScreen(modifier, homeViewModel.homeUiState) { cardId: String ->
                navController.navigate(
                    ProductCardRoute(id = cardId)
                )
            }
        }

        composable<ProductCardRoute> {
            val args = it.toRoute<ProductCardRoute>()
            ProductCardScreen(modifier, args.id)
        }
    }
}

@Serializable
object HomeRoute


@Serializable
data class ProductCardRoute(
    val id: String
)