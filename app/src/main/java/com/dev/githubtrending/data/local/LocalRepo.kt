package com.dev.githubtrending.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev.githubtrending.data.models.GithubRepo

@Entity(
    tableName = GithubDB.REPOS_TABLE_NAME
)
data class LocalRepo(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String,
    val url: String,
    val description: String?,
    val language: String?,
    val stars: String,
    val forks: String,
    val owner: String,
    val avatar: String,
    @ColumnInfo("created_time")
    val createdTime: Long = System.currentTimeMillis()
) {
    fun toGithubRepo(): GithubRepo {
        return GithubRepo(
            this.id,
            this.name,
            this.url,
            this.description,
            this.language,
            this.stars,
            this.forks,
            this.owner,
            this.avatar
        )
    }
}