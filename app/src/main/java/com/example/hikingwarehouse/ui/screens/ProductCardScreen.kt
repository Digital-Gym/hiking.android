package com.example.hikingwarehouse.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hikingwarehouse.R
import com.example.hikingwarehouse.model.ItemResponse
import com.example.hikingwarehouse.ui.composables.Chip
import com.example.hikingwarehouse.ui.composables.AlertDialog

@Composable
fun ProductCardScreen(
    modifier: Modifier,
    productModel: ProductCardViewModel
){
    when (productModel.productUiState){
        is ProductUiState.Success ->
            ProductCard(
                modifier,
                item = (productModel.productUiState as ProductUiState.Success).item,
                productModel = productModel
            )
        is ProductUiState.Error ->
            ErrorScreen(
                modifier = modifier.fillMaxSize(),
                onRefreshClicked = {productModel.getProduct()}
            )
        is ProductUiState.Loading ->
            LoadingScreen(modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    item: ItemResponse,
    productModel: ProductCardViewModel
) {
    var quantity by remember { mutableStateOf(item.quantity) }
    val context = LocalContext.current

    //  update handle
    when (productModel.productUpdateUiState) {
        is ProductUpdateUiState.Success -> {
            Toast.makeText(context, stringResource(R.string.updated), Toast.LENGTH_SHORT).show()
        }
        is ProductUpdateUiState.Error -> {
            Toast.makeText(context, stringResource(R.string.error), Toast.LENGTH_SHORT).show()
        }
        else -> {}
    }


    // delete handle
    when (
        productModel.productDeleteUiState) {
        is ProductDeleteUiState.Dialog -> {
            AlertDialog(
                onDismissRequest = {
                    productModel.productDeleteUiState = ProductDeleteUiState.Idle
                },
                onConfirmation = {
                    productModel.productDeleteUiState = ProductDeleteUiState.Idle
                    productModel.deleteItem(item.id)
                },
                dialogTitle = stringResource(R.string.confirm_deletion),
                dialogText = stringResource(R.string.are_you_sure_delete),
                icon = Icons.Default.Info
            )
        }

        is ProductDeleteUiState.Success -> {
            Toast.makeText(context, stringResource(R.string.deleted), Toast.LENGTH_SHORT).show()
        }

        is ProductDeleteUiState.Error -> {
            Toast.makeText(context, stringResource(R.string.error), Toast.LENGTH_SHORT).show()
        }

        else -> {}
    }


    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row (verticalAlignment = Alignment.CenterVertically) {
                // Placeholder image. Replace with your actual image loading
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = stringResource(R.string.product_image),
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = item.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "${stringResource(R.string.quantity)}: ${item.quantity}",
                        fontSize = 14.sp
                    )
                    Row {
                        if (item.category.isNotEmpty()) {
                            Chip(text = item.category)
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                        if (item.waterproof == "1") {
                            Chip(text = stringResource(R.string.waterproof))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "${stringResource(R.string.brand)}: ${item.brand}", fontSize = 14.sp)
            Text(text = "${stringResource(R.string.price)}: ${item.price} UZS", fontSize = 14.sp)
            Text(text = "${stringResource(R.string.color)}: ${item.color}", fontSize = 14.sp)
            Text(text = "${stringResource(R.string.size)}: ${item.size}", fontSize = 14.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = stringResource(R.string.update_quantity), fontSize = 14.sp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${stringResource(R.string.quantity)}: $quantity", fontSize = 14.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton (onClick = {
                        val intVal = quantity?.toInt() ?: 0

                        if(intVal > 1){
                            quantity = (intVal - 1).toString()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = stringResource(R.string.decrease_quantity))
                    }
                    Text(text = "$quantity", fontSize = 16.sp)
                    IconButton(onClick = { quantity = ((quantity?.toInt() ?: 0) + 1).toString() }) {
                        Icon(imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = stringResource(R.string.increase_quantity))
                    }
                }
            }
            Text(text = stringResource(R.string.update_quantity_info), fontSize = 12.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    productModel.updateQuantity(item, quantity ?: "0")
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = productModel.productUpdateUiState !is ProductUpdateUiState.Loading
            ) {
                Text(text = stringResource(R.string.update))
            }

            OutlinedButton(
                onClick = {
                    productModel.productDeleteUiState = ProductDeleteUiState.Dialog
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = productModel.productDeleteUiState !is ProductDeleteUiState.Loading
            ) {
                Text(text = stringResource(R.string.delete))
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}