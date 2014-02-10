package com.retrogames.app.tanks;

import android.graphics.Canvas;

import com.retrogames.app.tetris.TetrisShapes;

import java.util.Random;

/**
 * Created by Piotr on 19.01.14.
 */
public class TanksFigure {

    private int angle = 0;
    public void setAngle(int angle) {
        this.angle = angle;
    }
    public int getAngle() {
        return this.angle;
    }

    private TanksShapes shape;
    public TanksShapes getShape() {
        return this.shape;
    }
    public void setShape(TanksShapes shape) {
        this.shape = shape;
    }


    private TanksSingleGrid[][] grid;
    public TanksSingleGrid[][] getGrid() {
        return  this.grid;
    }
    public void setGrid(TanksSingleGrid[][] grid) {
        this.grid = grid;
    }

    public TanksFigure(TanksShapes shape, int angle) {
        this.shape = shape;
        this.angle = angle;
        this.makeGrid(angle);
    }

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
    private void makeGrid(int angle) {
        if(shape== TanksShapes.YOU)
        {
            makeGridForYOU();
        }
        else if(shape == TanksShapes.ENEMY)
        {
            Random rnd = new Random();
            makeGridForENEMY(rnd.nextInt(7));
        }
        else if(shape == TanksShapes.BULLET)
        {
             makeGridForBULLET(angle);
        }
        else
        {
            this.grid = null;
        }

    }

    private void makeGridForYOU()
    {
        grid = new TanksSingleGrid[3][3];
        grid[0][0] = new TanksSingleGrid(false,  9, 14);
        grid[0][1] = new TanksSingleGrid(true, 10, 14);
        grid[0][2] = new TanksSingleGrid(false, 11, 14);
        grid[1][0] = new TanksSingleGrid(true, 9, 15);
        grid[1][1] = new TanksSingleGrid(true, 10, 15);
        grid[1][2] = new TanksSingleGrid(true, 11, 15);
        grid[2][0] = new TanksSingleGrid(true, 9, 16);
        grid[2][1] = new TanksSingleGrid(true, 10, 16);
        grid[2][2] = new TanksSingleGrid(true, 11, 16);
        angle = 180;
    }
    private void makeGridForENEMY(int pos)
    {
        grid = new TanksSingleGrid[3][3];
        grid[0][0] = new TanksSingleGrid(true,  0, 0);
        grid[0][1] = new TanksSingleGrid(true, 1, 0);
        grid[0][2] = new TanksSingleGrid(false, 2, 0);
        grid[1][0] = new TanksSingleGrid(false, 0, 1);
        grid[1][1] = new TanksSingleGrid(true, 1, 1);
        grid[1][2] = new TanksSingleGrid(true, 2, 1);
        grid[2][0] = new TanksSingleGrid(true, 0, 2);
        grid[2][1] = new TanksSingleGrid(true, 1, 2);
        grid[2][2] = new TanksSingleGrid(false, 2, 2);
        angle = 270;

        if(pos == 0)
        {

        }
        else if(pos == 1)
        {
            grid = MoveX(grid,9);
            angle = 0;

        }
        else if(pos == 2)
        {
            grid = MoveX(grid,17);
           grid = RotateFigureRight(grid);
            angle = 90;

        }
        else if(pos == 3)
        {
            grid = MoveY(grid,14);
            grid = RotateFigureLeft(grid);
            angle = 270;

        }
        else if(pos == 4)
        {
            grid = MoveY(grid,27);
            grid = RotateFigureLeft(grid);
            angle = 270;
        }
        else if(pos == 5)
        {
            grid = MoveXY(grid,17,14);
            grid = RotateFigureRight(grid);
            angle = 90;
        }
        else if(pos == 6)
        {
            grid = MoveXY(grid,17,27);
            grid = RotateFigureRight(grid);
            angle = 90;
        }
        else if(pos == 7)
        {
            grid = MoveXY(grid,9,27);
            grid = RotateFigureRight(grid);
            grid = RotateFigureRight(grid);
            angle = 180;
        }



    }
    private void makeGridForBULLET(int direction)
    {
        grid = new TanksSingleGrid[1][1];
        grid[0][0] = new TanksSingleGrid(true,5,5);
        angle = direction;
    }

    private TanksSingleGrid[][] RotateFigureLeft(TanksSingleGrid[][]matrix)
    {
        int n = matrix.length;
        TanksSingleGrid[][] ret = new TanksSingleGrid[n][n];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                //ret[i][j] = matrix[n - j - 1] [i];
                ret[i][j] = new TanksSingleGrid( matrix[n - j - 1] [i].getOccupied(), matrix[i] [j].getX(),matrix[i] [j].getY());
            }
        }

        return ret;
    }
    private TanksSingleGrid[][] RotateFigureRight(TanksSingleGrid[][] matrix)
    {

        int n = matrix.length;
        TanksSingleGrid[][] ret = new TanksSingleGrid[n][n];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                //ret[i][j] = matrix[n - j - 1] [i];
                ret[i][j] = new TanksSingleGrid( matrix[i] [j].getOccupied(), matrix[i] [j].getX(),matrix[i] [j].getY());
            }
        }
        ret = RotateFigureLeft(ret);
        ret = RotateFigureLeft(ret);
        ret = RotateFigureLeft(ret);

        return ret;
    }

    private TanksSingleGrid[][] MoveX(TanksSingleGrid[][]matrix, int t)
    {
        int n = matrix.length;
        TanksSingleGrid[][] ret = new TanksSingleGrid[n][n];

        for(int i= 0; i<n;i++)
        {
            for(int j = 0; j<n; j++)
            {
                ret[i][j] = new TanksSingleGrid( matrix[i] [j].getOccupied(), i+t,j);
            }
        }
        return ret;
    }
    private TanksSingleGrid[][] MoveXY(TanksSingleGrid[][]matrix, int t, int k)
    {
        int n = matrix.length;
        TanksSingleGrid[][] ret = new TanksSingleGrid[n][n];

        for(int i= 0; i<n;i++)
        {
            for(int j = 0; j<n; j++)
            {
                ret[i][j] = new TanksSingleGrid( matrix[i] [j].getOccupied(), i+t,j+k);
            }
        }
        return ret;
    }
    private TanksSingleGrid[][] MoveY(TanksSingleGrid[][]matrix, int t)
    {
        int n = matrix.length;
        TanksSingleGrid[][] ret = new TanksSingleGrid[n][n];

        for(int i= 0; i<n;i++)
        {
            for(int j = 0; j<n; j++)
            {
                ret[i][j] = new TanksSingleGrid( matrix[i] [j].getOccupied(), i,j+t);
            }
        }
        return ret;
    }


}
