package com.example.githubtrending.network

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.assertNull
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GitHubRepoDataRepositoryImplTest {

    @Mock
    private lateinit var gitHubRepoDataApiService: GithubApiService
    @Mock
    private lateinit var gitHubRepoDataList: List<GitHubRepoData>

    private lateinit var subject: GitHubRepoDataRepositoryImpl

    @Before
    fun setUp() {
        subject = GitHubRepoDataRepositoryImpl(gitHubRepoDataApiService)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun fetchGitHubRepoData_ForceFetchDataTrue() = runBlockingTest {

        given(gitHubRepoDataApiService.getRepositories("no-cache")).willReturn(GlobalScope.async { gitHubRepoDataList })

        assertThat(subject.fetchGitHubRepoData(true), IsEqual(gitHubRepoDataList))

    }

    @ExperimentalCoroutinesApi
    @Test
    fun fetchGitHubRepoData_ForceFetchDataFalse() = runBlockingTest {

        given(gitHubRepoDataApiService.getRepositories(null)).willReturn(GlobalScope.async { gitHubRepoDataList })

        assertThat(subject.fetchGitHubRepoData(false), IsEqual(gitHubRepoDataList))

    }

    @Test
    fun getCacheHeader_whenForceFetchTrue() {
        assertThat(subject.getCacheHeader(true), IsEqual("no-cache"))
    }

    @Test
    fun getCacheHeader_whenForceFetchFalse() {
        assertNull(subject.getCacheHeader(false))
    }


}