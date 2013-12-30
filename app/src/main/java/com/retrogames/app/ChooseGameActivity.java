package com.retrogames.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class ChooseGameActivity extends FragmentActivity {

    private MyAdapter myAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);

        myAdapter = new MyAdapter(getSupportFragmentManager());

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(myAdapter);
    }

    public static class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ChooseGameFragment();
                case 1:
                    return new TetrisFragment();
                default:
                    return null;
            }
        }

    }

}
