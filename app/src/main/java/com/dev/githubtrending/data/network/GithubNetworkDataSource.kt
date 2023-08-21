package com.dev.githubtrending.data.network

import com.dev.githubtrending.data.network.models.NetworkGitRepos
import com.dev.githubtrending.utils.Constants.GITHUB_TRENDING_BASE_URL
import com.dev.githubtrending.utils.Constants.JSON
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GithubNetworkDataSource {

    @Headers("Authorization: Bearer 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")
    @GET("/search/repositories")
    suspend fun getTrendingRepositories(
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc",
        @Query("page") page: Int = 1,
        @Query("per_page") limit: Int = 100,
        @Query("q") search: String = "followers:>=1000",
    ): NetworkGitRepos

    companion object {
        fun get(): GithubNetworkDataSource {
            val client = OkHttpClient.Builder().apply {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }.build()
            return Retrofit.Builder()
                .baseUrl(GITHUB_TRENDING_BASE_URL)
                .addConverterFactory(JSON.asConverterFactory("application/json".toMediaType()))
                .client(client)
                .build()
                .create(GithubNetworkDataSource::class.java)
        }
    }

}