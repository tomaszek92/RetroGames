package com.retrogames.app.race;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.retrogames.app.ChooseGameActivity;
import com.retrogames.app.R;

/**
 * Created by Tomasz on 06.02.14.
 */
public class RaceSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private RaceActivity activity;
    private RaceThread thread;
    private SurfaceHolder surfaceHolder;
    private Handler handler;
    private float mX;
    private static final float TOUCH_TOLERANCE = 5;

    public RaceSurfaceView(Context context) {
        super(context);
        this.activity = (RaceActivity)context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                thread.setRunning(false);
                thread.interrupt();
                Intent intent = new Intent(getContext(), ChooseGameActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                getContext().startActivity(intent);
            }
        };
        thread = new RaceThread(getHolder(),
                this,
                Typeface.createFromAsset(getContext().getAssets(), ChooseGameActivity.FONT),
                getResources().getString(R.string.score),
                handler,
                activity);
        thread.setRunning(true);
        thread.start();
    }

    public RaceThread getThread() {
        return this.thread;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        thread.setSurfaceSize(width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            }
            catch (InterruptedException e) {
            }
        }
    }
    // kliknięto pierwszy raz
    private void touchStart(float x) {
        mX = x;
    }

    // przesuwanie palcem po ekranie
    private void touchMove(float x) {
        float dx = Math.abs(x - mX);
        if (dx >= TOUCH_TOLERANCE) {
            mX = x;
        }
        thread.moveCar(mX);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(touchX);
                break;

            case MotionEvent.ACTION_MOVE:
                if (thread != null) {
                    touchMove(touchX);
                }
                break;

            default:
                return false;
        }

        return true;
    }
}
