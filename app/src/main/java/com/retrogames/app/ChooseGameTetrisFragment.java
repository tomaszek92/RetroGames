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

import com.retrogames.app.tetris.TetrisActivity;
import com.retrogames.app.tetris.TetrisGrid;

/**
 * Created by Tomasz on 30.12.13.
 */
public class ChooseGameTetrisFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_game_tetris, container, false);

        if (ChooseGameSettingsFragment.SOUND) {
            MediaPlayer.create(view.getContext(), R.raw.all_next_game).start();
        }

        // ustawianie czcionki
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), ChooseGameActivity.FONT);
        TextView textView = (TextView) view.findViewById(R.id.choose_game_tetris_texView);
        textView.setTypeface(font);

        Button button_start_game = (Button)view.findViewById(R.id.choose_game_tetris_button_start);
        button_start_game.setTypeface(font);

        // dodanie obsługi przycisku button_start
        button_start_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TetrisActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        Button button_best_score = (Button)view.findViewById(R.id.choose_game_tetris_button_best_score);
        button_best_score.setTypeface(font);
        button_best_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BestScoreActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra(ChooseGameActivity.INDEX_GAME_STRING, ChooseGameActivity.INDEX_TETRIS);
                startActivity(intent);
            }
        });

        Button button_level = (Button)view.findViewById(R.id.choose_game_tetris_button_level);
        button_level.setTypeface(font);
        button_level.setText(getString(R.string.level) + " " + TetrisGrid.LEVEL);
        button_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TetrisGrid.LEVEL++;
                if (TetrisGrid.LEVEL > TetrisGrid.LEVEL_MAX) {
                    TetrisGrid.LEVEL = 1;
                }
                Button button_level = (Button)view.findViewById(R.id.choose_game_tetris_button_level);
                button_level.setText(getString(R.string.level) + " " + TetrisGrid.LEVEL);
            }
        });

        return view;
    }
}
