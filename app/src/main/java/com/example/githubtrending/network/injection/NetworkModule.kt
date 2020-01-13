package com.example.githubtrending.network.injection

import android.content.Context
import com.example.githubtrending.MyApplication
import com.example.githubtrending.network.GitHubRepoDataRepository
import com.example.githubtrending.network.GitHubRepoDataRepositoryImpl
import com.example.githubtrending.network.GithubApiService
import com.example.githubtrending.network.NetworkInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideContext(application: MyApplication) : Context = application.applicationContext

    @Provides
    fun providesMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    fun providesCache(context: Context): Cache {
        val cacheSize = (5 * 1024 * 1024).toLong()
        return Cache(context.cacheDir, cacheSize)
    }


    @Provides
    fun provideOkHttpClient(cache: Cache, networkInterceptor: NetworkInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(networkInterceptor)
            .build()


    @Provides
    fun provideGithubApiService(moshi: Moshi, okHttpClient: OkHttpClient): GithubApiService {
        val baseUrl = "https://github-trending-api.now.sh/"
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
            .create(GithubApiService::class.java)
    }

    @Provides
    fun providesGitHubRepoDataRepository(githubApiService: GithubApiService): GitHubRepoDataRepository =
        GitHubRepoDataRepositoryImpl(githubApiService)
}