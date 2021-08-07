package com.example.league_fixture_genarator.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.example.league_fixture_genarator.R;
import com.example.league_fixture_genarator.adapter.TeamsRecyclerAdapter;
import com.example.league_fixture_genarator.model.LeagueTeams;
import com.example.league_fixture_genarator.viewmodel.TeamsFixtureViewModel;

import java.util.List;


public class TeamsListFragment extends Fragment {
    private static final String TAG = TeamsListFragment.class.getSimpleName();
    private static Boolean DEBUG = true;

    private TeamsRecyclerAdapter teamsRecyclerAdapter;
    private TeamsFixtureViewModel teamsFixtureViewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<LeagueTeams> leagueTeamsList;


    public TeamsListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_teams_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log("onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
//        progressBar = view.findViewById(R.id.progress);
        teamsFixtureViewModel = ViewModelProviders.of(this).get(TeamsFixtureViewModel.class);
        teamsFixtureViewModel.getTeamsFixtureViewModelCall();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        observerLiveData();
        teamsRecyclerAdapter = new TeamsRecyclerAdapter(getContext(), leagueTeamsList);
        recyclerView.setAdapter(teamsRecyclerAdapter);


    }


    public void observerLiveData() {
        Log("observerLiveData");
        teamsFixtureViewModel.getTeamsListObserver().observe(getViewLifecycleOwner(), leagueList -> {
            Log("leaugeList" + leagueList.toString());
            leagueTeamsList = leagueList;
            teamsRecyclerAdapter.setTeamList(leagueTeamsList);


        });
     /*   teamsFixtureViewModel.getProgess().observe(getViewLifecycleOwner(), progess -> {
            if(progess){
                progressBar.setVisibility(View.VISIBLE);
            }else{
                progressBar.setVisibility(View.GONE);
            }


        });*/
    }

    private static void Log(String message) {
        if (DEBUG) {
            Log.d(TAG, message);
        }


    }

}