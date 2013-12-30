package com.retrogames.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class ChooseGameActivity extends FragmentActivity {

    private MyAdapter myAdapter;
    private ViewPager viewPager;

    public static final int INDEX_MAIN = 0;
    public static final int INDEX_RACE = 1;
    public static final int INDEX_TANKS = 2;
    public static final int INDEX_TETRIS = 3;
    public static final int INDEX_SETTINGS = 4;

    public static final String FONT = "fonts/ka1.ttf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);

        myAdapter = new MyAdapter(getSupportFragmentManager());

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(myAdapter);
    }

    public ViewPager getViewPager() {
        return (ViewPager)findViewById(R.id.viewpager);
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
