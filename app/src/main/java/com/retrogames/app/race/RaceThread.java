package com.retrogames.app.race;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.SurfaceHolder;

import com.retrogames.app.ChooseGameActivity;
import com.retrogames.app.ChooseGameSettingsFragment;

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

            raceGrid = new RaceGrid(view, canvasHeight, canvasWidth);
            RaceGrid.setPointsScore(0);

            int size = (int)((canvasWidth - 2 * RaceGrid.MARGIN_LEFT - RaceGrid.STROKE_WIDTH * 2) / RaceGrid.GRID_WIDTH);
            RaceSingleGrid.SIZE = size;
            RaceGrid.MARGIN_TOP = canvasHeight - 2 * RaceGrid.STROKE_WIDTH - RaceGrid.MARGIN_BOTTOM - RaceGrid.GRID_HEIGHT * RaceSingleGrid.SIZE;
            RaceGrid.MARGIN_RIGHT = 2 * RaceGrid.STROKE_WIDTH + RaceGrid.MARGIN_LEFT + RaceGrid.GRID_WIDTH * RaceSingleGrid.SIZE;

            car = new RaceCar();
            raceGrid.addCar(car);

            timerCounter = 0;
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
        }, RaceGrid.DOWN_SPEED - RaceGrid.DOWN_SPEED_CHANGE *  RaceGrid.LEVEL,
                RaceGrid.DOWN_SPEED - RaceGrid.DOWN_SPEED_CHANGE *  RaceGrid.LEVEL);
    }

    private void goOneToDown() {
        if (raceGrid.getMovesToNextCar() == 10) {
            raceGrid.addEnemyCar();
            raceGrid.setMovesToNextCar(0);
        }
        else {
            raceGrid.setMovesToNextCar(raceGrid.getMovesToNextCar() + 1);
        }
        if (raceGrid.goOneDown(car)) {
            endGame();
        }
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

    private boolean canMoveCar = true;
    public void moveCar(float x) {
        RacePosition newPosition;
        if (x >= 0 && x < canvasWidth / 3) {
            newPosition = RacePosition.LEFT;
        }
        else if (x > canvasWidth / 3 && x < canvasWidth / 3 * 2) {
            newPosition = RacePosition.CENTER;
        }
        else {
            newPosition = RacePosition.RIGHT;
        }
        if (canMoveCar && raceGrid.move(newPosition, car)) {
            endGame();
        }
    }

    public static int timerCounter = 0;
    private void endGame() {
        raceGrid.playSoundExsplosion();
        Timer endTimer = new Timer();
        vibrate(VIBRATOR_LENGTH);
        endTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                canMoveCar = false;
                raceGrid.animateCrash(car);
                RaceThread.timerCounter++;
                if (RaceThread.timerCounter == 10) {
                    checkAndSaveBestScore();
                    handler.sendMessage(Message.obtain());
                    run = false;
                    this.cancel();
                }
            }
        }, 200, 200);
    }

    public void checkAndSaveBestScore() {
        if (ChooseGameActivity.BEST_SCORE_RACE < RaceGrid.getPointsScore()) {
            ChooseGameActivity.BEST_SCORE_RACE = RaceGrid.getPointsScore();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(ChooseGameActivity.BEST_SCORE_RACE_STRING, ChooseGameActivity.BEST_SCORE_RACE);
            editor.commit();
        }
    }

    private void vibrate(int lenght) {
        if (ChooseGameSettingsFragment.VIBRATION) {
            Vibrator vibrator = (Vibrator)activity.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(lenght);
        }
    }
}
