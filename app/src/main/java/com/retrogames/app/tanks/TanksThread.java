package com.retrogames.app.tanks;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

import com.retrogames.app.ChooseGameActivity;
import com.retrogames.app.ChooseGameSettingsFragment;
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
    public TanksFigure figure;


    private int canvasWidth;
    private int canvasHeight;
    private TanksActivity activity;


    private Timer timer;

    public TanksThread(SurfaceHolder surfaceHolder, TanksSurfaceView view, Typeface typeface, String scoreString, Handler handler) {
        this.surfaceHolder = surfaceHolder;
        this.view = view;
        this.typeface = typeface;
        this.scoreString = scoreString;
        this.handler = handler;

        timerCounter = 0;
        this.endTimer = null;
    }
    private static int MOVE_SPPED = 200;
    public void startTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                EnemyTurn();
            }
        }, MOVE_SPPED, MOVE_SPPED);
        }

    public  int num = 0;
    boolean chujZFlaga = true;
    private void EnemyTurn()
    {
        if (chujZFlaga) {
            num++;
            num =num%4;

            boolean itsEnd = false;
            TanksSingleGrid[][] grids = figure.getGrid();

            int putNew = 5;
            Random random = new Random();
            if(random.nextInt(15) == putNew )
            {
                //dodajemy nowego tanka
                TanksFigure newTank = new TanksFigure( TanksShapes.ENEMY, new Random().nextInt(4) * 90);
                TanksSingleGrid[][] isReserved = newTank.getGrid();
                boolean add = true;
                for(int i = 0; i<isReserved.length; i++)
                {
                    for(int j=0; j<isReserved[i].length; j++)
                    {
                        if(!tanksGrid.isNotOccupied(isReserved[i][j].getX(),isReserved[i][j].getY()))
                        {
                            add = false;
                        }
                    }
                }
                if(add == true)
                {
                    tanksGrid.addFigure(newTank);
                }
            }
            for (int i=0; i<tanksGrid.gameFigures.size(); i++)
            {
                TanksFigure shape = tanksGrid.gameFigures.get(i);
                if( shape.getShape() == TanksShapes.ENEMY)
                {
                    int move = 1;
                    int turn = 2;
                    int shoot = 3;
                    Random random1 = new Random();
                    int rnd = random1.nextInt(4);
                    if(rnd == move)
                    {
                        //Poruszamy czołgiem, jeśli możliwe
                        moveForward(shape);
                        tanksGrid.refreshGrid();
                    }
                    else if(rnd==turn)
                    {
                        // obróć czołg
                        if(random1.nextInt(2) == 1)
                        {
                            turnTankLeft(shape);
                            int ang = shape.getAngle();
                            ang = ang - 90;
                            if(ang<0)
                            {
                                ang=270;
                            }
                            shape.setAngle(ang);
                        }
                        else
                        {
                            turnTankRight(shape);
                            int ang = shape.getAngle();
                            ang = ang + 90;
                            if(ang>270)
                            {
                                ang=0;
                            }
                            shape.setAngle(ang);
                        }
                        tanksGrid.refreshGrid();
                    }
                    rnd = random.nextInt(10);
                    if(rnd==shoot)
                    {
                       shoot(shape);
                        tanksGrid.refreshGrid();
                    }

                }
                if(shape.getShape() == TanksShapes.BULLET)
                {
                     moveBullet(shape); // zawsze poruszamy
                    tanksGrid.refreshGrid();
                }
            }
        }
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


            figure = new TanksFigure( TanksShapes.YOU, new Random().nextInt(4) * 90);
            tanksGrid.addFigure(figure);



            timer = new Timer();
            startTimer();
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
    public void checkAndSaveBestScore() {
        if (ChooseGameActivity.BEST_SCORE_TANKS < TanksGrid.getPointsScore()) {
            ChooseGameActivity.BEST_SCORE_TANKS = TanksGrid.getPointsScore();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(ChooseGameActivity.BEST_SCORE_TANKS_STRING, ChooseGameActivity.BEST_SCORE_TANKS);
            editor.commit();
        }
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
    public Point resolveMove(TanksFigure figure)
    {
        int angle = figure.getAngle();
        int x = 0;
        int y = 0;
        if(angle == 0)
        {
            x = 0;
            y = 1;
        }
        else if(angle == 90)
        {
            x = -1;
            y =0;
        }
        else if(angle == 180)
        {
            x =0;
            y=-1;
        }
        else if(angle == 270)
        {
            x=1;
            y=0;
        }

        return new Point(x,y);
    }
    public boolean isMovePossible(TanksFigure figure, Point direction)
    {
        TanksFigure movedFigure = figure;
        TanksSingleGrid[][] grid = movedFigure.getGrid();
        for(int i=0; i< grid.length; i++)
        {
            for(int j=0;j<grid[i].length;j++)
            {
                grid[i][j].setX(grid[i][j].getX()+direction.x);
                grid[i][j].setY(grid[i][j].getY() + direction.y);
                if(grid[i][j].getX() < 0 || grid[i][j].getY()<0 || grid[i][j].getX() >tanksGrid.GRID_WIDTH || grid[i][j].getY() > tanksGrid.GRID_HEIGHT)
                {
                    return false;
                }
                if(!tanksGrid.isNotOccupied(grid[i][j].getX(),grid[i][j].getY()))
                {
                    return false;
                }

            }
        }
        return true;
    }
    public TanksSingleGrid[][] moveIfPossible(TanksFigure figure, Point direction)
    {
        //TanksSingleGrid[][] ret = figure.getGrid();
        TanksFigure movedFigure = figure;
        TanksSingleGrid[][] grid = movedFigure.getGrid();
        TanksSingleGrid[][] gridClone = TanksSingleGrid.cloneArrayDim2(grid);
        for(int i=0; i< grid.length; i++)
        {
            for(int j=0;j<grid[i].length;j++)
            {
                gridClone[i][j].setX(grid[i][j].getX() + direction.x);
                gridClone[i][j].setY(grid[i][j].getY() + direction.y);
                if(gridClone[i][j].getX() < 0 || gridClone[i][j].getY() <0
                        || gridClone[i][j].getX() >=tanksGrid.GRID_WIDTH || gridClone[i][j].getY() >= tanksGrid.GRID_HEIGHT)
                {
                    return grid;
                }
            }
        }


        if(figure.getAngle() == 0 )
        {
            if(!tanksGrid.isNotOccupied(grid[1][1].getX(), grid[1][1].getY()+2)
                    && !tanksGrid.isNotOccupied(grid[1][1].getX()+1, grid[1][1].getY()+1)
                    && !tanksGrid.isNotOccupied(grid[1][1].getX()-1, grid[1][1].getY()+1))
            {
                return grid;
            }
        }
        else if(figure.getAngle() == 90 )
        {
            if(!tanksGrid.isNotOccupied(grid[1][1].getX()-2, grid[1][1].getY())
                    && !tanksGrid.isNotOccupied(grid[1][1].getX()-1, grid[1][1].getY()+1)
                    && !tanksGrid.isNotOccupied(grid[1][1].getX()-1, grid[1][1].getY()-1))
            {
                return grid;
            }
        }
        else if(figure.getAngle() == 180 )
        {
            if(!tanksGrid.isNotOccupied(grid[1][1].getX(), grid[1][1].getY()-2)
                    && !tanksGrid.isNotOccupied(grid[1][1].getX()-1,grid[1][1].getY()-1)
                    && !tanksGrid.isNotOccupied(grid[1][1].getX()+1, grid[1][1].getY()-1))
            {
                return grid;
            }
        }
        else if(figure.getAngle() == 270 )
        {
            if(!tanksGrid.isNotOccupied(grid[1][1].getX()+2, grid[1][1].getY())
                    && !tanksGrid.isNotOccupied(grid[1][1].getX()+1, grid[1][1].getY()-1)
                    && !tanksGrid.isNotOccupied(grid[1][1].getX()+1, grid[1][1].getY()+1))
            {
                return grid;
            }
        }



        return gridClone;

    }
    public void moveForward(TanksFigure figure)
    {
        Point direction = resolveMove(figure);
        TanksSingleGrid[][] ret = moveIfPossible(figure, direction);
        TanksSingleGrid[][] grid = figure.getGrid();
        for(int i=0; i< grid.length; i++)
        {
            for(int j=0;j<grid[i].length;j++)
            {
                     grid[i][j].setX(ret[i][j].getX());
                     grid[i][j].setY(ret[i][j].getY());
                     grid[i][j].setNewRectByXY();
            }
        }

    }

    public void turnTankLeft(TanksFigure figure)
    {
        TanksSingleGrid[][] matrix = figure.getGrid();
        int n = matrix.length;
        TanksSingleGrid[][] ret = new TanksSingleGrid[n][n];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                //ret[i][j] = matrix[n - j - 1] [i];
                ret[i][j] = new TanksSingleGrid( matrix[n - j - 1] [i].getOccupied(), matrix[i] [j].getX(),matrix[i] [j].getY());
            }
        }
        for(int i=0; i< matrix.length; i++)
        {
            for(int j=0;j<matrix[i].length;j++)
            {
                matrix[i][j] = new TanksSingleGrid(ret[i][j].getOccupied(),ret[i][j].getX(), ret[i][j].getY());
                matrix[i][j].setNewRectByXY();
            }
        }
    }
    public void turnTankRight(TanksFigure figure)
    {
        TanksSingleGrid[][] matrix = figure.getGrid();
        int n = matrix.length;
        TanksSingleGrid[][] ret = new TanksSingleGrid[n][n];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                //ret[i][j] = matrix[n - j - 1] [i];
                ret[i][j] = new TanksSingleGrid( matrix[i] [j].getOccupied(), matrix[i] [j].getX(),matrix[i] [j].getY());
            }
        }
        if(figure.getShape() == TanksShapes.YOU)
        {
            int angle = figure.getAngle();
            angle = angle - 90;
            if(angle<0)
            {
                angle = 270;
            }
            figure.setAngle(angle);
        }

        turnTankLeft(figure);
        turnTankLeft(figure);
        turnTankLeft(figure);
    }

    public TanksSingleGrid[][]moveBulletIfPossible(TanksFigure figure, Point direction)
    {
        //sprawdź czy nie ma kolizji z tankiem, jeśli to YOU to koniec gry
        TanksSingleGrid[][] grid = figure.getGrid();
        for(int i=0; i< grid.length; i++)
        {
            for(int j=0;j<grid[i].length;j++)
            {
                grid[i][j].setX(grid[i][j].getX()+direction.x);
                grid[i][j].setY(grid[i][j].getY() + direction.y);
                if(grid[i][j].getX() < 0 || grid[i][j].getY()<0 || grid[i][j].getX() >=tanksGrid.GRID_WIDTH || grid[i][j].getY() >= tanksGrid.GRID_HEIGHT)
                {
                    return null;
                }

            }
        }
        return grid;

    }
    public void moveBullet(TanksFigure figure)
    {
        Point direction = resolveMove(figure);
        TanksSingleGrid[][] grid = figure.getGrid();
        TanksSingleGrid[][] ret = moveBulletIfPossible(figure, direction) ;
        if(ret == null)
        {
            //tanksGrid.setNotOccupied(grid[0][0].getX(), grid[0][0].getY());
            tanksGrid.gameFigures.remove(figure);

        }
        else
        {

                grid[0][0].setX(ret[0][0].getX());
                grid[0][0].setY(ret[0][0].getY());
                for(int i =0; i<tanksGrid.gameFigures.size();i++)
                {
                    TanksSingleGrid[][] occ = tanksGrid.gameFigures.get(i).getGrid();
                    for(int j =0; j<occ.length; j++)
                    {
                        for(int h =0; h<occ[j].length; h++)
                        {
                            if(tanksGrid.gameFigures.get(i).getShape() == TanksShapes.ENEMY || (tanksGrid.gameFigures.get(i).getShape() == TanksShapes.BULLET &&tanksGrid.gameFigures.get(i) != figure ))
                            {
                            if(occ[j][h].getOccupied() && occ[j][h].getX() == grid[0][0].getX() && occ[j][h].getY() == grid[0][0].getY())
                            {
                                tanksGrid.gameFigures.remove(i);
                                tanksGrid.gameFigures.remove(figure);
                                int score = tanksGrid.getPointsScore();
                                score = score + tanksGrid.pointsForDestroyingTank;
                                tanksGrid.setPointsScore(score);
                                tanksGrid.playSoundKillEnemy();
                                //drawScore();
                                return;
                            }
                            }
                            else if(tanksGrid.gameFigures.get(i).getShape() == TanksShapes.YOU)
                            {
                                if(occ[j][h].getOccupied() && occ[j][h].getX() == grid[0][0].getX() && occ[j][h].getY() == grid[0][0].getY())
                                {
                                checkAndSaveBestScore();
                                endGame();
                                }

                            }
                        }
                    }
                }
                grid[0][0].setNewRectByXY();

        }
    }

    public void stopEndTimer() {
        if (endTimer != null) {
            endTimer.cancel();
        }
    }

    private Timer endTimer;
    public static int timerCounter = 0;
    private void endGame() {
        this.chujZFlaga = false;
        tanksGrid.playSoundDie();
        endTimer = new Timer();
        endTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                tanksGrid.animateCrash(figure);
                TanksThread.timerCounter++;
                if (TanksThread.timerCounter == 10) {
                    checkAndSaveBestScore();
                    handler.sendMessage(Message.obtain());
                    run = false;
                    this.cancel();
                }
            }
        }, 200, 200);
    }

    public Point resolveLocation(Point tankPos, int angle)
    {
        int x = 0;
        int y = 0;
        if(angle == 0)
        {
            x = 0;
            y = 2;
        }
        else if(angle == 90)
        {
            x = -2;
            y =0;
        }
        else if(angle == 180)
        {
            x =0;
            y= -2;
        }
        else if(angle == 270)
        {
            x=2;
            y=0;
        }

        return new Point(tankPos.x +x, tankPos.y + y);
    }
    public void shoot(TanksFigure figure)
    {
        tanksGrid.playSoundShoot();
        TanksSingleGrid[][] grid = figure.getGrid();
        Point tankPos = new Point(grid[1][1].getX(),grid[1][1].getY());
        Point location = resolveLocation(tankPos, figure.getAngle());
        if(location.x < 0 || location.y<0 || location.x >=tanksGrid.GRID_WIDTH || location.y>= tanksGrid.GRID_HEIGHT)
        {
            return;
        }
        TanksFigure newBullet = new TanksFigure(TanksShapes.BULLET, figure.getAngle());
//        TanksSingleGrid[][] newGrid = TanksSingleGrid.cloneArrayDim2(grid);
        TanksSingleGrid[][] newGrid = new TanksSingleGrid[1][1];
        newGrid[0][0] = new TanksSingleGrid(true, location.x, location.y);
        newBullet.setGrid(newGrid);
        tanksGrid.addFigure(newBullet);
    }
}
