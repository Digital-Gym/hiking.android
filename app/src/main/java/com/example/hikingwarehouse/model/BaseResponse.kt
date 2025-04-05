package com.example.hikingwarehouse.model

import kotlinx.serialization.Serializable

@Serializable
open class BaseResponse<T>(
    val code: Int,
    val status: String,
    val message: String,
    val data: T
)