package com.retrogames.app.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

/**
 * Created by Tomasz on 31.12.13.
 */
public class TetrisSingleGrid {

    private boolean occupied;
    private TetrisColor color;
    private Rect rect;

    public TetrisSingleGrid(boolean occupied, TetrisColor color, Rect rect) {
        this.occupied = occupied;
        this.color = color;
        this.rect = rect;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    public boolean getOccupied() {
        return this.occupied;
    }

    public void setColor(TetrisColor color) {
        this.color = color;
    }
    public TetrisColor getColor() {
        return this.color;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }
    public Rect getRect() {
        return this.rect;
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(rect, color.getPaint());
    }
}
