package net.retrogame;

class Tile {
    private TileState currentState;
    private int howManyNeighborsHaveBombs = 0;
    private boolean isBomb = false;
    
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
    
    public int getHowManyNeighborsHaveBombs() {
        return howManyNeighborsHaveBombs;
    }
    
    public void setHowManyNeighborsHaveBombs(int howManyNeighborsHaveBombs) {
        this.howManyNeighborsHaveBombs = howManyNeighborsHaveBombs;
    }
    
    public boolean isBomb() {
        return isBomb;
    }
    
    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }
    
    @Override
    public String toString() {
        return String.format("%s: currentState:%s", getClass().getSimpleName(), getCurrentState());
    }
}