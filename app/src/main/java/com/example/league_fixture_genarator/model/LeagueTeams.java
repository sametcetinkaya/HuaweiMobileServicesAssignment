package com.example.league_fixture_genarator.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeagueTeams {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("team_name")
    @Expose
    private String teamName;
    @SerializedName("team_amblem")
    @Expose
    private String teamAmblem;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    @Override
    public String toString() {
        return "LeagueTeams{" +
                "id='" + id + '\'' +
                ", teamName='" + teamName + '\'' +
                ", teamAmblem='" + teamAmblem + '\'' +
                '}';
    }

    public String getTeamName() { return teamName; }

    public void setTeamName(String teamName) { this.teamName = teamName; }

    public String getTeamAmblem() { return teamAmblem; }

    public void setTeamAmblem(String teamAmblem) { this.teamAmblem = teamAmblem; }
}
