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


@Composable
fun HomeScreen(
    modifier: Modifier,
    homeUiState: HomeUiState,
    onCardOpen: (String) -> Unit,
    onAddProduct: () -> Unit
){

    when (homeUiState){
        is HomeUiState.Success ->
            ResultScreen(
                homeUiState.items,
                onCardOpen = onCardOpen,
                onAddProduct = onAddProduct
            )
        is HomeUiState.Error ->
            ErrorScreen(modifier = modifier.fillMaxSize())
        is HomeUiState.Loading ->
            LoadingScreen(modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ResultScreen(
    items: String,
    modifier: Modifier = Modifier,
    onCardOpen: (String) -> Unit,
    onAddProduct: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(top = 32.dp, start = 16.dp, end=16.dp)
    ) {
        Button(
            onClick = { onCardOpen("66") }
        ) {
            Text("Go to next")
        }

        Button(
            onClick = { onAddProduct() }
        ) {
            Text("Go to next")
        }

        Text(text = items)
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
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}