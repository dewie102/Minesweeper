package net.retrogame;

class Tile {
    private TileState currentState;
    private int numberOfBombsNearby = 0;
    private boolean isBomb = false;
    
    public Tile() {
        currentState = TileState.COVERED;
    }
    
    public String displayTile() {
        String colorSetting = getCurrentState().toString();
        String numberOfBombsDisplayString = " ";
        
        if(getCurrentState().equals(TileState.UNCOVERED)) {
            if(isBomb()) {
                colorSetting = ConsoleColor.RED_BG.toString() + ConsoleColor.BLACK_FG;
            }
            
            if(getNumberOfBombsNearby() != 0) {
                numberOfBombsDisplayString = String.format("%s", getNumberOfBombsNearby());
            }
        }

        String display = colorSetting +
                "  " + // Before number space
                numberOfBombsDisplayString + // Don't display number if zero
                "  "; // After number space
        
        display += ConsoleColor.RESET_COLOR.toString() + ConsoleDisplayUtil.BOARD_BACKGROUND_COLOR;
        
        return display;
    }
    
    public TileState getCurrentState() {
        return currentState;
    }
    
    public void setState(TileState currentState) {
        this.currentState = currentState;
    }
    
    public int getNumberOfBombsNearby() {
        return numberOfBombsNearby;
    }
    
    public void setNumberOfBombsNearby(int numberOfBombsNearby) {
        this.numberOfBombsNearby = numberOfBombsNearby;
    }
    
    public boolean isBomb() {
        return isBomb;
    }
    
    public void setAsBomb(boolean bomb) {
        isBomb = bomb;
    }
    
    @Override
    public String toString() {
        return String.format("%s: currentState=%s, numberOfBombsNearby=%s, isBomb=%s",
                getClass().getSimpleName(), getCurrentState().name(), getNumberOfBombsNearby(), isBomb());
    }
}