package net.retrogame;

public enum ConsoleColor {
    RESET_COLOR("\u001B[0m"),
    BLACK_FG("\u001B[30m"),
    BLACK_BG("\u001B[40m"),
    RED_FG("\u001B[31m"),
    RED_BG("\u001B[41m"),
    GREEN_FG("\u001B[32m"),
    GREEN_BG("\u001B[42m"),
    YELLOW_FG("\u001B[33m"),
    YELLOW_BG("\u001B[43m"),
    WHITE_FG("\u001B[37m"),
    WHITE_BG("\u001B[47m"),
    GRAY_FG("\u001B[90m"),
    GRAY_BG("\u001B[100m");
    
    public String colorString;
    
    ConsoleColor(String colorString) {
        this.colorString = colorString;
    }
    
    @Override
    public String toString() {
        return colorString;
    }
}