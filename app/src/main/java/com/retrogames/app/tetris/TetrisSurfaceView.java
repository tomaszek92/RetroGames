package com.retrogames.app.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.logging.Handler;

/**
 * Created by Tomasz on 31.12.13.
 */
public class TetrisSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private TetrisThread thread;
    private SurfaceHolder sh;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public TetrisSurfaceView(Context context) {
        super(context);
        sh = getHolder();
        sh.addCallback(this);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        setFocusable(true);
    }

    public TetrisThread getThread() {
        return thread;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = sh.lockCanvas();
        canvas.drawColor(Color.BLACK);
        canvas.drawCircle(100, 200, 50, paint);
        sh.unlockCanvasAndPost(canvas);

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
        try {
            thread.join();
            thread.setRunning(false);
        }
        catch (InterruptedException e) {
        }
    }

    private float mX;
    private float mY;
    private static final float TOUCH_TOLERANCE = 4;

    private void touch_start(float x, float y) {
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mX = x;
            mY = y;
        }
        thread.move(mX, mY);
    }
    private void touch_up() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(touchX, touchY);
                break;

            case MotionEvent.ACTION_MOVE:
                if (thread != null) {
                    touch_move(touchX, touchY);
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