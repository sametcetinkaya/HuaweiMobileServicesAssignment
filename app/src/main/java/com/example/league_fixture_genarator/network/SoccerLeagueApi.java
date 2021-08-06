package com.example.league_fixture_genarator.network;

import com.example.league_fixture_genarator.model.LeagueTeams;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface SoccerLeagueApi {
    @GET("teams/")
    Observable<Object> getTeams();

}
