package com.example.githubtrending.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubtrending.network.GitHubRepoData

abstract class MainActivityViewModel : ViewModel() {

    sealed class State {
        data class Success(val result: List<GitHubRepoData>) : State()
        object Loading : State()
        object Error : State()
    }

    abstract fun state(): LiveData<State>

    abstract fun fetchGitHubRepoData()

    abstract fun sortRepoDataByStars()

}