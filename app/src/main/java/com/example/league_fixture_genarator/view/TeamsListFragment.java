package com.example.league_fixture_genarator.view;


import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.league_fixture_genarator.R;
import com.example.league_fixture_genarator.adapter.TeamsRecyclerAdapter;
import com.example.league_fixture_genarator.model.LeagueTeams;
import com.example.league_fixture_genarator.viewmodel.TeamsViewModel;
import java.util.ArrayList;


public class TeamsListFragment extends Fragment {

    private static final String TAG = TeamsListFragment.class.getSimpleName();
    private static Boolean DEBUG = true;

    private TeamsRecyclerAdapter teamsRecyclerAdapter;
    private TeamsViewModel teamsViewModel;
    private RecyclerView recyclerViewTeams;
    private ArrayList<LeagueTeams> leagueTeamsList = new ArrayList<>();
    private TextView fixtureButton;
    private SwitchCompat switchCompat;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teams_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewTeams = view.findViewById(R.id.recyclerViewTeams);
        fixtureButton = view.findViewById(R.id.fixtureButton);
        switchCompat = view.findViewById(R.id.switch_button);
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            getActivity().setTheme(R.style.Theme_Dark);
        }else{
            getActivity().setTheme(R.style.Theme_Light);
        }

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        teamsViewModel = ViewModelProviders.of(this).get(TeamsViewModel.class);
        teamsViewModel.getTeamsViewModelCall();

        teamListObserverLiveData();
        setupRecyclerAdapter();

        fixtureButton.setOnClickListener(btn -> {
            goToSecond(btn);
        });

    }

    private void setupRecyclerAdapter() {
        Log("setupRecyclerAdapter");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewTeams.setLayoutManager(layoutManager);
        teamsRecyclerAdapter = new TeamsRecyclerAdapter(getContext(), leagueTeamsList);
        recyclerViewTeams.setAdapter(teamsRecyclerAdapter);

    }

    private void teamListObserverLiveData() {
        Log("teamListObserverLiveData");
        teamsViewModel.getTeamsListObserver().observe(getViewLifecycleOwner(), leagueList -> {
            leagueTeamsList = leagueList;
            teamsRecyclerAdapter.setTeamList(leagueTeamsList);
        });

    }

    private static void Log(String message) {
        if (DEBUG) {
            Log.d(TAG, message);
        }
    }

    private void goToSecond(View view) {
        Log("goToSecond");
        NavDirections action = TeamsListFragmentDirections.actionTeamsListFragmentToFixtureListFragment();
        Navigation.findNavController(view).navigate(action);
    }

    private void CheckSwitch () {

    }
}