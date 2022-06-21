package com.arthurribeiro.photos.util

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arthurribeiro.photos.api.UnsplashServiceImp
import com.arthurribeiro.photos.model.UnsplashDTO
import retrofit2.HttpException

private const val STARTING_PAGE_INDEX = 0

class UnsplashPhotoPagingSource(
    private val unsplashService: UnsplashServiceImp,
    private val query: String = "",
) :
    PagingSource<Int, UnsplashDTO>() {

    override fun getRefreshKey(state: PagingState<Int, UnsplashDTO>): Int? {
        return state.anchorPosition?.apply {
            state.closestPageToPosition(this)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(this)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashDTO> {
        if (query.isBlank() || query.isEmpty()) {
            return try {
                var photos = listOf<UnsplashDTO>()
                val pageNumber = params.key ?: STARTING_PAGE_INDEX
                val response = unsplashService.getList(pageNumber.toString())
                if (response.isSuccessful) {
                    if (!response.body().isNullOrEmpty()) photos = response.body()!!
                }
                val nextKey = if (photos.isEmpty()) null else pageNumber + (params.loadSize / 30)
                LoadResult.Page(
                    data = photos,
                    prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber,
                    nextKey = nextKey
                )
            } catch (exception: Exception) {
                LoadResult.Error(exception)
            } catch (exception: HttpException) {
                LoadResult.Error(exception)
            }
        }
        else{
            return try {
                var photos = listOf<UnsplashDTO>()
                val pageNumber = params.key ?: STARTING_PAGE_INDEX
                val response = unsplashService.getSearchedList(pageNumber.toString(), query)
                if (response.isSuccessful) {
                    if (!response.body()?.results.isNullOrEmpty()) photos = response.body()?.results!!
                }
                val nextKey = if (photos.isEmpty()) null else pageNumber + (params.loadSize / 30)
                LoadResult.Page(
                    data = photos,
                    prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber,
                    nextKey = nextKey
                )
            } catch (exception: Exception) {
                LoadResult.Error(exception)
            } catch (exception: HttpException) {
                LoadResult.Error(exception)
            }
        }
    }
}