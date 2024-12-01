package ru.kram.pokemonpaging

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.kram.pokemonpaging.di.pokemonModule
import timber.log.Timber

class PokemonApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(pokemonModule)
            androidContext(this@PokemonApplication)
        }

        Timber.plant(Timber.DebugTree())
    }
}