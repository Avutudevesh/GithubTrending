package com.example.githubtrending

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.githubtrending.network.GithubApiService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var apiService: GithubApiService

    private var job = Job()

    private var coroutineScope = CoroutineScope(job + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApplication).appComponent.inject(this)
        setContentView(R.layout.activity_main)
        getContent()
    }

    private fun getContent() {
        coroutineScope.launch {
            var getRepositoriesDeferred = apiService.getRepositories()
            try {
                val listResult = getRepositoriesDeferred.await()
                main_text.text = listResult[0].author
            } catch (e: Exception) {
                Log.d("Error", e.toString())
            }
        }
    }
}
