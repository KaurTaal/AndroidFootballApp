package com.example.football.activity

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.football.R
import com.example.football.databinding.ActivitySaveNewLeagueBinding
import com.example.football.room.LeagueEntity
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File


class SaveNewLeagueActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySaveNewLeagueBinding
    private lateinit var selectedLeague: LeagueEntity

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySaveNewLeagueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadAndShowMatch()
        setupSaveButton()
    }

    private fun loadAndShowMatch() {
        val name = intent.extras?.getString("name")
        val code = intent.extras?.getString("code")
        val type = intent.extras?.getString("type")
        val startDate = intent.extras?.getString("startDate")
        val endDate = intent.extras?.getString("endDate")
        val matchDay = intent.extras?.getInt("matchDay")

        val leagueEntity = LeagueEntity()
        leagueEntity.name = name
        leagueEntity.code = code
        leagueEntity.type = type
        leagueEntity.startDate = startDate
        leagueEntity.endDate = endDate
        leagueEntity.matchday = matchDay
        selectedLeague = leagueEntity
        showLeague(selectedLeague)
    }

    @SuppressLint("SetTextI18n")
    private fun showLeague(league: LeagueEntity) {
        league.apply {
            binding.textviewDetailsTitle.text = league.name
            binding.textviewDetailsStart.text = "Start: ${league.startDate}"
            binding.textviewDetailsEnd.text = "End: ${league.endDate}"
            setEmblem(league.code.toString())
        }
    }

    private fun setEmblem(code: String){
        val image: ImageView = findViewById(R.id.emblem)
        image.setImageResource(getImageId(this, code.lowercase()))
    }

    @SuppressLint("DiscouragedApi")
    fun getImageId(context: Context, imageName: String): Int {
        return context.resources.getIdentifier("drawable/$imageName", null, context.packageName)
    }

    private fun setupSaveButton() {
        binding.buttonSave.setOnClickListener {
            saveMatchToDb(selectedLeague)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun saveMatchToDb(newLeague: LeagueEntity) {
        val db = FirebaseFirestore.getInstance()

        db.collection("favourites").document("${newLeague.name}").set(newLeague)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }
}