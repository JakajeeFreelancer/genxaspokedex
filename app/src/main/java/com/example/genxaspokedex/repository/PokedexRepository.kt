package com.example.genxaspokedex.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.genxaspokedex.models.PokedexResponse
import com.example.genxaspokedex.providers.PokedexService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokedexRepository {
    val pokedexLiveData: MutableLiveData<PokedexResponse> = MutableLiveData<PokedexResponse>()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getPokedexData(limit: Int?,offset:Int?) {

        val pokedexService = PokedexService.create().getPokedexData(limit,offset)
        if (pokedexService != null) {

            pokedexService.enqueue( object : Callback<PokedexResponse?> {
                override fun onResponse(
                    call: Call<PokedexResponse?>,
                    response: Response<PokedexResponse?>
                ) {
                    if(response.body() != null)
                    {
                        pokedexLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<PokedexResponse?>, t: Throwable) {
                    val mockData = PokedexResponse(0,"","",
                        mutableListOf( )
                    )
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

    fun getPokedexLiveData(): LiveData<PokedexResponse> {
        return pokedexLiveData
    }
}