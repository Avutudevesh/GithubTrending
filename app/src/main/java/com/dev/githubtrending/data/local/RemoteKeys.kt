package com.dev.githubtrending.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev.githubtrending.data.local.GithubDB.Companion.REMOTE_KEYS_TABLE_NAME

@Entity(tableName = REMOTE_KEYS_TABLE_NAME)
data class RemoteKeys(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "repo_id")
    val repoId: Long,
    val prevKey: Int?,
    val currentPage: Int,
    val nextKey: Int?,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)