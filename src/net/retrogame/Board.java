package net.retrogame;

import static net.retrogame.ConsoleDisplayUtil.*;

import java.util.*;

public class Board {
    private static final int DEFAULT_ROW_COLUMN_SIZE = 9;
    private static final int DEFAULT_NUMBER_OF_BOMBS = 10;
    
    private final int rows;
    private final int columns;
    private final int numberOfBombs;
    private final Timer playTimer = new Timer();
    
    // [Row, Column]
    private List<List<Tile>> tiles;
    private boolean isGameOver = false;
    private boolean gameWon = false;
    private boolean madeFirstClick = false;

    private int remainingTiles;
    private int flagCount;

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
        String middle = buildBoardMiddle(getColumns()); // Build the middle row once since it doesn't change
        
        System.out.println(); // Blank line for clarity
        System.out.println(buildBoardStats(getFlagCount(), playTimer.getElapsedTimeSinceStartInSeconds()));
        System.out.println(buildBoardHeader(getColumns())); // Display the header
        System.out.println(buildBoardTop(getColumns())); // Display the column numbers
        
        for(int row = 0; row < getRows(); row++) {
            // Left border with character
            System.out.print(createStringWithDefaultColorAndReset((char)('A' + row) + " ║"));
            for(int column = 0; column < getColumns(); column++) {
                // displayTile sets color based on state and sets the appropriate spaces
                System.out.printf("%s║", getTile(row, column).displayTile());
            }
            // Right Border with no letters/ numbers
            System.out.println(buildBorderWithNoNumber());
            
            // Display the middle string if we are not the last row
            if(row != getRows() - 1) {
                System.out.println(middle);
            }
        }
        // Display the last row
        System.out.println(buildBoardBottom(getColumns()));
    }
    
    public void instantiateBoard() {
        tiles = new ArrayList<>();
        for(int row = 0; row < getRows(); row++) {
            tiles.add(new ArrayList<>());
            for(int column = 0; column < getColumns(); column++) {
                tiles.get(row).add(new Tile());
            }
        }

        placeBombsRandomly();
        updateTileNumbers();
        initializeRemainingTiles();
        setFlagCount(getNumberOfBombs());
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
                System.out.printf("BOMB: Row:%s, Column:%s\n", (char)(row + 'A'), column + 1);
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
    public boolean doAction(int row, int column, boolean isFlag) {

        Tile chosenTile = tiles.get(row).get(column);
        boolean done = false;

        if(!isFlag) {
            done = doActionClicking(row, column, chosenTile);
        }
        else {
            done = doActionFlagging(row, column, chosenTile);
        }
        checkForWinState();

        return done;
    }

    private boolean doActionClicking(int row, int col, Tile tile) {
        boolean done = false;
        switch (tile.getCurrentState()) {
            case UNCOVERED:
                System.out.println();
                System.out.println("That tile has already been uncovered. Please enter the coordinates of a different tile.");
                break;
            case COVERED:
                if(!madeFirstClick) {
                    playTimer.startStopWatch();
                    madeFirstClick = true;
                }
                
                if (tile.isBomb()) {
                    setGameOver(true);
                    playTimer.stopStopWatch();
                } else {
                    if (tile.getNumberOfBombsNearby() == 0) {
                        int uncovered = processPossibleIslandOfZeros(row, col);
                        remainingTiles -= uncovered;
                    } else {
                        remainingTiles--;
                    }
                }
                tile.setState(TileState.UNCOVERED);
                done = true;
                break;
            case FLAGGED:
                System.out.println();
                System.out.println("A flagged tile cannot be clicked. Please enter the coordinates of a different tile.");
                break;
        }
        return done;
    }


    private boolean doActionFlagging(int row, int col, Tile tile) {
        boolean done = false;
        switch (tile.getCurrentState()) {
           case UNCOVERED:
               System.out.println();
               System.out.println("An uncovered tile cannot be flagged. Please enter the coordinates of a different tile.");
               break;
           case COVERED:
               tile.setState(TileState.FLAGGED);
               done = true;
               decrementFlagCount();
               break;
           case FLAGGED:
               tile.setState(TileState.COVERED);
               done = true;
               break;
            }

        return done;
    }
    
    
    private int processPossibleIslandOfZeros(int row, int column) {
        int numberOfTilesUncovered = 0;
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
            
            if(!visited.contains(currentTile) && currentTile.getCurrentState() == TileState.COVERED) {
                // If we are part of the 0 island proceed by adding the proper neighbors into the list
                if (currentTile.getNumberOfBombsNearby() == 0) {
                    // Check all 8 directions and if it is in bounds, add it to the list to be processed
                    for (Direction currentDirection : Direction.values()) {
                        int newX = currentRow + currentDirection.x;
                        int newY = currentColumn + currentDirection.y;
                        if (inBounds(newX, newY)) {
                            tilesToProcess.add(new TileTuple(getTile(newX, newY), newX, newY));
                        }
                    }
                }
    
                visited.add(currentTile); // Add tile to visited
                currentTile.setState(TileState.UNCOVERED); // Uncover tile
                numberOfTilesUncovered++; // increment number of tiles uncovered
            }
        }
        
        return numberOfTilesUncovered;
    }

    public boolean inBounds(int row, int column) {

        return 0 <= row && row < getRows() &&
                0 <= column && column < getColumns();
    }

    // Package private for testing?
    Tile getTile(int row, int column) {
        return tiles.get(row).get(column);
    }

    private void initializeRemainingTiles() {
        this.remainingTiles = (getRows() * getColumns()) - getNumberOfBombs();
    }

    private void checkForWinState() {
        if(remainingTiles == 0) {
            setGameWon(true);
            setGameOver(true);
            playTimer.stopStopWatch();
            System.out.println("You WIN!");
        }
    }
    
    public long getPlayTime() {
        return playTimer.getRecordedTimeInSeconds();
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

    public boolean wasGameWon() {
        return gameWon;
    }

    private void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

    // package private for testing
    int getRemainingTiles() {
        return remainingTiles;
    }

    public int getFlagCount() {
        return flagCount;
    }

    private void setFlagCount(int flagCount) {
        this.flagCount = flagCount;
    }

    private void decrementFlagCount() {
        setFlagCount(getFlagCount() - 1);
    }
}