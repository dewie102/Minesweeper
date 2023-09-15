package net.retrogame;

class Tile {
    private TileState currentState;
    
    public Tile() {
        currentState = TileState.COVERED;
    }
    
    public void displayTile() {
    
    }
    
    public TileState getCurrentState() {
        return currentState;
    }
    
    public void setCurrentState(TileState currentState) {
        this.currentState = currentState;
    }
    
    @Override
    public String toString() {
        return String.format("%s: currentState:%s", getClass().getSimpleName(), getCurrentState());
    }
}