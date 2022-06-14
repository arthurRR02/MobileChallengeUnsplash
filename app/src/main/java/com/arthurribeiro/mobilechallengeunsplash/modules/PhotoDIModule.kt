package com.arthurribeiro.mobilechallengeunsplash.modules

import com.arthurribeiro.photos.PhotoViewModel
import com.arthurribeiro.photos.api.UnsplashServiceImp
import com.arthurribeiro.photos.api.createPhotoGridService
import com.arthurribeiro.photos.api.getRetrofit
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val photosDIModule = module {
    factory { UnsplashServiceImp(get()) }
    viewModel { PhotoViewModel(get()) }
    single { getRetrofit() }
    factory { createPhotoGridService(get()) }
}