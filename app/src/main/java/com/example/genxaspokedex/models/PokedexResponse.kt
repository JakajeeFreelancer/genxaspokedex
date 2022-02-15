package com.example.genxaspokedex.models

data class PokedexResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Pokemon>
)