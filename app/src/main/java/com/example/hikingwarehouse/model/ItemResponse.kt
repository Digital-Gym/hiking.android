package com.example.hikingwarehouse.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemResponse(
    @SerialName("id")
    val id: Int,

    @SerialName("student_id")
    val studentId: String,

    @SerialName("title")
    val name: String,

    @SerialName("description")
    val comments: String? = null,

    @SerialName("phone")
    val category: String,

    @SerialName("url")
    val brand: String,

    @SerialName("age")
    val age: Int? = null,

    @SerialName("is_it_true")
    val isItTrue: Boolean? = null,

    @SerialName("is_it_really_true")
    val isItReallyTrue: Boolean? = null,

    @SerialName("color")
    val color: String? = null,

    @SerialName("size")
    val unusedSize: String? = null,

    @SerialName("price")
    val price: Double,

    @SerialName("type")
    val size: String? = null,

    @SerialName("date")
    val date: String? = null,

    @SerialName("another_date")
    val anotherDate: String? = null,

    @SerialName("integer_one")
    val quantity: String? = null,

    @SerialName("integer_two")
    val waterproof: String? = null,

    @SerialName("integer_three")
    val uvResistant: String? = null,

    @SerialName("integer_four")
    val integerFour: String? = null,

    @SerialName("integer_five")
    val integerFive: String? = null,

    @SerialName("integer_six")
    val integerSix: String? = null,

    @SerialName("integer_seven")
    val integerSeven: String? = null,

    @SerialName("double_one")
    val doubleOne: String? = null,

    @SerialName("double_two")
    val doubleTwo: String? = null,

    @SerialName("double_three")
    val doubleThree: String? = null,

    @SerialName("double_four")
    val doubleFour: String? = null,

    @SerialName("double_five")
    val doubleFive: String? = null,

    @SerialName("double_six")
    val doubleSix: String? = null,

    @SerialName("double_seven")
    val doubleSeven: String? = null,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("updated_at")
    val updatedAt: String,

    @SerialName("int_list")
    val intList: List<Int> = emptyList(),

    @SerialName("text_list")
    val textList: List<String> = emptyList(),

    @SerialName("double_list")
    val doubleList: List<Double> = emptyList()
)