package com.arthurribeiro.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arthurribeiro.photos.api.UnsplashServiceImp
import com.arthurribeiro.photos.model.UnsplashDTO
import com.arthurribeiro.photos.util.UnsplashPhotoPagingSource
import kotlinx.coroutines.flow.Flow

class PhotoViewModel(
    private val unsplashService: UnsplashServiceImp,
) : ViewModel() {

    private val _photos = MutableLiveData<Flow<PagingData<UnsplashDTO>>>()
    val photos: LiveData<Flow<PagingData<UnsplashDTO>>> = _photos


    fun getPhotos(query: String = "") {
        _photos.postValue(Pager(PagingConfig(30, enablePlaceholders = false)) {
            UnsplashPhotoPagingSource(unsplashService, query)
        }.flow.cachedIn(viewModelScope))
    }
}