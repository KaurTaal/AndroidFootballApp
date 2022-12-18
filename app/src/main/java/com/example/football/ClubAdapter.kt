package com.example.football

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.football.room.ClubEntity
import com.example.football.room.LeagueEntity

class ClubAdapter(
    var data: Array<ClubEntity> =  arrayOf(),
) : RecyclerView.Adapter<ClubAdapter.MatchViewHolder>() {


    inner class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_club, parent, false)
        return MatchViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val club = data[position]

        val clubPos = holder.itemView.context.resources.getString(R.string.club_single_component_position, club.position)
        val clubName = holder.itemView.context.resources.getString(R.string.club_single_component_name, club.name)
        val clubPoints = holder.itemView.context.resources.getString(R.string.club_single_component_points, club.points)
        val clubWins = holder.itemView.context.resources.getString(R.string.club_single_component_wins, club.wins)
        val clubDraws = holder.itemView.context.resources.getString(R.string.club_single_component_draws, club.draws)
        val clubLosses = holder.itemView.context.resources.getString(R.string.club_single_component_losses, club.losses)

        holder.itemView.apply {
            this.findViewById<TextView>(R.id.club_position).text = clubPos
            this.findViewById<TextView>(R.id.club_name).text = clubName
            this.findViewById<TextView>(R.id.club_points).text = clubPoints
            this.findViewById<TextView>(R.id.club_wins).text = clubWins
            this.findViewById<TextView>(R.id.club_draws).text = clubDraws
            this.findViewById<TextView>(R.id.club_losses).text = clubLosses
        }
    }

}