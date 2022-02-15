package com.example.genxaspokedex.repository

import androidx.annotation.WorkerThread
import com.example.genxaspokedex.models.Pokemon

class PokedexRepository() {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getPokedexData(limit: Int?) :List<Pokemon>{
        val url = "https://pokeapi.co/api/v2/pokemon?limit=$limit&offset=0"

        ////assume it is REST API get data
        var pokemons = listOf( Pokemon( "name1", "url1" ), Pokemon( "name2", "url2" ))
        ////
        return pokemons
    }
}