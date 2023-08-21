package com.dev.githubtrending

import android.app.Application
import com.dev.githubtrending.data.DataRepository
import com.dev.githubtrending.data.network.GithubNetworkDataSource

class MainApplication : Application() {

    lateinit var dataRepository: DataRepository

    override fun onCreate() {
        super.onCreate()
        dataRepository = DataRepository(GithubNetworkDataSource.get())
    }
}