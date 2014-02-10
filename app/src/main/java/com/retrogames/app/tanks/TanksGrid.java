package com.retrogames.app.tanks;

import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;

import com.retrogames.app.R;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Piotr on 18.01.14.
 */
public class TanksGrid {


    public TanksSingleGrid[][] gameGrid;
    public List<TanksFigure> gameFigures;
    // marginesy do rysowania wyniku
    public static int MARGIN_SCORE_TOP = 50;
    public static int MARGIN_SCORE_LEFT = 50;
    public static int SCORE_COLOR = Color.WHITE;

    // marginesy do rysowania siatki gry
    public static int MARGIN_RIGHT;
    public static int MARGIN_TOP;
    public static int MARGIN_BOTTOM = 10;
    public static int MARGIN_LEFT = 10;

    // zmienne do rysowania
    public static final int STROKE_WIDTH = 5;
    public static final int LINE_COLOR = Color.WHITE;
    public static final int BACKGROUND_COLOR = Color.rgb(32,32,32);
    // zmienne do wyniku
    private static int pointsScore = 0;
    public static final int pointsForDestroyingTank = 100;

    // wymiar siatki w klockach
    public static int GRID_WIDTH = 20;
    public static int GRID_HEIGHT = 30;

    // wymiar całego canvasu
    public static int CANVAS_HEIGHT;
    public static int CANVAS_WIDTH;

    private MediaPlayer mp_destoying_tank;
    private MediaPlayer mp_destroying_yourself;
    private MediaPlayer mp_shoot;


    public TanksGrid(TanksSurfaceView view, int height, int width) {
        CANVAS_HEIGHT = height;
        CANVAS_WIDTH = width;

        gameGrid = new TanksSingleGrid[GRID_WIDTH][GRID_HEIGHT];
        gameFigures = new LinkedList<TanksFigure>();

        this.mp_destoying_tank = MediaPlayer.create(view.getContext(),R.raw.race_explosion);
        this.mp_destroying_yourself = MediaPlayer.create(view.getContext(),R.raw.tanks_self_explosion);
        this.mp_shoot = MediaPlayer.create(view.getContext(),R.raw.tanks_shoot);
    }


    public static int getPointsScore() {
        return pointsScore;
    }
    public static void setPointsScore(int score) {
        pointsScore = score;
    }

