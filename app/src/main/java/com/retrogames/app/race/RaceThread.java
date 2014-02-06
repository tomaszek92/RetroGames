package com.retrogames.app.race;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.SurfaceHolder;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tomasz on 06.02.14.
 */
public class RaceThread extends Thread {

    private RaceActivity activity;
    private SurfaceHolder surfaceHolder;
    private RaceSurfaceView view;
    private Canvas canvas = null;
    private boolean run = false;
    private Typeface typeface;
    private String scoreString;
    private Handler handler;
    private RaceGrid raceGrid;
    private RaceCar car;

    // rozmiary ekranu
    private int canvasWidth;
    private int canvasHeight;

    private static int VIBRATOR_LENGTH = 100;
    private static int DOWN_SPPED = 500;
    private Timer timer;

    public RaceThread(SurfaceHolder surfaceHolder,
                      RaceSurfaceView view,
                      Typeface typeface,
                      String scoreString,
                      Handler handler,
                      RaceActivity activity) {
        this.surfaceHolder = surfaceHolder;
        this.view = view;
        this.typeface = typeface;
        this.scoreString = scoreString;
        this.handler = handler;
        this.activity = activity;
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

            raceGrid = new RaceGrid(canvasHeight, canvasWidth);
            RaceGrid.setPointsScore(0);

            int size = (int)((canvasWidth - 2 * RaceGrid.MARGIN_LEFT - RaceGrid.STROKE_WIDTH * 2) / RaceGrid.GRID_WIDTH);
            RaceSingleGrid.SIZE = size;
            RaceGrid.MARGIN_TOP = canvasHeight - 2 * RaceGrid.STROKE_WIDTH - RaceGrid.MARGIN_BOTTOM - RaceGrid.GRID_HEIGHT * RaceSingleGrid.SIZE;
            RaceGrid.MARGIN_RIGHT = 2 * RaceGrid.STROKE_WIDTH + RaceGrid.MARGIN_LEFT + RaceGrid.GRID_WIDTH * RaceSingleGrid.SIZE;

            car = new RaceCar();
            raceGrid.addCar(car);

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

    private void goOneToDown() {

    }

    private void drawAll() {
        if (canvas != null) {
            drawBackground();
            drawScore();
            raceGrid.draw(canvas);
        }
    }

    private void drawScore() {
        Paint paint = new Paint();
        paint.setColor(RaceGrid.SCORE_COLOR);
        paint.setTypeface(typeface);
        paint.setTextSize(40);
        canvas.drawText(scoreString + " " + RaceGrid.getPointsScore(), RaceGrid.MARGIN_SCORE_LEFT, RaceGrid.MARGIN_SCORE_TOP, paint);
    }

    private void drawBackground() {
        // wypełnianie tła
        canvas.drawColor(RaceGrid.BACKGROUND_COLOR);

        // rysowanie linii
        Paint paint = new Paint();
        paint.setStrokeWidth(RaceGrid.STROKE_WIDTH);
        paint.setColor(RaceGrid.LINE_COLOR);

        canvas.drawLine(RaceGrid.MARGIN_LEFT,
                RaceGrid.MARGIN_TOP,
                RaceGrid.MARGIN_LEFT,
                canvasHeight - RaceGrid.MARGIN_BOTTOM,
                paint);
        canvas.drawLine(RaceGrid.MARGIN_LEFT,
                canvasHeight - RaceGrid.MARGIN_BOTTOM,
                RaceGrid.MARGIN_RIGHT,
                canvasHeight - RaceGrid.MARGIN_BOTTOM,
                paint);
        canvas.drawLine(RaceGrid.MARGIN_RIGHT,
                canvasHeight - RaceGrid.MARGIN_BOTTOM,
                RaceGrid.MARGIN_RIGHT,
                RaceGrid.MARGIN_TOP,
                paint);
        canvas.drawLine(RaceGrid.MARGIN_RIGHT,
                RaceGrid.MARGIN_TOP,
                RaceGrid.MARGIN_LEFT,
                RaceGrid.MARGIN_TOP,
                paint);
    }

    public void moveCar(float x, float y) {
        int newPosition;
        if (x >= 0 && x < canvasWidth / 3) {
            newPosition = 0;
        }
        else if (x > canvasWidth / 3 && x < canvasWidth / 3 * 2) {
            newPosition = 1;
        }
        else {
            newPosition = 2;
        }
        car.setPosition(newPosition);
    }

}
