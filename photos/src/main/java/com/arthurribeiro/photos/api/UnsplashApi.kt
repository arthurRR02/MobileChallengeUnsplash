package com.arthurribeiro.photos.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL =
    "https://api.unsplash.com/"

fun getRetrofit(): Retrofit {
    val logging: HttpLoggingInterceptor = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    httpClient.addInterceptor(logging)
    return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .build()
}

fun createPhotoGridService(retrofit: Retrofit): UnsplashService =
    retrofit.create(UnsplashService::class.java)