package com.example.football.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.football.LeagueAdapter
import com.example.football.LeagueViewModel
import com.example.football.databinding.ActivityViewNewLeaguesBinding
import com.example.football.enums.Token
import com.example.football.room.LeagueEntity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class ViewLeaguesActivity : AppCompatActivity() {
    private lateinit var existingLeagues: ArrayList<String?>
    private lateinit var binding: ActivityViewNewLeaguesBinding
    private lateinit var leagueAdapter: LeagueAdapter
    private val leagueIds = arrayOf(2114, 2088, 2224, 2088, 2114, 2075)
    val model: LeagueViewModel by viewModels()


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        existingLeagues = intent.extras?.getStringArrayList("existingLeagues") as ArrayList<String?>

        binding = ActivityViewNewLeaguesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestNewLeagues()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val leagueClickListener = LeagueAdapter.LeagueClickListener { p -> openNewLeagueActivity(p) }
        leagueAdapter = LeagueAdapter(model.matchArray, leagueClickListener)
        binding.recyclerviewLeagueList.adapter = leagueAdapter
        binding.recyclerviewLeagueList.layoutManager = LinearLayoutManager(this)
    }

    private fun openNewLeagueActivity(league: LeagueEntity) {
        val intent = Intent(this, SaveNewLeagueActivity::class.java)
        val bundle = Bundle()
        bundle.putString("name", league.name)
        bundle.putString("code", league.code)
        bundle.putString("type", league.type)
        bundle.putString("startDate", league.startDate)
        bundle.putString("endDate", league.endDate)
        bundle.putStringArrayList("favLeagues", existingLeagues)
        league.matchday?.let { bundle.putInt("matchDay", it) }

        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun requestNewLeagues(){
        leagueIds.forEach {
            requestLeague(it)
        }
    }

    private fun requestLeague(leagueId: Int) {
        val linkTrang = "https://api.football-data.org/v4/competitions?areas=${leagueId}"

        val queue = Volley.newRequestQueue(this)

        val stringRequest = @SuppressLint("NotifyDataSetChanged")
        object: StringRequest(Method.GET, linkTrang,
            Response.Listener{ response ->
                try {
                    val jObject = JSONObject(response)
                    val competitions: JSONArray = (jObject.get("competitions") as JSONArray)
                    for(i in 0 until competitions.length()){
                        val league = competitions.get(i) as JSONObject
                        val leagueEntity = leagueDTOToEntity(league)
                        if (!existingLeagues.contains(leagueEntity.code)) {
                            leagueAdapter.data = leagueAdapter.data.plus(leagueEntity)
                            leagueAdapter.notifyDataSetChanged()
                        }
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

    private fun leagueDTOToEntity(leagueDTO: JSONObject): LeagueEntity {
        val name: String = leagueDTO.get("name") as String
        val code = leagueDTO.get("code") as String
        val type = leagueDTO.get("type") as String
        val currentSeason = leagueDTO.get("currentSeason") as JSONObject
        val startDate = currentSeason.get("startDate") as String
        val endDate = currentSeason.get("endDate") as String
        val matchday = currentSeason.get("currentMatchday") as Int

        val leagueEntity = LeagueEntity()
        leagueEntity.name = name
        leagueEntity.code = code
        leagueEntity.type = type
        leagueEntity.startDate = startDate
        leagueEntity.endDate = endDate
        leagueEntity.matchday = matchday
        return leagueEntity
    }

}