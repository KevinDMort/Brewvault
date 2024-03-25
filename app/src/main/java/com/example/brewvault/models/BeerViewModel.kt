package com.example.brewvault.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.brewvault.Beer
import com.example.brewvault.repositories.BeersRepository


class BeerViewModel : ViewModel() {
    private val repository: BeersRepository = BeersRepository()
    val beersLiveData: LiveData<List<Beer>?> = repository.beerLiveData
    val errorMessage: LiveData<String> = repository.errorMessageLiveData
    val deleteBeerErrorMessage: LiveData<String> = repository.deleteBeerErrorMessageLiveData

    fun get(position: Int): Beer? {
        return beersLiveData.value?.get(position)
    }
    fun reload(email: String, swiperefresh: SwipeRefreshLayout) {
        repository.getBeers(email, swiperefresh)

    }
    fun saveBeer(beer: Beer)
    {
        repository.saveBeer(beer)
    }

    fun deleteBeer(id: Int)
    {
        repository.deleteBeer(id)
    }
}