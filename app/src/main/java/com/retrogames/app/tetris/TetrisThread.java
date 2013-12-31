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
    private Canvas canvas = null;
    private boolean run = false;

    private int canvasWidth = 200;
    private int canvasHeight = 400;

    private float positionX;
    private float positionY;

    public TetrisThread(SurfaceHolder surfaceHolder, TetrisSurfaceView view) {
        this.surfaceHolder = surfaceHolder;
        this.view = view;
    }

    public void setRunning(boolean run) {
        this.run = run;
    }

    public void doStart() {
        synchronized (surfaceHolder) {
            if (canvas != null) {
                positionX = canvasWidth / 2;
                positionY = canvasHeight / 2;
            }
        }
    }

    @Override
    public void run() {
        while (run) {
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
        canvas.restore();
        canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();
        paint.setARGB(100, 255, 255, 255);
        canvas.drawCircle(positionX, positionY, 50, paint);
    }

    public void move(float x, float y) {
        positionX = x;
        positionY = y;
    }
}