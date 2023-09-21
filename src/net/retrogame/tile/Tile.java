package net.retrogame.tile;

import net.retrogame.ConsoleColor;
import net.retrogame.ConsoleDisplayUtil;

public class Tile {
    private Tile.State currentState;
    private int numberOfBombsNearby = 0;
    private boolean isBomb = false;
    
    public Tile() {
        currentState = Tile.State.COVERED;
    }
    
    public String displayTile() {
        String colorSetting = getCurrentState().toString();
        String numberOfBombsDisplayString = " ";
        
        if(getCurrentState().equals(Tile.State.UNCOVERED)) {
            if(isBomb()) {
                colorSetting = ConsoleColor.RED_BG.toString() + ConsoleColor.BLACK_FG;
                numberOfBombsDisplayString = "B";
            }
            
            if(getNumberOfBombsNearby() != 0) {
                numberOfBombsDisplayString = String.format("%s", getNumberOfBombsNearby());
            }
        }
        
        if(getCurrentState().equals(Tile.State.FLAGGED)) {
            numberOfBombsDisplayString = "F";
        }

        String display = colorSetting +
                "  " + // Before number space
                numberOfBombsDisplayString + // Don't display number if zero
                "  "; // After number space
        
        display += ConsoleColor.RESET_COLOR.toString() + ConsoleDisplayUtil.BOARD_BACKGROUND_COLOR;
        
        return display;
    }
    
    public Tile.State getCurrentState() {
        return currentState;
    }
    
    public void setState(Tile.State currentState) {
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
    
    // Make a smart enum to include all color properties
    public enum State {
        COVERED(ConsoleColor.RESET_COLOR, ConsoleColor.GRAY_BG),
        UNCOVERED(ConsoleColor.BLACK_FG, ConsoleColor.GREEN_BG),
        FLAGGED(ConsoleColor.BLACK_FG, ConsoleColor.YELLOW_BG);
        
        private final ConsoleColor foreground;
        private final ConsoleColor background;
        
        State(ConsoleColor foreground, ConsoleColor background) {
            this.foreground = foreground;
            this.background = background;
        }
        
        @Override
        public String toString() {
            return foreground.toString() + background;
        }
    }
}