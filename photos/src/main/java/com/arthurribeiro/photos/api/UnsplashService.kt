package com.arthurribeiro.photos.api

import com.arthurribeiro.photos.model.SearchedUnsplashDTO
import com.arthurribeiro.photos.model.UnsplashDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashService {

    @GET("photos/?client_id=GyqHN_Gd0LSf0xMEWpUFOAPGFGhl3lvYao9O_xMMVPY&per_page=30")
    suspend fun getPhotoGrid(
        @Query("page") pageNumber: String,
    ): Response<List<UnsplashDTO>>

    @GET("search/photos/?client_id=GyqHN_Gd0LSf0xMEWpUFOAPGFGhl3lvYao9O_xMMVPY&per_page=30")
    suspend fun getSearchedList(
        @Query("page") pageNumber: String,
        @Query("query") query: String
    ): Response<SearchedUnsplashDTO>
}