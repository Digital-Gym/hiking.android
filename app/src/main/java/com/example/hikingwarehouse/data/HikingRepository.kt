package com.example.hikingwarehouse.data

import com.example.hikingwarehouse.model.ItemResponse
import com.example.hikingwarehouse.network.HikingApi

interface HikingRepository {
    suspend fun getAllItems(): List<ItemResponse>
}

class NetworkHikingRepository():HikingRepository {
    override suspend fun getAllItems(): List<ItemResponse> {
        return HikingApi.retrofitService.getAllItems().data
    }
}