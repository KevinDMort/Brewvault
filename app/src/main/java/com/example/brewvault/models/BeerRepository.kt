package com.example.brewvault.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.brewvault.Beer
import com.example.brewvault.models.BeersService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BeersRepository {
    private val baseUrl = "https://anbo-restbeer.azurewebsites.net/api/"
    private val beersService: BeersService
    val beerLiveData: MutableLiveData<List<Beer>?> = MutableLiveData<List<Beer>?>()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        beersService = retrofit.create(BeersService::class.java)
        getBeers()
    }
    fun getBeers() {
        val call: Call<List<Beer>> = beersService.getBeers()
        call.enqueue(object : Callback<List<Beer>?> {
            override fun onResponse(
                call: Call<List<Beer>?>,
                response: Response<List<Beer>?>
            ) {
                if (response.isSuccessful) {
                    Log.d("APPLE", response.body().toString() + "HERE")
                    beerLiveData.value = response.body()
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    //errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }
            override fun onFailure(call: Call<List<Beer>?>, t: Throwable) {
                Log.d("APPLE", "onFailure")
                Log.d("APPLE", t.message!!)
                beerLiveData.postValue(null)
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }
}