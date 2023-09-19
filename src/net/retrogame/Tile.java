package net.retrogame;

class Tile {
    private TileState currentState;
    private int numberOfBombsNearby = 0;
    private boolean isBomb = false;
    
    public Tile() {
        currentState = TileState.COVERED;
    }
    
    public String displayTile() {
        String tileBackground = ConsoleColor.BLACK_BG.toString();
        String colorSetting = getCurrentState().toString();
        String numberOfBombsDisplayString = " ";
        
        if(getCurrentState().equals(TileState.UNCOVERED)) {
            // TODO: Move this under a check for uncovered as we don't want to give away the bombs
            if(isBomb()) {
                colorSetting = ConsoleColor.RED_BG.toString() + ConsoleColor.BLACK_FG;
            }
            
            if(getNumberOfBombsNearby() != 0) {
                numberOfBombsDisplayString = String.format("%s", getNumberOfBombsNearby());
            }
        }
        
        // TODO: Remove this temporary variable (tempSpacing) when we actually use bomb variable
        //String tempSpacing = (getNumberOfBombsNearby() >= 10 ? " " : "  ");
        //String display = colorSetting + "  " + getNumberOfBombsNearby() + tempSpacing;

        String display = colorSetting +
                "  " + // Before number space
                numberOfBombsDisplayString + // Don't display number if zero
                "  "; // After number space
        
        //display += ConsoleColor.RESET_COLOR;
        
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