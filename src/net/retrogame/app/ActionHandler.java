package net.retrogame.app;

import com.apps.util.Prompter;
import net.retrogame.Board;
import net.retrogame.Player;
import net.retrogame.tile.Tool;

import static net.retrogame.ConsoleColor.GREEN_BG;
import static net.retrogame.ConsoleColor.YELLOW_BG;
import static net.retrogame.ConsoleDisplayUtil.createStringWithColorAndReset;

public class ActionHandler {
    private final Prompter prompter;
    private final HelpMenu helpMenu;


    private Board board = null;
    private Player player = null;
    private boolean retry = true;

    public ActionHandler(Prompter prompter, HelpMenu helpMenu) {
        this.prompter = prompter;
        this.helpMenu = helpMenu;
    }

    public void promptUserForAction() {
        boolean validInput = false;
        
        String flagging = createStringWithColorAndReset("flagging", YELLOW_BG);
        String clicking = createStringWithColorAndReset("clicking", GREEN_BG);
        
        System.out.println("What would you like to do next?");
        System.out.println();
        
        System.out.println(
        "Current tool: " + (player.getCurrentTool() == Tool.FLAG ? flagging : clicking) + "\n" +
        "[S] to swap to " + (player.getCurrentTool() == Tool.FLAG ? clicking :flagging) + "\n" +
        "[H] for help\n" +
        "[X] to exit the game\n" +
        "[e.g B8] coordinates to select a tile");

        System.out.println();
        String userInput = prompter.prompt("> ").toUpperCase().trim();

        while(!validInput) {
            String invalidInputPrompt = "Please enter a valid input. Enter H if you need help.\n> ";
            
            // If we add more tools we will need to fix this first if statement
            if ("S".equals(userInput)){
                player.swapTool();
                validInput = true;
            }
            else if ("H".equals(userInput)){
                helpMenu.help();
                validInput = true;
            }
            else if ("X".equals(userInput)){
                setRetry(false);
                board.setGameOver();
                validInput = true;
            }
            else if (areValidCoordinates(userInput)) {
                boolean actionTaken = doAction(userInput);
                if (actionTaken) {
                    validInput = true;
                }
                else {
                    userInput = prompter.prompt("> ").toUpperCase().trim();
                }
            }
            else {
                userInput = prompter.prompt(invalidInputPrompt).toUpperCase().trim();
            }
        }
    }

    private boolean doAction(String input) {
        int row = input.charAt(0) - 'A';
        // Substring starts at index 1 till the end of the string
        int col = Integer.parseInt(input.substring(1)) - 1;

        return board.doAction(row, col, player.getCurrentTool());
    }

    private boolean areValidCoordinates(String input) {

        boolean isValid = false;
    
        if(input.matches("[A-Za-z]\\d{1,2}")) {
            char rowString = input.substring(0, 1).charAt(0);
            String columnString = input.substring(1);
        
            int row = rowString - 'A';
            int column = Integer.parseInt(columnString) - 1;
        
            if(board.inBounds(row, column)) {
                isValid = true;
            }
        }

        return isValid;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private Player getPlayer() {
        return player;
    }

    public boolean willRetry() {
        return retry;
    }

    public void setRetry(boolean retry) {
        this.retry = retry;
    }
}