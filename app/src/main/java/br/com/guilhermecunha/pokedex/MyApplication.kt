package br.com.guilhermecunha.pokedex

import android.app.Application
import br.com.guilhermecunha.pokedex.di.networkModule
import br.com.guilhermecunha.pokedex.di.repositoryModule
import br.com.guilhermecunha.pokedex.di.viewModelModule
import com.facebook.stetho.Stetho
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        // Start stetho
        if(BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)
        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                listOf(
                    viewModelModule,
                    networkModule,
                    repositoryModule
                )
            )
        }
    }
}