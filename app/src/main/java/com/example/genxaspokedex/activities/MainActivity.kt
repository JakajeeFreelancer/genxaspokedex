package com.example.genxaspokedex.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.google.android.material.textfield.TextInputLayout

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
        val pokedex by pokedexLiveData.observeAsState(initial = emptyList())
        MainLayout(pokedex)
    }

    @Composable
    fun MainLayout(pokemons: List<Pokemon>) {
        val lightColors = lightColors(
            primary = Color(0xFFFFFFFF)
        )

        val darkColors = darkColors(
            primary = Color(0xFF66ffc7)
        )

//        val colors = if (isSystemInDarkTheme()) darkColors else lightColors
        MaterialTheme (colors = lightColors){

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(start = 0.dp, end = 0.dp)
            )
            {
                
                TextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Search") }
                )

                Pokedex(pokemons)
            }
        }
    }

    @Composable
    fun Pokedex(pokemons: List<Pokemon>){
        if(pokemons.any())
        {
            Column {
                pokemons.forEach { pokemon ->
                    PokemonCard(pokemon)
                    Divider()
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
        val mockData : List<Pokemon> = listOf( Pokemon( "name1", "url1" ), Pokemon( "name2", "url2" ))
        MainLayout(mockData)
    }
}