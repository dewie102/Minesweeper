package net.retrogame;

/**
 * Custom Tuple for the Difficulty Defaults
 * This has the numberOfRows, numberOfColumns and numberOfBombs in 1 datastructure
 * to allow easier instantiation of board difficulties
 */
public class DifficultyTuple {
    public final int numberOfRows;
    public final int numberOfColumns;
    public final int numberOfBombs;
    
    public DifficultyTuple(int numberOfRows, int numberOfColumns, int numberOfBombs) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.numberOfBombs = numberOfBombs;
    }

    public boolean equals(DifficultyTuple tuple) {
        return  this.getNumberOfRows() == tuple.getNumberOfRows() &&
                this.getNumberOfColumns() == tuple.getNumberOfColumns() &&
                this.getNumberOfBombs() == tuple.getNumberOfBombs();
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public int getNumberOfBombs() {
        return numberOfBombs;
    }
}