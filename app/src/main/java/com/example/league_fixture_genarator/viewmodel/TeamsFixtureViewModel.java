package com.example.league_fixture_genarator.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.league_fixture_genarator.model.LeagueTeams;
import com.example.league_fixture_genarator.network.SoccerLeagueApi;
import com.example.league_fixture_genarator.network.SoccerLeagueService;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class TeamsFixtureViewModel extends ViewModel {

    private static final String TAG = TeamsFixtureViewModel.class.getSimpleName();
    private static Boolean DEBUG = true;


    public static void getTeamsFixtureViewModel() {
        SoccerLeagueApi apiService = SoccerLeagueService.getClient().create(SoccerLeagueApi.class);
        Observable<Object> call = apiService.getTeams();
        call
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Object>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log("LeagueTeamsOnSubscribe");

                    }

                    @Override
                    public void onNext(@NonNull Object object) {
                        Log("LeagueTeamsOnNext" + object);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        Log("LeagueTeamsOnError" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log("LeagueTeamsOnComplete");

                    }
                });
    }

    private static void Log(String message) {
        if (DEBUG) {
            Log.d(TAG, message);
        }


    }

}
