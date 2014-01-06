package com.retrogames.app.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Tomasz on 31.12.13.
 */
public class TetrisSingleGrid {

    private boolean occupied;
    private TetrisColors color;
    private Rect rect;

    // współrzędne w siatce gry
    private int x;
    private int y;

    // parametry do rysowania klocka
    private static final int MARGIN = 5;
    private static final int COLOR_MARGIN = Color.BLACK;
    public static int SIZE = 30;

    public TetrisSingleGrid(boolean occupied, TetrisColors color, int x, int y) {
        this.occupied = occupied;
        this.color = color;
        this.x = x;
        this.y = y;
        setNewRectByXY();
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    public boolean getOccupied() {
        return this.occupied;
    }

    public void setColor(TetrisColors color) {
        this.color = color;
    }
    public TetrisColors getColor() {
        return this.color;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }
    public Rect getRect() {
        return this.rect;
    }

    public void setX(int x) {
        this.x = x;
    }
    public int getX() {
        return this.x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public int getY() {
        return this.y;
    }

    // ustawianie kwadratu na podstawie współrzędnych x y
    public void setNewRectByXY() {
        this.rect = new Rect(x * SIZE, y * SIZE, x * SIZE + SIZE, y * SIZE + SIZE);
        this.rect.left = this.rect.left + TetrisGrid.MARGIN_LEFT + TetrisGrid.STROKE_WIDTH;
        this.rect.right = this.rect.right + TetrisGrid.MARGIN_LEFT + TetrisGrid.STROKE_WIDTH;
        this.rect.top = this.rect.top + TetrisGrid.MARGIN_TOP + TetrisGrid.STROKE_WIDTH;
        this.rect.bottom = this.rect.bottom + TetrisGrid.MARGIN_TOP + TetrisGrid.STROKE_WIDTH;
    }

    // rysowanie pojedyńczego klocka
    public void drawSingleGrid(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(COLOR_MARGIN);
        canvas.drawRect(rect, paint);
        Rect rectNew = new Rect(rect.left + MARGIN, rect.top + MARGIN, rect.right - MARGIN, rect.bottom - MARGIN);
        canvas.drawRect(rectNew, color.getPaint());
    }

    // tworzenie nowej siatki na podstawie podanej w parametrze z usuniętym wieszem row
    public static TetrisSingleGrid[][] deleteRow(TetrisSingleGrid[][] grids, int row) {
        TetrisSingleGrid[][] newGrid = new TetrisSingleGrid[grids.length - 1][grids[0].length];
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                try {
                    if (i < row) {
                        newGrid[i][j] = grids[i][j];
                    }
                    else if (i > row) {
                        newGrid[i-1][j] = grids[i][j];
                    }
                }
                catch (Exception e) {
                    return newGrid;
                }
            }
        }
        return newGrid;
    }

    // klonowanie tablicy dwuwymiarowej
    public static TetrisSingleGrid[][] cloneArrayDim2(TetrisSingleGrid[][] input) {
        TetrisSingleGrid[][] output = new TetrisSingleGrid[input.length][];
        for (int i = 0; i < output.length; i++) {
            output[i] = new TetrisSingleGrid[input[i].length];
        }
        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output[i].length; j++) {
                output[i][j] = new TetrisSingleGrid(input[i][j].getOccupied(), input[i][j].getColor(), input[i][j].getX(), input[i][j].getY());
            }
        }
        return output;
    }
}
