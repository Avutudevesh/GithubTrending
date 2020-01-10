package com.example.githubtrending.viewmodel

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

@Module
class MainActivityViewModelModule {

    @Provides
    internal fun provideViewModelFactory(mainActivityViewModelFactory: MainActivityViewModelFactory): ViewModelProvider.Factory =
        mainActivityViewModelFactory

    @Provides
    internal fun provideJob(): Job = Job()

    @Provides
    internal fun provideCoroutineScope(job: Job): CoroutineScope =
        CoroutineScope(job + Dispatchers.Main)
}