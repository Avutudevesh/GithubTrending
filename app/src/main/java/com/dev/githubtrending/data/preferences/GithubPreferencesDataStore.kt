package com.dev.githubtrending.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull

class GithubPreferencesDataStore(
    private val context: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "github_preferences")

    suspend fun getLastRefreshTime(): Long {
        val preferences = context.dataStore.data.firstOrNull()
        return preferences?.get(LAST_REFRESH_TIME) ?: 0L
    }

    suspend fun saveLastRefreshTime(time: Long) {
        context.dataStore.edit { settings ->
            settings[LAST_REFRESH_TIME] = time
        }
    }

    companion object {
        private val LAST_REFRESH_TIME = longPreferencesKey("last_refresh_time")
    }


}