package com.example.githubtrending.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface GithubApiService {

    @GET("repositories")
    fun getRepositories() : Deferred<String>
}