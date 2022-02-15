package com.example.genxaspokedex.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.genxaspokedex.PokedexApplication
import com.example.genxaspokedex.R
import com.example.genxaspokedex.models.Pokemon
import com.example.genxaspokedex.viewmodels.PokedexViewModel
import com.example.genxaspokedex.viewmodels.PokedexViewModelFactory

class MainActivity : AppCompatActivity() {

    private val pokedexViewModel: PokedexViewModel by viewModels {
        PokedexViewModelFactory((application as PokedexApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityCompose(pokedexViewModel.pokemons)
        }
    }

    @Composable
    fun MainActivityCompose(pokedexLiveData: LiveData<List<Pokemon>>) {
        val pokemons by pokedexLiveData.observeAsState(initial = emptyList())

        MaterialTheme {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(start = 30.dp, end = 30.dp)
            )
            {
                Column()
                {
                    TextField(
                        value = "",
                        onValueChange = {},
                        label = { Text("Search") }
                    )
                }
                Column()
                {
                    //Pokedex(pokemons.toMutableStateList())
                    Pokedex(pokemons)
                }
            }
        }
    }

    @Composable
    fun Pokedex(pokemons: List<Pokemon>?){
        if(pokemons != null && pokemons.isNotEmpty()) {
            Column {
                pokemons.forEach { pokemon ->
                    PokemonCard(pokemon)
                }
            }
        }
        else {
            Text("Data not found")
        }
    }

    @Composable
    fun PokemonCard(pokemon: Pokemon){
        Row(modifier = Modifier.padding(all = 5.dp)) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RectangleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, RectangleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = pokemon.name,//pokemon.name
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }

    @Preview
    @Composable
    fun PreviewActivityLayout() {
        val mockData : LiveData<List<Pokemon>> = liveData {
            val pokemonList = listOf( Pokemon( "name1", "url1" ), Pokemon( "name2", "url2" ))
            emit(pokemonList)
        }
        MainActivityCompose(mockData)
    }
}