package com.example.hikingwarehouse.ui.screens

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikingwarehouse.data.NetworkHikingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed interface AddProductUiState {
    data class Success(val message: String) : AddProductUiState
    data object Error : AddProductUiState
    data object Loading : AddProductUiState
    data object Idle: AddProductUiState
}


class AddProductViewModel : ViewModel() {
    var addProductUiState: AddProductUiState by mutableStateOf(AddProductUiState.Idle)
        private set

    // Fields
    var name by mutableStateOf("")
    var category by mutableStateOf("Hiking")
    var brand by mutableStateOf("The North Face")
    var price by mutableStateOf("")
    var quantity by mutableStateOf("")
    var color by mutableStateOf("")
    var size by mutableStateOf("")
    var waterproof by mutableStateOf(false)
    var uvResistant by mutableStateOf(false)
    var comments by mutableStateOf("")

    // hard coded dropdown options
    val categories = listOf("Tents", "Backpacks", "Cooking", "Shoes", "Clothes")
    val brands = listOf("The North Face", "Nike", "Gregory", "Jetboil")
    val colors = listOf("Olive green", "Coal black", "Military", "<No color>")
    val sizes = listOf("S", "M", "L", "XL")

    // Validation
    private val _nameError = mutableStateOf<String?>(null)
    val nameError: State<String?> get() = _nameError

    private val _priceError = mutableStateOf<String?>("Price required")
    val priceError: State<String?> get() = _priceError

    private val _categoryError = mutableStateOf<String?>(null)
    val categoryError: State<String?> get() = _categoryError

    private val _brandError = mutableStateOf<String?>(null)
    val brandError: State<String?> get() = _brandError

    private val _quantityError = mutableStateOf<String?>("Quantity required")
    val quantityError: State<String?> get() = _quantityError

    private val _commentsError = mutableStateOf<String?>(null)
    val commentsError: State<String?> get() = _commentsError

    private val _formValid = MutableStateFlow(false)
    val formValid: StateFlow<Boolean> = _formValid
    private fun resetForm(){
        name = ""
        category = "Hiking"
        brand = "The North Face"
        price = ""
        quantity = ""
        color = ""
        size = ""
        waterproof = false
        uvResistant = false
        comments = ""
    }

    private fun validateField(
        fieldName: String,
        value: String,
        isRequired: Boolean,
        isNumber: Boolean,
        alwaysPositive: Boolean,
        min: Int,
        max: Int
    ): String?{
        return when {
            value.isBlank() && isRequired -> {
                "$fieldName required"
            }
            value.length < min -> {
                "Too short"
            }
            value.length > max -> {
                "Too long"
            }
            isNumber -> {
                if(value.toFloatOrNull() == null){
                    return "Must be a number"
                }
                if (value.toFloat() <= 0 && alwaysPositive){
                    return "Must be positive"
                }
                return null
            }
            else -> null
        }
    }

    fun onNameChanged(value: String) {
        name = value
        _nameError.value = validateField(
            "Name",
            value,
            isRequired = true,
            isNumber = false,
            alwaysPositive = false,
            min = 3,
            max = 30
        )
        validateForm()
    }

    fun onPriceChanged(value: String) {
        price = value
        _priceError.value = validateField(
            "Price",
            value,
            isRequired = true,
            isNumber = true,
            alwaysPositive = true,
            min = 3,
            max = 20
        )
        validateForm()
    }

    fun onQuantityChanged(value: String) {
        quantity = value
        _quantityError.value = validateField(
            "Quantity",
            value,
            isRequired = true,
            isNumber = true,
            alwaysPositive = true,
            min = 1,
            max = 10
        )
        validateForm()
    }

    fun onCategoryChanged(value: String) {
        category = value
        _categoryError.value = validateField(
            "Category",
            value,
            isRequired = true,
            isNumber = false,
            alwaysPositive = false,
            min = 3,
            max = 30
        )
        validateForm()
    }

    fun onBrandChanged(value: String) {
        brand = value
        _brandError.value = validateField(
            "Brand",
            value,
            isRequired = true,
            isNumber = false,
            alwaysPositive = false,
            min = 3,
            max = 30
        )
        validateForm()
    }

    fun onCommentsChanged(value: String) {
        comments = value
        _commentsError.value = validateField(
            "Comments",
            value,
            isRequired = false,
            isNumber = false,
            alwaysPositive = false,
            min = 3,
            max = 120
        )
        validateForm()
    }

    private fun validateForm() {
        _formValid.value = listOf(
            _nameError.value,
            _priceError.value,
            _quantityError.value,
            _categoryError.value,
            _brandError.value,
            _commentsError.value
        ).all { it == null }
    }

    fun onSubmit() {
        println("Submitting: $name, $category, $price, etc.")

        viewModelScope.launch {
            addProductUiState = AddProductUiState.Loading
            addProductUiState = try {
                val hikingRepository = NetworkHikingRepository()
                val results = hikingRepository.addItem(
                    name, category, brand, price, quantity, color, size, waterproof, uvResistant, comments
                )

                resetForm()

                AddProductUiState.Success(results.message)
            } catch (e: Exception){
                println("[Error bro!] $e")
                AddProductUiState.Error
            }
        }
    }
}
