package com.arthurribeiro.photos

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthurribeiro.photos.api.UnsplashServiceImp
import com.arthurribeiro.photos.model.SearchedUnsplashDTO
import com.arthurribeiro.photos.model.UnsplashDTO
import kotlinx.coroutines.launch
import retrofit2.Response

class PhotoViewModel(private val unsplashService: UnsplashServiceImp) : ViewModel() {

    private val _photoList: MutableLiveData<Response<List<UnsplashDTO>>> = MutableLiveData()
    val photoList: LiveData<Response<List<UnsplashDTO>>> = _photoList

    private val _searchedPhotoList: MutableLiveData<Response<SearchedUnsplashDTO>> = MutableLiveData()
    val searchedPhotoList: LiveData<Response<SearchedUnsplashDTO>> = _searchedPhotoList

    private val _errorValue: MutableLiveData<String> = MutableLiveData()
    val errorValue: LiveData<String> = _errorValue

    var scrolling: Boolean? = null
    var currentItem: Int = 0
    var totalItem: Int = 0
    var scrollOut: Int = 0
    var page: Int = 0
    var perPage: Int = 30
    val adapterPhotoList: MutableList<UnsplashDTO> = mutableListOf()
    var comingFromPhotoDetail: Boolean = false
    var recyclerState: Parcelable? = null


    fun getList(page: String, perPage: String){
        try {
            viewModelScope.launch {
                _photoList.value = unsplashService.getList(page, perPage)
            }
        } catch (e: Exception){
            _errorValue.value = e.message
        }
    }

    fun getSearchedList(page: String, perPage: String, query: String){
        try {
            viewModelScope.launch {
                _searchedPhotoList.value = unsplashService.getSearchedList(page, perPage, query)
            }
        } catch (e: Exception){
            _errorValue.value = e.message
        }
    }

    fun resetItems(){
        page = 0
        totalItem = 0
        currentItem = 0
        scrollOut = 0
        adapterPhotoList.clear()
    }
}