package com.example.githubtrending.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header

interface GithubApiService {

    @GET("repositories")
    fun getRepositories(@Header("Cache-Control") cacheControl: String?): Deferred<List<GitHubRepoData>>
}