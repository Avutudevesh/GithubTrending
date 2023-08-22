package com.dev.githubtrending.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [LocalRepo::class, RemoteKeys::class], version = 2, exportSchema = true)
abstract class GithubDB : RoomDatabase() {

    abstract fun githubDao(): GithubLocalDataSource

    abstract fun remoteKeysDao(): RemoteKeysLocalDataSource

    companion object {
        private const val DB_NAME = "githubsearch.db"
        const val REPOS_TABLE_NAME = "repos"
        const val REMOTE_KEYS_TABLE_NAME = "remote_keys"
        fun get(context: Context): GithubDB {
            return Room.databaseBuilder(
                context.applicationContext,
                GithubDB::class.java,
                DB_NAME
            ).build()
        }
    }
}