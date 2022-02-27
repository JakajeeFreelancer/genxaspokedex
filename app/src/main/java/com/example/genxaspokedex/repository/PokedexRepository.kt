package com.example.genxaspokedex.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.genxaspokedex.models.PokedexResponse
import com.example.genxaspokedex.models.Pokemon
import com.example.genxaspokedex.providers.PokedexService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokedexRepository {
    val pokedexLiveData: MutableLiveData<List<Pokemon>> = MutableLiveData<List<Pokemon>>()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getPokedexData(limit: Int?,offset:Int?,keyword:String) {

        val pokedexService = PokedexService.create().getPokedexData(limit,offset)
        if (pokedexService != null) {

            pokedexService.enqueue( object : Callback<PokedexResponse?> {
                override fun onResponse(
                    call: Call<PokedexResponse?>,
                    response: Response<PokedexResponse?>
                ) {
                    if(response.body() != null)
                    {
                        var pokemonList : MutableList<Pokemon> = mutableListOf()
                        if(!keyword.isNullOrEmpty())
                        {
                            response.body()?.results?.forEach { pokemon ->
                                if(pokemon.name.contains(keyword,true)) {
                                    pokemonList.add(pokemon)
                                }
                            }
                            pokedexLiveData.postValue(pokemonList)
                        }
                        else
                        {
                            pokedexLiveData.postValue(response.body()?.results)
                        }
                    }
                }

                override fun onFailure(call: Call<PokedexResponse?>, t: Throwable) {
                    val mockData = listOf<Pokemon>()
                    pokedexLiveData.postValue(mockData)
                }
            })

        }
        else {
            //var pokemons = listOf( Pokemon( "name1", "url1" ), Pokemon( "name2", "url2" ))
            ////
            //return pokemons
        }
    }

    fun getPokedexLiveData(): LiveData<List<Pokemon>> {
        return pokedexLiveData
    }
}