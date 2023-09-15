package net.retrogame;

class Tile {
    private TileState currentState;
    
    public void displayTile() {
    
    }
    
    public TileState getCurrentState() {
        return currentState;
    }
    
    public void setCurrentState(TileState currentState) {
        this.currentState = currentState;
    }
}