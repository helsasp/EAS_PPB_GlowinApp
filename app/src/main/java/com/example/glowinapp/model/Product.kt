package com.example.glowinapp.model

data class Product(
    val name: String,
    val desc: String,
    val price: String = "",
    val imageRes: Int = 0,
    val quantity: Int = 1,
    val ingredients: String = "",
    val usage: String = "",
    val brand: String,
)
