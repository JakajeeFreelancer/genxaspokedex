package com.example.genxaspokedex.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.genxaspokedex.PokedexApplication
import com.example.genxaspokedex.R
import com.example.genxaspokedex.models.PokedexResponse
import com.example.genxaspokedex.models.Pokemon
import com.example.genxaspokedex.viewmodels.PokedexViewModel
import com.example.genxaspokedex.viewmodels.PokedexViewModelFactory
import com.google.android.material.textfield.TextInputLayout
import org.intellij.lang.annotations.JdkConstants

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
    fun MainActivityCompose(pokedexLiveData: LiveData<PokedexResponse>) {
        val pokedexState: State<PokedexResponse?> = pokedexLiveData.observeAsState()
        val pokedex: PokedexResponse? = pokedexState.value
        MainLayout(pokedex)

    }

    @Composable
    fun MainLayout(pokedex: PokedexResponse?) {
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
                    .padding(10.dp)
            )
            {
                var text by remember { mutableStateOf(TextFieldValue("")) }
                TextField(
                    value = text,
                    onValueChange = { newText ->
                        text = newText
                        pokedexViewModel.getPokedexData()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    leadingIcon = {Icon(Icons.Filled.Search, "", tint = Color.DarkGray)}
                )
                if(pokedex == null){
                    Pokedex(listOf())
                }
                else{
                    Pokedex(pokedex.results)
                }
            }
        }
    }

    @Composable
    fun Pokedex(pokemons: List<Pokemon>){
        var rowCount: Int = 1
        if(pokemons.any()) {
            pokemons.forEach { pokemon ->
                PokemonCard(pokemon, ((rowCount / 10) % 2 == 0))
                Divider()
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
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                        builder = {
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = pokemon.name,//pokemon.name
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
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = pokemon.name,//pokemon.name
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = rememberImagePainter(
                        data = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                        builder = {
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }

    @Preview
    @Composable
    fun PreviewActivityLayout() {
        val mockData : PokedexResponse = PokedexResponse(14,"","",
            mutableListOf( Pokemon( "name1", "url1" ),
                Pokemon( "name2", "url2" ),
                Pokemon( "name3", "url3" ),
                Pokemon( "name4", "url4" ),
                Pokemon( "name5", "url5" ),
                Pokemon( "name6", "url6" ),
                Pokemon( "name7", "url7" ),
                Pokemon( "name8", "url8" ),
                Pokemon( "name9", "url9" ),
                Pokemon( "name10", "url10" ),
                Pokemon( "name11", "url11" ),
                Pokemon( "name12", "url12" ),
                Pokemon( "name13", "url13" ),
                Pokemon( "name14", "url14" )
            )
        )
        MainLayout(mockData)
    }
}