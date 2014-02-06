package com.retrogames.app.race;

import android.graphics.Canvas;

/**
 * Created by Tomasz on 06.02.14.
 */
public class RaceCar {

    //   X
    // X X X
    //   X
    // X X X
    private RaceSingleGrid[][] grid;

    // 0 - left
    // 1 - center
    // 2 - right
    private int position = 1;
    public int getPosition() {
        return this.position;
    }
    public void setPosition(int position) {
        this.position = position;
        setXs(position);
        setRects();
    }

    public RaceCar() {
        createGrid();
        setOccupieds();
        setXs(1);
        setYs();
        setRects();
    }

    private void setRects() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j].setNewRectByXY();
            }
        }
    }

    private void setYs() {
        grid[0][0].setY(10);
        grid[0][1].setY(10);
        grid[0][2].setY(10);
        grid[1][0].setY(11);
        grid[1][1].setY(11);
        grid[1][2].setY(11);
        grid[2][0].setY(12);
        grid[2][1].setY(12);
        grid[2][2].setY(12);
        grid[3][0].setY(13);
        grid[3][1].setY(13);
        grid[3][2].setY(13);
    }

    private void setXs(int k) {
        grid[0][0].setX(k * 3 + 0);
        grid[0][1].setX(k * 3 + 1);
        grid[0][2].setX(k * 3 + 2);
        grid[1][0].setX(k * 3 + 0);
        grid[1][1].setX(k * 3 + 1);
        grid[1][2].setX(k * 3 + 2);
        grid[2][0].setX(k * 3 + 0);
        grid[2][1].setX(k * 3 + 1);
        grid[2][2].setX(k * 3 + 2);
        grid[3][0].setX(k * 3 + 0);
        grid[3][1].setX(k * 3 + 1);
        grid[3][2].setX(k * 3 + 2);
    }

    private void setOccupieds() {
        grid[0][0].setOccupied(false);
        grid[0][1].setOccupied(true);
        grid[0][2].setOccupied(false);
        grid[1][0].setOccupied(true);
        grid[1][1].setOccupied(true);
        grid[1][2].setOccupied(true);
        grid[2][0].setOccupied(false);
        grid[2][1].setOccupied(true);
        grid[2][2].setOccupied(false);
        grid[3][0].setOccupied(true);
        grid[3][1].setOccupied(true);
        grid[3][2].setOccupied(true);
    }

    private void createGrid() {
        grid = new RaceSingleGrid[4][3];
        grid[0][0] = new RaceSingleGrid();
        grid[0][1] = new RaceSingleGrid();
        grid[0][2] = new RaceSingleGrid();
        grid[1][0] = new RaceSingleGrid();
        grid[1][1] = new RaceSingleGrid();
        grid[1][2] = new RaceSingleGrid();
        grid[2][0] = new RaceSingleGrid();
        grid[2][1] = new RaceSingleGrid();
        grid[2][2] = new RaceSingleGrid();
        grid[3][0] = new RaceSingleGrid();
        grid[3][1] = new RaceSingleGrid();
        grid[3][2] = new RaceSingleGrid();
    }

    public void drawCar(Canvas canvas) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (!grid[i][j].isNotOccupied()) {
                    grid[i][j].drawSingleGrid(canvas);
                }
            }
        }
    }
}
