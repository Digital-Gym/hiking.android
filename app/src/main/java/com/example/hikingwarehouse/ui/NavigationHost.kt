package com.example.hikingwarehouse.ui

import com.example.hikingwarehouse.ui.screens.AddProductScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.hikingwarehouse.ui.layouts.BaseLayout
import com.example.hikingwarehouse.ui.screens.AddProductViewModel
import com.example.hikingwarehouse.ui.screens.HomeScreen
import com.example.hikingwarehouse.ui.screens.HomeViewModel
import com.example.hikingwarehouse.ui.screens.ProductCardScreen
import com.example.hikingwarehouse.ui.screens.ProductCardViewModel
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

            HomeScreen(modifier, homeViewModel.homeUiState, { cardId: String ->
                navController.navigate(
                    ProductCardRoute(id = cardId)
                )
            }, {
                homeViewModel.getHomeItems()
            }
            ){
                navController.navigate(AddProductRoute)
            }
        }

        composable<ProductCardRoute> {
            val args = it.toRoute<ProductCardRoute>()
            val productViewModel: ProductCardViewModel = viewModel()
            productViewModel.id = args.id
            productViewModel.getProduct()

            BaseLayout(
                title="Product card",
                showBack = true,
                onBack = { navController.popBackStack() }
            ) {
                ProductCardScreen(
                    modifier,
                    productViewModel
                )
            }
        }

        composable<AddProductRoute> {
            val addViewModel: AddProductViewModel = viewModel()

            BaseLayout(
                title="Add product",
                showBack = true,
                onBack = { navController.popBackStack() }
            ) {
                AddProductScreen(addViewModel)
            }
        }
    }
}

@Serializable
object HomeRoute


@Serializable
data class ProductCardRoute(
    val id: String
)

@Serializable
object AddProductRoute