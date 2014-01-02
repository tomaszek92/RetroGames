package com.retrogames.app.tetris;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by Tomasz on 31.12.13.
 */
public enum TetrisColors {
    GREEN(1),
    YELLOW(2),
    RED(3),
    BLUE(4);

    private int type;

    private TetrisColors(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static TetrisColors randomColor() {
        Random random = new Random();
        int rand = random.nextInt(4);

        if (rand == 0) {
            return TetrisColors.GREEN;
        }
        else if (rand == 1) {
            return TetrisColors.YELLOW;
        }
        else if (rand == 2) {
            return TetrisColors.RED;
        }
        else if (rand == 3) {
            return  TetrisColors.BLUE;
        }
        return TetrisColors.GREEN;
    }

    // zwraca gotowy kolor
    public Paint getPaint() {
        Paint paint = new Paint();
        if (this == GREEN) {
            paint.setColor(Color.GREEN);
        }
        else if (this == YELLOW) {
            paint.setColor(Color.YELLOW);
        }
        else  if (this == RED) {
            paint.setColor(Color.RED);
        }
        else if (this == BLUE) {
            paint.setColor(Color.BLUE);
        }
        return paint;
    }
}
