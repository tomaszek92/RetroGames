package com.retrogames.app.tanks;

import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;

import com.retrogames.app.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Piotr on 18.01.14.
 */
public class TanksGrid {


    private TanksSingleGrid[][] gameGrid;
    private List<TanksFigure> gameFigures;
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
    private static final int pointsForDestroyingTank = 100;

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
        gameFigures.add(newFigure);
        playSoundDie(); // del
        //moveFigureToStartPosition(newFigure);
        addFigureToGameGrid(newFigure);
    }
    private void addFigureToGameGrid(TanksFigure figure) {
        TanksSingleGrid[][] tetrisSingleGrids = figure.getGrid();
        playSoundShoot();//del
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


}
