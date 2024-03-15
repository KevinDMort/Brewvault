package com.example.brewvault.models

import com.example.brewvault.Beer
import retrofit2.Call
import retrofit2.http.GET

interface BeersService {
    @GET("beers")
    fun getBeers(): Call<List<Beer>>
}