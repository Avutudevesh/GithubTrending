package com.dev.githubtrending

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dev.githubtrending.data.network.models.NetworkGitRepos
import com.dev.githubtrending.data.network.models.Repo
import com.dev.githubtrending.ui.components.Forks
import com.dev.githubtrending.ui.components.Header
import com.dev.githubtrending.ui.components.Language
import com.dev.githubtrending.ui.components.Stars
import com.dev.githubtrending.ui.theme.GithubTrendingTheme
import com.dev.githubtrending.utils.Result

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubTrendingTheme {
                val uiState = viewModel.fetchTrendingRepos().collectAsState(initial = Result.Loading)
                val expandedIndex: MutableState<Int?> = remember {
                    mutableStateOf(null)
                }
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column {
                        Header(modifier = Modifier.fillMaxWidth())
                        TrendingRepoContainer(uiState = uiState, expandedIndex = expandedIndex, modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}

@Composable
fun TrendingRepoContainer(uiState: State<Result<NetworkGitRepos>>, expandedIndex: MutableState<Int?>, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        when (val state = uiState.value) {
            Result.Loading -> {
                Text(text = "Loading...")
            }

            is Result.Success -> {
                LazyColumn(modifier = modifier) {
                    items(state.data.items.size) {
                        RepoListItem(repo = state.data.items[it], expanded = expandedIndex.value == it, onClick = {
                            if (expandedIndex.value == it) {
                                expandedIndex.value = null
                            } else {
                                expandedIndex.value = it
                            }
                        })
                    }
                }
            }

            is Result.Error -> {
                Text(text = "Error ${state.exception?.localizedMessage.orEmpty()}")
            }
        }
    }
}

@Composable
fun RepoListItem(repo: Repo, onClick: () -> Unit, expanded: Boolean, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = repo.owner.avatar,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .clip(RoundedCornerShape(100))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = repo.owner.login, style = TextStyle(fontWeight = FontWeight.W400, fontSize = 12.sp, color = Color.DarkGray))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = repo.name, style = TextStyle(fontWeight = FontWeight.W600, fontSize = 16.sp, color = Color.Black))
            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = repo.description.orEmpty(), style = TextStyle(fontWeight = FontWeight.W400, fontSize = 14.sp, color = Color.Black), maxLines = 2, overflow = TextOverflow.Ellipsis)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        if (repo.language != null) {
                            Language(repo.language)
                        }
                        Stars(stars = repo.stars)
                        Forks(forks = repo.forks)
                    }
                }
            }
        }
    }
}