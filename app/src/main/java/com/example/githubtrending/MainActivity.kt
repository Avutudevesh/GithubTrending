package com.example.githubtrending

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
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
import kotlinx.android.synthetic.main.view_loading.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var gitHubRepoListAdapter: GitHubRepoListAdapter
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: MainActivityViewModel

    private enum class Child {
        LOADING,
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
        viewModel.fetchGitHubRepoData(false)
        viewModel.state().observe(this, Observer { onStateChanged(it) })
        retry_button.setOnClickListener {
            viewModel.fetchGitHubRepoData(true)
        }
        swipe_refresh.setOnRefreshListener {
            viewModel.fetchGitHubRepoData(true)
            swipe_refresh.isRefreshing = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sort_by_stars -> {
                viewModel.sortRepoDataByStars()
            }
            R.id.sort_by_name -> {
                viewModel.sortRepoDateByName()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onStateChanged(state: MainActivityViewModel.State) {
        when (state) {
            is MainActivityViewModel.State.Success -> {
                gitHubRepoListAdapter.submitList(state.result)
                shimmer_view_container.stopShimmer()
                Handler().postDelayed({
                    github_repo_recycler_view.smoothScrollToPosition(0)
                }, 800)
                view_flipper.displayedChild = Child.LOADED.ordinal
            }
            is MainActivityViewModel.State.Error -> {
                shimmer_view_container.stopShimmer()
                view_flipper.displayedChild = Child.ERROR.ordinal
            }
            is MainActivityViewModel.State.Loading -> {
                view_flipper.displayedChild = Child.LOADING.ordinal
                shimmer_view_container.startShimmer()
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

    override fun onPause() {
        super.onPause()
        shimmer_view_container.stopShimmer()
    }

}
