package net.retrogame;

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