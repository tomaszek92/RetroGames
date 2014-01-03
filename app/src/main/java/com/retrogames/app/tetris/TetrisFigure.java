package com.retrogames.app.tetris;

import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by Tomasz on 31.12.13.
 */
public class TetrisFigure {

    private int angle = 0;
    private TetrisColors color;
    private TetrisShapes shape;
    private TetrisSingleGrid[][] grid;

    public TetrisFigure() {
        this.color = TetrisColors.randomColor();
        this.shape = TetrisShapes.randomShape();
        Random random = new Random();
        this.angle = random.nextInt(4) * 90;
        this.makeGrid();
    }

    public TetrisFigure(TetrisColors color, TetrisShapes shape, int angle) {
        this.color = color;
        this.shape = shape;
        this.angle = angle;
        this.makeGrid();
    }

    // tworzenie siatki figury, w zależności od typu figury i kąta obrotu
    private void makeGrid() {
        if (shape == TetrisShapes.BOX_1X1) {
            grid = new TetrisSingleGrid[1][1];
            grid[0][0] = new TetrisSingleGrid(true, color, 4, 0);
        }
        else if (shape == TetrisShapes.BOX_2X1) {
            if (this.angle == 0 || this.angle == 180) {
                grid = new TetrisSingleGrid[2][1];
                grid[0][0] = new TetrisSingleGrid(true, color, 4, 0);
                grid[1][0] = new TetrisSingleGrid(true, color, 4, 1);
            }
            // this.angle == 90 || this.angle == 180
            else {
                grid = new TetrisSingleGrid[1][2];
                grid[0][0] = new TetrisSingleGrid(true, color, 4, 0);
                grid[0][1] = new TetrisSingleGrid(true, color, 5, 0);
            }
        }

        else if (shape == TetrisShapes.BOX_2X2) {
            grid = new TetrisSingleGrid[2][2];
            grid[0][0] = new TetrisSingleGrid(true, color, 4, 0);
            grid[1][0] = new TetrisSingleGrid(true, color, 4, 1);
            grid[0][1] = new TetrisSingleGrid(true, color, 5, 0);
            grid[1][1] = new TetrisSingleGrid(true, color, 5, 1);
        }

        else if (shape == TetrisShapes.BOX_3X1) {
            if (this.angle == 0 || this.angle == 180) {
                grid = new TetrisSingleGrid[3][1];
                grid[0][0] = new TetrisSingleGrid(true, color, 4, 0);
                grid[1][0] = new TetrisSingleGrid(true, color, 4, 1);
                grid[2][0] = new TetrisSingleGrid(true, color, 4, 2);
            }
            else {
                grid = new TetrisSingleGrid[1][3];
                grid[0][0] = new TetrisSingleGrid(true, color, 3, 0);
                grid[0][1] = new TetrisSingleGrid(true, color, 4, 0);
                grid[0][2] = new TetrisSingleGrid(true, color, 5, 0);
            }

        }

        else if (shape == TetrisShapes.BOX_4X1) {
            if (this.angle == 0 || this.angle == 180) {
                grid = new TetrisSingleGrid[4][1];
                grid[0][0] = new TetrisSingleGrid(true, color, 4, 0);
                grid[1][0] = new TetrisSingleGrid(true, color, 4, 1);
                grid[2][0] = new TetrisSingleGrid(true, color, 4, 2);
                grid[3][0] = new TetrisSingleGrid(true, color, 4, 3);
            }
            else {
                grid = new TetrisSingleGrid[1][4];
                grid[0][0] = new TetrisSingleGrid(true, color, 3, 0);
                grid[0][1] = new TetrisSingleGrid(true, color, 4, 0);
                grid[0][2] = new TetrisSingleGrid(true, color, 5, 0);
                grid[0][3] = new TetrisSingleGrid(true, color, 6, 0);
            }
        }

        else if (shape == TetrisShapes.CLIPPER) {
            if (this.angle == 0 || this.angle == 180) {
                grid = new TetrisSingleGrid[3][2];
                grid[0][0] = new TetrisSingleGrid(true, color, 4, 0);
                grid[0][1] = new TetrisSingleGrid(false, color, 5, 0);
                grid[1][0] = new TetrisSingleGrid(true, color, 4, 1);
                grid[1][1] = new TetrisSingleGrid(true, color, 5, 1);
                grid[2][0] = new TetrisSingleGrid(false, color, 4, 2);
                grid[2][1] = new TetrisSingleGrid(true, color, 5, 2);
            }
            else {
                grid = new TetrisSingleGrid[2][3];
                grid[0][0] = new TetrisSingleGrid(false, color, 3, 0);
                grid[0][1] = new TetrisSingleGrid(true, color, 4, 0);
                grid[0][2] = new TetrisSingleGrid(true, color, 5, 0);
                grid[1][0] = new TetrisSingleGrid(true, color, 3, 1);
                grid[1][1] = new TetrisSingleGrid(true, color, 4, 1);
                grid[1][2] = new TetrisSingleGrid(false, color, 5, 1);
            }
        }

        else if (shape == TetrisShapes.CLIPPER_R) {
            if (this.angle == 0 || this.angle == 180) {
                grid = new TetrisSingleGrid[3][2];
                grid[0][0] = new TetrisSingleGrid(false, color, 4, 0);
                grid[0][1] = new TetrisSingleGrid(true, color, 5, 0);
                grid[1][0] = new TetrisSingleGrid(true, color, 4, 1);
                grid[1][1] = new TetrisSingleGrid(true, color, 5, 1);
                grid[2][0] = new TetrisSingleGrid(true, color, 4, 2);
                grid[2][1] = new TetrisSingleGrid(false, color, 5, 2);
            }
            else {
                grid = new TetrisSingleGrid[2][3];
                grid[0][0] = new TetrisSingleGrid(true, color, 3, 0);
                grid[0][1] = new TetrisSingleGrid(true, color, 4, 0);
                grid[0][2] = new TetrisSingleGrid(false, color, 5, 0);
                grid[1][0] = new TetrisSingleGrid(false, color, 3, 1);
                grid[1][1] = new TetrisSingleGrid(true, color, 4, 1);
                grid[1][2] = new TetrisSingleGrid(true, color, 5, 1);
            }
        }

        else if (shape == TetrisShapes.LETTER_L_BIG) {
            if (this.angle == 0) {
                grid = new TetrisSingleGrid[3][2];
                grid[0][0] = new TetrisSingleGrid(true, color, 4, 0);
                grid[0][1] = new TetrisSingleGrid(false, color, 5, 0);
                grid[1][0] = new TetrisSingleGrid(true, color, 4, 1);
                grid[1][1] = new TetrisSingleGrid(false, color, 5, 1);
                grid[2][0] = new TetrisSingleGrid(true, color, 4, 2);
                grid[2][1] = new TetrisSingleGrid(true, color, 5, 2);
            }
            else if (this.angle == 90) {
                grid = new TetrisSingleGrid[2][3];
                grid[0][0] = new TetrisSingleGrid(false, color, 3, 0);
                grid[0][1] = new TetrisSingleGrid(false, color, 4, 0);
                grid[0][2] = new TetrisSingleGrid(true, color, 5, 0);
                grid[1][0] = new TetrisSingleGrid(true, color, 3, 1);
                grid[1][1] = new TetrisSingleGrid(true, color, 4, 1);
                grid[1][2] = new TetrisSingleGrid(true, color, 5, 1);
            }
            else if (this.angle == 180) {
                grid = new TetrisSingleGrid[3][2];
                grid[0][0] = new TetrisSingleGrid(true, color, 4, 0);
                grid[0][1] = new TetrisSingleGrid(true, color, 5, 0);
                grid[1][0] = new TetrisSingleGrid(false, color, 4, 1);
                grid[1][1] = new TetrisSingleGrid(true, color, 5, 1);
                grid[2][0] = new TetrisSingleGrid(false, color, 4, 2);
                grid[2][1] = new TetrisSingleGrid(true, color, 5, 2);
            }
            else {
                grid = new TetrisSingleGrid[2][3];
                grid[0][0] = new TetrisSingleGrid(true, color, 3, 0);
                grid[0][1] = new TetrisSingleGrid(true, color, 4, 0);
                grid[0][2] = new TetrisSingleGrid(true, color, 5, 0);
                grid[1][0] = new TetrisSingleGrid(true, color, 3, 1);
                grid[1][1] = new TetrisSingleGrid(false, color, 4, 1);
                grid[1][2] = new TetrisSingleGrid(false, color, 5, 1);
            }
        }

        else if (shape == TetrisShapes.LETTER_L_BIG_R) {
            if (this.angle == 0) {
                grid = new TetrisSingleGrid[3][2];
                grid[0][0] = new TetrisSingleGrid(false, color, 4, 0);
                grid[0][1] = new TetrisSingleGrid(true, color, 5, 0);
                grid[1][0] = new TetrisSingleGrid(false, color, 4, 1);
                grid[1][1] = new TetrisSingleGrid(true, color, 5, 1);
                grid[2][0] = new TetrisSingleGrid(true, color, 4, 2);
                grid[2][1] = new TetrisSingleGrid(true, color, 5, 2);
            }
            else if (this.angle == 90) {
                grid = new TetrisSingleGrid[2][3];
                grid[0][0] = new TetrisSingleGrid(true, color, 3, 0);
                grid[0][1] = new TetrisSingleGrid(true, color, 4, 0);
                grid[0][2] = new TetrisSingleGrid(true, color, 5, 0);
                grid[1][0] = new TetrisSingleGrid(false, color, 3, 1);
                grid[1][1] = new TetrisSingleGrid(false, color, 4, 1);
                grid[1][2] = new TetrisSingleGrid(true, color, 5, 1);
            }
            else if (this.angle == 180) {
                grid = new TetrisSingleGrid[3][2];
                grid[0][0] = new TetrisSingleGrid(true, color, 4, 0);
                grid[0][1] = new TetrisSingleGrid(true, color, 5, 0);
                grid[1][0] = new TetrisSingleGrid(true, color, 4, 1);
                grid[1][1] = new TetrisSingleGrid(false, color, 5, 1);
                grid[2][0] = new TetrisSingleGrid(true, color, 4, 2);
                grid[2][1] = new TetrisSingleGrid(false, color, 5, 2);
            }
            else {
                grid = new TetrisSingleGrid[2][3];
                grid[0][0] = new TetrisSingleGrid(true, color, 3, 0);
                grid[0][1] = new TetrisSingleGrid(false, color, 4, 0);
                grid[0][2] = new TetrisSingleGrid(false, color, 5, 0);
                grid[1][0] = new TetrisSingleGrid(true, color, 3, 1);
                grid[1][1] = new TetrisSingleGrid(true, color, 4, 1);
                grid[1][2] = new TetrisSingleGrid(true, color, 5, 1);
            }
        }

        else if (shape == TetrisShapes.LETTER_L_SMALL) {
            if (this.angle == 0) {
                grid = new TetrisSingleGrid[2][2];
                grid[0][0] = new TetrisSingleGrid(true, color, 4, 0);
                grid[0][1] = new TetrisSingleGrid(false, color, 5, 0);
                grid[1][0] = new TetrisSingleGrid(true, color, 4, 1);
                grid[1][1] = new TetrisSingleGrid(true, color, 5, 1);
            }
            else if (this.angle == 90) {
                grid = new TetrisSingleGrid[2][2];
                grid[0][0] = new TetrisSingleGrid(false, color, 4, 0);
                grid[0][1] = new TetrisSingleGrid(true, color, 5, 0);
                grid[1][0] = new TetrisSingleGrid(true, color, 4, 1);
                grid[1][1] = new TetrisSingleGrid(true, color, 5, 1);
            }
            else if (this.angle == 180) {
                grid = new TetrisSingleGrid[2][2];
                grid[0][0] = new TetrisSingleGrid(true, color, 4, 0);
                grid[0][1] = new TetrisSingleGrid(true, color, 5, 0);
                grid[1][0] = new TetrisSingleGrid(false, color, 4, 1);
                grid[1][1] = new TetrisSingleGrid(true, color, 5, 1);
            }
            else {
                grid = new TetrisSingleGrid[2][2];
                grid[0][0] = new TetrisSingleGrid(true, color, 4, 0);
                grid[0][1] = new TetrisSingleGrid(false, color, 5, 0);
                grid[1][0] = new TetrisSingleGrid(true, color, 4, 1);
                grid[1][1] = new TetrisSingleGrid(false, color, 5, 1);
            }
        }

        else {
            this.grid = null;
        }
    }

    public TetrisColors getColor() {
        return this.color;
    }
    public void setColor(TetrisColors color) {
        this.color = color;
    }

    public TetrisShapes getShape() {
        return this.shape;
    }
    public void setShape(TetrisShapes shape) {
        this.shape = shape;
    }

    public TetrisSingleGrid[][] getGrid() {
        return  this.grid;
    }

    // rysowanie figury
    public void drawFigure(Canvas canvas) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].getOccupied()){
                    grid[i][j].drawSingleGrid(canvas);
                }
            }
        }
    }

    // TODO: napisac obracanie figur
    public void turn() {
        this.angle = (this.angle + 90) % 360;
        this.makeGrid();
    }
}
