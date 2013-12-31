package com.retrogames.app.tetris;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by Tomasz on 31.12.13.
 */
public enum TetrisColor {
    GREEN(1),
    YELLOW(2),
    RED(3),
    BLUE(4);

    private int type;

    private TetrisColor(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static TetrisColor randomColor() {
        Random random = new Random();
        int rand = random.nextInt(4);

        if (rand == 0) {
            return TetrisColor.GREEN;
        }
        else if (rand == 1) {
            return TetrisColor.YELLOW;
        }
        else if (rand == 2) {
            return TetrisColor.RED;
        }
        else if (rand == 3) {
            return  TetrisColor.BLUE;
        }
        return TetrisColor.GREEN;
    }

    public Paint getPaint() {
        if (this == GREEN) {
            return new Paint(Color.GREEN);
        }
        else if (this == YELLOW) {
            return new Paint(Color.YELLOW);
        }
        else  if (this == RED) {
            return new Paint(Color.RED);
        }
        else if (this == BLUE) {
            return new Paint(Color.BLUE);
        }
        return new Paint(Color.GREEN);
    }
}
