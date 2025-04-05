package com.example.hikingwarehouse.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemResponse(
    val id: Int,
    @SerialName("student_id") val studentId: String,
    val title: String,
    val description: String,
    val phone: String,
    val url: String?,
    val age: Int?,
    @SerialName("is_it_true") val isItTrue: Boolean?,
    @SerialName("is_it_really_true") val isItReallyTrue: Boolean?,
    val color: String?,
    val size: String?,
    val price: Double?,
    val type: String?,
    val date: String?,
    @SerialName("another_date") val anotherDate: String?,
    @SerialName("integer_one") val integerOne: Int?,
    @SerialName("integer_two") val integerTwo: Int?,
    @SerialName("integer_three") val integerThree: Int?,
    @SerialName("integer_four") val integerFour: Int?,
    @SerialName("integer_five") val integerFive: Int?,
    @SerialName("integer_six") val integerSix: Int?,
    @SerialName("integer_seven") val integerSeven: Int?,
    @SerialName("double_one") val doubleOne: Double?,
    @SerialName("double_two") val doubleTwo: Double?,
    @SerialName("double_three") val doubleThree: Double?,
    @SerialName("double_four") val doubleFour: Double?,
    @SerialName("double_five") val doubleFive: Double?,
    @SerialName("double_six") val doubleSix: Double?,
    @SerialName("double_seven") val doubleSeven: Double?,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("int_list") val intList: List<Int>,
    @SerialName("text_list") val textList: List<String>,
    @SerialName("double_list") val doubleList: List<Double>
)
