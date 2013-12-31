package com.retrogames.app.tetris;

import java.util.Random;

/**
 * Created by Tomasz on 31.12.13.
 */
public enum TetrisShapes {

    // X
    BOX_1X1(0),

    // X X
    BOX_2X1(1),

    // X X
    // X X
    BOX_2X2(2),

    // X X X
    BOX_3X1(3),

    // X X X X
    BOX_4X1(4),

    // X
    // X X
    LETTER_L_SMALL(5),

    //   X
    // X X
    LETTER_L_SMALL_R(6),

    // X
    // X
    // X X
    LETTER_L_BIG(7),

    //   X
    //   X
    // X X
    LETTER_L_BIG_R(8),

    // X
    // X X
    //   X
    CLIPPER(9),

    //   X
    // X X
    // X
    CLIPPER_R(10);

    private int type;

    private TetrisShapes(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public TetrisShapes randomShap() {
        Random random = new Random();
        int rand = random.nextInt(11);

        if (rand == 0) {
            return TetrisShapes.BOX_1X1;
        }
        else if (rand == 1) {
            return TetrisShapes.BOX_2X1;
        }

        else if (rand == 2) {
            return TetrisShapes.BOX_2X2;
        }

        else if (rand == 3) {
            return TetrisShapes.BOX_3X1;
        }

        else if (rand == 4) {
            return TetrisShapes.BOX_4X1;
        }

        else if (rand == 5) {
            return TetrisShapes.LETTER_L_SMALL;
        }

        else if (rand == 6) {
            return TetrisShapes.LETTER_L_SMALL_R;
        }

        else if (rand == 7) {
            return TetrisShapes.LETTER_L_BIG;
        }

        else if (rand == 8) {
            return TetrisShapes.LETTER_L_BIG_R;
        }

        else if (rand == 9) {
            return TetrisShapes.CLIPPER;
        }

        else if (rand == 10) {
            return TetrisShapes.CLIPPER_R;
        }

        return TetrisShapes.BOX_1X1;
    }
}
