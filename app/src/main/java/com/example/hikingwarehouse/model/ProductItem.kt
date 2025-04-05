package com.example.hikingwarehouse.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductItem (
    val id: Int? = null,
    @SerialName("title")
    val name: String,
    @SerialName("phone")
    val category: String,
    @SerialName("url")
    val brand: String,
    val price: Double,
    @SerialName("integer_one")
    val quantity: Int,
    @SerialName("color")
    val color: String,
    @SerialName("type")
    val size: String,
    @SerialName("integer_two")
    val waterproof: Int,
    @SerialName("integer_three")
    val uvResistant: Int,
    @SerialName("description")
    val comments: String
)