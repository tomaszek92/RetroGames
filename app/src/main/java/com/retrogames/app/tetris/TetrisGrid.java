package com.retrogames.app.tetris;

import android.graphics.Canvas;
import android.graphics.Color;

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
    public static void setPointsScore(int score) {
        pointsScore = score;
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

    // sprawdzanie i ewnetualne usuwanie całego rzędu
    public void checkGridAddPointsAndRemoveRows() {
        for (int j = 0; j < GRID_HEIGHT; j++) {
            boolean allRowsAreOccupied = true;
            boolean oneColor = true;

            for (int i = 0; i < gameGrid.length && allRowsAreOccupied; i++) {
                if (gameGrid[i][j] != null  && gameGrid[i][j].getOccupied()) {
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
                deleteRow(j);
                clearGridRow(j);
                downGridRow(j);
                if (oneColor) {
                    pointsScore += pointsForOneRoweInOneColor;
                }
                else {
                    pointsScore += pointsForOneRow;
                }
            }
        }
    }

    // usuwanie rzędu
    private void deleteRow(int row) {
        List<TetrisFigure> gameFiguresDeleted = new LinkedList<TetrisFigure>();
        // wyszukiwanie figur, które leżą w usuwanym wierzu
        for (int i = 0; i < gameFigures.size(); i++) {
            TetrisSingleGrid[][] grids = gameFigures.get(i).getGrid();
            for (int k = 0; k < grids.length; k++) {
                for (int j = 0; j < grids[k].length; j++) {
                    if (row == grids[k][j].getY()) {
                        gameFiguresDeleted.add(gameFigures.get(i));
                    }
                }
            }
        }
        for (int i = 0; i < gameFiguresDeleted.size(); i++) {
            changeFigure(gameFiguresDeleted.get(i), row);
        }
    }

    // tworzenie nowej siatki figury
    // figure - zmieniana figura
    // deletedRow - usuwany wiersz
    private void changeFigure(TetrisFigure figure, int deletedRow) {
        if (figure.getShape() == TetrisShapes.BOX_1X1) {
            changeFigureBOX_1X1(figure);
        }
        else if (figure.getShape() == TetrisShapes.BOX_2X1) {
            changeFigureBOX_2X1(figure, deletedRow);
        }
        else if (figure.getShape() == TetrisShapes.BOX_3X1) {
            changeFigureBOX_3X1(figure, deletedRow);
        }
        else if (figure.getShape() == TetrisShapes.BOX_2X2) {
            changeFigureBOX_2X2(figure, deletedRow);
        }
        else if (figure.getShape() == TetrisShapes.CLIPPER) {

        }
        else if (figure.getShape() == TetrisShapes.CLIPPER_R) {

        }
        else if (figure.getShape() == TetrisShapes.LETTER_L_BIG) {

        }
        else if (figure.getShape() == TetrisShapes.LETTER_L_BIG_R) {

        }
        else if (figure.getShape() == TetrisShapes.LETTER_L_SMALL) {
            changeFigure_LETTER_L_SMALL(figure, deletedRow);
        }
    }

    private void changeFigureBOX_1X1(TetrisFigure figure) {
        gameFigures.remove(figure);
    }

    private void changeFigureBOX_2X1(TetrisFigure figure, int deletedRow) {
        if (figure.getAngle() == 0 || figure.getAngle() == 180) {
            // zmieniamy kształt na klocek 1x1
            figure.setShape(TetrisShapes.BOX_1X1);
            TetrisSingleGrid[][] grids = figure.getGrid();
            // wyszukiwanie który rząd usuwamy
            int i;
            for (i = 0; i < grids.length; i++) {
                if (grids[i][0].getY() == deletedRow) {
                    break;
                }
            }
            // zmiana siatki figury
            figure.setGrid(TetrisSingleGrid.deleteRow(figure.getGrid(), i));
        }
        else {
            gameFigures.remove(figure);
        }
    }

    private void changeFigureBOX_3X1(TetrisFigure figure, int deletedRow) {
        if (figure.getAngle() == 0 || figure.getAngle() == 180) {
            TetrisSingleGrid[][] grids = figure.getGrid();
            int i;
            for (i = 0; i < grids.length; i++) {
                if (grids[i][0].getY() == deletedRow) {
                    break;
                }
            }
            if (i == 0 || i == 2) {
                figure.setShape(TetrisShapes.BOX_2X1);
                figure.setGrid(TetrisSingleGrid.deleteRow(figure.getGrid(), i));
            }
            else {
                TetrisSingleGrid[][] gridToNewFigure = new TetrisSingleGrid[grids.length][];
                for (int i1 = 0; i1 < gridToNewFigure.length; i1++) {
                    gridToNewFigure[i1] = new TetrisSingleGrid[grids[i1].length];
                }
                for (int i1 = 0; i1 < gridToNewFigure.length; i1++) {
                    for (int j1 = 0; j1 < gridToNewFigure[i1].length; j1++) {
                        gridToNewFigure[i1][j1] = new TetrisSingleGrid(grids[i1][j1].getOccupied(), grids[i1][j1].getColor(), grids[i1][j1].getX(), grids[i1][j1].getY());
                    }
                }
                figure.setShape(TetrisShapes.BOX_1X1);
                figure.setGrid(TetrisSingleGrid.deleteRow(figure.getGrid(), 2));
                figure.setGrid(TetrisSingleGrid.deleteRow(figure.getGrid(), 1));
                TetrisFigure newFigure = new TetrisFigure(figure.getColor(), figure.getShape(), figure.getAngle());
                newFigure.setGrid(gridToNewFigure);
                newFigure.setGrid(TetrisSingleGrid.deleteRow(newFigure.getGrid(), 0));
                newFigure.setGrid(TetrisSingleGrid.deleteRow(newFigure.getGrid(), 0));
                addFigure(newFigure);
            }
        }
        else {
            gameFigures.remove(figure);
        }
    }

    private void changeFigureBOX_2X2(TetrisFigure figure, int deletedRow) {
        figure.setShape(TetrisShapes.BOX_2X1);
        TetrisSingleGrid[][] grids = figure.getGrid();
        int i;
        for (i = 0; i < grids.length; i++) {
            if (grids[i][0].getY() == deletedRow) {
                break;
            }
        }
        figure.setGrid(TetrisSingleGrid.deleteRow(figure.getGrid(), i));
    }

    private void changeFigure_LETTER_L_SMALL(TetrisFigure figure, int deletedRow) {
        TetrisSingleGrid[][] grids = figure.getGrid();
        int i;
        for (i = 0; i < grids.length; i++) {
            if (grids[i][0].getY() == deletedRow) {
                break;
            }
        }
        if (figure.getAngle() == 0 || figure.getAngle() == 90) {
            if (i == 0) {
                figure.setShape(TetrisShapes.BOX_2X1);
                figure.setGrid(TetrisSingleGrid.deleteRow(figure.getGrid(), 0));
            }
            else {
                figure.setShape(TetrisShapes.BOX_1X1);
                figure.setGrid(TetrisSingleGrid.deleteRow(figure.getGrid(), 1));
            }
        }
        else {
            if (i == 0) {
                figure.setShape(TetrisShapes.BOX_2X1);
                figure.setGrid(TetrisSingleGrid.deleteRow(figure.getGrid(), 0));
            }
            else {
                figure.setShape(TetrisShapes.BOX_1X1);
                figure.setGrid(TetrisSingleGrid.deleteRow(figure.getGrid(), 1));
            }
        }
        figure.setAngle(90);
        if (figure.getShape() == TetrisShapes.BOX_1X1) {
            grids = figure.getGrid();
            TetrisSingleGrid[][] gridNew = new TetrisSingleGrid[1][1];
            if (grids[0][0] != null) {
                gridNew[0][0] = new TetrisSingleGrid(grids[0][0].getOccupied(), grids[0][0].getColor(), grids[0][0].getX(), grids[0][0].getY());
            }
            else {
                gridNew[0][0] = new TetrisSingleGrid(grids[0][1].getOccupied(), grids[0][1].getColor(), grids[0][1].getX(), grids[0][1].getY());
            }
            figure.setGrid(gridNew);

            TetrisFigure newFigure = new TetrisFigure(figure.getColor(), figure.getShape(), figure.getAngle());
            newFigure.setGrid(gridNew);
            gameGrid[gridNew[0][0].getX()][gridNew[0][0].getY()] = gridNew[0][0];
            gameFigures.add(newFigure);
            gameFigures.remove(figure);
        }
        else {
            grids = figure.getGrid();
            TetrisFigure newFigure = new TetrisFigure(figure.getColor(), figure.getShape(), figure.getAngle());
            newFigure.setGrid(grids);
            gameFigures.remove(figure);
            gameFigures.add(newFigure);
        }
    }

    // czyszczenie rzędu
    private void clearGridRow(int deletedRow) {
        for (int i = 0; i < gameGrid.length; i++) {
            gameGrid[i][deletedRow] = null;
        }
    }

    // przesuwanie w dół wszystkich klocków powyżej deletedRow
    private void downGridRow(int deletedRow) {
        for (int i = 0; i < gameGrid.length; i++) {
            for (int j = deletedRow; j > 0; j--) {
                gameGrid[i][j] = gameGrid[i][j-1];
                if (gameGrid[i][j] != null) {
                    gameGrid[i][j].setY(gameGrid[i][j].getY() + 1);
                    gameGrid[i][j].setNewRectByXY();
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
    private static int xCoordinateToGrid(float x) {
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
    private static int yCoordinateToGrid(float y) {
        if (y < MARGIN_TOP + STROKE_WIDTH) {
            y = MARGIN_TOP + STROKE_WIDTH;
        }
        else if (y >  CANVAS_HEIGHT - MARGIN_BOTTOM - STROKE_WIDTH) {
            y = CANVAS_HEIGHT - MARGIN_BOTTOM - TetrisSingleGrid.SIZE;
        }

        int positionInGrid = (int)((y - MARGIN_TOP - STROKE_WIDTH) / TetrisSingleGrid.SIZE);

        return positionInGrid;
    }

    // sprawdzanie czy pole na siatce nie jest zajęte
    public boolean isNotOccupied(int x, int y) {
        if (x >= GRID_WIDTH || y >= GRID_HEIGHT) {
            return true;
        }
        if (this.gameGrid[x][y] == null) {
            return true;
        }
        return !this.gameGrid[x][y].getOccupied();
    }

    // wyznaczanie siatki gry od nowa na podstawie listy gameFigures
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

    // przesuwanie figury na podane współrzędne x i y w pikselach
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
                int xNew = xCoordinateToGrid(x + TetrisSingleGrid.SIZE * j);
                int yNew = yCoordinateToGrid(y + TetrisSingleGrid.SIZE * i);
                if (xNew < 0 || xNew >= GRID_WIDTH ||
                        yNew < 0 || yNew >= GRID_HEIGHT) {
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
        // sprawdzanie, czy żadne klocki na siebie nie naszły
        // poza dolną krawędź ekranu
        int count = 0;
        for (int i = 0; i < gridsClone.length; i++) {
            for (int j = 0; j < gridsClone[i].length; j++) {
                if (gridsClone[i][j].getY() == GRID_HEIGHT - 1) {
                    count++;
                }
            }
            if (count >= 3 && (figure.getShape() == TetrisShapes.CLIPPER_R
                    || figure.getShape() == TetrisShapes.CLIPPER
                    || figure.getShape() == TetrisShapes.BOX_2X2
                    || figure.getShape() == TetrisShapes.LETTER_L_SMALL)) {
                return;
            }
            else if (count >= 4 && (figure.getShape() == TetrisShapes.LETTER_L_BIG
                    || figure.getShape() == TetrisShapes.LETTER_L_BIG_R)) {
                return;
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

    private TetrisSingleGrid[][] cloneTetrisSingleGridArrayDim2(TetrisSingleGrid[][] input) {
        return new TetrisSingleGrid[0][0];
    }
}
