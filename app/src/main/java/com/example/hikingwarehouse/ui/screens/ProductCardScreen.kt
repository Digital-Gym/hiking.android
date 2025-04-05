package com.example.hikingwarehouse.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProductCardScreen(modifier: Modifier, id: String){
    Text("Product $id", modifier = modifier.fillMaxSize())
}