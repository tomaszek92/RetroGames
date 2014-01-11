package com.retrogames.app.tetris;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.SurfaceHolder;
import android.view.View;

import com.retrogames.app.ChooseGameActivity;
import com.retrogames.app.R;

import java.util.Random;
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
    private Handler handler;

    // rozmiary ekranu
    private int canvasWidth;
    private int canvasHeight;

    private static int DOWN_SPPED = 500;
    private Timer timer;

    public TetrisThread(SurfaceHolder surfaceHolder, TetrisSurfaceView view, Typeface typeface, String scoreString, Handler handler) {
        this.surfaceHolder = surfaceHolder;
        this.view = view;
        this.typeface = typeface;
        this.scoreString = scoreString;
        this.handler = handler;
    }

    public void setRunning(boolean run) {
        this.run = run;
    }

    public Timer getTimer() {
        return this.timer;
    }

    @Override
    public void run() {
        while (run) {
            try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    if (canvas != null) {
                    drawAll();
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

            tetrisGrid = new TetrisGrid(view, canvasHeight, canvasWidth);
            tetrisGrid.setPointsScore(0);

            int size = (int)((canvasWidth - 2 * TetrisGrid.MARGIN_LEFT - TetrisGrid.STROKE_WIDTH * 2) / TetrisGrid.GRID_WIDTH);
            TetrisSingleGrid.SIZE = size;
            TetrisGrid.MARGIN_TOP = canvasHeight - 2 * TetrisGrid.STROKE_WIDTH - TetrisGrid.MARGIN_BOTTOM - TetrisGrid.GRID_HEIGHT * TetrisSingleGrid.SIZE;
            TetrisGrid.MARGIN_RIGHT = 2 * TetrisGrid.STROKE_WIDTH + TetrisGrid.MARGIN_LEFT + TetrisGrid.GRID_WIDTH * TetrisSingleGrid.SIZE;

            figure = new TetrisFigure(TetrisColors.randomColor(), TetrisShapes.randomShape(), new Random().nextInt(4) * 90);
            tetrisGrid.addFigure(figure);

            timer = new Timer();
            startTimer();
        }
    }

    public void startTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                goOneToDown();
            }
        }, DOWN_SPPED, DOWN_SPPED);
    }

    // przesuwanie figury o jeden w dół
    private void goOneToDown() {
        boolean itsEnd = false;
        TetrisSingleGrid[][] grids = figure.getGrid();

        for (int i = grids.length - 1; i >= 0 && !itsEnd; i--) {
            for (int j = grids[i].length - 1; j >= 0 && !itsEnd ; j--) {
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
                    if (i - 1 >= 0 && !grids[i-1][j].getOccupied()) {
                        itsEnd = false;
                    }
                }
            }
        }

        if (!itsEnd) {
            for (int i = 0; i < grids.length; i++) {
                for (int j = 0; j < grids[i].length ; j++) {
                    grids[i][j].setY(grids[i][j].getY() + 1);
                    grids[i][j].setNewRectByXY();
                    tetrisGrid.refreshGrid();
                }
            }
        }
        else {
            tetrisGrid.playSoundPutBlock();
            for (int k = 0; k < TetrisGrid.GRID_HEIGHT - 1; k++) {
                tetrisGrid.checkGridAddPointsAndRemoveRows();
            }
            figure = new TetrisFigure(TetrisColors.randomColor(), TetrisShapes.randomShape(), new Random().nextInt(4) * 90);
            grids = figure.getGrid();
            boolean endGame = false;

            for (int i = 0; i < grids.length; i++) {
                for (int j = 0; j < grids[i].length; j++) {
                    if (!tetrisGrid.isNotOccupied(grids[i][j].getX(), grids[i][j].getY())) {
                        endGame = true;
                    }
                }
            }

            tetrisGrid.addFigure(figure);
            if (endGame) {
                if (run) {
                    if (ChooseGameActivity.BEST_SCORE_TETRIS < TetrisGrid.getPointsScore()) {
                        saveNewBestScore();
                    }
                    handler.sendMessage(Message.obtain());
                    run = false;
                }
            }
        }
    }

    private void saveNewBestScore() {
        ChooseGameActivity.BEST_SCORE_TETRIS = TetrisGrid.getPointsScore();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ChooseGameActivity.BEST_SCORE_TETRIS_STRING, ChooseGameActivity.BEST_SCORE_TETRIS);
        editor.commit();
    }

    private void drawAll() {
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

    public void moveFigure(float x, float y) {
        tetrisGrid.moveFigure(figure, x, y);
    }

    public void turnFigure() {
        if (figure.turn()) {
            tetrisGrid.playSoundTurnFigure();
            tetrisGrid.refreshGrid();
        }
    }
}