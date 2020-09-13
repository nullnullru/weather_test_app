package com.utim.weathertestapp

import android.app.Application
import com.utim.weathertestapp.di.AppComponent
import com.utim.weathertestapp.di.AppModule
import com.utim.weathertestapp.di.DaggerAppComponent
import io.paperdb.Paper

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        initDI()
        Paper.init(this)
    }

    fun initDI(){
        component = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        lateinit var component: AppComponent
    }
}