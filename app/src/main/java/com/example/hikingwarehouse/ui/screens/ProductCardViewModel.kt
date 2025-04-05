package com.example.hikingwarehouse.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikingwarehouse.data.NetworkHikingRepository
import com.example.hikingwarehouse.model.ItemResponse
import kotlinx.coroutines.launch

sealed interface ProductUiState {
    data class Success(val item: ItemResponse) : ProductUiState
    data object Error : ProductUiState
    data object Loading : ProductUiState
}

sealed interface ProductUpdateUiState {
    data object Success : ProductUpdateUiState
    data object Error : ProductUpdateUiState
    data object Loading : ProductUpdateUiState
    data object Idle: ProductUpdateUiState
}

sealed interface ProductDeleteUiState {
    data object Success : ProductDeleteUiState
    data object Error : ProductDeleteUiState
    data object Loading : ProductDeleteUiState
    data object Idle: ProductDeleteUiState
    data object Dialog: ProductDeleteUiState
}


class ProductCardViewModel: ViewModel() {
    var productUiState: ProductUiState by mutableStateOf(ProductUiState.Loading)
        private set

    var productUpdateUiState: ProductUpdateUiState by mutableStateOf(ProductUpdateUiState.Idle)
        private set

    var productDeleteUiState: ProductDeleteUiState by mutableStateOf(ProductDeleteUiState.Idle)


    var id: String? = null


    fun getProduct() {
        viewModelScope.launch {
            productUiState = try {
                assert(!id.isNullOrBlank())

                val hikingRepository = NetworkHikingRepository()
                val item = hikingRepository.getItemById(id = id!!)

                ProductUiState.Success(item)
            } catch (e: Exception){
                Log.e("MyTag", "Error occurred: ${e.message}", e)
                ProductUiState.Error
            }
        }
    }

    fun updateQuantity(item: ItemResponse, quantity: String) {
        item.quantity = quantity
        productUpdateUiState = ProductUpdateUiState.Loading

        viewModelScope.launch {
            productUpdateUiState = try {
                val hikingRepository = NetworkHikingRepository()
                val res = hikingRepository.updateItemById(item)

                assert(res.code == 200)
                ProductUpdateUiState.Success
            } catch (e: Exception){
                Log.e("MyTag", "Error occurred: ${e.message}", e)
                ProductUpdateUiState.Error
            }
        }
    }


    fun deleteItem(id: Int) {
        productDeleteUiState = ProductDeleteUiState.Loading
        viewModelScope.launch {
            productDeleteUiState = try {
                val hikingRepository = NetworkHikingRepository()
                val res = hikingRepository.deleteItemById(id.toString())

                assert(res.code == 200)
                ProductDeleteUiState.Success
            } catch (e: Exception){
                Log.e("MyTag", "Error occurred: ${e.message}", e)
                ProductDeleteUiState.Error
            }
        }
    }
}