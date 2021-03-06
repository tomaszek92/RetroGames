package com.retrogames.app;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Tomasz on 30.12.13.
 */
public class ChooseGameMainFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // ustawianie widoku
        View view = inflater.inflate(R.layout.choose_game_main, container, false);

        if (ChooseGameSettingsFragment.SOUND) {
            MediaPlayer.create(view.getContext(), R.raw.all_next_game).start();
        }

        // ustawianie czcionki
        TextView textView = (TextView) view.findViewById(R.id.choose_game_main_texView);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), ChooseGameActivity.FONT);
        textView.setTypeface(font);

        // pobieranie listy gier
        String[] games = getActivity().getResources().getStringArray(R.array.games);
        ListView listView = (ListView)view.findViewById(R.id.choose_game_main_listView);

        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < games.length; i++) {
            list.add(games[i]);
        }

        GamesArrayAdapter adapter = new GamesArrayAdapter(getActivity(), list);

        // dodawanie listenera
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);

                switch (position) {
                    case ChooseGameActivity.INDEX_RACE-1:
                        ((ChooseGameActivity)getActivity()).getViewPager().setCurrentItem(ChooseGameActivity.INDEX_RACE);
                        break;
                    case ChooseGameActivity.INDEX_TANKS-1:
                        ((ChooseGameActivity)getActivity()).getViewPager().setCurrentItem(ChooseGameActivity.INDEX_TANKS);
                        break;
                    case ChooseGameActivity.INDEX_TETRIS -1:
                        ((ChooseGameActivity)getActivity()).getViewPager().setCurrentItem(ChooseGameActivity.INDEX_TETRIS);
                        break;
                    case ChooseGameActivity.INDEX_SETTINGS-1:
                        ((ChooseGameActivity)getActivity()).getViewPager().setCurrentItem(ChooseGameActivity.INDEX_SETTINGS);
                        break;
                }
            }
        });

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }
}
