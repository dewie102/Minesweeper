package net.retrogame.tile;

/**
 * Custom Tuple for the Tiles
 * This has the tile, row and column in 1 datastructure
 * to keep track of where things are in the board
 */
public class TileTuple {
    
    public final Tile tile;
    public final int row;
    public final int column;
    
    public TileTuple(Tile tile, int row, int column) {
        this.tile = tile;
        this.row = row;
        this.column = column;
    }
}