package com.dev.githubtrending

import android.app.Application
import com.dev.githubtrending.data.DataRepository
import com.dev.githubtrending.data.local.GithubDB
import com.dev.githubtrending.data.local.GithubLocalDataSource
import com.dev.githubtrending.data.network.GithubNetworkDataSource
import com.dev.githubtrending.data.preferences.GithubPreferencesDataStore

class MainApplication : Application() {

    lateinit var dataRepository: DataRepository
    lateinit var networkDataSource: GithubNetworkDataSource
    lateinit var dataStore: GithubPreferencesDataStore
    lateinit var database: GithubDB
    lateinit var localDataSource: GithubLocalDataSource

    override fun onCreate() {
        super.onCreate()
        networkDataSource = GithubNetworkDataSource.get()
        dataStore = GithubPreferencesDataStore(this)
        database = GithubDB.get(this)
        localDataSource = database.githubDao()
        dataRepository = DataRepository(database, dataStore, networkDataSource, localDataSource)
    }
}