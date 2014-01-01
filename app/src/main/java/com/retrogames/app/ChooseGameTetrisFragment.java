package com.retrogames.app;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.retrogames.app.tetris.TetrisActivity;

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
                startActivity(new Intent(getActivity(), TetrisActivity.class));
            }
        });

        return view;
    }
}