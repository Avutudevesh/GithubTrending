package com.example.githubtrending.network.injection

import com.example.githubtrending.network.GithubApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideGithubApiService(): GithubApiService {
        val baseUrl = "https://github-trending-api.now.sh/"
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(GithubApiService::class.java)
    }
}