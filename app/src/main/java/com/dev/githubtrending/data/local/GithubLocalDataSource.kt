package com.dev.githubtrending.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dev.githubtrending.data.local.GithubDB.Companion.REPOS_TABLE_NAME

@Dao
interface GithubLocalDataSource {

    @Upsert
    suspend fun upsertAll(repos: List<LocalRepo>)

    @Query("SELECT * FROM $REPOS_TABLE_NAME ORDER BY created_time")
    fun pagingSource(): PagingSource<Int, LocalRepo>

    @Query("DELETE FROM $REPOS_TABLE_NAME")
    suspend fun clearAll()

}