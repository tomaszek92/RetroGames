package com.retrogames.app.tanks;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.appcompat.R;

/**
 * Created by Piotr on 19.01.14.
 */
public class TanksSingleGrid {
    private boolean occupied;
    //private TetrisColors color;
    private Rect rect;

    // współrzędne w siatce gry
    private int x;
    private int y;

    // parametry do rysowania czołga
    private static final int MARGIN = 5;
    private static final int COLOR_MARGIN = Color.BLACK;
    public static int SIZE = 30;

    public TanksSingleGrid(boolean occupied, int x, int y) {
        this.occupied = occupied;
        //this.color = color;
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
        this.rect.left = this.rect.left + TanksGrid.MARGIN_LEFT + TanksGrid.STROKE_WIDTH;
        this.rect.right = this.rect.right + TanksGrid.MARGIN_LEFT + TanksGrid.STROKE_WIDTH;
        this.rect.top = this.rect.top + TanksGrid.MARGIN_TOP + TanksGrid.STROKE_WIDTH;
        this.rect.bottom = this.rect.bottom + TanksGrid.MARGIN_TOP + TanksGrid.STROKE_WIDTH;
    }

    // rysowanie pojedyńczego klocka
    public void drawSingleGrid(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(COLOR_MARGIN);
        canvas.drawRect(rect, paint);
        Rect rectNew = new Rect(rect.left + MARGIN, rect.top + MARGIN, rect.right - MARGIN, rect.bottom - MARGIN);
        paint.setColor(Color.GREEN);
        canvas.drawRect(rectNew, paint);
    }


    // klonowanie tablicy dwuwymiarowej
    public static TanksSingleGrid[][] cloneArrayDim2(TanksSingleGrid[][] input) {
        TanksSingleGrid[][] output = new TanksSingleGrid[input.length][];
        for (int i = 0; i < output.length; i++) {
            output[i] = new TanksSingleGrid[input[i].length];
        }
        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output[i].length; j++) {
                output[i][j] = new TanksSingleGrid(input[i][j].getOccupied(),  input[i][j].getX(), input[i][j].getY());
            }
        }
        return output;
    }
}
