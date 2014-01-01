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
    private static final int MARGIN_TOP = 100;
    private static final int MARGIN_BOTTOM = 10;
    private static final int MARGIN_LEFT = 10;
    private static final int MARGIN_RIGHT = 10;

    private static final int STROKE_WIDTH = 5;
    private static final int LINE_COLOR = Color.WHITE;
    private static final int BACKGROUND_COLOR = Color.DKGRAY;

    private float positionX;
    private float positionY;

    public TetrisThread(SurfaceHolder surfaceHolder, TetrisSurfaceView view) {
        this.surfaceHolder = surfaceHolder;
        this.view = view;
    }

    public void setRunning(boolean run) {
        this.run = run;
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
            int size = (int)((canvasWidth - MARGIN_RIGHT - MARGIN_LEFT - STROKE_WIDTH * 2) / TetrisGrid.GRID_WIDTH);
            TetrisSingleGrid.SIZE = size;
        }
    }

    private TetrisShapes shape = TetrisShapes.randomShape();
    private TetrisColors color = TetrisColors.randomColor();
    private TetrisFigure figure = new TetrisFigure(color, shape);

    private void doDraw() {
        drawBackground();

        figure.drawFigure(canvas);
    }

    private void drawBackground() {
        // wypełnianie tłą
        canvas.drawColor(BACKGROUND_COLOR);

        // rysowanie linii
        Paint paint = new Paint();
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setColor(LINE_COLOR);
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