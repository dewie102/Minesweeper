package net.retrogame;

public class ConsoleDisplayUtil {
    private static final int BOARD_DISPLAY_SPACE = 5;
    private static final int BORDER_SPACING_NO_NUMBER = 2;
    static final ConsoleColor BOARD_BACKGROUND_COLOR = ConsoleColor.GRAY_BG;
    
    private ConsoleDisplayUtil() {
    }
    
    static String buildBoardHeader(int columns) {
        //String header = String.format("     1     2     3     4     5     6     7     8     9   ");
        String intersectionSpace = " ";
        StringBuilder header = new StringBuilder();
        // This pushes over everything for the Rows display
        header.append(" ".repeat(BORDER_SPACING_NO_NUMBER)); // 2
        header.append(intersectionSpace); // 1
        
        // Set row to 1 and <= getRows as we want numbers 1-9 not 0-8
        for(int column = 1; column <= columns; column++) {
            header.append(" ".repeat(BORDER_SPACING_NO_NUMBER)); // 2
            header.append(column); // number
            if(column >= 10) {
                header.append(" ".repeat(BORDER_SPACING_NO_NUMBER - 1)); // 1
            } else {
                header.append(" ".repeat(BORDER_SPACING_NO_NUMBER)); // 2
            }
            header.append(intersectionSpace); // 1
        }
        
        header.append(" ".repeat(BORDER_SPACING_NO_NUMBER));
        
        return BOARD_BACKGROUND_COLOR + header.toString() + ConsoleColor.RESET_COLOR;
    }
    
    static String buildBoardTop(int columns) {
        //String top = String.format("  ╔═════╦═════╦═════╦═════╦═════╦═════╦═════╦═════╦═════╗");
        
        StringBuilder top = new StringBuilder();
        top.append(" ".repeat(BORDER_SPACING_NO_NUMBER));
        top.append("╔");
        top.append("═".repeat(BOARD_DISPLAY_SPACE));
        
        // 1 less then getRows as we already did the first one
        for(int column = 0; column < columns - 1; column++) {
            top.append("╦");
            top.append("═".repeat(BOARD_DISPLAY_SPACE));
        }
        
        top.append("╗");
        
        top.append(" ".repeat(BORDER_SPACING_NO_NUMBER));
        
        return BOARD_BACKGROUND_COLOR + top.toString() + ConsoleColor.RESET_COLOR;
    }
    
    static String buildBoardMiddle(int columns) {
        // String middle = String.format("  ╠═════╬═════╬═════╬═════╬═════╬═════╬═════╬═════╬═════╣");
        
        StringBuilder middle = new StringBuilder();
        
        middle.append(" ".repeat(BORDER_SPACING_NO_NUMBER));
        middle.append("╠");
        middle.append("═".repeat(BOARD_DISPLAY_SPACE));
        
        // 1 less then getRows as we already did the first one
        for(int column = 0; column < columns - 1; column++) {
            middle.append("╬");
            middle.append("═".repeat(BOARD_DISPLAY_SPACE));
        }
        
        middle.append("╣");
        
        middle.append(" ".repeat(BORDER_SPACING_NO_NUMBER));
        
        return BOARD_BACKGROUND_COLOR + middle.toString() + ConsoleColor.RESET_COLOR;
    }
    
    static String buildBoardBottom(int columns) {
        //String bottom = String.format("  ╚═════╩═════╩═════╩═════╩═════╩═════╩═════╩═════╩═════╝");
        
        StringBuilder bottom = new StringBuilder();
        bottom.append(" ".repeat(BORDER_SPACING_NO_NUMBER));
        bottom.append("╚");
        bottom.append("═".repeat(BOARD_DISPLAY_SPACE));
        
        // 1 less then getRows as we already did the first one
        for(int column = 0; column < columns - 1; column++) {
            bottom.append("╩");
            bottom.append("═".repeat(BOARD_DISPLAY_SPACE));
        }
        
        bottom.append("╝");
        
        bottom.append(" ".repeat(BORDER_SPACING_NO_NUMBER));
        
        return BOARD_BACKGROUND_COLOR + bottom.toString() + ConsoleColor.RESET_COLOR;
    }
    
    static String buildBoardStats(int flagCount, int elapsedTime) {
        return String.format("Flags: %s\nTimer: %s", flagCount, elapsedTime);
    }
    
    static String buildBorderWithNoNumber() {
        return BOARD_BACKGROUND_COLOR + " ". repeat(BORDER_SPACING_NO_NUMBER) + ConsoleColor.RESET_COLOR;
    }
    
    public static String createStringWithColorAndReset(String string, ConsoleColor color) {
        return color + ConsoleColor.BLACK_FG.toString() + string + ConsoleColor.RESET_COLOR;
    }
    
    static String createStringWithDefaultColorAndReset(String string) {
        return BOARD_BACKGROUND_COLOR + string + ConsoleColor.RESET_COLOR;
    }
}