package com.retrogames.app.tanks;

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
import com.retrogames.app.tetris.TetrisThread;
import  android.support.v4.view.MotionEventCompat;

/**
 * Created by Piotr on 18.01.14.
 */
public class TanksSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    public TanksActivity activity;
    private TanksThread thread;
    public SurfaceHolder surfaceHolder;
    private Handler handler;
    private float mX;
    private float mY;
    private static final float TOUCH_TOLERANCE = 5;
    private static final float TOUCH_RANGE = 50;
    private static final long DOUBLE_TAP_TIME = 200;
    public TanksSurfaceView(Context context) {
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
        thread = new TanksThread(getHolder(), this, Typeface.createFromAsset(getContext().getAssets(),ChooseGameActivity.FONT),getResources().getString(R.string.score), handler );
        thread.setRunning(true);
        thread.start();
    }
    public TanksThread getThread() {
        return this.thread;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        thread.setSurfaceSize(width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.stopEndTimer();
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
        if(dx>=TOUCH_RANGE || dy>=TOUCH_RANGE )
        {
            return;
        }
        thread.moveTank(mX, mY);
    }
    int clickCounts = 0;
    long startTime;
    boolean isFirstMultiTouch = true;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        if(event.getPointerCount()>1) {
            if (isFirstMultiTouch) {
                //multitouch event
                isFirstMultiTouch = false;
                //Log.i("DUPA", "multitouch");
                thread.turnTankRight(thread.figure);
                //thread.figure.setAngle(thread.figure.getAngle()+ 90 % 360);
            }
            else {
                isFirstMultiTouch = true;
            }
        }
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
                        thread.shoot(thread.figure);
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
            case MotionEvent.AXIS_HSCROLL:
                thread.turnTankLeft(thread.figure);
                break;

            default:
                return false;
        }

        return true;
    }
}
