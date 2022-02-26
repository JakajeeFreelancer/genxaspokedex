package com.example.genxaspokedex.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.*
import com.example.genxaspokedex.models.PokedexResponse
import com.example.genxaspokedex.repository.PokedexRepository
import com.example.genxaspokedex.models.Pokemon
import kotlinx.coroutines.delay


class PokedexViewModel(private val repository: PokedexRepository) : ViewModel() {

    val pokedexData: LiveData<PokedexResponse> = repository.getPokedexLiveData()

    fun getPokedexData(){
        repository.getPokedexData(30)
    }

}

class PokedexViewModelFactory(private val repository: PokedexRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokedexViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PokedexViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}