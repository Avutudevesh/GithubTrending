package com.example.githubtrending.injection

import android.content.Context
import com.example.githubtrending.MainActivity
import com.example.githubtrending.network.injection.NetworkModule
import com.example.githubtrending.viewmodel.MainActivityViewModelModule
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        NetworkModule::class,
        MainActivityViewModelModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)

}