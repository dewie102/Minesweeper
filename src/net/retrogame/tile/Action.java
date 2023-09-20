package net.retrogame.tile;

import net.retrogame.Board;

public interface Action {
    
    boolean execute(TileTuple tileInfo, Board board);
}
