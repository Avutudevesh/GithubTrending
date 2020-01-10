package com.example.githubtrending

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubtrending.network.GithubApiService
import com.example.githubtrending.view.adapter.GitHubRepoListAdapter
import com.example.githubtrending.viewmodel.MainActivityViewModel
import com.example.githubtrending.viewmodel.MainActivityViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Error
import javax.inject.Inject
import javax.inject.Singleton

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var gitHubRepoListAdapter: GitHubRepoListAdapter
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApplication).appComponent.inject(this)
        setContentView(R.layout.activity_main)
        setUpActionBar()
        setUpRecyclerView()
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)
        viewModel.fetchGitHubRepoData()
        viewModel.state().observe(this, Observer { onStateChanged(it) })
    }

    private fun onStateChanged(state: MainActivityViewModel.State) {
        when (state) {
            is MainActivityViewModel.State.Success -> {
                gitHubRepoListAdapter.submitList(state.result)
            }
            is MainActivityViewModel.State.Error -> {
                //ToDo: Add error handling
            }
            is MainActivityViewModel.State.Loading -> {
                //ToDo: Add loading screen
            }
        }

    }

    private fun setUpActionBar() {
        supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.actionbar)
        }
    }

    private fun setUpRecyclerView() {
        github_repo_recycler_view.apply {
            adapter = gitHubRepoListAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

}
