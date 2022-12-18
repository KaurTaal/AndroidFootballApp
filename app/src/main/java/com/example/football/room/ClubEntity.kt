package com.example.football.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import java.util.*

@Parcelize
@Entity(tableName = "clubs")
data class ClubEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String? = null,
    var position: Int? = null,
    var wins: Int? = null,
    var losses: Int? = null,
    var draws: Int? = null,
    var points: Int? = null,
): Parcelable
