package com.example.league_fixture_genarator.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.league_fixture_genarator.model.LeagueTeams;
import com.example.league_fixture_genarator.network.SoccerLeagueApi;
import com.example.league_fixture_genarator.network.SoccerLeagueService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class TeamsViewModel extends ViewModel {

    private static final String TAG = TeamsViewModel.class.getSimpleName();
    private static Boolean DEBUG = true;

    private MutableLiveData<ArrayList<LeagueTeams>> leagueList;
    private MutableLiveData<Boolean> progess;

    public TeamsViewModel() { //constructor
        leagueList = new MutableLiveData<ArrayList<LeagueTeams>>();

    }

    public MutableLiveData<ArrayList<LeagueTeams>> getTeamsListObserver() {
        return leagueList;
    }



    public void getTeamsViewModelCall() {
        SoccerLeagueApi apiService = SoccerLeagueService.getClient().create(SoccerLeagueApi.class);
        Observable<ArrayList<LeagueTeams>> call = apiService.getTeams();
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<LeagueTeams>>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log("LeagueTeamsOnSubscribe");

                    }

                    @Override
                    public void onNext(@NonNull ArrayList<LeagueTeams> leagueTeams) {
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
