package com.example.hikingwarehouse.data

import com.example.hikingwarehouse.model.ItemResponse
import com.example.hikingwarehouse.model.ProductItem
import com.example.hikingwarehouse.model.StatusResponse
import com.example.hikingwarehouse.network.HikingApi


interface HikingRepository {
    suspend fun getAllItems(): List<ItemResponse>
    suspend fun addItem(
        name: String,
        category: String,
        brand: String,
        price: String,
        quantity: String,
        color: String?,
        size: String?,
        waterproof: Boolean,
        uvResistant: Boolean,
        comments: String?
    ): StatusResponse
}

class NetworkHikingRepository():HikingRepository {
    override suspend fun getAllItems(): List<ItemResponse> {
        return HikingApi.retrofitService.getAllItems().data
    }

    override suspend fun addItem(
        name: String,
        category: String,
        brand: String,
        price: String,
        quantity: String,
        color: String?,
        size: String?,
        waterproof: Boolean,
        uvResistant: Boolean,
        comments: String?
    ): StatusResponse {
        val priceDouble = price.toDoubleOrNull() ?: 0.0
        val quantityInt = quantity.toIntOrNull() ?: 0

        // Convert waterproof and uvResistant booleans to Int
        val waterproofInt = if (waterproof) 1 else 0
        val uvResistantInt = if (uvResistant) 1 else 0

        // Create the ProductItem object
        val productItem = ProductItem(
            name = name,
            category = category,
            brand = brand,
            price = priceDouble,
            quantity = quantityInt,
            color = color ?: "",
            size = size ?: "",
            waterproof = waterproofInt,
            uvResistant = uvResistantInt,
            comments = comments ?: ""
        )

        return HikingApi.retrofitService.addItem(productItem)
    }
}