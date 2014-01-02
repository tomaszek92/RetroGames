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



    private float positionX;
    private float positionY;

    private TetrisGrid tetrisGrid = new TetrisGrid();

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
                    if (canvas != null) {
                    doDraw();
                    }
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

            TetrisGrid.GRID_BEGIN = TetrisGrid.MARGIN_TOP + TetrisGrid.STROKE_WIDTH;
            TetrisGrid.GRID_CENTER = height / 2;

            int size = (int)((canvasWidth - TetrisGrid.MARGIN_RIGHT - TetrisGrid.MARGIN_LEFT - TetrisGrid.STROKE_WIDTH * 2) / TetrisGrid.GRID_WIDTH);
            TetrisSingleGrid.SIZE = size;

            figure = new TetrisFigure(color, shape);
            tetrisGrid.addFigure(figure);
        }
    }

    private TetrisShapes shape = TetrisShapes.randomShape();
    private TetrisColors color = TetrisColors.randomColor();
    private TetrisFigure figure;

    private void doDraw() {
        if (canvas != null) {
            drawBackground();
            tetrisGrid.draw(canvas);
        }
    }

    private void drawBackground() {
        // wypełnianie tła
        canvas.drawColor(TetrisGrid.BACKGROUND_COLOR);

        // rysowanie linii
        Paint paint = new Paint();
        paint.setStrokeWidth(TetrisGrid.STROKE_WIDTH);
        paint.setColor(TetrisGrid.LINE_COLOR);
        canvas.drawLine(TetrisGrid.MARGIN_LEFT,
                TetrisGrid.MARGIN_TOP,
                TetrisGrid.MARGIN_LEFT,
                canvasHeight - TetrisGrid.MARGIN_BOTTOM,
                paint);
        canvas.drawLine(TetrisGrid.MARGIN_LEFT,
                canvasHeight - TetrisGrid.MARGIN_BOTTOM,
                canvasWidth - TetrisGrid.MARGIN_RIGHT,
                canvasHeight - TetrisGrid.MARGIN_BOTTOM,
                paint);
        canvas.drawLine(canvasWidth - TetrisGrid.MARGIN_RIGHT,
                canvasHeight - TetrisGrid.MARGIN_BOTTOM,
                canvasWidth - TetrisGrid.MARGIN_RIGHT,
                TetrisGrid.MARGIN_TOP,
                paint);
        canvas.drawLine(canvasWidth - TetrisGrid.MARGIN_RIGHT,
                TetrisGrid.MARGIN_TOP,
                TetrisGrid.MARGIN_LEFT,
                TetrisGrid.MARGIN_TOP,
                paint);
    }

    public void move(float x, float y) {
        positionX = x;
        positionY = y;
    }
}