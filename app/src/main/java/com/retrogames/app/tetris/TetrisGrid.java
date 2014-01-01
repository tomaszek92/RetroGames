package com.retrogames.app.tetris;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tomasz on 01.01.14.
 */
public class TetrisGrid {

    private TetrisSingleGrid[][] gameGrid;
    private List<TetrisFigure> gameFigures;

    // liczba klocków
    public static int GRID_WIDTH = 10;
    public static int GRID_HEIGHT = 17;

    public TetrisGrid() {
        gameGrid = new TetrisSingleGrid[GRID_WIDTH][GRID_HEIGHT];
        gameFigures = new LinkedList<TetrisFigure>();
    }

    public void addFigure(TetrisFigure newFigure) {
        gameFigures.add(newFigure);
    }

    public void deleteFigure(TetrisFigure deletedFigure) {
        gameFigures.remove(deletedFigure);
    }

    public void draw() {

    }

    // TODO: napisac to
    public int xCoordinateToGrid(float x) {
        return 0;
    }

    public int yCoordinateToGrid(float x) {
        return 0;
    }
}
