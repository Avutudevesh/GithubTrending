package com.example.githubtrending

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.runner.RunWith
import androidx.test.rule.ActivityTestRule
import com.example.githubtrending.network.GitHubRepoData
import com.example.githubtrending.utils.ViewModelUtil
import com.example.githubtrending.utils.createFakeActivityInjector
import com.example.githubtrending.view.adapter.GitHubRepoListAdapter
import com.example.githubtrending.view.viewholder.GitRepoItemViewHolder
import com.example.githubtrending.viewmodel.MainActivityViewModel
import org.junit.Test
import org.mockito.Mockito.*


@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private var mainActivityViewModel: MainActivityViewModel =
        mock(MainActivityViewModel::class.java)
    private var stateLiveData = MutableLiveData<MainActivityViewModel.State>()
    private lateinit var mockGitHubRepoListAdapter: GitHubRepoListAdapter
    private lateinit var resultlist: List<GitHubRepoData>


    @get:Rule
    val activityTestRule =
        object : ActivityTestRule<MainActivity>(MainActivity::class.java, true, true) {
            override fun beforeActivityLaunched() {
                super.beforeActivityLaunched()
                mockGitHubRepoListAdapter = GitHubRepoListAdapter()
                doReturn(stateLiveData).`when`(mainActivityViewModel).state()
                val myApp =
                    InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MyApplication
                myApp.activityDispatchingAndroidInjector =
                    createFakeActivityInjector<MainActivity> {
                        viewModelFactory = ViewModelUtil.createFor(mainActivityViewModel)
                        gitHubRepoListAdapter = mockGitHubRepoListAdapter
                    }
            }
        }

    @Test
    fun mainActivity_LoadingScreenShown() {
        stateLiveData.postValue(MainActivityViewModel.State.Loading)
        onView(withId(R.id.loading_view)).check(matches(isDisplayed()))
    }

    @Test
    fun mainActivity_whenContentShown() {
        givenTestList()
        stateLiveData.postValue(MainActivityViewModel.State.Success(resultlist))
        onView(withId(R.id.view_content)).check(matches(isDisplayed()))
    }

    @Test
    fun mainActivity_whenErrorErrorScreenShown() {
        stateLiveData.postValue(MainActivityViewModel.State.Error)
        onView(withId(R.id.error_view)).check(matches(isDisplayed()))
    }

    @Test
    fun performClick() {
        givenTestList()
        stateLiveData.postValue(MainActivityViewModel.State.Success(resultlist))
        onView(withId(R.id.view_content)).check(matches(isDisplayed()))
        onView(withId(R.id.github_repo_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<GitRepoItemViewHolder>(
                0,
                click()
            )
        )
    }

    private fun givenTestList() {
        resultlist = listOf(
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