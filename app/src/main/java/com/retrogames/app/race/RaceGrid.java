package com.retrogames.app.race;

import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tomasz on 06.02.14.
 */
public class RaceGrid {

    List<RaceCar> cars = new LinkedList<RaceCar>();

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
    public static final int BACKGROUND_COLOR = Color.DKGRAY;

    // zmienne do wyniku
    private static int pointsScore = 0;
    private static final int pointsToAdd = 100;

    // wymiar siatki w klockach
    public static int GRID_WIDTH = 9;
    public static int GRID_HEIGHT = 14;

    // wymiar całego canvasu
    public static int CANVAS_HEIGHT;
    public static int CANVAS_WIDTH;

    public RaceGrid(int canvasHeight, int canvasWidth) {
        CANVAS_HEIGHT = canvasHeight;
        CANVAS_WIDTH = canvasWidth;
    }

    public void draw(Canvas canvas) {
        for (int i = 0; i < cars.size(); i++) {
            cars.get(i).drawCar(canvas);
        }
    }

    public void addCar(RaceCar car) {
        cars.add(car);
    }

    private void removeCar(RaceCar car) {
        cars.remove(car);
    }

    public static int getPointsScore() {
        return pointsScore;
    }

    public static void setPointsScore(int score) {
        pointsScore = score;
    }
}
