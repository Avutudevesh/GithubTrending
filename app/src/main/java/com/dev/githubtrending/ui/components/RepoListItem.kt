package com.dev.githubtrending.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dev.githubtrending.data.models.GithubRepo

@Composable
fun RepoListItem(repo: GithubRepo, onClick: () -> Unit, expanded: Boolean, modifier: Modifier = Modifier) {
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
            model = repo.avatar,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .clip(RoundedCornerShape(100))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = repo.owner, style = TextStyle(fontWeight = FontWeight.W400, fontSize = 12.sp, color = Color.DarkGray))
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