package com.example.genxaspokedex.models

data class PokedexResponse(
    var count: Int,
    var next: String,
    var previous: String,
    var results: MutableList<Pokemon>
)