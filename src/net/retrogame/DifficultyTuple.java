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
}