    public void playSoundKillEnemy()
    {
        mp_destoying_tank.start();
    }
    public void playSoundDie()
    {
        mp_destroying_yourself.start();
    }
    public void playSoundShoot()
    {
        mp_shoot.start();
    }
    public void addFigure(TanksFigure newFigure) {
        if(CheckIfAdd(newFigure))
        {
        gameFigures.add(newFigure);
        addFigureToGameGrid(newFigure);
        }
    }
    public boolean CheckIfAdd(TanksFigure figure)
    {
        TanksSingleGrid[][] tetrisSingleGrids = figure.getGrid();
        for(int i = 0; i<gameFigures.size();i++)
        {
            TanksFigure oldFigure = gameFigures.get(i);
            TanksSingleGrid[][] oldFigureGrid = oldFigure.getGrid();
            for(int a = 0; a<tetrisSingleGrids.length;a++)
            {
                for(int b = 0; b<tetrisSingleGrids[a].length;b++)
                {
                    for( int x = 0; x<oldFigureGrid.length; x++)
                    {
                        for( int y = 0; y<oldFigureGrid[x].length; y++)
                        {
                            if(tetrisSingleGrids[a][b].getX() == oldFigureGrid[x][y].getX() && tetrisSingleGrids[a][b].getY() == oldFigureGrid[x][y].getY() && tetrisSingleGrids[a][b].getOccupied() && oldFigureGrid[x][y].getOccupied() )
                            {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    private void addFigureToGameGrid(TanksFigure figure) {
        TanksSingleGrid[][] tetrisSingleGrids = figure.getGrid();
      //  gdybyśmy chcieli wstawić nową figurę na innej, wtedy przerywamy operację

        for (int i = 0; i < tetrisSingleGrids.length; i++) {
            for (int j = 0; j < tetrisSingleGrids[i].length; j++) {
                if (tetrisSingleGrids[i][j].getOccupied()) {
                    gameGrid[tetrisSingleGrids[i][j].getX()][tetrisSingleGrids[i][j].getY()] = tetrisSingleGrids[i][j];
                }
            }
        }

    }
    // rysowanie wszystkich figur
    public void drawAllFigures(Canvas canvas) {
        for (int i = 0; i <  gameFigures.size(); i++) {
            gameFigures.get(i).drawFigure(canvas);
        }
    }
    private static int xCoordinateToGrid(float x) {
        if (x < MARGIN_LEFT + STROKE_WIDTH) {
            x = MARGIN_LEFT + STROKE_WIDTH;
        }
        else if (x > MARGIN_RIGHT) {
            x = MARGIN_RIGHT;
        }

        int positionInGrid = (int)((x - MARGIN_LEFT - STROKE_WIDTH) / TanksSingleGrid.SIZE);

        return positionInGrid;
    }

    // zamiena współrzędnej y w pikselach na odpowiednią współrzędną w siatce
    private static int yCoordinateToGrid(float y) {
        if (y < MARGIN_TOP + STROKE_WIDTH) {
            y = MARGIN_TOP + STROKE_WIDTH;
        }
        else if (y >  CANVAS_HEIGHT - MARGIN_BOTTOM - STROKE_WIDTH) {
            y = CANVAS_HEIGHT - MARGIN_BOTTOM - TanksSingleGrid.SIZE;
        }

        int positionInGrid = (int)((y - MARGIN_TOP - STROKE_WIDTH) / TanksSingleGrid.SIZE);

        return positionInGrid;
    }

    public void setNotOccupied(int x, int y)
    {
        gameGrid[x][y] = null;
    }
    public boolean isNotOccupied(int x, int y) {
        if (x >= GRID_WIDTH || y >= GRID_HEIGHT) {
            return true;
        }
        if (this.gameGrid[x][y] == null) {
            return true;
        }
        return !this.gameGrid[x][y].getOccupied();
    }
    public void moveTank(TanksFigure figure, float x, float y) {
        if (x < MARGIN_LEFT + STROKE_WIDTH) {
            x = MARGIN_LEFT + STROKE_WIDTH;
        }
        else if (x > MARGIN_RIGHT) {
            x = MARGIN_RIGHT;
        }

        if (y < MARGIN_TOP + STROKE_WIDTH) {
            y = MARGIN_TOP + STROKE_WIDTH;
        }
        else if (y >  CANVAS_HEIGHT - MARGIN_BOTTOM - STROKE_WIDTH) {
            y = CANVAS_HEIGHT - MARGIN_BOTTOM - TanksSingleGrid.SIZE;
        }

        TanksSingleGrid[][] grids = figure.getGrid();
        TanksSingleGrid[][] gridsClone = TanksSingleGrid.cloneArrayDim2(grids);

        int exitL = 0;
        // sprawdzanie czy jest puste pole
        for (int i = 0; i < gridsClone.length; i++) {
            for (int j = 0; j < gridsClone[i].length; j++) {
                int xNew = xCoordinateToGrid(x + TanksSingleGrid.SIZE * j);
                int yNew = yCoordinateToGrid(y + TanksSingleGrid.SIZE * i);
                if (xNew >= GRID_WIDTH || yNew < 0 || yNew >= GRID_HEIGHT) {
                    return;
                }
                if (xNew < 0 && exitL > xNew) {
                    exitL = xNew;
                }
                if (this.isNotOccupied(xNew, yNew)) {
                    gridsClone[i][j].setX(xNew);
                    gridsClone[i][j].setY(yNew);
                }
                else {
                    return;
                }
            }
        }

        if (exitL < 0) {
            for (int i = 0; i < gridsClone.length; i++) {
                for (int j = 0; j < gridsClone[i].length; j++) {
                    gridsClone[i][j].setX(gridsClone[i][j].getX() - exitL);
                }
            }
        }

        // sprawdzanie, czy żadne klocki na siebie nie naszły
        // poza dolną krawędź ekranu
        int count = 0;
        for (int i = 0; i < gridsClone.length; i++) {
            for (int j = 0; j < gridsClone[i].length; j++) {
                if (gridsClone[i][j].getY() == GRID_HEIGHT - 1) {
                    count++;
                }
            }
        }
        if(count>=2)
        {
            return;
        }
        // przesuwanie klocka na nową pozycję
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                grids[i][j].setX(gridsClone[i][j].getX());
                grids[i][j].setY(gridsClone[i][j].getY());
                grids[i][j].setNewRectByXY();
            }
        }
        this.refreshGrid();
    }
    public void refreshGrid() {
        for (int i = 0; i < gameGrid.length; i++) {
            for (int j = 0; j < gameGrid[i].length; j++) {
                gameGrid[i][j] = null;
            }
        }
        for (TanksFigure figure : this.gameFigures) {
            addFigureToGameGrid(figure);
        }
    }
    public void animateCrash(TanksFigure tank) {
        TanksSingleGrid[][] carGrid = tank.getGrid();
        Random rand = new Random();
        for (int i = 0; i < carGrid.length; i++) {
            for (int j = 0; j < carGrid[i].length; j++) {
                carGrid[i][j].setOccupied(rand.nextBoolean());
            }
        }
    }

}
