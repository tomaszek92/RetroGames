package com.retrogames.app.race;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

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

    private int nextCar;
    public void setNextCar(int nextCar) {
        this.nextCar = nextCar;
    }
    public int getNextCar() {
        return this.nextCar;
    }

    public RaceGrid(int canvasHeight, int canvasWidth) {
        CANVAS_HEIGHT = canvasHeight;
        CANVAS_WIDTH = canvasWidth;
        this.grids = new RaceSingleGrid[GRID_WIDTH][GRID_HEIGHT];
        this.nextCar = 9;
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
                grids[grid[i][j].getX()][grid[i][j].getY()] = grid[i][j];
            }
        }
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

    public boolean isNotOccupied(int x, int y) {
        if (x >= GRID_WIDTH || y >= GRID_HEIGHT) {
            return true;
        }
        if (this.grids[x][y] == null) {
            return true;
        }
        return !this.grids[x][y].getOccupied();
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
                    grids[gridCar[k][j].getX()][gridCar[k][j].getY()] = gridCar[k][j];
                }
            }
        }
    }

    public void move(RacePosition newPosition, RaceCar car) {
        if (!carCrashed(newPosition, car)) {
            car.setPosition(newPosition);
            refreshGrid();
        }
        else {
            //TODO koniec gry
            Log.i("END GAME", "END GAME");
        }
    }

    private boolean carCrashed(RacePosition newRacePosition, RaceCar car) {
        RaceSingleGrid[][] grid1 = car.getGrid();
        for (int i = 0; i < 4; i++) {
            if (!isNotOccupied(newRacePosition.toInt() * 3 + 1 , grid1[i][1].getY())) {
                return true;
            }
        }
        return false;
    }

    public void goOneDown(RaceCar car) {
        // przez wszystkie samochody
        for (int i = 0; i < cars.size(); i++) {
            // tylko nie przez sterowany przez gracz
            if (cars.get(i) != car) {
                RaceSingleGrid[][] gridCar = cars.get(i).getGrid();

                // przechodzimy przez całą siatkę
                boolean deletedRow = false;
                for (int k = 0; k < gridCar.length && !deletedRow; k++) {
                    for (int j = 0; j < gridCar[k].length; j++) {
                        // ustawiamy nowe Y
                        gridCar[k][j].setY(gridCar[k][j].getY() + 1);
                        gridCar[k][j].setNewRectByXY();

                        // jeśli siatka samochodu wychodzi poza ekran gry
                        if (gridCar[k][j].getY() == GRID_HEIGHT) {
                            if (gridCar.length == 1) {
                                removeCar(cars.get(i));
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
    }

    public void addEnemyCar() {
        RaceCar newCar = new RaceCar(RacePosition.randomPosition(), 0);
        cars.add(newCar);
    }
}
