package com.example.githubtrending.network

abstract class GitHubRepoDataRepository {

    abstract suspend fun fetchGitHubRepoData(isForceRefresh: Boolean): List<GitHubRepoData>
}