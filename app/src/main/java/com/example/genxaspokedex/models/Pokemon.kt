package com.example.genxaspokedex.models

data class Pokemon (
    val name: String,
    val url: String
)
{
    val imgURL: String
        get() {
            var tempURL = url
            tempURL = tempURL.replace("https://pokeapi.co/api/v2/pokemon/", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/")
            tempURL = tempURL.removeRange(tempURL.length - 1,tempURL.length)
            return tempURL.plus(".png")
        }
}