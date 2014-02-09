package com.retrogames.app.race;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Tomasz on 06.02.14.
 */
public class RaceSingleGrid {

    // czy pole jest zajmowane
    public boolean occupied;
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    public boolean getOccupied() {
        return this.occupied;
    }

    // kwadrat opisujący położenie klocka
    private Rect rect;
    public Rect getRect() {
        return this.rect;
    }
    public void setRect(Rect newRect) {
        this.rect = newRect;
    }

    // współrzędne w siatce gry
    private int x;
    public void setY(int y) {
        this.y = y;
    }
    public int getY() {
        return this.y;
    }

    private int y;
    public void setX(int x) {
        this.x = x;
    }
    public int getX() {
        return this.x;
    }

    // parametry do rysowania klocka
    private static final int MARGIN = 5;
    private static final int COLOR_MARGIN = Color.BLACK;
    private static final int COLOR_SINGLE_GRID = Color.WHITE; // Color.rgb(51,102,77);
    public static int SIZE = 30;

    // rysowanie pojedyńczego klocka
    public void drawSingleGrid(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(COLOR_MARGIN);
        canvas.drawRect(rect, paint);
        Rect rectNew = new Rect(rect.left + MARGIN, rect.top + MARGIN, rect.right - MARGIN, rect.bottom - MARGIN);
        paint.setColor(COLOR_SINGLE_GRID);
        canvas.drawRect(rectNew, paint);
    }

    public boolean isNotOccupied() {
        if (this == null) {
            return true;
        }
        return !this.occupied;
    }

    public void setNewRectByXY() {
        this.rect = new Rect(x * SIZE, y * SIZE, x * SIZE + SIZE, y * SIZE + SIZE);
        this.rect.left = this.rect.left + RaceGrid.MARGIN_LEFT + RaceGrid.STROKE_WIDTH;
        this.rect.right = this.rect.right + RaceGrid.MARGIN_LEFT + RaceGrid.STROKE_WIDTH;
        this.rect.top = this.rect.top + RaceGrid.MARGIN_TOP + RaceGrid.STROKE_WIDTH;
        this.rect.bottom = this.rect.bottom + RaceGrid.MARGIN_TOP + RaceGrid.STROKE_WIDTH;
    }

    public static RaceSingleGrid[][] deleteLastRow(RaceSingleGrid[][] grid) {
        RaceSingleGrid[][] returnGrid = new RaceSingleGrid[grid.length - 1][3];
        for (int i = 0; i < grid.length - 1; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                returnGrid[i][j] = copy(grid[i][j]);
            }
        }
        return returnGrid;
    }

    private static RaceSingleGrid copy(RaceSingleGrid copied) {
        RaceSingleGrid singleGrid = new RaceSingleGrid();
        singleGrid.setY(copied.getY());
        singleGrid.setX(copied.getX());
        singleGrid.setRect(copied.getRect());
        singleGrid.setOccupied(copied.getOccupied());
        return singleGrid;
    }
}
