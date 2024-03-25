package com.example.brewvault.models

import com.example.brewvault.Beer
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface BeersService {
    @GET("beers")
    fun getBeers(): Call<List<Beer>>
    @POST("beers")
    fun saveBeer(@Body beer: Beer): Call<List<Beer>>
    @PUT("beers")
    fun updateBeer(beer: Beer): Call<List<Beer>>

}