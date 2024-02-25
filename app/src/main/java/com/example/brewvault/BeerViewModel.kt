package com.example.brewvault

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class BeerViewModel : ViewModel() {
    private val _beerList = MutableLiveData<List<Beer>>()
    val beerList: LiveData<List<Beer>> get() = _beerList

    fun fetchBeerData() {
        _beerList.value = listOf(
            Beer(
                id = 1,
                user = "anbo@zealand.dk",
                brewery = "Tuborg",
                name = "Grøn",
                style = "Pilsner",
                abv = 4.6,
                volume = 33,
                pictureUrl = "https://frugt.dk/img/p/2/4/0/2/6/24026.jpg",
                howMany = 1
            ),
            Beer(
                id = 2,
                user = "another@zealand.dk",
                brewery = "Carlsberg",
                name = "Carlsberg Lager",
                style = "Lager",
                abv = 5.0,
                volume = 330,
                pictureUrl = "https://example.com/carlsberg.jpg",
                howMany = 3
            )
        )
    }
}