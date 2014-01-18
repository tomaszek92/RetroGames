package com.retrogames.app.tanks;

import android.app.Activity;
import android.os.Bundle;
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
}
