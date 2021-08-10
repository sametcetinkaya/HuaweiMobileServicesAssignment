package com.example.league_fixture_genarator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.league_fixture_genarator.R;
import com.example.league_fixture_genarator.model.FixtureList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeamsFixtureRecyclerAdapter extends RecyclerView.Adapter<TeamsFixtureRecyclerAdapter.FixtureViewHolder> {

    private static final String TAG = TeamsRecyclerAdapter.class.getSimpleName();
    private static Boolean DEBUG = true;

    List<FixtureList> fixtureList;
    Context context;

    public TeamsFixtureRecyclerAdapter(Context context, List<FixtureList> fixtureList){

        this.context = context;
        this.fixtureList = fixtureList;
    }


    @NonNull
    @Override
    public TeamsFixtureRecyclerAdapter.FixtureViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fixture_list_item,viewGroup,false);
        return new TeamsFixtureRecyclerAdapter.FixtureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamsFixtureRecyclerAdapter.FixtureViewHolder viewHolder, int position) {

       viewHolder.home_team_name.setText(fixtureList.get(position).getHomeTeam());
       viewHolder.away_team_name.setText(fixtureList.get(position).getAwayTeam());
       Glide.with(context).load(fixtureList.get(position).getHomeAmblem()).apply(RequestOptions.centerCropTransform()).into(viewHolder.home_image);
       Glide.with(context).load(fixtureList.get(position).getAwayAmblem()).apply(RequestOptions.centerCropTransform()).into(viewHolder.away_image);

    }

    @Override
    public int getItemCount() {
        if(this.fixtureList != null){
            return this.fixtureList.size();
        }
        return 0;
    }

    public class FixtureViewHolder extends RecyclerView.ViewHolder {

        TextView away_team_name, home_team_name, versus;
        CircleImageView away_image, home_image;

        public FixtureViewHolder(@NonNull View view) {
            super(view);

            away_team_name = view.findViewById(R.id.away_team_name);
            home_team_name = view.findViewById(R.id.home_team_name);
            versus = view.findViewById(R.id.versus);
            away_image = view.findViewById(R.id.away_image);
            home_image = view.findViewById(R.id.home_image);
        }
    }
}
