package com.dev.githubtrending.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface RemoteKeysLocalDataSource {
    @Upsert
    suspend fun upsert(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM ${GithubDB.REMOTE_KEYS_TABLE_NAME} WHERE repo_id = :id")
    suspend fun getRemoteKeyByRepoId(id: Long): RemoteKeys?

    @Query("DELETE FROM ${GithubDB.REMOTE_KEYS_TABLE_NAME}")
    suspend fun clearRemoteKeys()

    @Query("SELECT created_at FROM ${GithubDB.REMOTE_KEYS_TABLE_NAME} ORDER BY  created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?

    @Query("SELECT nextKey FROM ${GithubDB.REMOTE_KEYS_TABLE_NAME} ORDER BY nextKey DESC LIMIT 1")
    suspend fun getNextKey(): Int?
}