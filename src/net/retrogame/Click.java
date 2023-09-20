package net.retrogame;

class Click implements Action {
    
    @Override
    public boolean performAction(TileTuple tileInfo, Board board) {
        Tile tile = tileInfo.tile;
        
        boolean done = false;
        
        switch (tile.getCurrentState()) {
            case UNCOVERED:
                System.out.println();
                System.out.println("That tile has already been uncovered. Please enter the coordinates of a different tile.");
                break;
            case COVERED:
                if (tile.isBomb()) {
                    board.setGameOver(true);
                } else {
                    if (tile.getNumberOfBombsNearby() == 0) {
                        int uncovered = board.processPossibleIslandOfZeros(tileInfo);
                        board.decreaseRemainingTilesByAmount(uncovered);
                    } else {
                        board.decreaseRemainingTilesByAmount(1);
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
}