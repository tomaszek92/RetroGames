package com.retrogames.app.tetris;

import android.graphics.Canvas;

/**
 * Created by Tomasz on 31.12.13.
 */
public class TetrisFigure {

    private TetrisColors color;
    private TetrisShapes shape;
    private TetrisSingleGrid[][] grid;

    public TetrisFigure() {
        this.color = TetrisColors.randomColor();
        this.shape = TetrisShapes.randomShape();
        makeGrid();
    }

    public TetrisFigure(TetrisColors color, TetrisShapes shape) {
        this.color = color;
        this.shape = shape;
        makeGrid();
    }

    // tworzenie siatki figury, w zależności od typu figury
    private void makeGrid() {
        TetrisColors color = TetrisColors.randomColor();

        if (shape == TetrisShapes.BOX_1X1) {
            grid = new TetrisSingleGrid[1][1];
            grid[0][0] = new TetrisSingleGrid(true, color, 4, 0);
        }
        else if (shape == TetrisShapes.BOX_2X1) {
            grid = new TetrisSingleGrid[2][1];
            grid[0][0] = new TetrisSingleGrid(true, color, 4, 0);
            grid[1][0] = new TetrisSingleGrid(true, color, 4, 1);
        }

        else if (shape == TetrisShapes.BOX_2X2) {
            grid = new TetrisSingleGrid[2][2];
            grid[0][0] = new TetrisSingleGrid(true, color, 4, 0);
            grid[1][0] = new TetrisSingleGrid(true, color, 4, 1);
            grid[0][1] = new TetrisSingleGrid(true, color, 5, 0);
            grid[1][1] = new TetrisSingleGrid(true, color, 5, 1);
        }

        else if (shape == TetrisShapes.BOX_3X1) {
            grid = new TetrisSingleGrid[3][1];
            grid[0][0] = new TetrisSingleGrid(true, color, 4, 0);
            grid[1][0] = new TetrisSingleGrid(true, color, 4, 1);
            grid[2][0] = new TetrisSingleGrid(true, color, 4, 2);
        }

        else if (shape == TetrisShapes.BOX_4X1) {
            grid = new TetrisSingleGrid[4][1];
            grid[0][0] = new TetrisSingleGrid(true, color, 4, 0);
            grid[1][0] = new TetrisSingleGrid(true, color, 4, 1);
            grid[2][0] = new TetrisSingleGrid(true, color, 4, 2);
            grid[3][0] = new TetrisSingleGrid(true, color, 4, 3);
        }

        else if (shape == TetrisShapes.CLIPPER) {
            grid = new TetrisSingleGrid[3][2];
            grid[0][0] = new TetrisSingleGrid(true, color, 4, 0);
            grid[0][1] = new TetrisSingleGrid(false, color, 5, 0);
            grid[1][0] = new TetrisSingleGrid(true, color, 4, 1);
            grid[1][1] = new TetrisSingleGrid(true, color, 5, 1);
            grid[2][0] = new TetrisSingleGrid(false, color, 4, 2);
            grid[2][1] = new TetrisSingleGrid(true, color, 5, 2);
        }

        else if (shape == TetrisShapes.CLIPPER_R) {
            grid = new TetrisSingleGrid[3][2];
            grid[0][0] = new TetrisSingleGrid(false, color, 4, 0);
            grid[0][1] = new TetrisSingleGrid(true, color, 5, 0);
            grid[1][0] = new TetrisSingleGrid(true, color, 4, 1);
            grid[1][1] = new TetrisSingleGrid(true, color, 5, 1);
            grid[2][0] = new TetrisSingleGrid(true, color, 4, 2);
            grid[2][1] = new TetrisSingleGrid(false, color, 5, 2);
        }

        else if (shape == TetrisShapes.LETTER_L_BIG) {
            grid = new TetrisSingleGrid[3][2];
            grid[0][0] = new TetrisSingleGrid(true, color, 4, 0);
            grid[0][1] = new TetrisSingleGrid(false, color, 5, 0);
            grid[1][0] = new TetrisSingleGrid(true, color, 4, 1);
            grid[1][1] = new TetrisSingleGrid(false, color, 5, 1);
            grid[2][0] = new TetrisSingleGrid(true, color, 4, 2);
            grid[2][1] = new TetrisSingleGrid(true, color, 5, 2);
        }

        else if (shape == TetrisShapes.LETTER_L_BIG_R) {
            grid = new TetrisSingleGrid[3][2];
            grid[0][0] = new TetrisSingleGrid(false, color, 4, 0);
            grid[0][1] = new TetrisSingleGrid(true, color, 5, 0);
            grid[1][0] = new TetrisSingleGrid(false, color, 4, 1);
            grid[1][1] = new TetrisSingleGrid(true, color, 5, 1);
            grid[2][0] = new TetrisSingleGrid(true, color, 4, 2);
            grid[2][1] = new TetrisSingleGrid(true, color, 5, 2);
        }

        else if (shape == TetrisShapes.LETTER_L_SMALL) {
            grid = new TetrisSingleGrid[2][2];
            grid[0][0] = new TetrisSingleGrid(true, color, 4, 0);
            grid[0][1] = new TetrisSingleGrid(false, color, 5, 0);
            grid[1][0] = new TetrisSingleGrid(true, color, 4, 1);
            grid[1][1] = new TetrisSingleGrid(true, color, 5, 1);
        }

        else if (shape == TetrisShapes.LETTER_L_SMALL_R) {
            grid = new TetrisSingleGrid[2][2];
            grid[0][0] = new TetrisSingleGrid(false, color, 4, 0);
            grid[0][1] = new TetrisSingleGrid(true, color, 5, 0);
            grid[1][0] = new TetrisSingleGrid(true, color, 4, 1);
            grid[1][1] = new TetrisSingleGrid(true, color, 5, 1);
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

    }
}
