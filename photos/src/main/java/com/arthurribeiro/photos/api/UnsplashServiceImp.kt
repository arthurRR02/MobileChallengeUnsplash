package com.arthurribeiro.photos.api

import com.arthurribeiro.photos.model.SearchedUnsplashDTO
import com.arthurribeiro.photos.model.UnsplashDTO
import retrofit2.Response
import java.lang.Exception

class UnsplashServiceImp(private val service: UnsplashService) {

    suspend fun getList(page: String): Response<List<UnsplashDTO>> {
        try {
            return service.getPhotoGrid(page)
        } catch (e: Throwable) {
            throw Exception(e)
        }
    }

    suspend fun getSearchedList(
        page: String,
        query: String
    ): Response<SearchedUnsplashDTO> {
        try {
            return service.getSearchedList(page, query)
        } catch (e: Throwable) {
            throw Exception(e)
        }
    }

}