package com.example.brewvault.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
    val deleteBeerErrorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    private var userEmail: String? = null

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        beersService = retrofit.create(BeersService::class.java)
    }

    fun getBeers(userEmail: String, swiperefresh: SwipeRefreshLayout? = null) {
        this.userEmail = userEmail // Store the user's email
        fetchBeers(swiperefresh)
    }

    fun fetchBeers(swiperefresh: SwipeRefreshLayout? = null) {
        userEmail?.let { email ->
            val call: Call<List<Beer>> = beersService.getBeers(email)
            call.enqueue(object : Callback<List<Beer>?> {
                override fun onResponse(
                    call: Call<List<Beer>?>,
                    response: Response<List<Beer>?>
                ) {
                    if (response.isSuccessful) {
                        beerLiveData.value = response.body()
                        swiperefresh?.isRefreshing = false
                        errorMessageLiveData.postValue("")
                    } else {
                        val message = response.code().toString() + " " + response.message()
                        //errorMessageLiveData.postValue(message)
                        Log.d("APPLE", message)
                    }
                }

                override fun onFailure(call: Call<List<Beer>?>, t: Throwable) {
                    Log.d("APPLE", "onFailure")
                    beerLiveData.postValue(null)
                    errorMessageLiveData.postValue(t.message)
                    Log.d("APPLE", t.message!!)
                }
            })
        }
    }
    fun saveBeer(beer: Beer) {
        val call: Call<List<Beer>> = beersService.saveBeer(beer)
        call.enqueue(object : Callback<List<Beer>?> {
            override fun onResponse(call: Call<List<Beer>?>, response: Response<List<Beer>?>) {
                if (response.isSuccessful) {
                    // If successful, refresh the list of beers
                    userEmail?.let { getBeers(it) }
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<List<Beer>?>, t: Throwable) {
                Log.d("APPLE", "onFailure")
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }
    fun deleteBeer(id: Int)
    {
        val call: Call<List<Beer>> = beersService.deleteBeer(id)

        call.enqueue(object : Callback<List<Beer>> {
            override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {

                if (response.isSuccessful) {
                    userEmail?.let { getBeers(it) }
                } else {
                    val message = "Error: ${response.code()} ${response.message()}"
                    deleteBeerErrorMessageLiveData.postValue(message)

                }
            }
            override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
                deleteBeerErrorMessageLiveData.postValue(t.message)
                Log.d("APPLE", "onFailure: ${t.message}")
            }
        })
    }
    fun updateBeer(id: Int, beer:Beer)
    {
        val call: Call<List<Beer>> = beersService.updateBeer(id, beer)

        call.enqueue(object : Callback<List<Beer>> {
            override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {

                if (response.isSuccessful) {
                    userEmail?.let { getBeers(it) }
                } else {
                    val message = "Error: ${response.code()} ${response.message()}"
                    deleteBeerErrorMessageLiveData.postValue(message)
                }
            }
                override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
                    deleteBeerErrorMessageLiveData.postValue(t.message)
                    Log.d("APPLE", "onFailure: ${t.message}")
                }
            })
    }

    }

