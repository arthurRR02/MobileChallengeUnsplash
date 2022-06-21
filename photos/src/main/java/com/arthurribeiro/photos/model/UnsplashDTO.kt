package com.arthurribeiro.photos.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class SearchedUnsplashDTO(
    val total: Long? = null,
    val totalPages: Long? = null,
    val results: List<UnsplashDTO>? = null
) : Parcelable

@Parcelize
class UnsplashDTO(
    val id: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val width: Long? = null,
    val height: Long? = null,
    val color: String? = null,
    val description: String? = null,
    val urls: Urls? = null,
    val likes: Long? = null,
    val likedByUser: Boolean? = null,
    val sponsorship: Sponsorship? = null
) : Parcelable

@Parcelize
class Urls(
    val raw: String? = null,
    val full: String? = null,
    val regular: String? = null,
    val small: String? = null,
    val thumb: String? = null,
    val smallS3: String? = null
) : Parcelable


@Parcelize
class Sponsorship(
    val sponsor: Sponsor? = null
) : Parcelable

@Parcelize
class Sponsor(
    val id: String? = null,
    val userName: String? = null,
    val name: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
) : Parcelable

@Parcelize
class SponsorLinks(
    val self: String? = null,
    val html: String? = null,
    val photos: String? = null,
    val likes: String? = null,
    val portfolio: String? = null,
    val following: String? = null,
    val followers: String? = null
) : Parcelable

@Parcelize
class ProfileImage(
    val small: String? = null,
    val medium: String? = null,
    val large: String? = null
) : Parcelable



