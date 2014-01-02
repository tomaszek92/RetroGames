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

    private static final int MARGIN = 5;
    private static final int COLOR_MARGIN = Color.BLACK;
    public static int SIZE = 30;

    public TetrisSingleGrid(boolean occupied, TetrisColors color, int x, int y) {
        this.occupied = occupied;
        this.color = color;
        this.rect = new Rect(x * SIZE, y * SIZE, x * SIZE + SIZE, y * SIZE + SIZE);
        this.x = x;
        this.y = y;
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

    public void drawSingleGrid(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(COLOR_MARGIN);
        canvas.drawRect(rect, paint);
        Rect rectNew = new Rect(rect.left + MARGIN, rect.top + MARGIN, rect.right - MARGIN, rect.bottom - MARGIN);
        canvas.drawRect(rectNew, color.getPaint());
    }
}
