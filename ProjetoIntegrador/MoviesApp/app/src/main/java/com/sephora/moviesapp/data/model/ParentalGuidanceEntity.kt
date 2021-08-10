package com.sephora.moviesapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "parental_guidance")
class ParentalGuidanceEntity (
    @PrimaryKey(autoGenerate = true) val id :Int,
    val parentalGuidance : String?,
    val movieId : Int
) : Parcelable