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
    public RaceSingleGrid[][] getGrid() {
        return this.grid;
    }
    public void setGrid(RaceSingleGrid[][] grid) {
        this.grid = grid;
    }

    private RacePosition position = RacePosition.CENTER;
    public RacePosition getPosition() {
        return this.position;
    }
    public void setPosition(RacePosition position) {
        this.position = position;
        setXs(position.toInt());
        setRects();
    }

    public RaceCar() {
        createGrid();
        setOccupieds();
        setXs(RacePosition.CENTER.toInt());
        setYs(10);
        setRects();
    }

    public RaceCar(RacePosition position, int upY) {
        createGrid();
        setOccupieds();
        setXs(position.toInt());
        setYs(upY);
        setRects();
    }

    private void setRects() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j].setNewRectByXY();
            }
        }
    }

    private void setYs(int pos) {
        grid[0][0].setY(pos);
        grid[0][1].setY(pos);
        grid[0][2].setY(pos);
        grid[1][0].setY(pos + 1);
        grid[1][1].setY(pos + 1);
        grid[1][2].setY(pos + 1);
        grid[2][0].setY(pos + 2);
        grid[2][1].setY(pos + 2);
        grid[2][2].setY(pos + 2);
        grid[3][0].setY(pos + 3);
        grid[3][1].setY(pos + 3);
        grid[3][2].setY(pos + 3);
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
            try {
                for (int j = 0; j < grid[i].length; j++) {
                    if (!grid[i][j].isNotOccupied()) {
                        if (grid[i][j].getY() >= 0
                                && grid[i][j].getY() < RaceGrid.GRID_HEIGHT
                                && grid[i][j].getX() >= 0
                                && grid[i][j].getX() < RaceGrid.GRID_WIDTH ) {
                            grid[i][j].drawSingleGrid(canvas);
                        }
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
