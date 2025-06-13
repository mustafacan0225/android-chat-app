package com.mustafacan.data.network.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mustafacan.core.domain.model.users.User
import com.mustafacan.data.network.datasource.UsersRemoteDataSource
import kotlinx.coroutines.delay

class UsersPagingSource (
    private val remoteDataSource: UsersRemoteDataSource
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val currentPage = params.key ?: 1
        val pageSize = params.loadSize

        if (currentPage == 1) {
            delay(2000)
        }

        val result = remoteDataSource.getAllUsersByPagination(currentPage, pageSize)
        return if (result.isSuccess) {
            val data = result.getOrNull()
            if (data != null) {
                LoadResult.Page(
                    data = data.users,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (currentPage == data.totalPages) null else currentPage + 1
                )
            } else {
                LoadResult.Error(Throwable("Empty Data"))
            }
        } else {
            LoadResult.Error(result.exceptionOrNull() ?: Throwable("Unknown error"))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}