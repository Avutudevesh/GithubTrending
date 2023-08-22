package com.dev.githubtrending.data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dev.githubtrending.data.network.GithubNetworkDataSource
import com.dev.githubtrending.data.network.models.Repo

class GithubPagingSource(
    private val networkDataSource: GithubNetworkDataSource
) : PagingSource<Int, Repo>() {
    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        // Try to find the page key of the closest page to anchorPosition from
        // either the prevKey or the nextKey; you need to handle nullability
        // here.
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        return try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = networkDataSource.getTrendingRepositories(page = nextPageNumber)
            LoadResult.Page(
                data = response.items,
                prevKey = null, // Only paging forward.
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error for
            // expected errors (such as a network failure).
            LoadResult.Error(e)
        }
    }
}