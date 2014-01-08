package com.retrogames.app.tetris;

import android.app.Activity;
import android.content.Context;;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

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
}
