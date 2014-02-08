package com.retrogames.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

public class ChooseGameActivity extends FragmentActivity {

    private MyAdapter myAdapter;
    private ViewPager viewPager;

    private static final int ONE_SECOND = 1000;

    // indeksy gier i string do ich pobierania
    public static final String INDEX_GAME_STRING = "INDEX_GAME_STRING";
    public static final int INDEX_MAIN = 0;
    public static final int INDEX_RACE = 1;
    public static final int INDEX_TANKS = 2;
    public static final int INDEX_TETRIS = 3;
    public static final int INDEX_SETTINGS = 4;

    // stringi do pobierania najlepszych wyników
    public static final String BEST_SCORE_RACE_STRING = "BEST_SCORE_TETRIS_RACE";
    public static final String BEST_SCORE_TANKS_STRING = "BEST_SCORE_TETRIS_TANKS";
    public static final String BEST_SCORE_TETRIS_STRING = "BEST_SCORE_TETRIS_STRING";

    // zmienne przechowujące najlepsze wyniki
    public static int BEST_SCORE_RACE = 0;
    public static int BEST_SCORE_TANKS = 0;
    public static int BEST_SCORE_TETRIS = 0;

    public static final String FONT = "fonts/ka1.ttf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);

        myAdapter = new MyAdapter(getSupportFragmentManager());

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(myAdapter);

        loadSharedPreferences();

        Bundle bundle = getIntent().getExtras();
        try {
            viewPager.setCurrentItem(bundle.getInt(INDEX_GAME_STRING));
        }
        catch (Exception e) {

        }
    }

    int countBackPressed = 0;
    long startTime = 0;
    @Override
    public void onBackPressed() {
        if (countBackPressed == 0) {
            startTime = System.currentTimeMillis();
            countBackPressed++;
            Toast.makeText(this, R.string.exit_string, Toast.LENGTH_SHORT).show();
            return;
        }
        else if (countBackPressed == 1) {
            long duration = System.currentTimeMillis() - startTime;
            if (duration < ONE_SECOND) {
                super.onBackPressed();
            }
            else {
                countBackPressed = 0;
            }
        }
    }

    public ViewPager getViewPager() {
        return (ViewPager)findViewById(R.id.viewpager);
    }

    private void loadSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        BEST_SCORE_RACE = sharedPreferences.getInt(BEST_SCORE_RACE_STRING, 0);
        BEST_SCORE_TANKS = sharedPreferences.getInt(BEST_SCORE_TANKS_STRING, 0);
        BEST_SCORE_TETRIS = sharedPreferences.getInt(BEST_SCORE_TETRIS_STRING, 0);
        ChooseGameSettingsFragment.SOUND = sharedPreferences.getBoolean(ChooseGameSettingsFragment.SOUND_STRING, true);
        ChooseGameSettingsFragment.VIBRATION = sharedPreferences.getBoolean(ChooseGameSettingsFragment.VIBRATION_STRING, true);
    }

    public static class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case INDEX_MAIN:
                    return new ChooseGameMainFragment();
                case INDEX_RACE:
                    return new ChooseGameRaceFragment();
                case INDEX_TANKS:
                    return new ChooseGameTanksFragment();
                case INDEX_TETRIS:
                    return new ChooseGameTetrisFragment();
                case INDEX_SETTINGS:
                    return new ChooseGameSettingsFragment();
                default:
                    return null;
            }
        }

    }

}
