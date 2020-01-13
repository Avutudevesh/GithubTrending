package com.example.githubtrending.injection

import com.example.githubtrending.MainActivity
import com.example.githubtrending.viewmodel.MainActivityViewModelModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindings {
    @ContributesAndroidInjector(modules = [ MainActivityViewModelModule::class])
    abstract fun bindMainActivity(): MainActivity
}