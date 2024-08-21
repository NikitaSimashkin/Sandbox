package ru.kram.sandbox.paging3.di

import androidx.room.Room
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.dsl.onClose
import ru.kram.sandbox.paging3.data.PokemonPagingSource
import ru.kram.sandbox.paging3.data.PokemonRemoteMediator
import ru.kram.sandbox.paging3.data.PokemonRepository
import ru.kram.sandbox.paging3.data.db.PokemonDataBase
import ru.kram.sandbox.paging3.data.network.PokemonRemoteDataSource
import ru.kram.sandbox.paging3.presentation.PokemonViewModel

val pokemonModule = module {

    single {
        Room.databaseBuilder(
            context = androidApplication(),
            klass = PokemonDataBase::class.java,
            name = "pokemon_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
            }
        }
    } onClose {
        it?.close()
    }

    single { PokemonRemoteDataSource(httpClient = get()) }

    single {
        PokemonRepository(
            pokemonRemoteDataSource = get(),
            pokemonDataBase = get()
        )
    }

    factory { PokemonRemoteMediator.Factory(get()) }

    factory { PokemonPagingSource.Factory(get()) }

    viewModel<PokemonViewModel> {
        PokemonViewModel(
            pokemonRepository = get(),
            pagingSourceFactory = get(),
            pokemonRemoteMediatorFactory = get()
        )
    }
}


