package com.example.league_fixture_genarator.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.league_fixture_genarator.R;
import com.example.league_fixture_genarator.viewmodel.TeamsFixtureViewModel;


public class TeamsListFragment extends Fragment {
    private TeamsFixtureViewModel teamsFixtureViewModel;


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
        teamsFixtureViewModel = ViewModelProviders.of(this).get(TeamsFixtureViewModel.class);
        teamsFixtureViewModel.getTeamsFixtureViewModel();
        return inflater.inflate(R.layout.fragment_teams_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}