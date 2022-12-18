package com.example.football

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.football.room.ClubEntity
import com.example.football.room.LeagueEntity

class ClubViewModel(private val app: Application): AndroidViewModel(app) {

    var matchArray: Array<ClubEntity> = arrayOf(

    )

    fun refresh(){

    }


}

