package net.retrogame;

import java.util.ArrayList;

class Board {
    private static final int DEFAULT_ROW_COLUMN_SIZE = 9;
    private static final int DEFAULT_NUMBER_OF_BOMBS = 10;
    
    private int rows = DEFAULT_ROW_COLUMN_SIZE;
    private int columns = DEFAULT_ROW_COLUMN_SIZE;
    private int numberOfBombs = DEFAULT_NUMBER_OF_BOMBS;
    
    private final ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();
    private boolean isGameOver = false;
    
    public Board(int rows, int columns, int numberOfBombs) {
        this.rows = rows;
        this.columns = columns;
        this.numberOfBombs = numberOfBombs;
    }
    
    public void showBoard() {
    
    }
    
    public void instantiateBoard() {
    
    }
    
    private void placeBombsRandomly() {
    
    }
    
    private void updateTileNumbers() {
    
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getColumns() {
        return columns;
    }
    
    public int getNumberOfBombs() {
        return numberOfBombs;
    }
    
    public boolean isGameOver() {
        return isGameOver;
    }
    
    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
}