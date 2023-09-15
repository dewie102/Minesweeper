package net.retrogame;

class Tile {
    private TileState currentState;
    private int numberOfBombsNearby = 0;
    private boolean isBomb = false;
    
    public Tile() {
        currentState = TileState.COVERED;
    }
    
    public String displayTile() {
        String colorSetting = "";
        switch(getCurrentState()){
            case COVERED:
                colorSetting = ConsoleColor.UNSET_BG.toString();
                break;
            case UNCOVERED:
                colorSetting = ConsoleColor.GRAY_BG.toString() + ConsoleColor.GREEN_FG;
                break;
            case FLAGGED:
                colorSetting = ConsoleColor.GRAY_BG.toString() + ConsoleColor.YELLOW_FG;
                break;
        }
        
        if(isBomb()) {
            colorSetting = ConsoleColor.RED_BG.toString() + ConsoleColor.BLACK_FG;
        }
        
        // TODO: Remove this temporary variable (tempSpacing) when we actually use bomb variable
        String tempSpacing = (getNumberOfBombsNearby() >= 10 ? " " : "  ");
        
        String display = colorSetting + "  " + getNumberOfBombsNearby() + tempSpacing;
        
        display += ConsoleColor.UNSET_BG;
        
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
        return String.format("%s: currentState:%s", getClass().getSimpleName(), getCurrentState());
    }
}