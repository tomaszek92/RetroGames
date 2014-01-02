package com.retrogames.app.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tomasz on 01.01.14.
 */
public class TetrisGrid {

    // marginesy do rysowania tła
    public static final int MARGIN_TOP = 100;
    public static final int MARGIN_BOTTOM = 10;
    public static final int MARGIN_LEFT = 10;
    public static final int MARGIN_RIGHT = 10;

    public static final int STROKE_WIDTH = 5;
    public static final int LINE_COLOR = Color.WHITE;
    public static final int BACKGROUND_COLOR = Color.DKGRAY;

    public static int GRID_BEGIN;
    public static int GRID_CENTER;

    private TetrisSingleGrid[][] gameGrid;
    private List<TetrisFigure> gameFigures;

    // wymiar siatki
    public static int GRID_WIDTH = 10;
    public static int GRID_HEIGHT = 17;

    public TetrisGrid() {
        gameGrid = new TetrisSingleGrid[GRID_WIDTH][GRID_HEIGHT];
        gameFigures = new LinkedList<TetrisFigure>();
    }

    // dodanie nowej figury do planszy gry
    public void addFigure(TetrisFigure newFigure) {
        gameFigures.add(newFigure);
        moveFigureToStartPosition(newFigure);
        addFigureToGameGrid(newFigure);
    }

    // ustawianie figury na środku i górze siatki
    private void moveFigureToStartPosition(TetrisFigure figure) {
        TetrisSingleGrid[][] tetrisSingleGrids = figure.getGrid();
        for (int i = 0; i < tetrisSingleGrids.length; i++) {
            for (int j = 0; j < tetrisSingleGrids[i].length; j++) {
                Rect rect = tetrisSingleGrids[i][j].getRect();
                rect.set(rect.left, rect.top + GRID_BEGIN, rect.right, rect.bottom + GRID_BEGIN);
                tetrisSingleGrids[i][j].setRect(rect);
            }
        }
    }

    // dodawanie klocków do gameGrid
    private void addFigureToGameGrid(TetrisFigure figure) {
        TetrisSingleGrid[][] tetrisSingleGrids = figure.getGrid();
        for (int i = 0; i < tetrisSingleGrids.length; i++) {
            for (int j = 0; j < tetrisSingleGrids[i].length; j++) {
                if (tetrisSingleGrids[i][j].getOccupied()) {
                    gameGrid[tetrisSingleGrids[i][j].getX()][tetrisSingleGrids[i][j].getY()] = tetrisSingleGrids[i][j];
                }
            }
        }
    }

    // usuwanie figury z planszy gry
    public void deleteFigure(TetrisFigure deletedFigure) {
        gameFigures.remove(deletedFigure);
    }

    public void draw(Canvas canvas) {
        for (TetrisFigure figure : gameFigures) {
            figure.drawFigure(canvas);
        }
    }

    // TODO: napisac to
    public int xCoordinateToGrid(float x) {

        return 0;
    }

    public int yCoordinateToGrid(float x) {
        return 0;
    }
}
