package com.example.githubtrending.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.githubtrending.network.GitHubRepoData
import com.example.githubtrending.network.GitHubRepoDataRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.lang.IllegalStateException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MainActivityViewModelImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private var coroutineScope = CoroutineScope(Job() + Dispatchers.Unconfined)
    @Mock
    private lateinit var mockObserver: Observer<MainActivityViewModel.State>
    @Mock
    private lateinit var mockGithubRepoList: List<GitHubRepoData>
    @Mock
    private lateinit var mockGitHubRepoDataRepository: GitHubRepoDataRepository

    lateinit var subject: MainActivityViewModelImpl

    @Before
    fun setUp() {
        subject = MainActivityViewModelImpl(
            coroutineScope,
            mockGitHubRepoDataRepository
        )
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

    @ExperimentalCoroutinesApi
    @Test
    fun fetchGitHubRepoData_notForceRefreshWhenSuccess() = runBlockingTest {
        given(mockGitHubRepoDataRepository.fetchGitHubRepoData(false)).willReturn(mockGithubRepoList)

        subject.fetchGitHubRepoData(false)

        assertThat(subject.githubRepoDataList, IsEqual(mockGithubRepoList))
        then(mockObserver).should(Mockito.times(1)).onChanged(MainActivityViewModel.State.Loading)
        then(mockObserver).should(Mockito.times(1))
            .onChanged(MainActivityViewModel.State.Success(mockGithubRepoList))
        then(mockObserver).shouldHaveNoMoreInteractions()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun fetchGitHubRepoData_notForceRefreshWhenError() = runBlockingTest {
        given(mockGitHubRepoDataRepository.fetchGitHubRepoData(false)).willThrow(IllegalStateException())

        subject.fetchGitHubRepoData(false)

        assertThat(subject.githubRepoDataList, IsEqual(emptyList()))
        then(mockObserver).should(Mockito.times(1)).onChanged(MainActivityViewModel.State.Loading)
        then(mockObserver).should(Mockito.times(1))
            .onChanged(MainActivityViewModel.State.Error)
        then(mockObserver).shouldHaveNoMoreInteractions()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun fetchGitHubRepoData_ForceRefreshWhenSuccess() = runBlockingTest {
        given(mockGitHubRepoDataRepository.fetchGitHubRepoData(true)).willReturn(mockGithubRepoList)

        subject.fetchGitHubRepoData(true)

        assertThat(subject.githubRepoDataList, IsEqual(mockGithubRepoList))
        then(mockObserver).should(Mockito.times(1)).onChanged(MainActivityViewModel.State.Loading)
        then(mockObserver).should(Mockito.times(1)).onChanged(
            MainActivityViewModel.State.Success(
                mockGithubRepoList
            )
        )
        then(mockObserver).shouldHaveNoMoreInteractions()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun fetchGitHubRepoData_ForceRefreshWhenError() = runBlockingTest {
        given(mockGitHubRepoDataRepository.fetchGitHubRepoData(true)).willThrow(IllegalStateException())

        subject.fetchGitHubRepoData(true)

        assertThat(subject.githubRepoDataList, IsEqual(emptyList()))
        then(mockObserver).should(Mockito.times(1)).onChanged(MainActivityViewModel.State.Loading)
        then(mockObserver).should(Mockito.times(1))
            .onChanged(MainActivityViewModel.State.Error)
        then(mockObserver).shouldHaveNoMoreInteractions()
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