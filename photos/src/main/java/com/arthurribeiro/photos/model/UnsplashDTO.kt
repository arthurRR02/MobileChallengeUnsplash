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
    val promoted_at: String? = null,
    val width: Long? = null,
    val height: Long? = null,
    val color: String? = null,
    val blurHash: String? = null,
    val description: String? = null,
    val altDescription: String? = null,
    val urls: Urls? = null,
    val links: Links? = null,
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
class Links(
    val self: String? = null,
    val html: String? = null,
    val download: String? = null,
    val downloadLocation: String? = null
) : Parcelable

@Parcelize
class Sponsorship(
    val impressionUrls: List<String>? = null,
    val taglineUrl: String? = null,
    val sponsor: Sponsor? = null
) : Parcelable

@Parcelize
class Sponsor(
    val id: String? = null,
    val updatedAt: String? = null,
    val userName: String? = null,
    val name: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val twitterUserName: String? = null,
    val portfolioUrl: String? = null,
    val bio: String? = null,
    val location: String? = null,
    val links: SponsorLinks? = null,
    val profileImage: ProfileImage? = null,
    val instagramUserName: String? = null,
    val totalCollections: Int? = null,
    val totalLikes: Int? = null,
    val totalPhotos: Int? = null,
    val acceptedTos: Boolean? = null,
    val forHire: Boolean? = null,
    val social: Social? = null
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

@Parcelize
class Social(
    val instagramUserName: String? = null,
    val portfolioUserName: String? = null,
    val twitterUserName: String? = null,
    val paypalEmail: String? = null
) : Parcelable


