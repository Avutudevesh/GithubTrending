package com.dev.githubtrending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.dev.githubtrending.data.DataRepository
import com.dev.githubtrending.data.local.GithubSearchMediator
import com.dev.githubtrending.data.network.GithubNetworkDataSource
import com.dev.githubtrending.utils.asResult

class MainViewModel(
    private val dataRepository: DataRepository
) : ViewModel() {

    fun fetchTrendingRepos() = dataRepository.getTrendingRepositories().asResult()

    val trendingGithubReposFlow = dataRepository.trendingGithubReposFlow.cachedIn(viewModelScope)

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val dataRepository = (this[APPLICATION_KEY] as MainApplication).dataRepository
                MainViewModel(
                    dataRepository = dataRepository,
                )
            }
        }
    }

}