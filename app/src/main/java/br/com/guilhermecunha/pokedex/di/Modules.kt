package br.com.guilhermecunha.pokedex.di

import br.com.guilhermecunha.pokedex.api.AuthInterceptor
import br.com.guilhermecunha.pokedex.api.PokemonRepositoryImpl
import br.com.guilhermecunha.pokedex.api.PokemonService
import br.com.guilhermecunha.pokedex.repository.PokemonRepository
import br.com.guilhermecunha.pokedex.view.splash.SplashViewModel
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
}
val repositoryModule = module {
    single<PokemonRepository> { PokemonRepositoryImpl(get()) }
}
val networkModule = module {
    single<Interceptor> { AuthInterceptor() }
    single { createNetworkClient(get()).create(PokemonService::class.java) }
    single { createOkhttpClientAuth(get()) }
}

private fun createNetworkClient(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://pokedexdx.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
private fun createOkhttpClientAuth(authInterceptor: Interceptor): OkHttpClient {
    val builder = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addNetworkInterceptor(StethoInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
    return builder.build()
}