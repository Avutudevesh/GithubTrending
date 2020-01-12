package com.example.githubtrending.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.githubtrending.network.GitHubRepoData
import com.example.githubtrending.network.GithubApiService
import kotlinx.coroutines.CoroutineScope
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MainActivityViewModelImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Mock
    private lateinit var gitHubApiService: GithubApiService
    @Mock
    private lateinit var coroutineScope: CoroutineScope
    @Mock
    private lateinit var mockObserver: Observer<MainActivityViewModel.State>

    lateinit var subject: MainActivityViewModelImpl

    @Before
    fun setUp() {
        subject = MainActivityViewModelImpl(coroutineScope, gitHubApiService)
        subject.state().observeForever(mockObserver)
    }

    @Test
    fun sortRepoDataByStars_whenListIsNotEmpty() {
        //GIVEN
        val repoList = givenRepoData()
        subject.githubRepoDataList = repoList

        //WHEN
        subject.sortRepoDataByStars()

        //THEN
        then(mockObserver).should(Mockito.times(1))
            .onChanged(MainActivityViewModel.State.Success(repoList.sortedByDescending { it.stars }))
        then(mockObserver).shouldHaveNoMoreInteractions()

    }

    @Test
    fun sortRepoDataByStars_whenListEmpty() {
        //GIVEN
        subject.githubRepoDataList = emptyList()

        //WHEN
        subject.sortRepoDataByStars()

        //THEN
        then(mockObserver).shouldHaveZeroInteractions()

    }

    @Test
    fun sortRepoDataByName_whenListIsNotEmpty() {
        //GIVEN
        val repoList = givenRepoData()
        subject.githubRepoDataList = repoList

        //WHEN
        subject.sortRepoDataByName()

        //THEN
        then(mockObserver).should(Mockito.times(1))
            .onChanged(MainActivityViewModel.State.Success(repoList.sortedBy {
                it.name.toLowerCase(
                    Locale.ENGLISH
                )
            }))
        then(mockObserver).shouldHaveNoMoreInteractions()
    }

    @Test
    fun sortRepoDataByName_whenListEmpty() {
        //GIVEN
        subject.githubRepoDataList = emptyList()

        //WHEN
        subject.sortRepoDataByName()

        //THEN
        then(mockObserver).shouldHaveZeroInteractions()

    }

    private fun givenRepoData(): List<GitHubRepoData> {
        return listOf(
            GitHubRepoData(
                author = "abhat222",
                name = "Data-Science--Cheat-Sheet",
                avatar = "https://github.com/abhat222.png",
                description = "https://github.com/abhat222/Data-Science--Cheat-Sheet",
                language = null,
                stars = 121,
                forks = 2,
                languageColor = null
            ),
            GitHubRepoData(
                author = "Azure",
                name = "azure-powershell",
                avatar = "https://github.com/Azure.png",
                description = "https://github.com/Azure/azure-powershell",
                language = null,
                stars = 123,
                forks = 2,
                languageColor = null
            )
        )
    }


}