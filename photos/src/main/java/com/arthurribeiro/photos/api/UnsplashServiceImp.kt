package com.arthurribeiro.photos.api

import com.arthurribeiro.photos.model.SearchedUnsplashDTO
import com.arthurribeiro.photos.model.UnsplashDTO
import retrofit2.Response
import java.lang.Exception

class UnsplashServiceImp(private val service: UnsplashService) {

    suspend fun getList(page: String, perPage: String): Response<List<UnsplashDTO>> {
        try {
            return service.getPhotoGrid(page, perPage)
        } catch (e: Throwable) {
            throw Exception(e)
        }
    }

    suspend fun getSearchedList(
        page: String,
        perPage: String,
        query: String
    ): Response<SearchedUnsplashDTO> {
        try {
            return service.getSearchedList(page, perPage, query)
        } catch (e: Throwable) {
            throw Exception(e)
        }
    }

}