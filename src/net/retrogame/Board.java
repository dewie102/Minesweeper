package net.retrogame;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private static final int DEFAULT_ROW_COLUMN_SIZE = 9;
    private static final int DEFAULT_NUMBER_OF_BOMBS = 10;
    
    private final int rows;
    private final int columns;
    private final int numberOfBombs;
    
    // [Row, Column]
    private List<List<Tile>> tiles;
    private boolean isGameOver = false;
    
    public Board() {
        this.rows = DEFAULT_ROW_COLUMN_SIZE;
        this.columns = DEFAULT_ROW_COLUMN_SIZE;
        this.numberOfBombs = DEFAULT_NUMBER_OF_BOMBS;
    }
    
    public Board(int rows, int columns, int numberOfBombs) {
        this.rows = rows;
        this.columns = columns;
        this.numberOfBombs = numberOfBombs;
    }
    
    public void showBoard() {
        // TODO: Make this more dynamic to scale with the rows and columns
        // TODO: Also, include color border based on something (TileState.COVERED)
        System.out.println("     1     2     3     4     5     6     7     8     9   ");
        System.out.println("  ╔═════╦═════╦═════╦═════╦═════╦═════╦═════╦═════╦═════╗");
        for(int row = 0; row < getRows(); row++) {
            System.out.print((char)('A' + row) + " ║"); // Left border
            for(int column = 0; column < getColumns(); column++) {
                System.out.printf("%s║", getTile(row, column).displayTile());
            }
            System.out.print("\n"); // newline
            if(row != getRows() - 1) {
                System.out.println("  ╠═════╬═════╬═════╬═════╬═════╬═════╬═════╬═════╬═════╣");
            }
        }
        System.out.println("  ╚═════╩═════╩═════╩═════╩═════╩═════╩═════╩═════╩═════╝");
    }
    
    public void instantiateBoard() {
        tiles = new ArrayList<>();
        for(int row = 0; row < getRows(); row++) {
            tiles.add(new ArrayList<>());
            for(int column = 0; column < getColumns(); column++) {
                tiles.get(row).add(new Tile());
                Tile tile = getTile(row, column);
                //tile.setNumberOfBombsNearby((row*getColumns()) + column);
                
                // TODO: Get rid of this as this just uncovers even tiles
                if((row*getColumns()+column) % 2 == 0)
                {
                    tile.setState(TileState.UNCOVERED);
                }
            }
        }
        
        // TODO: Get rid of these as well, they are just temporary to see how different tile display
        getTile(getRows() - 1, getColumns() - 1).setState(TileState.FLAGGED);
    
        placeBombsRandomly();
        updateTileNumbers();
    }
    
    private void placeBombsRandomly() {
        int bombCount = 0;
        while(bombCount < getNumberOfBombs()) {
            int row = randomCoord(getRows());
            int column = randomCoord(getColumns());
            
            Tile tile = getTile(row, column);
            if(!tile.isBomb()) {
                tile.setAsBomb(true);
                bombCount++;
                System.out.printf("BOMB: Row:%s, Column:%s\n", row, column);
            } else {
                System.out.println("Invalid placement, bomb present");
            }
        }
    }
    
    private int randomCoord(int limitExclusive) {
        int number = (int)(Math.random() * limitExclusive);
        return number;
    }
    
    private void updateTileNumbers() {
        for(int row = 0; row < getRows(); row++) {
            for(int column = 0; column < getColumns(); column++) {
                if(!getTile(row, column).isBomb()) {
                    int bombsNearby = GetBombsFromNeighbors(row, column);
                    getTile(row, column).setNumberOfBombsNearby(bombsNearby);
                }
            }
        }
    }

    private int GetBombsFromNeighbors(int row, int column) {
        int result = 0;
        for(Direction currentDirection : Direction.values()) {
            int newX = row + currentDirection.x;
            int newY = column + currentDirection.y;
            if(inBounds(newX, newY) && getTile(newX, newY).isBomb()) {
                result++;
            }
        }

        return result;
    }

    // row = A-Z
    // Column = 1-#
    // isClick = player clicking or flagging
    public void doAction(int row, int column, boolean isClick) {
        System.out.printf("doAction did something at %s, %s, %s\n", row, column, isClick);

        Tile chosenTile = tiles.get(row).get(column);

        if(isClick) {
            doActionClicking(row, column, chosenTile);
        }
        else {
            doActionFlagging(row, column, chosenTile);
        }
    }

    private void doActionClicking(int row, int col, Tile tile) {
        switch (tile.getCurrentState()) {
            case UNCOVERED:
                System.out.println("That tile has already been uncovered");
            case COVERED:
                tile.setState(TileState.UNCOVERED);
                //TODO: if isBomb, endGame()
            case FLAGGED:
                System.out.println("A flagged tile cannot be clicked");

        }
    }

    private void doActionFlagging(int row, int col, Tile tile) {
        switch (tile.getCurrentState()) {
            case UNCOVERED:
                System.out.println("An uncovered tile cannot be flagged");
            case COVERED:
                tile.setState(TileState.FLAGGED);
            case FLAGGED:
                tile.setState(TileState.COVERED);
        }
    }

    private boolean inBounds(int row, int column) {

        return 0 <= row && row < getRows() &&
                0 <= column && column < getColumns();
    }

    private Tile getTile(int row, int column) {
        return tiles.get(row).get(column);
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