package com.retrogames.app.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tomasz on 01.01.14.
 */
public class TetrisGrid {

    private TetrisSingleGrid[][] gameGrid;
    private List<TetrisFigure> gameFigures;

    // marginesy do rysowania wyniku
    public static int MARGIN_SCORE_TOP = 50;
    public static int MARGIN_SCORE_LEFT = 50;
    public static int SCORE_COLOR = Color.WHITE;

    // marginesy do rysowania siatki gry
    public static int MARGIN_RIGHT;
    public static int MARGIN_TOP;
    public static int MARGIN_BOTTOM = 10;
    public static int MARGIN_LEFT = 10;

    // zmienne do rysowania
    public static final int STROKE_WIDTH = 5;
    public static final int LINE_COLOR = Color.WHITE;
    public static final int BACKGROUND_COLOR = Color.DKGRAY;

    // zmienne do wyniku
    private static int pointsScore = 0;
    private static final int pointsForOneRoweInOneColor = 1000;
    private static final int pointsForOneRow = 100;

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

    public static int getPointsScore() {
        return pointsScore;
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

    // sprawdzanie i ewnetualne usuwanie całego rzędu
    public void checkGrid() {
        for (int j = GRID_HEIGHT - 1; j >= 0; j--) {
            boolean allRowsAreOccupied = true;
            boolean oneColor = true;

            for (int i = 0; i < gameGrid.length; i++) {
                if (gameGrid[i][j] != null) {
                    if (!gameGrid[i][j].getOccupied()) {
                        allRowsAreOccupied = false;
                    }
                    if (oneColor && gameGrid[0][j].getColor() != gameGrid[i][j].getColor()) {
                        oneColor = false;
                    }
                }
                else {
                    allRowsAreOccupied = false;
                    oneColor = false;
                    break;
                }
            }
            if (allRowsAreOccupied) {
                if (oneColor) {
                    pointsScore += pointsForOneRoweInOneColor;
                }
                else {
                    pointsScore += pointsForOneRow;
                }
            }
        }
    }

    // rysowanie wszystkich figur
    public void drawAllFigures(Canvas canvas) {
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

    public boolean isNotOccupied(int x, int y) {
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

    public void moveFigure(TetrisFigure figure, float x, float y) {
        TetrisSingleGrid[][] grids = figure.getGrid();
        TetrisSingleGrid[][] gridsClone = new TetrisSingleGrid[grids.length][];
        // kopiowanie tablicy grids do gridsClone
        for (int i = 0; i < gridsClone.length; i++) {
            gridsClone[i] = new TetrisSingleGrid[grids[i].length];
        }
        for (int i = 0; i < gridsClone.length; i++) {
            for (int j = 0; j < gridsClone[i].length; j++) {
                gridsClone[i][j] = new TetrisSingleGrid(grids[i][j].getOccupied(), grids[i][j].getColor(), grids[i][j].getX(), grids[i][j].getY());
            }
        }
        // sprawdzanie czy jest puste pole
        for (int i = 0; i < gridsClone.length; i++) {
            for (int j = 0; j < gridsClone[i].length; j++) {
                int xNew = TetrisGrid.xCoordinateToGrid(x + TetrisSingleGrid.SIZE * j);
                int yNew = TetrisGrid.yCoordinateToGrid(y + TetrisSingleGrid.SIZE * i);
                if (xNew < 0 || xNew >= TetrisGrid.GRID_WIDTH ||
                        yNew < 0 || yNew >= TetrisGrid.GRID_HEIGHT) {
                    return;
                }
                if (this.isNotOccupied(xNew, yNew)) {
                    gridsClone[i][j].setX(xNew);
                    gridsClone[i][j].setY(yNew);
                }
                else {
                    return;
                }
            }
        }
        // przesuwanie klocka na nową pozycję
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                grids[i][j].setX(gridsClone[i][j].getX());
                grids[i][j].setY(gridsClone[i][j].getY());
                grids[i][j].setNewRectByXY();
            }
        }
        this.refreshGrid();
    }
}
