package com.retrogames.app;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        // ustawianie czcionki
        TextView textView = (TextView) view.findViewById(R.id.choose_game_race_texView);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), ChooseGameActivity.FONT);
        textView.setTypeface(font);
        return view;
    }
}
