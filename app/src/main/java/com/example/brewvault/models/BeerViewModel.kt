package com.example.brewvault.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.brewvault.Beer
import com.example.brewvault.repositories.BeersRepository


class BeerViewModel : ViewModel() {
    private val repository: BeersRepository = BeersRepository()
    val beersLiveData: LiveData<List<Beer>?> = repository.beerLiveData
    val errorMessage: LiveData<String> = repository.errorMessageLiveData
    fun get(position: Int): Beer? {
        return beersLiveData.value?.get(position)
    }
}