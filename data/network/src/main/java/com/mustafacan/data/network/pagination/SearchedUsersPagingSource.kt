package com.mustafacan.data.network.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mustafacan.core.model.users.UserSearchRequest
import com.mustafacan.core.model.users.User
import com.mustafacan.data.network.datasource.UsersRemoteDataSource
import kotlinx.coroutines.delay

class SearchedUsersPagingSource(
    private val remoteDataSource: UsersRemoteDataSource,
    private val userSearchRequest: UserSearchRequest
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val currentPage = params.key ?: 1
        val pageSize = params.loadSize

        if (currentPage == 1) {
            delay(2000)
        }

        val result = remoteDataSource.getPaginatedSearchedUsers(currentPage, pageSize, userSearchRequest)
        return if (result.isSuccess) {
            val data = result.getOrNull()
            if (data != null) {
                // nextKey hesaplaması güncellendi
                val isLastPage = data.users.isEmpty() || data.totalPages == 0 || currentPage >= data.totalPages

                LoadResult.Page(
                    data = data.users,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (isLastPage) null else currentPage + 1
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