package com.example.githubtrending.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import com.example.githubtrending.network.GitHubRepoData
import com.example.githubtrending.network.GitHubRepoDataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MainActivityViewModelImpl @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val gitHubRepoDataRepository: GitHubRepoDataRepository
) : MainActivityViewModel() {

    private val stateLiveData = MutableLiveData<State>()

    @VisibleForTesting
    var githubRepoDataList = emptyList<GitHubRepoData>()

    override fun state() = stateLiveData

    override fun fetchGitHubRepoData(isForceRefresh: Boolean) {
        coroutineScope.launch {
            try {
                stateLiveData.value = State.Loading
                val gitHubRepoData = gitHubRepoDataRepository.fetchGitHubRepoData(isForceRefresh)
                githubRepoDataList = gitHubRepoData
                stateLiveData.value = State.Success(gitHubRepoData)
            } catch (e: Exception) {
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