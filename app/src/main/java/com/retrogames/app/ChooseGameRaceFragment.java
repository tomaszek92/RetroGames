package com.retrogames.app;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.retrogames.app.race.RaceActivity;
import com.retrogames.app.race.RaceGrid;

/**
 * Created by Tomasz on 30.12.13.
 */
public class ChooseGameRaceFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_game_race, container, false);

        if (ChooseGameSettingsFragment.SOUND) {
            MediaPlayer.create(view.getContext(), R.raw.all_next_game).start();
        }

        // ustawianie czcionki
        TextView textView = (TextView) view.findViewById(R.id.choose_game_race_texView);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), ChooseGameActivity.FONT);
        textView.setTypeface(font);

        Button buttonStart = (Button)view.findViewById(R.id.choose_game_race_button_start);
        buttonStart.setTypeface(font);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RaceActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        Button buttonBestScore  = (Button)view.findViewById(R.id.choose_game_race_button_best_score);
        buttonBestScore.setTypeface(font);
        buttonBestScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BestScoreActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra(ChooseGameActivity.INDEX_GAME_STRING, ChooseGameActivity.INDEX_RACE);
                startActivity(intent);
            }
        });

        Button buttonLevel = (Button)view.findViewById(R.id.choose_game_race_button_level);
        buttonLevel.setTypeface(font);
        buttonLevel.setText(getString(R.string.level) + " " + RaceGrid.LEVEL);
        buttonLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RaceGrid.LEVEL++;
                if (RaceGrid.LEVEL > RaceGrid.LEVEL_MAX) {
                    RaceGrid.LEVEL = 1;
                }
                Button buttonLevel = (Button)view.findViewById(R.id.choose_game_race_button_level);
                buttonLevel.setText(getString(R.string.level) + " " + RaceGrid.LEVEL);
            }
        });

        return view;
    }
}
