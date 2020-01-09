package com.example.githubtrending

import android.app.Application
import com.example.githubtrending.injection.AppComponent
import com.example.githubtrending.injection.DaggerAppComponent

open class MyApplication : Application() {

    val appComponent : AppComponent by lazy {
        DaggerAppComponent.create()
    }
}