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
    private TetrisGrid tetrisGrid;
    private Canvas canvas = null;
    private boolean run = false;

    // rozmiary ekranu
    private int canvasWidth;
    private int canvasHeight;

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

            tetrisGrid = new TetrisGrid(canvasHeight, canvasWidth);

            int size1 = (int)((canvasWidth - 2 * TetrisGrid.MARGIN_LEFT - TetrisGrid.STROKE_WIDTH * 2) / TetrisGrid.GRID_WIDTH);
            TetrisSingleGrid.SIZE = size1;
            TetrisGrid.MARGIN_TOP = canvasHeight - 2 * TetrisGrid.STROKE_WIDTH - TetrisGrid.MARGIN_BOTTOM - TetrisGrid.GRID_HEIGHT * TetrisSingleGrid.SIZE;
            TetrisGrid.MARGIN_RIGHT = 2 * TetrisGrid.STROKE_WIDTH + TetrisGrid.MARGIN_LEFT + TetrisGrid.GRID_WIDTH * TetrisSingleGrid.SIZE;

            figure = new TetrisFigure(TetrisColors.randomColor(), TetrisShapes.randomShape());
            tetrisGrid.addFigure(figure);
        }
    }

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
                TetrisGrid.MARGIN_RIGHT,
                canvasHeight - TetrisGrid.MARGIN_BOTTOM,
                paint);
        canvas.drawLine(TetrisGrid.MARGIN_RIGHT,
                canvasHeight - TetrisGrid.MARGIN_BOTTOM,
                TetrisGrid.MARGIN_RIGHT,
                TetrisGrid.MARGIN_TOP,
                paint);
        canvas.drawLine(TetrisGrid.MARGIN_RIGHT,
                TetrisGrid.MARGIN_TOP,
                TetrisGrid.MARGIN_LEFT,
                TetrisGrid.MARGIN_TOP,
                paint);
    }

    // TODO: poprawić, aby nie wychodziło poza ekran
    public void move(float x, float y) {
        TetrisSingleGrid[][] grids = figure.getGrid();
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                grids[i][j].setX(TetrisGrid.xCoordinateToGrid(x + TetrisSingleGrid.SIZE * j));
                grids[i][j].setY(TetrisGrid.yCoordinateToGrid(y + TetrisSingleGrid.SIZE * i));
                grids[i][j].setNewRectByXY();
            }
        }
    }
}