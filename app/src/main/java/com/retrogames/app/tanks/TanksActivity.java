package com.retrogames.app.tanks;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.retrogames.app.ChooseGameActivity;
import com.retrogames.app.R;
import com.retrogames.app.race.RaceGrid;

import java.util.Timer;

/**
 * Created by Piotr on 18.01.14.
 */
public class TanksActivity extends Activity {
    TanksSurfaceView view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new TanksSurfaceView(this);
        setContentView(view);
    }
    @Override
    public void onResume() {
        super.onResume();
        TanksThread thread = view.getThread();
        if (thread != null) {
            thread.setRunning(true);
           // thread.startTimer();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        TanksThread thread = view.getThread();
        if (thread != null) {
            stopThreadAndTimer(thread);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        TanksThread thread = view.getThread();
        if (thread != null) {
            stopThreadAndTimer(thread);
        }
    }

    private void stopThreadAndTimer(TanksThread thread) {
        thread.setRunning(false);
        Timer timer = thread.getTimer();
        timer.cancel();
    }

    private static final int ONE_SECOND = 1000;
    int countBackPressed = 0;
    long startTime = 0;
    @Override
    public void onBackPressed() {
        if (countBackPressed == 0) {
            startTime = System.currentTimeMillis();
            countBackPressed++;
            Toast.makeText(this, R.string.return_to_main_menu, Toast.LENGTH_SHORT).show();
            return;
        }
        else if (countBackPressed == 1) {
            long duration = System.currentTimeMillis() - startTime;
            if (duration < ONE_SECOND) {
                if (ChooseGameActivity.BEST_SCORE_TANKS < TanksGrid.getPointsScore()) {
                    ChooseGameActivity.BEST_SCORE_TANKS= TanksGrid.getPointsScore();
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(ChooseGameActivity.BEST_SCORE_TANKS_STRING, ChooseGameActivity.BEST_SCORE_TANKS);
                    editor.commit();
                }
                Intent intent = new Intent(this, ChooseGameActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
            else {
                countBackPressed = 0;
            }
        }
    }
}
