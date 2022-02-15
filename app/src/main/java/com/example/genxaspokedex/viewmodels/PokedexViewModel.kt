package com.example.genxaspokedex.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.*
import com.example.genxaspokedex.repository.PokedexRepository
import com.example.genxaspokedex.models.Pokemon
import kotlinx.coroutines.delay


class PokedexViewModel(private val repository: PokedexRepository) : ViewModel() {

    val pokemons: LiveData<List<Pokemon>> = liveData {
        val pokemonList = getPokedexData()
        emit(pokemonList)
    }

    suspend fun getPokedexData():List<Pokemon>{
        delay(2000)
        return repository.getPokedexData(30)
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