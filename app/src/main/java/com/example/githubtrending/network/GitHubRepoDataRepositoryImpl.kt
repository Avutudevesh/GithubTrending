package com.example.githubtrending.network

import androidx.annotation.VisibleForTesting
import javax.inject.Inject

class GitHubRepoDataRepositoryImpl @Inject constructor(
    private val githubApiService: GithubApiService
) : GitHubRepoDataRepository() {

    override suspend fun fetchGitHubRepoData(isForceRefresh: Boolean) =
        githubApiService.getRepositories(getCacheHeader(isForceRefresh)).await()

    @VisibleForTesting
    fun getCacheHeader(isForceRefresh: Boolean) =
        if (isForceRefresh) "no-cache"
        else null

}