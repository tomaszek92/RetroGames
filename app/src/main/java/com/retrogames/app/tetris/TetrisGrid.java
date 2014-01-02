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

    private TetrisSingleGrid[][] gameGrid;
    private List<TetrisFigure> gameFigures;

    // marginesy do rysowania tła
    public static int MARGIN_RIGHT;
    public static int MARGIN_TOP;
    public static int MARGIN_BOTTOM = 10;
    public static int MARGIN_LEFT = 10;

    public static final int STROKE_WIDTH = 5;
    public static final int LINE_COLOR = Color.WHITE;
    public static final int BACKGROUND_COLOR = Color.DKGRAY;

    // wymiar siatki w klockach
    public static int GRID_WIDTH = 10;
    public static int GRID_HEIGHT = 15;

    // wymiar całego canvasu
    public static int CANVAS_HEIGHT;
    public static int CANVAS_WIDTH;

    public TetrisGrid(int height, int width) {
        CANVAS_HEIGHT = height;
        CANVAS_WIDTH = width;

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
                tetrisSingleGrids[i][j].setNewRectByXY();
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

    // rysowanie wszystkich figur
    public void draw(Canvas canvas) {
        for (int i = 0; i <  gameFigures.size(); i++) {
            gameFigures.get(i).drawFigure(canvas);
        }
    }

    // zamiena współrzędnej x w pikselach na odpowiednią współrzędną w siatce
    public static int xCoordinateToGrid(float x) {
        if (x < MARGIN_LEFT + STROKE_WIDTH) {
            x = MARGIN_LEFT + STROKE_WIDTH;
        }
        else if (x > MARGIN_RIGHT) {
            x = MARGIN_RIGHT;
        }

        int positionInGrid = (int)((x-MARGIN_LEFT - STROKE_WIDTH) / TetrisSingleGrid.SIZE);

        return positionInGrid;
    }

    // zamiena współrzędnej y w pikselach na odpowiednią współrzędną w siatce
    public static int yCoordinateToGrid(float y) {
        if (y < MARGIN_TOP + STROKE_WIDTH) {
            y = MARGIN_TOP + STROKE_WIDTH;
        }
        else if (y >  CANVAS_HEIGHT - MARGIN_BOTTOM - STROKE_WIDTH) {
            y = CANVAS_HEIGHT - MARGIN_BOTTOM - TetrisSingleGrid.SIZE;
        }

        int positionInGrid = (int)((y - MARGIN_TOP - STROKE_WIDTH) / TetrisSingleGrid.SIZE);

        return positionInGrid;
    }

    public boolean isFree(int x, int y) {
        if (x >= GRID_WIDTH || y >= GRID_HEIGHT) {
            return true;
        }
        if (this.gameGrid[x][y] == null) {
            return true;
        }
        return !this.gameGrid[x][y].getOccupied();
    }

    public void refreshGrid() {
        for (int i = 0; i < gameGrid.length; i++) {
            for (int j = 0; j < gameGrid[i].length; j++) {
                gameGrid[i][j] = null;
            }
        }
        for (TetrisFigure figure : this.gameFigures) {
            addFigureToGameGrid(figure);
        }
    }

    private void addToGrid(TetrisFigure figure) {
        TetrisSingleGrid[][] grids = figure.getGrid();
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                gameGrid[i][j] = grids[i][j];
            }
        }
        //gameGrid[][] = figure;
    }
}
