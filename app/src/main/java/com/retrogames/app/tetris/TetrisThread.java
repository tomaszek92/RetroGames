package com.retrogames.app.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

/**
 * Created by Tomasz on 31.12.13.
 */
public class TetrisThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private TetrisSurfaceView view;
    private boolean run = false;
    private Canvas canvas = null;

    private int canvasWidth = 200;
    private int canvasHeight = 400;
    private static final int SPEED = 2;

    private float bubbleX;
    private float bubbleY;
    private float headingX;
    private float headingY;

    public TetrisThread(SurfaceHolder surfaceHolder, TetrisSurfaceView view) {
        this.surfaceHolder = surfaceHolder;
        this.view = view;
    }

    public void setRunning(boolean run) {
        this.run = run;
    }

    public void doStart() {
        synchronized (surfaceHolder) {
            bubbleX = canvasWidth / 2;
            bubbleY = canvasHeight / 2;
            headingX = (float) (-1 + (Math.random() * 2));
            headingY = (float) (-1 + (Math.random() * 2));
        }
    }

    @Override
    public void run() {
        while (run) {
            //Canvas c = null;
            try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    doDraw();
                }
            }
            finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public void setSurfaceSize(int width, int height) {
        synchronized (surfaceHolder) {
            canvasWidth = width;
            canvasHeight = height;
            doStart();
        }
    }

    private void doDraw() {
        //bubbleX = bubbleX + (headingX * SPEED);
        //bubbleY = bubbleY + (headingY * SPEED);
        canvas.restore();
        canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();
        paint.setARGB(100, 255, 255, 255);
        canvas.drawCircle(bubbleX, bubbleY, 50, paint);
    }

    public void move(float x, float y) {
        bubbleX = x;
        bubbleY = y;
    }
}