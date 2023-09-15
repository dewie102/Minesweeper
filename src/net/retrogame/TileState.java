package net.retrogame;

// Make a smart enum to include all color properties
enum TileState {
    COVERED(ConsoleColor.UNSET_BG, ConsoleColor.UNSET_BG),
    UNCOVERED(ConsoleColor.BLACK_FG, ConsoleColor.GREEN_BG),
    FLAGGED(ConsoleColor.BLACK_FG, ConsoleColor.YELLOW_BG);
    
    private final ConsoleColor foreground;
    private final ConsoleColor background;
    
    TileState(ConsoleColor foreground, ConsoleColor background) {
        this.foreground = foreground;
        this.background = background;
    }
    
    @Override
    public String toString() {
        return foreground.toString() + background;
    }
}