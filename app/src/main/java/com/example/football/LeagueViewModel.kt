package com.example.football

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.football.room.LeagueEntity

class LeagueViewModel(private val app: Application): AndroidViewModel(app) {

    var matchArray: Array<LeagueEntity> = arrayOf(

    )

    fun refresh(){

    }


}

