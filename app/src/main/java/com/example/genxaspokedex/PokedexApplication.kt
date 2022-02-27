package com.example.genxaspokedex

import android.app.Application
import com.example.genxaspokedex.repository.PokedexRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class PokedexApplication : Application() {
    //val applicationScope = CoroutineScope(SupervisorJob())
    //val database by lazy { SampleRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { PokedexRepository() }
}