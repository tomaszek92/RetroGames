package com.retrogames.app.tetris;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tomasz on 01.01.14.
 */
public class TetrisPanel {

    private TetrisSingleGrid[][] gameGrid;
    private List<TetrisFigure> gameFigures;

    private static int GRID_WIDTH;
    private static int GRID_HEIGHT;

    public TetrisPanel() {
        gameGrid = new TetrisSingleGrid[GRID_WIDTH][GRID_HEIGHT];
        gameFigures = new LinkedList<TetrisFigure>();
    }

    public static void setGRID_WIDTH(int width) {
        GRID_WIDTH = width;
    }
    public static int getGRID_WIDTH() {
        return GRID_WIDTH;
    }

    public static void setGRID_HEIGHT(int height) {
        GRID_HEIGHT = height;
    }
    public static int getGRID_HEIGHT() {
        return GRID_HEIGHT;
    }

    public void addFigure(TetrisFigure newFigure) {
        gameFigures.add(newFigure);
    }

    public void deleteFigure(TetrisFigure deletedFigure) {
        gameFigures.remove(deletedFigure);
    }

    public void draw() {

    }
}
