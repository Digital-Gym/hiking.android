package com.example.hikingwarehouse.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikingwarehouse.data.NetworkHikingRepository
import com.example.hikingwarehouse.model.ProductItem
import kotlinx.coroutines.launch


sealed interface HomeUiState {
    data class Success(val items: List<ProductItem>) : HomeUiState
    data object Error : HomeUiState
    data object Loading : HomeUiState
}

class HomeViewModel: ViewModel() {
    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getHomeItems()
    }

    fun getHomeItems() {
        viewModelScope.launch {
            homeUiState = try {
                val hikingRepository = NetworkHikingRepository()
                val results = hikingRepository.getAllItems()

                HomeUiState.Success(results)
            } catch (e: Exception){
                Log.e("MyTag", "Error occurred: ${e.message}", e)
                HomeUiState.Error
            }
        }
    }
}