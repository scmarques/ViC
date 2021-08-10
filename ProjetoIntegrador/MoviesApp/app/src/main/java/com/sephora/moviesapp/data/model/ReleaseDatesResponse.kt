package com.sephora.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class ReleaseDatesResponse (
    val id : Int,
    val results: List<ParentalGuidanceResponse>) {

    data class ParentalGuidanceResponse(
        val iso_3166_1: String,
        @SerializedName("release_dates") val releaseDate: List<ReleaseDateObject>
    )

    data class ReleaseDateObject(val  certification: String,
                                 val type : Int)


    data class ReleaseInfo(val certification: ReleaseInfo)

    override fun toString(): String {

        for (i in results) {
            if ((i.iso_3166_1). equals("BR")) {
                i.releaseDate[0].certification.isNotEmpty().let{
                    return "PG - ${i.releaseDate[0].certification}"
                }
            }
        }
        return ""
    }
}