package com.dev.githubtrending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dev.githubtrending.data.DataRepository
import com.dev.githubtrending.utils.asResult

class MainViewModel(
    private val dataRepository: DataRepository
) : ViewModel() {

    fun fetchTrendingRepos() = dataRepository.getTrendingRepositories().asResult()

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