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
/**
            tetrisGrid = new TetrisGrid(view, canvasHeight, canvasWidth);
            tetrisGrid.setPointsScore(0);

            int size = (int)((canvasWidth - 2 * TetrisGrid.MARGIN_LEFT - TetrisGrid.STROKE_WIDTH * 2) / TetrisGrid.GRID_WIDTH);
            TetrisSingleGrid.SIZE = size;
            TetrisGrid.MARGIN_TOP = canvasHeight - 2 * TetrisGrid.STROKE_WIDTH - TetrisGrid.MARGIN_BOTTOM - TetrisGrid.GRID_HEIGHT * TetrisSingleGrid.SIZE;
            TetrisGrid.MARGIN_RIGHT = 2 * TetrisGrid.STROKE_WIDTH + TetrisGrid.MARGIN_LEFT + TetrisGrid.GRID_WIDTH * TetrisSingleGrid.SIZE;

            figure = new TetrisFigure(TetrisColors.randomColor(), TetrisShapes.randomShape(), new Random().nextInt(4) * 90);
            tetrisGrid.addFigure(figure);
*/
            timer = new Timer();

        }
    }

    @Override
    public void run() {
        while (run) {
            try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    if (canvas != null) {
                        //drawAll();
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

}
