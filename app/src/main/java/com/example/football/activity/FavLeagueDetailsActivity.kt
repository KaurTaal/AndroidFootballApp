package com.example.football.activity

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.football.ClubAdapter
import com.example.football.ClubViewModel
import com.example.football.LeagueAdapter
import com.example.football.LeagueViewModel
import com.example.football.databinding.ActivityFavLeagueDetailsBinding
import com.example.football.enums.Token
import com.example.football.room.ClubEntity
import com.example.football.room.LeagueEntity
import com.google.firebase.firestore.FirebaseFirestore
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class FavLeagueDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavLeagueDetailsBinding
    private lateinit var clubAdapter: ClubAdapter
    val model: ClubViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup View Binding
        binding = ActivityFavLeagueDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadAndShowMatch()
    }

    private fun setupRecyclerView() {
        clubAdapter = ClubAdapter(model.matchArray)
        binding.clubs.adapter = clubAdapter
        binding.clubs.layoutManager = LinearLayoutManager(this)
    }

    private fun loadAndShowMatch() {
        val id = intent.getStringExtra("documentId")!!

        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("favourites").document(id)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val match = document.toObject(LeagueEntity::class.java)
                    if (match != null) {
                        showMatch(match)
                    }
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

    }

    @SuppressLint("SimpleDateFormat")
    private fun showMatch(league: LeagueEntity) {
        league.apply {
            binding.textviewDetailsTitle.text = title
            binding.textviewDetailsStart.text = startDate
            binding.textviewDetailsEnd.text = endDate
        }
        league.code?.let { requestLeagueStandings(it.uppercase()) }
    }

    private fun requestLeagueStandings(leagueCode: String) {
        val linkTrang = "https://api.football-data.org/v4/competitions/${leagueCode}/standings"

        val queue = Volley.newRequestQueue(this)

        val stringRequest = @SuppressLint("NotifyDataSetChanged")
        object: StringRequest(
            Method.GET, linkTrang,
            Response.Listener{ response ->
                try {
                    val jObject = JSONObject(response)
                    val standings: JSONArray = (jObject.get("standings") as JSONArray)
                    val total = standings.get(0) as JSONObject
                    val table = total.get("table") as JSONArray
                    for(i in 0 until table.length()){
                        val clubDTO = table.get(i) as JSONObject
                        val clubEntity = clubDTOToEntity(clubDTO)
                        clubAdapter.data = clubAdapter.data.plus(clubEntity)
                        clubAdapter.notifyDataSetChanged()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                Log.d("ERROR", "Error is: $it")
            })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["X-Auth-Token"] = Token.API_KEY.token
                return headers
            }
        }

        queue.add(stringRequest)
    }

    private fun clubDTOToEntity(clubDTO: JSONObject): ClubEntity {
        val pos = clubDTO.get("position") as Int
        val team = clubDTO.get("team") as JSONObject
        val name = team.get("name") as String
        val points = clubDTO.get("points") as Int
        val wins = clubDTO.get("won") as Int
        val draws = clubDTO.get("draw") as Int
        val losses = clubDTO.get("lost") as Int

        val clubEntity = ClubEntity()
        clubEntity.name = name
        clubEntity.position = pos
        clubEntity.points = points
        clubEntity.wins = wins
        clubEntity.draws = draws
        clubEntity.losses = losses
        return clubEntity
    }

}