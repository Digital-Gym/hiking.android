package com.example.hikingwarehouse.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.hikingwarehouse.R

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.hikingwarehouse.model.ProductItem
import com.example.hikingwarehouse.ui.composables.ProductCard
import java.util.Locale


@Composable
fun HomeScreen(
    modifier: Modifier,
    homeUiState: HomeUiState,
    onCardOpen: (String) -> Unit,
    onRefreshClicked: () -> Unit,
    onAddProduct: () -> Unit
){

    when (homeUiState){
        is HomeUiState.Success ->
            ProductListScreen(
                homeUiState.items,
                onCardOpen = onCardOpen,
                onRefreshClicked = onRefreshClicked,
                onAddProduct = onAddProduct
            )
        is HomeUiState.Error ->
            ErrorScreen(modifier = modifier.fillMaxSize(), onRefreshClicked = onRefreshClicked)
        is HomeUiState.Loading ->
            LoadingScreen(modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ProductListScreen(
    products: List<ProductItem>,
    modifier: Modifier = Modifier,
    onCardOpen: (String) -> Unit,
    onRefreshClicked: () -> Unit,
    onAddProduct: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    // Filter products based on search query (case-insensitive)
    val filteredProducts = if (searchQuery.isBlank()) {
        products
    } else {
        products.filter {
            it.name.lowercase(Locale.getDefault())
                .contains(searchQuery.lowercase(Locale.getDefault()))
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 32.dp, end = 16.dp, start = 16.dp, bottom = 32.dp),
        topBar = {
            Row (
                verticalAlignment = Alignment.CenterVertically
            )
            {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    label = { Text(stringResource(R.string.search)) },
                    placeholder = { Text(stringResource(R.string.search_products)) },
                    leadingIcon = {
                        Icon(Icons.Filled.Search, contentDescription = "Search Icon")
                    },
                    singleLine = true
                )

                Button(onClick = {onRefreshClicked()}) {
                    Icon(Icons.Filled.Refresh, contentDescription = stringResource(R.string.refresh) )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddProduct,
                shape = MaterialTheme.shapes.medium,
            ) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add_product))
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->

        if (filteredProducts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    if (searchQuery.isBlank()) stringResource(R.string.no_products_available) else "${stringResource(R.string.no_products_found)} \"$searchQuery\"",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            // List of Products
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(
                    items = filteredProducts,
                    key = { product -> product.id ?: product.name}
                ) { product ->
                    ProductCard(
                        product = product,
                        onClick = { onCardOpen(product.id.toString()) }
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier){
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, onRefreshClicked: () -> Unit) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {onRefreshClicked()},
        ) {
            Text(stringResource(R.string.refresh))
        }
    }
}