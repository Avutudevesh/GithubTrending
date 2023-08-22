package com.dev.githubtrending

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.dev.githubtrending.data.models.GithubRepo
import com.dev.githubtrending.ui.components.ErrorLoadingNextPageItem
import com.dev.githubtrending.ui.components.Header
import com.dev.githubtrending.ui.components.LoadingNextPageItem
import com.dev.githubtrending.ui.components.PageRefreshError
import com.dev.githubtrending.ui.components.PageRefreshLoading
import com.dev.githubtrending.ui.components.RepoListItem
import com.dev.githubtrending.ui.theme.GithubTrendingTheme
import com.dev.githubtrending.utils.OnBottomReached

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubTrendingTheme {
                val gitReposPagingItems = viewModel.trendingGithubReposFlow.collectAsLazyPagingItems()
                val expandedIndex: MutableState<Int?> = remember {
                    mutableStateOf(null)
                }
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column {
                        Header(modifier = Modifier.fillMaxWidth())
                        TrendingRepoContainer(gitReposPagingItems = gitReposPagingItems, expandedIndex = expandedIndex, modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}

@Composable
fun TrendingRepoContainer(gitReposPagingItems: LazyPagingItems<GithubRepo>, expandedIndex: MutableState<Int?>, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        val listState = rememberLazyListState()
        LazyColumn(modifier = modifier, state = listState) {
            if (gitReposPagingItems.loadState.refresh == LoadState.Loading) {
                item {
                    PageRefreshLoading()
                }
            }
            if (gitReposPagingItems.loadState.refresh is LoadState.Error) {
                item {
                    PageRefreshError(error = (gitReposPagingItems.loadState.refresh as LoadState.Error).error)
                }
            }

            items(gitReposPagingItems.itemCount, key = gitReposPagingItems.itemKey { it.id }) { index ->
                gitReposPagingItems[index]?.let {
                    RepoListItem(
                        repo = it,
                        expanded = expandedIndex.value == index,
                        onClick = {
                            if (expandedIndex.value == index) {
                                expandedIndex.value = null
                            } else {
                                expandedIndex.value = index
                            }
                        }
                    )
                }
            }

            when (gitReposPagingItems.loadState.append) {
                LoadState.Loading -> {
                    item {
                        LoadingNextPageItem()
                    }
                }

                is LoadState.Error -> {
                    item {
                        ErrorLoadingNextPageItem(onClick = { gitReposPagingItems.retry() })
                    }
                }

                else -> {
                    //Ignore
                }
            }
        }
    }
}