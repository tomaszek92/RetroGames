package com.retrogames.app.race;

import java.util.Random;

/**
 * Created by Tomasz on 07.02.14.
 */
public enum RacePosition {
    LEFT(0),
    CENTER(1),
    RIGHT(2);

    private int pos;

    private RacePosition(int pos) {
        this.pos = pos;
    }

    public int toInt() {
        return this.pos;
    }

    public static RacePosition randomPosition() {
        Random random = new Random();
        int pos = random.nextInt(3);
        if (pos == 0) {
            return RacePosition.LEFT;
        }
        else if (pos == 1) {
            return RacePosition.CENTER;
        }
        else if (pos == 2) {
            return RacePosition.RIGHT;
        }
        else {
            return RacePosition.CENTER;
        }
    }
}
