package com.retrogames.app.race;

import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.Log;

import com.retrogames.app.ChooseGameSettingsFragment;
import com.retrogames.app.R;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Tomasz on 06.02.14.
 */
public class RaceGrid {

    RaceSingleGrid[][] grids;
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

    public static int LEVEL = 1;
    public static int LEVEL_MAX = 10;
    public static int DOWN_SPEED_CHANGE = 40;
    public static int DOWN_SPEED = 500;

    private int movesToNextCar;
    public void setMovesToNextCar(int movesToNextCar) {
        this.movesToNextCar = movesToNextCar;
    }
    public int getMovesToNextCar() {
        return this.movesToNextCar;
    }

    private MediaPlayer mp_race_explosion;

    public RaceGrid(RaceSurfaceView view, int canvasHeight, int canvasWidth) {
        CANVAS_HEIGHT = canvasHeight;
        CANVAS_WIDTH = canvasWidth;
        this.grids = new RaceSingleGrid[GRID_WIDTH][GRID_HEIGHT];
        this.movesToNextCar = 9;

        this.mp_race_explosion = MediaPlayer.create(view.getContext(), R.raw.race_explosion);
    }

    public void playSoundExsplosion() {
        if (ChooseGameSettingsFragment.SOUND) {
            mp_race_explosion.start();
        }
    }

    public void draw(Canvas canvas) {
        for (int i = 0; i < cars.size(); i++) {
            cars.get(i).drawCar(canvas);
        }
    }

    public void addCar(RaceCar car) {
        cars.add(car);
        RaceSingleGrid[][] grid = car.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].getY() >= 0
                        && grid[i][j].getY() < GRID_HEIGHT
                        && grid[i][j].getX() >= 0
                        && grid[i][j].getX() < GRID_WIDTH ) {
                    grids[grid[i][j].getX()][grid[i][j].getY()] = grid[i][j];
                }
            }
        }
        refreshGrid();
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

    private void refreshGrid() {
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                grids[i][j] = null;
            }
        }

        for (int i = 0; i < cars.size(); i++) {
            RaceSingleGrid[][] gridCar = cars.get(i).getGrid();
            for (int k = 0; k < gridCar.length; k++) {
                for (int j = 0; j < gridCar[k].length; j++) {
                    if (gridCar[k][j].getY() >= 0
                            && gridCar[k][j].getY() < GRID_HEIGHT
                            && gridCar[k][j].getX() >= 0
                            && gridCar[k][j].getX() < GRID_WIDTH ) {
                    grids[gridCar[k][j].getX()][gridCar[k][j].getY()] = gridCar[k][j];
                    }
                }
            }
        }
    }

    // zwaraca true jeśli jest zderzenie, false jeśi nie ma zderzenia
    public boolean move(RacePosition newPosition, RaceCar car) {
        if (newPosition == car.getPosition()) {
            return false;
        }
        RaceSingleGrid[][] carGrid = car.getGrid();
        for (int i = 0; i < carGrid.length; i++) {
            for (int j = 0; j < carGrid[i].length; j++) {
                carGrid[i][j].setX(newPosition.toInt() * 3 + j);
            }
        }

        if (!carCrashed(newPosition, car)) {
            car.setPosition(newPosition);
            refreshGrid();
            return false;
        }
        else {
            car.setPosition(newPosition);
            refreshGrid();
            Log.i("Wypadek", "move");
            return true;
        }
    }

    private boolean carCrashed(RacePosition newRacePosition, RaceCar car) {
        RaceSingleGrid[][] grid1 = car.getGrid();
        for (int i = 0; i < 4; i++) {
            if (isOccupied(newRacePosition.toInt() * 3 + 1 , grid1[i][1].getY())) {
                return true;
            }
        }
        return false;
    }

    private boolean isOccupied(int x, int y) {
        if (x < 0 || x >= GRID_WIDTH || y < 0 || y >= GRID_HEIGHT) {
            return false;
        }
        if (grids[x][y] == null) {
            return false;
        }
        return grids[x][y].getOccupied();
    }

    // zwaraca true jeśli jest zderzenie, false jeśi nie ma zderzenia
    public boolean goOneDown(RaceCar car) {
        // przez wszystkie samochody
        for (int i = 0; i < cars.size(); i++) {
            // tylko nie przez sterowany przez gracz
            if (cars.get(i) != car) {
                RaceSingleGrid[][] gridCar = cars.get(i).getGrid();

                // sprawdzanie ostatniego rzędu, czy nie ma wypadku
                if (isOccupied(gridCar[gridCar.length - 1][1].getX(), gridCar[gridCar.length - 1][1].getY() + 1)) {
                    Log.i("WYPADEK", "goOneDown");
                    return true;
                }

                // przechodzimy przez całą siatkę
                boolean deletedRow = false;
                for (int k = 0; k < gridCar.length && !deletedRow; k++) {
                    for (int j = 0; j < gridCar[k].length; j++) {
                        // ustawiamy nowe Y
                        gridCar[k][j].setY(gridCar[k][j].getY() + 1);
                        gridCar[k][j].setNewRectByXY();

                        // jeśli siatka samochodu wychodzi poza ekran gry z dołu
                        if (gridCar[k][j].getY() == GRID_HEIGHT) {
                            if (gridCar.length == 1) {
                                removeCar(cars.get(i));
                                pointsScore += pointsToAdd;
                                deletedRow = true;
                                break;
                            }
                            else {
                                RaceSingleGrid[][] newGrid = RaceSingleGrid.deleteLastRow(gridCar);
                                cars.get(i).setGrid(newGrid);
                                deletedRow = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        refreshGrid();
        return false;
    }

    public void addEnemyCar() {
        RaceCar newCar = new RaceCar(RacePosition.randomPosition(), -3);
        addCar(newCar);
    }

    public void animateCrash(RaceCar car) {
        RaceSingleGrid[][] carGrid = car.getGrid();
        Random rand = new Random();
        for (int i = 0; i < carGrid.length; i++) {
            for (int j = 0; j < carGrid[i].length; j++) {
                carGrid[i][j].setOccupied(rand.nextBoolean());
            }
        }
    }
}
