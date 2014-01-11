package com.retrogames.app.tetris;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;
import android.os.Handler;
import com.retrogames.app.ChooseGameActivity;
import com.retrogames.app.R;

/**
 * Created by Tomasz on 31.12.13.
 */
public class TetrisSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    public TetrisActivity activity;
    private TetrisThread thread;
    private SurfaceHolder surfaceHolder;
    private Handler handler;
    private float mX;
    private float mY;
    private static final float TOUCH_TOLERANCE = 5;
    private static final long DOUBLE_TAP_TIME = 500;

    public TetrisSurfaceView(Context context) {
        super(context);
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
        thread = new TetrisThread(getHolder(), this, Typeface.createFromAsset(getContext().getAssets(), ChooseGameActivity.FONT), getResources().getString(R.string.score), handler);
        thread.setRunning(true);
        thread.start();
    }

    public TetrisThread getThread() {
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
    private void touchStart(float x, float y) {
        mX = x;
        mY = y;
    }

    // przesuwanie palcem po ekranie
    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mX = x;
            mY = y;
        }
        thread.moveFigure(mX, mY);
    }

    int clickCounts = 0;
    long startTime;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                clickCounts++;
                if (clickCounts == 1) {
                    startTime = System.currentTimeMillis();
                }
                else if (clickCounts == 2) {
                    long duration = System.currentTimeMillis() - startTime;
                    if (duration < DOUBLE_TAP_TIME) {
                        clickCounts = 0;
                        thread.turnFigure();
                        return true;
                    }
                    else  {
                        clickCounts = 1;
                        startTime = System.currentTimeMillis();
                        return true;
                    }
                }
                touchStart(touchX, touchY);
                break;

            case MotionEvent.ACTION_MOVE:
                if (thread != null) {
                    touchMove(touchX, touchY);
                }
                break;

            case MotionEvent.ACTION_UP:
                break;

            default:
                return false;
            }

        return true;
    }
}