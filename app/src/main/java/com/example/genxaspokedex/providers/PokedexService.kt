package com.example.genxaspokedex.providers

import com.example.genxaspokedex.models.PokedexResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface PokedexService {

    @GET("pokemon?")
    fun getPokedexData(@Query("limit") limit: Int?,@Query("offset")offset:Int?): Call<PokedexResponse?>?

    companion object {

        var BASE_URL = "https://pokeapi.co/api/v2/"

        fun create() : PokedexService {
            
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(PokedexService::class.java)

        }
    }
}