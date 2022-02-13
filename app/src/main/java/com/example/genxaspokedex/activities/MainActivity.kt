package com.example.genxaspokedex.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.genxaspokedex.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ActivityLayout()
        }
    }

    @Composable
    fun ActivityLayout() {

        MaterialTheme {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "Jakajee Hyperlapse")
                        }
                    )
                }
            ) {
                Text(text = "test")
            }
        }
    }

    @Preview
    @Composable
    fun PreviewActivityLayout() {
        ActivityLayout()
    }
}