package com.example.githubtrending.injection

import com.example.githubtrending.MainActivity
import com.example.githubtrending.network.injection.NetworkModule
import dagger.Component

@Component(
    modules = [
        NetworkModule::class
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)

}