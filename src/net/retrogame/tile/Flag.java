package net.retrogame.tile;

import net.retrogame.Board;

public class Flag implements Action {
    
    @Override
    public boolean execute(TileTuple tileInfo, Board board) {
        Tile tile = tileInfo.tile;
        
        boolean done = false;
        
        switch (tile.getCurrentState()) {
            case UNCOVERED:
                System.out.println();
                System.out.println("An uncovered tile cannot be flagged. Please enter the coordinates of a different tile.");
                break;
            case COVERED:
                tile.setState(TileState.FLAGGED);
                board.decrementFlagCount();
                done = true;
                break;
            case FLAGGED:
                tile.setState(TileState.COVERED);
                board.incrementFlagCount();
                done = true;
                break;
        }
        
        return done;
    }
}