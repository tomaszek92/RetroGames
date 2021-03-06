package com.retrogames.app.tetris;

import android.app.Activity;
import android.content.Context;;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.retrogames.app.ChooseGameActivity;
import com.retrogames.app.R;

import java.util.Timer;

/**
 * Created by Tomasz on 31.12.13.
 */
public class TetrisActivity extends Activity {
    TetrisSurfaceView view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new TetrisSurfaceView(this);
        setContentView(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        TetrisThread thread = view.getThread();
        if (thread != null) {
            thread.setRunning(true);
            thread.startTimer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        TetrisThread thread = view.getThread();
        if (thread != null) {
            stopThreadAndTimer(thread);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        TetrisThread thread = view.getThread();
        if (thread != null) {
            stopThreadAndTimer(thread);
        }
    }

    private void stopThreadAndTimer(TetrisThread thread) {
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
                if (ChooseGameActivity.BEST_SCORE_TETRIS < TetrisGrid.getPointsScore()) {
                    ChooseGameActivity.BEST_SCORE_TETRIS = TetrisGrid.getPointsScore();
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(ChooseGameActivity.BEST_SCORE_TETRIS_STRING, ChooseGameActivity.BEST_SCORE_TETRIS);
                    editor.commit();
                }
                Intent intent = new Intent(this, ChooseGameActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                //super.onBackPressed();
            }
            else {
                countBackPressed = 0;
            }
        }
    }
}
