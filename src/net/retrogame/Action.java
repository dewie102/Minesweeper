package net.retrogame;

public interface Action {
    
    boolean performAction(TileTuple tileInfo, Board board);
}
