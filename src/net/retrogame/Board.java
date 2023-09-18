package net.retrogame;

import java.util.*;

public class Board {
    private static final int DEFAULT_ROW_COLUMN_SIZE = 9;
    private static final int DEFAULT_NUMBER_OF_BOMBS = 10;
    private static final int BOARD_DISPLAY_SPACE = 5;
    private static final int BORDER_SPACING_NO_NUMBER = 2;
    
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
        // TODO: Include color border based on something (TileState.COVERED)
        String middle = buildBoardMiddle();

        System.out.println();
        System.out.println(buildBoardHeader());
        System.out.println(buildBoardTop());

        for(int row = 0; row < getRows(); row++) {
            System.out.print((char)('A' + row) + " ║"); // Left border
            for(int column = 0; column < getColumns(); column++) {
                // displayTile sets color based on state and sets the appropriate spaces
                System.out.printf("%s║", getTile(row, column).displayTile());
            }
            System.out.print("\n"); // newline
            if(row != getRows() - 1) {
                System.out.println(middle);
            }
        }
        System.out.println(buildBoardBottom());
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
                /*if((row*getColumns()+column) % 2 == 0)
                {
                    tile.setState(TileState.UNCOVERED);
                }*/
            }
        }
        
        // TODO: Get rid of these as well, they are just temporary to see how different tile display
        //getTile(getRows() - 1, getColumns() - 1).setState(TileState.FLAGGED);
    
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
    
    private void processPossibleIslandOfZeros(int row, int column) {
        // Create a LinkedList (FIFO) of tiles potentially part of the island of zeros
        // Put the first one in the list to process it
        Queue<TileTuple> tilesToProcess = new LinkedList<>();
        tilesToProcess.add(new TileTuple(getTile(row, column), row, column));
        
        // HashSet of all the tiles we have already visited so we don't run into a loop
        Set<Tile> visited = new HashSet<>();
        
        while(!tilesToProcess.isEmpty()) {
            // Remove the top tile with coordinates and separate the pieces
            TileTuple tuple = tilesToProcess.remove();
            Tile currentTile = tuple.tile;
            int currentRow = tuple.row;
            int currentColumn = tuple.column;
    
            // If we have not visited this tile and we are part of the 0 island proceed
            if(!visited.contains(currentTile) && currentTile.getNumberOfBombsNearby() == 0) {
                currentTile.setState(TileState.UNCOVERED); // Set tile to uncovered
                visited.add(currentTile); // Add tile to visited
    
                // Check all 8 directions and if it is in bounds, add it to the list to be processed
                for (Direction currentDirection : Direction.values()) {
                    int newX = currentRow + currentDirection.x;
                    int newY = currentColumn + currentDirection.y;
                    if (inBounds(newX, newY)) {
                        tilesToProcess.add(new TileTuple(getTile(newX, newY), newX, newY));
                    }
                }
            } else if(!visited.contains(currentTile)){
                // else we visited a cell that we haven't visited and the numberOfBombsNearby is not zero, so uncover it
                currentTile.setState(TileState.UNCOVERED);
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
                break;
            case COVERED:
                if(tile.isBomb()) {
                    setGameOver(true);
                } else {
                    if (tile.getNumberOfBombsNearby() == 0) {
                        processPossibleIslandOfZeros(row, col);
                    }
                }
                
                tile.setState(TileState.UNCOVERED);
                break;
            case FLAGGED:
                System.out.println("A flagged tile cannot be clicked");
                break;
        }
    }

    private void doActionFlagging(int row, int col, Tile tile) {
        switch (tile.getCurrentState()) {
            case UNCOVERED:
                System.out.println("An uncovered tile cannot be flagged");
                break;
            case COVERED:
                tile.setState(TileState.FLAGGED);
                break;
            case FLAGGED:
                tile.setState(TileState.COVERED);
                break;
        }
    }

    private boolean inBounds(int row, int column) {

        return 0 <= row && row < getRows() &&
                0 <= column && column < getColumns();
    }

    private Tile getTile(int row, int column) {
        return tiles.get(row).get(column);
    }

    private String buildBoardHeader() {
        //String header = String.format("     1     2     3     4     5     6     7     8     9   ");
        String intersectionSpace = " ";
        StringBuilder header = new StringBuilder();
        // This pushes over everything for the Rows display
        header.append(" ".repeat(BORDER_SPACING_NO_NUMBER)); // 2
        header.append(intersectionSpace); // 1

        // Set row to 1 and <= getRows as we want numbers 1-9 not 0-8
        for(int row = 1; row <= getRows(); row++) {
            header.append(" ".repeat(BORDER_SPACING_NO_NUMBER)); // 2
            header.append(row); // number
            if(row >= 10) {
                header.append(" ".repeat(BORDER_SPACING_NO_NUMBER - 1)); // 1
            } else {
                header.append(" ".repeat(BORDER_SPACING_NO_NUMBER)); // 2
            }
            header.append(intersectionSpace); // 1
        }

        return header.toString();
    }

    private String buildBoardTop() {
        //String top = String.format("  ╔═════╦═════╦═════╦═════╦═════╦═════╦═════╦═════╦═════╗");

        StringBuilder top = new StringBuilder();
        top.append(" ".repeat(BORDER_SPACING_NO_NUMBER));
        top.append("╔");
        top.append("═".repeat(BOARD_DISPLAY_SPACE));

        // 1 less then getRows as we already did the first one
        for(int row = 0; row < getRows() - 1; row++) {
            top.append("╦");
            top.append("═".repeat(BOARD_DISPLAY_SPACE));
        }

        top.append("╗");

        return top.toString();
    }

    private String buildBoardMiddle() {
        // String middle = String.format("  ╠═════╬═════╬═════╬═════╬═════╬═════╬═════╬═════╬═════╣");

        StringBuilder middle = new StringBuilder();

        middle.append(" ".repeat(BORDER_SPACING_NO_NUMBER));
        middle.append("╠");
        middle.append("═".repeat(BOARD_DISPLAY_SPACE));

        // 1 less then getRows as we already did the first one
        for(int row = 0; row < getRows() - 1; row++) {
            middle.append("╬");
            middle.append("═".repeat(BOARD_DISPLAY_SPACE));
        }

        middle.append("╣");

        return middle.toString();
    }

    private String buildBoardBottom() {
        //String top = String.format("  ╚═════╩═════╩═════╩═════╩═════╩═════╩═════╩═════╩═════╝");

        StringBuilder top = new StringBuilder();
        top.append(" ".repeat(BORDER_SPACING_NO_NUMBER));
        top.append("╚");
        top.append("═".repeat(BOARD_DISPLAY_SPACE));

        // 1 less then getRows as we already did the first one
        for(int row = 0; row < getRows() - 1; row++) {
            top.append("╩");
            top.append("═".repeat(BOARD_DISPLAY_SPACE));
        }

        top.append("╝");

        return top.toString();
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