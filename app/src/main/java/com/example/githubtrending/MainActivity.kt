package com.example.githubtrending

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubtrending.network.GithubApiService
import com.example.githubtrending.view.adapter.GitHubRepoListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var apiService: GithubApiService
    @Inject
    lateinit var gitHubRepoListAdapter: GitHubRepoListAdapter

    private var job = Job()

    private var coroutineScope = CoroutineScope(job + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApplication).appComponent.inject(this)
        setContentView(R.layout.activity_main)
        setUpRecyclerView()
        getContent()
    }

    private fun setUpRecyclerView() {
        github_repo_recycler_view.apply {
            adapter = gitHubRepoListAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun getContent() {
        coroutineScope.launch {
            var getRepositoriesDeferred = apiService.getRepositories()
            try {
                val listResult = getRepositoriesDeferred.await()
                gitHubRepoListAdapter.submitList(listResult)
            } catch (e: Exception) {
                Log.d("Error", e.toString())
            }
        }
    }
}
