package com.example.football.activity


import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.football.room.LeagueEntity
import com.google.firebase.firestore.FirebaseFirestore
import com.example.football.LeagueViewModel
import com.example.football.LeagueAdapter
import com.example.football.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var leagueAdapter: LeagueAdapter
    val model: LeagueViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        binding.buttonNewmatch.setOnClickListener { openViewLeaguesActivity() }


        getLeagues()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getLeagues(){
        val db = FirebaseFirestore.getInstance()

        db.collection("favourites")
            .orderBy("name")
            .get()
            .addOnSuccessListener { result ->
                leagueAdapter.data = arrayOf()
                for (document in result) {
                    val league = document.toObject(LeagueEntity::class.java)
                    leagueAdapter.data = leagueAdapter.data.plus(league)
                    leagueAdapter.notifyDataSetChanged()
                }

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    override fun onResume() {
        super.onResume()

    }

    private fun setupRecyclerView() {
        val leagueClickListener = LeagueAdapter.LeagueClickListener { p -> openMatchDetailsActivity(p) }
        leagueAdapter = LeagueAdapter(model.matchArray, leagueClickListener)
        binding.recyclerviewMatchlist.adapter = leagueAdapter
        binding.recyclerviewMatchlist.layoutManager = LinearLayoutManager(this)
    }

    private fun openViewLeaguesActivity() {
        val intent = Intent(this, ViewLeaguesActivity::class.java)
        val leagues = leagueAdapter.data
        val codes = mutableListOf<String?>()
        (leagues).forEach {
            codes.add(it.code)
        }
        val codesAsStringList: ArrayList<String?> = ArrayList(codes)
        val bundle = Bundle()
        bundle.putStringArrayList("existingLeagues", codesAsStringList)
        intent.putExtras(bundle)
        startActivity(intent)
        getLeagues()
    }

    private fun openMatchDetailsActivity(match: LeagueEntity) {
        val intent = Intent(this, FavLeagueDetailsActivity::class.java)
        intent.putExtra("documentId", match.name)
        startActivity(intent)
    }
}