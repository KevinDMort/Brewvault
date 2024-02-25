package com.example.brewvault

data class Beer(
    val id: Int,
    val user: String,
    val brewery: String,
    val name: String,
    val style: String,
    val abv: Double,
    val volume: Int,
    val pictureUrl: String,
    val howMany: Int
)
{

}