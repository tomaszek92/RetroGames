package com.retrogames.app.tetris;

import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by Tomasz on 31.12.13.
 */
public class TetrisFigure {

    private int angle = 0;
    public void setAngle(int angle) {
        this.angle = angle;
    }
    public int getAngle() {
        return this.angle;
    }

    private TetrisColors color;
    public TetrisColors getColor() {
        return this.color;
    }
    public void setColor(TetrisColors color) {
        this.color = color;
    }

    private TetrisShapes shape;
    public TetrisShapes getShape() {
        return this.shape;
    }
    public void setShape(TetrisShapes shape) {
        this.shape = shape;
    }

    private TetrisSingleGrid[][] grid;
    public TetrisSingleGrid[][] getGrid() {
        return  this.grid;
    }
    public void setGrid(TetrisSingleGrid[][] grid) {
        this.grid = grid;
    }

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
            makeGridForBOX_1_1();
        }
        else if (shape == TetrisShapes.BOX_2X1) {
            makeGridForBOX_2X1();
        }

        else if (shape == TetrisShapes.BOX_2X2) {
            makeGridForBOX2_2();
        }

        else if (shape == TetrisShapes.BOX_3X1) {
            makeGridForBOX_3X1();

        }

        else if (shape == TetrisShapes.CLIPPER) {
            makeGridForCLIPPER();
        }

        else if (shape == TetrisShapes.CLIPPER_R) {
            makeGridForCLIPPER_R();
        }

        else if (shape == TetrisShapes.LETTER_L_BIG) {
            makeGridForLETTER_L_BIG();
        }

        else if (shape == TetrisShapes.LETTER_L_BIG_R) {
            makeGridForLETTER_L_BIG_R();
        }

        else if (shape == TetrisShapes.LETTER_L_SMALL) {
            makeGridForLETTER_L_SMALL();
        }

        else {
            this.grid = null;
        }
    }

    private void makeGridForLETTER_L_SMALL() {
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
            grid[0][1] = new TetrisSingleGrid(true, color, 5, 0);
            grid[1][0] = new TetrisSingleGrid(true, color, 4, 1);
            grid[1][1] = new TetrisSingleGrid(false, color, 5, 1);
        }
    }

    private void makeGridForLETTER_L_BIG_R() {
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

    private void makeGridForLETTER_L_BIG() {
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

    private void makeGridForCLIPPER_R() {
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

    private void makeGridForCLIPPER() {
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

    private void makeGridForBOX_3X1() {
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

    private void makeGridForBOX2_2() {
        grid = new TetrisSingleGrid[2][2];
        grid[0][0] = new TetrisSingleGrid(true, color, 4, 0);
        grid[1][0] = new TetrisSingleGrid(true, color, 4, 1);
        grid[0][1] = new TetrisSingleGrid(true, color, 5, 0);
        grid[1][1] = new TetrisSingleGrid(true, color, 5, 1);
    }

    private void makeGridForBOX_2X1() {
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

    private void makeGridForBOX_1_1() {
        grid = new TetrisSingleGrid[1][1];
        grid[0][0] = new TetrisSingleGrid(true, color, 4, 0);
    }

    // rysowanie figury
    public void drawFigure(Canvas canvas) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                try {
                    if (grid[i][j] != null && grid[i][j].getOccupied()){
                        grid[i][j].drawSingleGrid(canvas);
                    }
                }
                catch (Exception e) {
                    return;
                }
            }
        }
    }

    // obracanie figury w lewo
    public boolean turn() {
        int posX = grid[0][0].getX();
        int posY = grid[0][0].getY();

        TetrisFigure newFigure = new TetrisFigure(this.color, this.shape, (this.angle + 90) % 360);
        newFigure.makeGrid();

        TetrisSingleGrid[][] cloneGrid = newFigure.getGrid();

        // side == -1 => wyszło w lewej strony
        // side == 1 => wyszło w prawej strony
        // side == 0 => mieście się w obszarze gry
        short side = 0;
        for (int i = 0; i < cloneGrid.length; i++) {
            for (int j = 0; j < cloneGrid[i].length; j++) {
                cloneGrid[i][j].setX(posX + j);
                cloneGrid[i][j].setY(cloneGrid[i][j].getY() + posY);
                if (cloneGrid[i][j].getX() >= TetrisGrid.GRID_WIDTH) {
                    side = 1;
                }
                else if (cloneGrid[i][j].getX() < 0) {
                    side = -1;
                }
                if (cloneGrid[i][j].getY() >= TetrisGrid.GRID_HEIGHT || cloneGrid[i][j].getY() < 0) {
                    return false;
                }
            }
        }
        if (side == 1) {
            for (int i = 0; i < cloneGrid.length; i++) {
                for (int j = 0; j < cloneGrid[i].length; j++) {
                    cloneGrid[i][j].setX(cloneGrid[i][j].getX() - 1);
                }
            }
        }
        else if (side == -1) {
            for (int i = 0; i < cloneGrid.length; i++) {
                for (int j = 0; j < cloneGrid[i].length; j++) {
                    cloneGrid[i][j].setX(cloneGrid[i][j].getX() + 1);
                }
            }
        }
        this.angle = (angle + 90) % 360;
        this.makeGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j].setX(cloneGrid[i][j].getX());
                grid[i][j].setY(cloneGrid[i][j].getY());
                grid[i][j].setNewRectByXY();
            }
        }
        return true;
    }
}
