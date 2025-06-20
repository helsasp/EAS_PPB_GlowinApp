package com.example.glowinapp.model

data class Item(
    val id: Int,
    val name: String,
    val desc: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String = ""
)
