package com.example.league_fixture_genarator.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.league_fixture_genarator.R;
import com.example.league_fixture_genarator.model.LeagueTeams;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeamsRecyclerAdapter extends RecyclerView.Adapter<TeamsRecyclerAdapter.TeamsRecyclerViewHolder>{
    private static final String TAG = TeamsRecyclerAdapter.class.getSimpleName();
    private static Boolean DEBUG = true;
    private List<LeagueTeams> leagueTeams;
    private Context context;

    public TeamsRecyclerAdapter(Context context, List<LeagueTeams> leagueTeams){

        this.context = context;
        this.leagueTeams = leagueTeams;
    }

    public void setTeamList(List<LeagueTeams> teamList) {
        this.leagueTeams = teamList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TeamsRecyclerAdapter.TeamsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.teams_list_item,parent,false);
        return new TeamsRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamsRecyclerAdapter.TeamsRecyclerViewHolder holder, int position) {

        holder.soccerTeam.setText(leagueTeams.get(position).getTeamName());
        Glide.with(context).load(leagueTeams.get(position).getTeamAmblem()).apply(RequestOptions.centerCropTransform()).into(holder.teamLogo);
    }

    @Override
    public int getItemCount() {
        if(this.leagueTeams != null){
            return this.leagueTeams.size();
        }
        return 0;
    }

    public class TeamsRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView soccerTeam;
        CircleImageView teamLogo;

        public TeamsRecyclerViewHolder(View itemView) {

            super(itemView);
            soccerTeam = itemView.findViewById(R.id.team_name);
            teamLogo = itemView.findViewById(R.id.team_logo);
        }
    }
    private static void Log(String message) {
        if (DEBUG) {
            Log.d(TAG, message);
        }
    }
}
