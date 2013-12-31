package com.retrogames.app.tetris;

import android.graphics.Canvas;

/**
 * Created by Tomasz on 31.12.13.
 */
public class TetrisFigure {

    private TetrisColor color;
    private TetrisShape shape;
    private TetrisSingleGrid[][] grid;

    public TetrisFigure() {
        this.color = TetrisColor.randomColor();
        this.shape = TetrisShape.randomShape();
    }

    public TetrisFigure(TetrisColor color, TetrisShape shape) {
        this.color = color;
        this.shape = shape;
    }
    
    // TO DO po sylwestrze
    private void makeGrid() {

    }

    public TetrisColor getColor() {
        return this.color;
    }
    public void setColor(TetrisColor color) {
        this.color = color;
    }

    public TetrisShape getShape() {
        return this.shape;
    }
    public void setShape(TetrisShape shape) {
        this.shape = shape;
    }

    public void draw(Canvas canvas) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j].draw(canvas);
            }
        }
    }
}
