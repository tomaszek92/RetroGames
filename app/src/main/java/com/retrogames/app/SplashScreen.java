package com.retrogames.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tomasz on 30.12.13.
 */
public class SplashScreen extends Activity {

    private static final int SPLASH_TIMEOUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_screen);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                methodToDo();
            }
        }, SPLASH_TIMEOUT);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            methodToDo();
        }
        return super.onTouchEvent(event);
    }

    private void methodToDo() {
        if (this.isFinishing()) {
            return;
        }
        startActivity(new Intent(SplashScreen.this, ChooseGameActivity.class));
        finish();
    }
}
