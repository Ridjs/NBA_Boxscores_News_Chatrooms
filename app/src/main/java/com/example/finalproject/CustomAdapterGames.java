package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

public class CustomAdapterGames extends ArrayAdapter<GamesFragment.Match> {
    Context mainContext;
    int adapterXML;
    List<GamesFragment.Match> matches;
    TextView status;
    TextView homeScore;
    TextView awayScore;
    TextView homeTeam;
    TextView awayTeam;
    ImageView homeTeamImage;
    ImageView awayTeamImage;
    public final String[] teams = new String[]{"Atlanta Hawks","Boston Celtics","Brooklyn Nets","Charlotte Hornets","Chicago Bulls","Cleveland Cavaliers","Dallas Mavericks","Denver Nuggets","Detroit Pistons","Golden State Warriors","Houston Rockets","Indiana Pacers","LA Clippers","Los Angeles Lakers","Memphis Grizzlies","Miami Heat","Milwaukee Bucks","Minnesota Timberwolves","New Orleans Pelicans","New York Knicks","Oklahoma City Thunder","Orlando Magic","Philadelphia 76ers","Phoenix Suns","Portland Trail Blazers","Sacramento Kings","San Antonio Spurs","Toronto Raptors","Utah Jazz","Washington Wizards"};
    public final int[] logos = new int[]{R.drawable.hawks,R.drawable.celtics,R.drawable.nets,R.drawable.hornets,R.drawable.bulls,R.drawable.cavaliers,R.drawable.mavericks,R.drawable.nuggets,R.drawable.pistons,R.drawable.warriors,R.drawable.rockets,R.drawable.pacers,R.drawable.clippers,R.drawable.lakers,R.drawable.grizzlies,R.drawable.heat,R.drawable.bucks,R.drawable.timberwolves,R.drawable.pelicans,R.drawable.knicks,R.drawable.thunder,R.drawable.magic,R.drawable.philadelphia,R.drawable.suns,R.drawable.blazers,R.drawable.kings,R.drawable.spurs,R.drawable.raptors,R.drawable.wizards};

    public CustomAdapterGames(@NonNull Context context, int resource, @NonNull List<GamesFragment.Match> objects) {
        super(context, resource, objects);
        mainContext = context;
        adapterXML = resource;
        matches = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mainContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View adapterView = layoutInflater.inflate(adapterXML, null);
        status = (TextView)adapterView.findViewById(R.id.id_game_time);
        homeScore = (TextView)adapterView.findViewById(R.id.id_score_home);
        awayScore = (TextView)adapterView.findViewById(R.id.id_score_away);
        homeTeam = (TextView)adapterView.findViewById(R.id.id_home_text);
        awayTeam = (TextView)adapterView.findViewById(R.id.id_away_text);
        homeTeamImage = (ImageView)adapterView.findViewById(R.id.id_home_image);
        awayTeamImage = (ImageView)adapterView.findViewById(R.id.id_away_image);
        status.setText(matches.get(position).getStatus());
        homeScore.setText(""+matches.get(position).getScoreHome());
        awayScore.setText(""+matches.get(position).getScoreAway());
        homeTeam.setText(matches.get(position).getHomeTeam());
        awayTeam.setText(matches.get(position).getAwayTeam());
        int homeLogoIndex = Arrays.asList(teams).indexOf(homeTeam.getText().toString());
        int awayLogoIndex = Arrays.asList(teams).indexOf(awayTeam.getText().toString());
        if (awayLogoIndex == -1) {
            awayTeamImage.setImageResource(R.drawable.games_card);
        }
        else{
            awayTeamImage.setImageResource(logos[awayLogoIndex]);
        }
        if (homeLogoIndex == -1) {
            homeTeamImage.setImageResource(R.drawable.games_card);
        }
        else{
            homeTeamImage.setImageResource(logos[homeLogoIndex]);
        }
        return adapterView;
    }
}
