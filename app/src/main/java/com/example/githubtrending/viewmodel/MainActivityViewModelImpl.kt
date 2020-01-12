package com.example.githubtrending.viewmodel

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import com.example.githubtrending.network.GitHubRepoData
import com.example.githubtrending.network.GithubApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MainActivityViewModelImpl @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val apiService: GithubApiService
) : MainActivityViewModel() {

    private val stateLiveData = MutableLiveData<State>()

    @VisibleForTesting
    var githubRepoDataList = emptyList<GitHubRepoData>()

    override fun state() = stateLiveData

    override fun fetchGitHubRepoData(isForceRefresh: Boolean) {
        coroutineScope.launch {
            val cacheHeader = if(isForceRefresh) "no-cache" else null
            val getRepositoriesDeferred = apiService.getRepositories(cacheHeader)
            try {
                stateLiveData.value = State.Loading
                val listResult = getRepositoriesDeferred.await()
                githubRepoDataList = listResult
                stateLiveData.value = State.Success(listResult)
            } catch (e: Exception) {
                Log.d("Error", e.toString())
                githubRepoDataList = emptyList()
                stateLiveData.value = State.Error
            }
        }
    }

    override fun sortRepoDataByStars() {
        githubRepoDataList.let {
            if (it.isNotEmpty()) {
                stateLiveData.value = State.Success(it.sortedByDescending { it.stars })
            }
        }
    }

    override fun sortRepoDataByName() {
        githubRepoDataList.let {
            if (it.isNotEmpty()) {
                stateLiveData.value = State.Success(it.sortedBy { it.name.toLowerCase(Locale.ENGLISH) })
            }
        }
    }
}