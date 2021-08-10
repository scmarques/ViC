package com.sephora.moviesapp.data.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(val url: String): Parcelable {
    companion object {
        private const val PATH = "https://image.tmdb.org/t/p"
    }

    @IgnoredOnParcel
    val large: Uri = Uri.parse("$PATH/w342/$url")

    @IgnoredOnParcel
    val original: Uri = Uri.parse("$PATH/original/$url")
}