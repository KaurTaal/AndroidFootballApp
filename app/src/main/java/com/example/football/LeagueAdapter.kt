package com.example.football

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.football.room.LeagueEntity

class LeagueAdapter(
    var data: Array<LeagueEntity> =  arrayOf(),
    private var listener: LeagueClickListener
    ) : RecyclerView.Adapter<LeagueAdapter.MatchViewHolder>() {

    fun interface LeagueClickListener {
        fun onLeagueClick(match: LeagueEntity)
    }


    inner class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_league, parent, false)
        return MatchViewHolder(view)
    }

    override fun getItemCount(): Int = data.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val league = data[position]

        holder.itemView.apply {
            this.findViewById<TextView>(R.id.matchTitleTextView).text = league.name
            this.findViewById<TextView>(R.id.type).text = "Type: ${league.type}"
            this.findViewById<TextView>(R.id.match_day).text = "Match day: ${league.matchday.toString()}"
            this.findViewById<TextView>(R.id.start_date).text = "Start: ${league.startDate}"
            this.findViewById<TextView>(R.id.end_date).text = "End: ${league.endDate}"

            setOnClickListener { listener.onLeagueClick(league) }
        }
    }

}