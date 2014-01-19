package com.retrogames.app.tanks;

/**
 * Created by Piotr on 19.01.14.
 */
public enum TanksShapes {

    //   X
    // X X X
    // X X X
    YOU(0),

    //   X
    // X X X
    // X   X
    ENEMY(1),

    // X
    BULLET(2);


    private int type;

    private TanksShapes(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }


}
