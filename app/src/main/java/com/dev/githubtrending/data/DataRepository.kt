package com.dev.githubtrending.data

import com.dev.githubtrending.data.network.GithubNetworkDataSource
import com.dev.githubtrending.data.network.models.NetworkGitRepos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DataRepository(
    private val networkDataSource: GithubNetworkDataSource
) {

    fun getTrendingRepositories(): Flow<NetworkGitRepos> = flow {
        val repos = networkDataSource.getTrendingRepositories()
        emit(repos)
    }.flowOn(Dispatchers.IO)
}