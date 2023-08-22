package com.dev.githubtrending.data.local

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.dev.githubtrending.data.network.GithubNetworkDataSource
import com.dev.githubtrending.data.preferences.GithubPreferencesDataStore
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class GithubSearchMediator(
    private val database: GithubDB,
    private val networkService: GithubNetworkDataSource,
    private val datastore: GithubPreferencesDataStore
) : RemoteMediator<Int, LocalRepo>() {

    private val reposDao = database.githubDao()
    private val remoteKeysDao = database.remoteKeysDao()


    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(2, TimeUnit.HOURS)
        return if (System.currentTimeMillis() - datastore.getLastRefreshTime() <= cacheTimeout) {
            // Cached data is up-to-date, so there is no need to re-fetch
            // from the network.
            Log.d("GitPaging", "skipping initial refresh")
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            Log.d("GitPaging", "requesting refresh page load")
            // Need to refresh cached data from network; returning
            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
            // APPEND and PREPEND from running until REFRESH succeeds.
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalRepo>
    ): MediatorResult {
        return try {
            // The network load method takes an optional after=<user.id>
            // parameter. For every page after the first, pass the last user
            // ID to let it continue from where it left off. For REFRESH,
            // pass null to load the first page.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    Log.d("GitPaging", "load type refresh")
                    1
                }
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND -> {
                    Log.d("GitPaging", "load type prepend")
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    val nextKey = remoteKeysDao.getNextKey()
                    Log.d("GitPaging", "load type append $nextKey")
                    nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            Log.d("GitPaging", "loading page $loadKey with size ${state.config.pageSize}")

            val response = networkService.getTrendingRepositories(
                page = loadKey,
                limit = state.config.pageSize
            )

            val endOfPagination = response.items.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    datastore.saveLastRefreshTime(System.currentTimeMillis())
                    reposDao.clearAll()
                    remoteKeysDao.clearRemoteKeys()
                }
                val prevKey = if (loadKey > 1) loadKey - 1 else null
                val nextKey = if (endOfPagination) null else loadKey + 1
                val remoteKeys = response.items.map {
                    RemoteKeys(repoId = it.id, prevKey = prevKey, currentPage = loadKey, nextKey = nextKey)
                }
                reposDao.upsertAll(response.items.map { it.toLocalRepo() })
                remoteKeysDao.upsert(remoteKeys)
            }

            MediatorResult.Success(
                endOfPaginationReached = endOfPagination
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}