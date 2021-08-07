package com.example.league_fixture_genarator.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.league_fixture_genarator.model.LeagueTeams;
import com.example.league_fixture_genarator.network.SoccerLeagueApi;
import com.example.league_fixture_genarator.network.SoccerLeagueService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class TeamsFixtureViewModel extends ViewModel {

    private static final String TAG = TeamsFixtureViewModel.class.getSimpleName();
    private static Boolean DEBUG = true;
    private MutableLiveData<List<LeagueTeams>> leagueList;
    private MutableLiveData<Boolean> progess;

    public TeamsFixtureViewModel() { //constructor
        leagueList = new MutableLiveData<List<LeagueTeams>>();

    }

    public MutableLiveData<List<LeagueTeams>> getTeamsListObserver() {
        return leagueList;
    }



    public void getTeamsFixtureViewModelCall() {
        SoccerLeagueApi apiService = SoccerLeagueService.getClient().create(SoccerLeagueApi.class);
        Observable<List<LeagueTeams>> call = apiService.getTeams();
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<LeagueTeams>>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log("LeagueTeamsOnSubscribe");

                    }

                    @Override
                    public void onNext(@NonNull List<LeagueTeams> leagueTeams) {
                        Log("LeagueTeamsOnNext" + leagueTeams);
                        leagueList.postValue(leagueTeams);

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
