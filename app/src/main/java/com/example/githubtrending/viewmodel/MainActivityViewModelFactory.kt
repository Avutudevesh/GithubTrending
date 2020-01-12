package com.example.githubtrending.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubtrending.network.GitHubRepoDataRepository
import com.example.githubtrending.network.GithubApiService
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class MainActivityViewModelFactory @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val gitHubRepoDataRepository: GitHubRepoDataRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModelImpl(coroutineScope, gitHubRepoDataRepository) as T
    }

}