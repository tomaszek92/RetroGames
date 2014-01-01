package com.retrogames.app.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;;
import android.view.SurfaceHolder;

/**
 * Created by Tomasz on 31.12.13.
 */
public class TetrisThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private TetrisSurfaceView view;
    private Canvas canvas = null;
    private boolean run = false;

    // rozmiary ekranu
    private int canvasWidth = 200;
    private int canvasHeight = 400;

    // marginesy do rysowania tła
    private static int MARGIN_TOP = 100;
    private static int MARGIN_BOTTOM = 10;
    private static int MARGIN_LEFT = 10;
    private static int MARGIN_RIGHT = 10;

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
            positionX = canvasWidth / 2;
            positionY = canvasHeight / 2;
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
        clearCanvas();
        drawBackground();

        TetrisFigure tetrisFigure = new TetrisFigure(TetrisColors.randomColor(), TetrisShapes.BOX_1X1);

        tetrisFigure.drawFigure(canvas);

    }

    private void clearCanvas() {
        canvas.restore();
        canvas.drawColor(Color.GREEN);
    }

    private void drawBackground() {
        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.WHITE);
        canvas.drawLine(MARGIN_LEFT, MARGIN_TOP, MARGIN_LEFT, canvasHeight - MARGIN_BOTTOM, paint);
        canvas.drawLine(MARGIN_LEFT, canvasHeight - MARGIN_BOTTOM, canvasWidth - MARGIN_RIGHT, canvasHeight - MARGIN_BOTTOM, paint);
        canvas.drawLine(canvasWidth - MARGIN_RIGHT, canvasHeight - MARGIN_BOTTOM, canvasWidth - MARGIN_RIGHT, MARGIN_TOP, paint);
        canvas.drawLine(canvasWidth - MARGIN_RIGHT, MARGIN_TOP, MARGIN_LEFT, MARGIN_TOP, paint);
    }

    public void move(float x, float y) {
        positionX = x;
        positionY = y;
    }
}