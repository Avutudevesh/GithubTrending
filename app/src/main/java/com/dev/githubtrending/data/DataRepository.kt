package com.dev.githubtrending.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.dev.githubtrending.data.local.GithubDB
import com.dev.githubtrending.data.local.GithubLocalDataSource
import com.dev.githubtrending.data.local.GithubSearchMediator
import com.dev.githubtrending.data.network.GithubNetworkDataSource
import com.dev.githubtrending.data.network.models.NetworkGitRepos
import com.dev.githubtrending.data.preferences.GithubPreferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class DataRepository(
    database: GithubDB,
    dataStore: GithubPreferencesDataStore,
    private val networkDataSource: GithubNetworkDataSource,
    private val localDataSource: GithubLocalDataSource
) {

    val trendingGithubReposFlow = Pager(
        config = PagingConfig(pageSize = 30),
        remoteMediator = GithubSearchMediator(database, networkDataSource, dataStore)
    ) {
        localDataSource.pagingSource()
    }.flow.map { pagingData ->
        pagingData.map {
            it.toGithubRepo()
        }
    }


    fun getTrendingRepositories(): Flow<NetworkGitRepos> = flow {
        val repos = networkDataSource.getTrendingRepositories()
        emit(repos)
    }.flowOn(Dispatchers.IO)
}