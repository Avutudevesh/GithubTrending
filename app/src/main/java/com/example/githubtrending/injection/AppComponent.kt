package com.example.githubtrending.injection

import com.example.githubtrending.MainActivity
import com.example.githubtrending.network.injection.NetworkModule
import com.example.githubtrending.viewmodel.MainActivityViewModelModule
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        NetworkModule::class,
        MainActivityViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)

}