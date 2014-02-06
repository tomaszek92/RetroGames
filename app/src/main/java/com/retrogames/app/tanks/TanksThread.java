package com.retrogames.app.tanks;

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
 * Created by Piotr on 18.01.14.
 */
public class TanksThread extends Thread {


    private SurfaceHolder surfaceHolder;
    private TanksSurfaceView view;
    private Canvas canvas = null;
    private boolean run = false;
    private Typeface typeface;
    private String scoreString;
    private Handler handler;
    private TanksGrid tanksGrid;
    private TanksFigure figure;

    private int canvasWidth;
    private int canvasHeight;

    private static int DOWN_SPPED = 500;
    private Timer timer;

    public TanksThread(SurfaceHolder surfaceHolder, TanksSurfaceView view, Typeface typeface, String scoreString, Handler handler) {
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

    public void setSurfaceSize(int width, int height) {
        synchronized (surfaceHolder) {
            canvasWidth = width;
            canvasHeight = height;

            tanksGrid = new TanksGrid(view, canvasHeight, canvasWidth);
            tanksGrid.setPointsScore(0);

            int size = (int)((canvasWidth - 2 * TanksGrid.MARGIN_LEFT - TanksGrid.STROKE_WIDTH * 2) / TanksGrid.GRID_WIDTH);
            TanksSingleGrid.SIZE = size;
            TanksGrid.MARGIN_TOP = canvasHeight - 2 * TanksGrid.STROKE_WIDTH - TanksGrid.MARGIN_BOTTOM - TanksGrid.GRID_HEIGHT * TanksSingleGrid.SIZE;
            TanksGrid.MARGIN_RIGHT = 2 * TanksGrid.STROKE_WIDTH + TanksGrid.MARGIN_LEFT + TanksGrid.GRID_WIDTH * TanksSingleGrid.SIZE;



            figure = new TanksFigure( TanksShapes.ENEMY, new Random().nextInt(4) * 90);
            tanksGrid.addFigure(figure);

            figure = new TanksFigure( TanksShapes.BULLET, new Random().nextInt(4) * 90);
            tanksGrid.addFigure(figure);
            figure = new TanksFigure( TanksShapes.ENEMY, new Random().nextInt(4) * 90);
            tanksGrid.addFigure(figure);
            figure = new TanksFigure( TanksShapes.ENEMY, new Random().nextInt(4) * 90);
            tanksGrid.addFigure(figure);
            figure = new TanksFigure( TanksShapes.ENEMY, new Random().nextInt(4) * 90);
            tanksGrid.addFigure(figure);
            figure = new TanksFigure( TanksShapes.ENEMY, new Random().nextInt(4) * 90);
            tanksGrid.addFigure(figure);
            figure = new TanksFigure( TanksShapes.ENEMY, new Random().nextInt(4) * 90);
            tanksGrid.addFigure(figure);
            figure = new TanksFigure( TanksShapes.ENEMY, new Random().nextInt(4) * 90);
            tanksGrid.addFigure(figure);
            figure = new TanksFigure( TanksShapes.ENEMY, new Random().nextInt(4) * 90);
            tanksGrid.addFigure(figure);
            figure = new TanksFigure( TanksShapes.ENEMY, new Random().nextInt(4) * 90);
            tanksGrid.addFigure(figure);
            figure = new TanksFigure( TanksShapes.ENEMY, new Random().nextInt(4) * 90);
            tanksGrid.addFigure(figure);

            figure = new TanksFigure( TanksShapes.ENEMY, new Random().nextInt(4) * 90);
            tanksGrid.addFigure(figure);
            figure = new TanksFigure( TanksShapes.ENEMY, new Random().nextInt(4) * 90);
            tanksGrid.addFigure(figure);
            figure = new TanksFigure( TanksShapes.ENEMY, new Random().nextInt(4) * 90);
            tanksGrid.addFigure(figure);
            figure = new TanksFigure( TanksShapes.ENEMY, new Random().nextInt(4) * 90);
            tanksGrid.addFigure(figure);
            figure = new TanksFigure( TanksShapes.ENEMY, new Random().nextInt(4) * 90);
            tanksGrid.addFigure(figure);
            figure = new TanksFigure( TanksShapes.ENEMY, new Random().nextInt(4) * 90);
            tanksGrid.addFigure(figure);

            figure = new TanksFigure( TanksShapes.YOU, new Random().nextInt(4) * 90);
            tanksGrid.addFigure(figure);



            timer = new Timer();
            //startTimer();
        }
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
    private void drawAll() {
        if (canvas != null) {
            drawBackground();
            drawScore();
            tanksGrid.drawAllFigures(canvas);
        }
    }

    public void moveTank(float x, float y) {
        tanksGrid.moveTank(figure, x, y);
    }
    private void drawScore() {
        Paint paint = new Paint();
        paint.setColor(TanksGrid.SCORE_COLOR);
        paint.setTypeface(typeface);
        paint.setTextSize(40);
        canvas.drawText(scoreString + " " + TanksGrid.getPointsScore(), TanksGrid.MARGIN_SCORE_LEFT, TanksGrid.MARGIN_SCORE_TOP, paint);
    }
    private void drawBackground() {
        // wypełnianie tła
        canvas.drawColor(TanksGrid.BACKGROUND_COLOR);

        // rysowanie linii
        Paint paint = new Paint();
        paint.setStrokeWidth(TanksGrid.STROKE_WIDTH);
        paint.setColor(TanksGrid.LINE_COLOR);

        canvas.drawLine(TanksGrid.MARGIN_LEFT,
                TanksGrid.MARGIN_TOP,
                TanksGrid.MARGIN_LEFT,
                canvasHeight - TanksGrid.MARGIN_BOTTOM,
                paint);
        canvas.drawLine(TanksGrid.MARGIN_LEFT,
                canvasHeight - TanksGrid.MARGIN_BOTTOM,
                TanksGrid.MARGIN_RIGHT,
                canvasHeight - TanksGrid.MARGIN_BOTTOM,
                paint);
        canvas.drawLine(TanksGrid.MARGIN_RIGHT,
                canvasHeight - TanksGrid.MARGIN_BOTTOM,
                TanksGrid.MARGIN_RIGHT,
                TanksGrid.MARGIN_TOP,
                paint);
        canvas.drawLine(TanksGrid.MARGIN_RIGHT,
                TanksGrid.MARGIN_TOP,
                TanksGrid.MARGIN_LEFT,
                TanksGrid.MARGIN_TOP,
                paint);
    }

}
