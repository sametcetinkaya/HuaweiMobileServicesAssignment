package com.example.league_fixture_genarator.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;
import androidx.transition.TransitionManager;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.league_fixture_genarator.R;
import com.example.league_fixture_genarator.adapter.TeamsFixtureRecyclerAdapter;
import com.example.league_fixture_genarator.model.FixtureList;
import com.example.league_fixture_genarator.model.LeagueTeams;
import com.example.league_fixture_genarator.viewmodel.FixtureGenerator;
import com.example.league_fixture_genarator.viewmodel.OnSwipeTouchListener;
import com.example.league_fixture_genarator.viewmodel.TeamsViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FixtureListFragment extends Fragment {

    private static final String TAG = FixtureListFragment.class.getSimpleName();
    private static Boolean DEBUG = true;

    List<String> teamAmblem;
    List<String> team_name;

    private TeamsViewModel teamsViewModel;
    public ArrayList<LeagueTeams> leagueTeamsList;
    List<FixtureList> fixtureList;

    TextView weekCounter;
    RecyclerView recyclerViewFixture;

    private int TOTAL_NUM_ITEMS;
    private int ITEMS_PER_PAGE;
    private int ITEMS_REMAINING;
    private int LAST_PAGE;
    private int totalPages;
    private int currentPage = 0;


    public FixtureListFragment() {
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

        return inflater.inflate(R.layout.fragment_fixture_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewFixture = view.findViewById(R.id.recyclerView_fixture);
        recyclerViewFixture.setLayoutManager(new LinearLayoutManager(getContext()));
        teamsViewModel = ViewModelProviders.of(this).get(TeamsViewModel.class);
        teamsViewModel.getTeamsViewModelCall();
        fixtureObserverLiveData(view);


    }

    private void fixtureObserverLiveData(View view) {
        teamsViewModel.getTeamsListObserver().observe(getViewLifecycleOwner(), leagueList -> {
            leagueTeamsList = leagueList;
            Collections.shuffle(leagueTeamsList);
            setLeagueTeamsList(leagueTeamsList, view);
            setFixtureList();


        });

    }

    private void setLeagueTeamsList(ArrayList<LeagueTeams> teamList, View view) {
        weekCounter = view.findViewById(R.id.week_count);
        weekCounter.setText("Week " + String.valueOf(currentPage + 1));

        team_name = new ArrayList<String>();
        teamAmblem = new ArrayList<String>();

        for (int c = 0; c < teamList.size(); c++) {
            team_name.add(teamList.get(c).getTeamName());
            teamAmblem.add(teamList.get(c).getTeamAmblem());
        }

    }

    private void setFixtureList() {
        fixtureList = new ArrayList<>();
        FixtureGenerator fixtureGenerator = new FixtureGenerator();
        List<List<FixtureList>> rounds = (List<List<FixtureList>>) fixtureGenerator.getFixtures(team_name, teamAmblem, true);

        for (int i = 0; i < rounds.size(); i++) {
            List<FixtureList> round = (List<FixtureList>) rounds.get(i);

            for (FixtureList fixturesList : round) {
                fixtureList.add(fixturesList);
            }
        }
        TOTAL_NUM_ITEMS = fixtureList.size();
        ITEMS_PER_PAGE = fixtureList.size() / rounds.size();
        ITEMS_REMAINING = TOTAL_NUM_ITEMS % ITEMS_PER_PAGE;
        LAST_PAGE = TOTAL_NUM_ITEMS / ITEMS_PER_PAGE;
        totalPages = TOTAL_NUM_ITEMS / ITEMS_PER_PAGE;
        recyclerViewFixture.setAdapter(new TeamsFixtureRecyclerAdapter(getContext(), generatePage(currentPage)));

        recyclerViewFixture.setOnTouchListener(new OnSwipeTouchListener(getContext()) {

            public void onSwipeRight() {
                if (currentPage == totalPages) {
                    currentPage -= 1;
                    weekCounter.setText("Week "+String.valueOf(currentPage+1));
                    Slide slide = new Slide();
                    slide.setSlideEdge(Gravity.END);
                    TransitionManager.beginDelayedTransition(recyclerViewFixture, slide);
                    recyclerViewFixture.setAdapter(new TeamsFixtureRecyclerAdapter(getContext(), generatePage(currentPage)));


                }else if (currentPage >= 1 && currentPage < totalPages) {
                    currentPage -= 1;
                    weekCounter.setText("Week "+String.valueOf(currentPage+1));
                    Slide slide = new Slide();
                    slide.setSlideEdge(Gravity.END);
                    TransitionManager.beginDelayedTransition(recyclerViewFixture, slide);
                    recyclerViewFixture.setAdapter(new TeamsFixtureRecyclerAdapter(getContext(), generatePage(currentPage)));

                }else{

                }
            }
            public void onSwipeLeft() {
                if (currentPage == 0) {
                    currentPage += 1;
                    weekCounter.setText("Week "+String.valueOf(currentPage+1));

                    Slide slide = new Slide();
                    slide.setSlideEdge(Gravity.START);
                    TransitionManager.beginDelayedTransition(recyclerViewFixture, slide);

                    recyclerViewFixture.setAdapter(new TeamsFixtureRecyclerAdapter(getContext(), generatePage(currentPage)));

                }else if (currentPage >= 1 && currentPage < totalPages) {
                    currentPage += 1;
                    if(currentPage == totalPages){
                        weekCounter.setText("Week "+String.valueOf(currentPage));
                    }else{
                        weekCounter.setText("Week "+String.valueOf(currentPage+1));
                    }

                    Slide slide = new Slide();
                    slide.setSlideEdge(Gravity.START);
                    TransitionManager.beginDelayedTransition(recyclerViewFixture, slide);
                    recyclerViewFixture.setAdapter(new TeamsFixtureRecyclerAdapter(getContext(), generatePage(currentPage)));

                }

                else{

                }
            }
        });
    }

    public ArrayList<FixtureList> generatePage(int currentPage) {
        int startItem = (currentPage * ITEMS_PER_PAGE);
        int numOfData = ITEMS_PER_PAGE;
        ArrayList<FixtureList> pageData = new ArrayList<>();

        if (currentPage == LAST_PAGE && ITEMS_REMAINING > 0) {
            for (int i = startItem; i < startItem + ITEMS_REMAINING; i++) {
                pageData.add(fixtureList.get(i));
            }
        } else {
            for (int i = startItem; i < startItem + numOfData; i++) {
                pageData.add(fixtureList.get(i));
            }
        }
        return pageData;
    }

    private static void Log(String message) {
        if (DEBUG) {
            Log.d(TAG, message);
        }
    }
}