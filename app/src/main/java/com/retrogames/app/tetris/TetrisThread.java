package com.retrogames.app.tetris;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;;
import android.graphics.Typeface;
import android.view.SurfaceHolder;
import android.os.Bundle;

import com.retrogames.app.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tomasz on 31.12.13.
 */
public class TetrisThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private TetrisSurfaceView view;
    private TetrisGrid tetrisGrid;
    private TetrisFigure figure;
    private Canvas canvas = null;
    private boolean run = false;
    private Typeface typeface;
    private String scoreString;

    // rozmiary ekranu
    private int canvasWidth;
    private int canvasHeight;

    private static int DRAG_TIMEOUT = 1000;
    private static int DOWN_SPPED = 500;
    private Timer timer;

    public TetrisThread(SurfaceHolder surfaceHolder, TetrisSurfaceView view, Typeface typeface, String scoreString) {
        this.surfaceHolder = surfaceHolder;
        this.view = view;
        this.typeface = typeface;
        this.scoreString = scoreString;
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

            figure = new TetrisFigure();
            tetrisGrid.addFigure(figure);

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    goOneToDown();
                }
            }, DOWN_SPPED, DOWN_SPPED);
        }
    }

    private void goOneToDown() {
        boolean itsEnd = false;
        TetrisSingleGrid[][] grids = figure.getGrid();
        for (int i = 0; i < grids.length && itsEnd == false; i++) {
            for (int j = 0; j < grids[i].length && itsEnd == false ; j++) {
                if (grids[i][j].getY() + 1 == TetrisGrid.GRID_HEIGHT) {
                    itsEnd = true;
                }
                if (i == grids.length - 1 && grids[i][j].getOccupied()) {
                    if (!tetrisGrid.isNotOccupied(grids[i][j].getX(), grids[i][j].getY() + 1)) {
                        itsEnd = true;
                    }
                }

                if (!grids[i][j].getOccupied()) {
                    if (!tetrisGrid.isNotOccupied(grids[i][j].getX(), grids[i][j].getY())) {
                        itsEnd = true;
                    }
                    if (j - 1 >= 0 && !grids[i][j-1].getOccupied()) {
                        itsEnd = false;
                    }
                }
            }
        }

        if (itsEnd == false) {
            for (int i = 0; i < grids.length; i++) {
                for (int j = 0; j < grids[i].length ; j++) {
                    grids[i][j].setY(grids[i][j].getY() + 1);
                    grids[i][j].setNewRectByXY();
                    tetrisGrid.refreshGrid();
                }
            }
        }
        else {
            figure = new TetrisFigure();
            tetrisGrid.addFigure(figure);
        }

        tetrisGrid.checkGrid();
    }

    private void doDraw() {
        if (canvas != null) {
            drawBackground();
            drawScore();
            tetrisGrid.drawAllFigures(canvas);
        }
    }

    private void drawScore() {
        Paint paint = new Paint();
        paint.setColor(TetrisGrid.SCORE_COLOR);
        paint.setTypeface(typeface);
        paint.setTextSize(40);
        canvas.drawText(scoreString + " " + TetrisGrid.getPointsScore(), TetrisGrid.MARGIN_SCORE_LEFT, TetrisGrid.MARGIN_SCORE_TOP, paint);
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

    // TODO: poprawić, aby nie wychodziło poza ekran i aby figury się nie rozjeżdzały
    public void move(float x, float y) {
        TetrisSingleGrid[][] grids = figure.getGrid();

        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                int xNew = TetrisGrid.xCoordinateToGrid(x + TetrisSingleGrid.SIZE * j);
                int yNew = TetrisGrid.yCoordinateToGrid(y + TetrisSingleGrid.SIZE * i);
                if (tetrisGrid.isNotOccupied(xNew, yNew)) {
                    grids[i][j].setX(xNew);
                    grids[i][j].setY(yNew);
                    grids[i][j].setNewRectByXY();
                    tetrisGrid.refreshGrid();
                }
            }
        }
    }
}