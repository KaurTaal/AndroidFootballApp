package com.example.football.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import java.util.*

@Parcelize
@Entity(tableName = "favourites")
data class LeagueEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String? = null,
    var code: String? = null,
    var type: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var matchday: Int? = null,
): Parcelable
