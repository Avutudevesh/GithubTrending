package com.example.githubtrending.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.githubtrending.network.GithubApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModelImpl @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val apiService: GithubApiService
) : MainActivityViewModel() {

    private val stateLiveData = MutableLiveData<State>()

    override fun state() = stateLiveData

    override fun fetchGitHubRepoData() {
        coroutineScope.launch {
            val getRepositoriesDeferred = apiService.getRepositories()
            try {
                stateLiveData.value = State.Loading
                val listResult = getRepositoriesDeferred.await()
                stateLiveData.value = State.Success(listResult)
            } catch (e: Exception) {
                Log.d("Error", e.toString())
                stateLiveData.value = State.Error
            }
        }

    }
}