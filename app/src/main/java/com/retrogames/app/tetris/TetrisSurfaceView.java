package com.retrogames.app.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Tomasz on 31.12.13.
 */
public class TetrisSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private TetrisThread thread;
    private SurfaceHolder surfaceHolder;
    public TetrisSurfaceView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new TetrisThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
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

    private float mX;
    private float mY;
    private static final float TOUCH_TOLERANCE = 4;

    private void touchStart(float x, float y) {
        mX = x;
        mY = y;
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mX = x;
            mY = y;
        }
        thread.move(mX, mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
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