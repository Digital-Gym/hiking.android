package com.example.hikingwarehouse.data

import com.example.hikingwarehouse.model.ItemResponse
import com.example.hikingwarehouse.model.ProductItem
import com.example.hikingwarehouse.model.StatusResponse
import com.example.hikingwarehouse.network.HikingApi


interface HikingRepository {
    suspend fun getAllItems(): List<ProductItem>
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
    suspend fun getItemById(id: String): ItemResponse
    suspend fun updateItemById(item: ItemResponse): StatusResponse
    suspend fun deleteItemById(id: String): StatusResponse
}

class NetworkHikingRepository:HikingRepository {
    override suspend fun getAllItems(): List<ProductItem> {
        val responseItem = HikingApi.retrofitService.getAllItems().data

        return responseItem.map { item ->
            ProductItem(
                id = item.id,
                name = item.name,
                category = item.category,
                brand = item.brand,
                price = item.price,
                quantity = item.quantity?.toInt() ?: 0,
                color = item.color ?: "",
                size = item.size ?: "",
                waterproof = item.waterproof?.toInt() ?: 0,
                uvResistant = item.uvResistant?.toInt() ?: 0,
                comments = item.comments ?: ""
            )
        }
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

    override suspend fun getItemById(id: String): ItemResponse {
        return HikingApi.retrofitService.getItemById(id.toInt()).data
    }

    override suspend fun updateItemById(item: ItemResponse): StatusResponse {
        return HikingApi.retrofitService.updateItemById(item.id, item)
    }

    override suspend fun deleteItemById(id: String): StatusResponse {
        return HikingApi.retrofitService.deleteById(id.toInt())
    }
}