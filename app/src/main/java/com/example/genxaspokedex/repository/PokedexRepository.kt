package com.example.genxaspokedex.repository

import androidx.annotation.WorkerThread
import com.example.genxaspokedex.models.PokedexResponse
import com.example.genxaspokedex.models.Pokemon
import com.example.genxaspokedex.providers.IPokedexAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PokedexRepository() {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getPokedexData(limit: Int?):List<Pokemon> {
        //val url = "https://pokeapi.co/api/v2/pokemon?limit=$limit&offset=0"

        val pokedexAPI = IPokedexAPI.create().getPokedexData(30)
        val result : MutableList<Pokemon> = mutableListOf()
        if (pokedexAPI != null) {

            pokedexAPI.enqueue( object : Callback<PokedexResponse?> {
                override fun onResponse(
                    call: Call<PokedexResponse?>,
                    response: Response<PokedexResponse?>
                ) {
                    if(response?.body() != null)
                    {
                        result.clear()
                        result.addAll(response?.body()!!.results)
                    }
                }

                override fun onFailure(call: Call<PokedexResponse?>, t: Throwable) {
                    result.clear()
                }
            })
            return result.toList()
        }
        else {
            //var pokemons = listOf( Pokemon( "name1", "url1" ), Pokemon( "name2", "url2" ))
            ////
            //return pokemons
            return listOf()
        }
    }


}