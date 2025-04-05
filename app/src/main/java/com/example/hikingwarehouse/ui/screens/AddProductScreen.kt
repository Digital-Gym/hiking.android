package com.example.hikingwarehouse.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


@Composable
fun AddProductScreen(viewModel: AddProductViewModel) {
    val formValid by viewModel.formValid.collectAsState()
    val context = LocalContext.current

    when (viewModel.addProductUiState) {
        is AddProductUiState.Success -> {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
        is AddProductUiState.Error -> {
            Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show()
        }
        else -> {}
    }

    Column(
        verticalArrangement = Arrangement
            .spacedBy(10.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            value = viewModel.name,
            onValueChange = viewModel::onNameChanged,
            label = { Text("Product name") },
            isError = viewModel.nameError.value != null,
            modifier = Modifier.fillMaxWidth()
        )
        viewModel.nameError.value?.let { Text(it, color = Color.Red) }

        Spacer(Modifier.height(8.dp))

        DropdownField(
            "Category",
            remember { mutableStateOf(viewModel.category) },
            viewModel.categories,
            onValueChange = viewModel::onCategoryChanged,
            isError = viewModel.categoryError.value != null
        ) {
            viewModel.category = it
        }

        DropdownField(
            "Brand",
            remember { mutableStateOf(viewModel.brand) },
            viewModel.brands,
            onValueChange = viewModel::onBrandChanged,
            isError = viewModel.brandError.value != null
        ) {
            viewModel.brand = it
        }

        Row {
            OutlinedTextField(
                value = viewModel.price,
                onValueChange = viewModel::onPriceChanged,
                label = { Text("Price") },
                modifier = Modifier.weight(1f),
                isError = viewModel.priceError.value != null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(Modifier.width(8.dp))
            OutlinedTextField(
                value = viewModel.quantity,
                onValueChange = viewModel::onQuantityChanged,
                label = { Text("Quantity") },
                modifier = Modifier.weight(1f),
                isError = viewModel.quantityError.value != null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        viewModel.priceError.value?.let { Text(it, color = Color.Red) }
        viewModel.quantityError.value?.let { Text(it, color = Color.Red) }

        Spacer(Modifier.height(8.dp))

        DropdownField(
            "Color",
            remember { mutableStateOf(viewModel.color) },
            viewModel.colors,
        ) {
            viewModel.color = it
        }

        DropdownField(
            "Size",
            remember { mutableStateOf(viewModel.size) },
            viewModel.sizes
        ) {
            viewModel.size = it
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = viewModel.waterproof, onCheckedChange = { viewModel.waterproof = it })
            Text("Waterproof")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = viewModel.uvResistant, onCheckedChange = { viewModel.uvResistant = it })
            Text("UV-resistant")
        }

        OutlinedTextField(
            value = viewModel.comments,
            onValueChange = viewModel::onCommentsChanged,
            label = { Text("Comments (optional)") },
            isError = viewModel.commentsError.value != null,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button (
            onClick = { viewModel.onSubmit() },
            enabled = formValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirm")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    label: String,
    selected: MutableState<String>,
    options: List<String>,
    onValueChange: (String) -> Unit = {},
    isError: Boolean = false,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = selected.value,
            onValueChange = {value -> onValueChange(value)},
            label = { Text(label) },
            readOnly = true,
            isError = isError,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.menuAnchor(type = MenuAnchorType.PrimaryEditable).fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selected.value = option
                        onSelect(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
