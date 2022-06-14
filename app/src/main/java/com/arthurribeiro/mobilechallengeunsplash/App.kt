package com.arthurribeiro.mobilechallengeunsplash

import android.app.Application
import com.arthurribeiro.mobilechallengeunsplash.modules.photosDIModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                photosDIModule
            )
        }
    }
}