package com.example.genxaspokedex.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.genxaspokedex.PokedexApplication
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
            MainActivityCompose(pokedexViewModel.pokedexData)
        }
    }

    @Composable
    fun MainActivityCompose(pokedexLiveData: LiveData<List<Pokemon>>) {
        val pokedexState: State<List<Pokemon>?> = pokedexLiveData.observeAsState()
        val pokedex: List<Pokemon>? = pokedexState.value
        MainLayout(pokedex)
    }

    @Composable
    fun MainLayout(pokedex: List<Pokemon>?) {
        MaterialTheme {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(10.dp)
            )
            {
                var keyword by remember { mutableStateOf(TextFieldValue("")) }
                TextField(
                    value = keyword,
                    onValueChange = { newText ->
                        keyword = newText
                        pokedexViewModel.getPokedexData(100,0,keyword.text)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent),
                        leadingIcon = {Icon(
                            Icons.Filled.Search,
                            "",
                            tint = Color.DarkGray,
                            modifier = Modifier.size(40.dp))
                        }
                )
                if(pokedex == null){
                    pokedexViewModel.getPokedexData(100,0,"")
                }
                else{
                    Pokedex(pokedex)
                }
            }
        }
    }

    @Composable
    fun Pokedex(pokemons: List<Pokemon>){
        var rowCount = 9
        if(pokemons.any()) {
            pokemons.forEach { pokemon ->
                PokemonCard(pokemon, ((rowCount / 10) % 2 != 0))
                rowCount += 1
            }
        }
        else {
            Text("Data not found")
        }
    }

    @Composable
    fun PokemonCard(pokemon: Pokemon, leftAlignment: Boolean){
        if(leftAlignment) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.CenterStart),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = pokemon.imgURL,
                        builder = {
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = pokemon.name,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        else {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.CenterEnd),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = pokemon.name,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = rememberImagePainter(
                        data = pokemon.imgURL,
                        builder = {
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
            }
        }
        Divider()
    }

    @Preview
    @Composable
    fun PreviewActivityLayout() {
        val mockData  = mutableListOf( Pokemon( "name1", "https://pokeapi.co/api/v2/pokemon/1/" ),
                Pokemon( "name2", "https://pokeapi.co/api/v2/pokemon/2/" ),
                Pokemon( "name3", "https://pokeapi.co/api/v2/pokemon/3/" ),
                Pokemon( "name4", "https://pokeapi.co/api/v2/pokemon/4/" ),
                Pokemon( "name5", "https://pokeapi.co/api/v2/pokemon/5/" ),
                Pokemon( "name6", "https://pokeapi.co/api/v2/pokemon/6/" ),
                Pokemon( "name7", "https://pokeapi.co/api/v2/pokemon/7/" ),
                Pokemon( "name8", "https://pokeapi.co/api/v2/pokemon/8/" ),
                Pokemon( "name9", "https://pokeapi.co/api/v2/pokemon/9/" ),
                Pokemon( "name10", "https://pokeapi.co/api/v2/pokemon/10/" ),
                Pokemon( "name11", "https://pokeapi.co/api/v2/pokemon/11/" ),
                Pokemon( "name12", "https://pokeapi.co/api/v2/pokemon/12/" ),
                Pokemon( "name13", "https://pokeapi.co/api/v2/pokemon/13/" ),
                Pokemon( "name14", "https://pokeapi.co/api/v2/pokemon/14/" )
            )
        MainLayout(mockData)
    }
}