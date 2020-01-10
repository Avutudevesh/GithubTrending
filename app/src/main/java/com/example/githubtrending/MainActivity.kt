package com.example.githubtrending

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubtrending.view.adapter.GitHubRepoListAdapter
import com.example.githubtrending.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_error.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var gitHubRepoListAdapter: GitHubRepoListAdapter
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: MainActivityViewModel

    private enum class Child {
        LOADED,
        ERROR
    }

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
        retry_button.setOnClickListener {
            viewModel.fetchGitHubRepoData()
        }
        swipe_refresh.setOnRefreshListener {
            viewModel.fetchGitHubRepoData()
            swipe_refresh.isRefreshing = false
        }
    }

    private fun onStateChanged(state: MainActivityViewModel.State) {
        when (state) {
            is MainActivityViewModel.State.Success -> {
                gitHubRepoListAdapter.submitList(state.result)
                view_flipper.displayedChild = Child.LOADED.ordinal
            }
            is MainActivityViewModel.State.Error -> {
                view_flipper.displayedChild = Child.ERROR.ordinal
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
