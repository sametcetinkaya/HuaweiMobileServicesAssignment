package com.example.league_fixture_genarator.viewmodel;

import com.example.league_fixture_genarator.model.FixtureList;
import com.example.league_fixture_genarator.model.LeagueTeams;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FixtureGenerator {

    public List<List<FixtureList>> getFixtures(List<String> teams, List<String> teamAmblem, boolean includeReverseFixtures) {

        int numberOfTeams = teams.size();

        boolean ghostTeam = false;
        if (numberOfTeams % 2 != 0) {
            numberOfTeams++;
            ghostTeam = true;
        }

        int totalRounds = numberOfTeams - 1;
        int matchesPerRound = (numberOfTeams / 2)-1;

        List<List<FixtureList>> rounds = new LinkedList<List<FixtureList>>();
        int home_amblem;
        int away_amblem;
        for (int round = 1; round < totalRounds+1; round++) {

            List<FixtureList> fixtureList = new LinkedList<FixtureList>();
            for (int match = 1; match < matchesPerRound+1; match++) {

                int home = (round + match) % (numberOfTeams - 1);
                int away = (numberOfTeams - 1 - match + round) % (numberOfTeams - 1);

                if (match == 0) {
                    away = numberOfTeams - 2;
                    away_amblem = away;

                }

                home_amblem = home;
                away_amblem = away;

                fixtureList.add(new FixtureList(teams.get(home), teams.get(away),teamAmblem.get(home_amblem)
                        ,teamAmblem.get(away_amblem)));
            }
            rounds.add(fixtureList);
        }

        List<List<FixtureList>> interleaved = new LinkedList<List<FixtureList>>();

        int evn = 0;
        int odd = (numberOfTeams / 2);
        for (int i = 0; i < rounds.size(); i++) {
            if (i % 2 == 0) {
                interleaved.add(rounds.get(evn++));
            } else {
                interleaved.add(rounds.get(odd++));
            }
        }

        rounds = interleaved;


        for (int roundNumber = 0; roundNumber < rounds.size(); roundNumber++) {
            if (roundNumber % 2 == 1) {
                FixtureList fixtureList = rounds.get(roundNumber).get(0);
                rounds.get(roundNumber).set(0, new FixtureList(fixtureList.getAwayTeam(), fixtureList.getHomeTeam(), fixtureList.getAwayAmblem(),
                        fixtureList.getHomeAmblem()));
            }
        }

        if(includeReverseFixtures){
            List<List<FixtureList>> reverseFixtures = new LinkedList<List<FixtureList>>();
            for(List<FixtureList> round: rounds){
                List<FixtureList> reverseRound = new LinkedList<FixtureList>();
                for(FixtureList fixtureList: round){
                    reverseRound.add(new FixtureList(fixtureList.getAwayTeam(), fixtureList.getHomeTeam(),fixtureList.getAwayAmblem(),
                            fixtureList.getHomeAmblem()));
                }
                reverseFixtures.add(reverseRound);
            }
            rounds.addAll(reverseFixtures);
        }

        return rounds;
    }

}
