package net.retrogame.app;

import com.apps.util.Prompter;
import net.retrogame.Board;
import net.retrogame.Player;
import net.retrogame.Tool;

import static net.retrogame.ConsoleColor.GREEN_BG;
import static net.retrogame.ConsoleColor.YELLOW_BG;
import static net.retrogame.ConsoleDisplayUtil.createStringWithColorAndReset;

public class ActionHandler {
    private final Prompter prompter;
    private final HelpMenu helpMenu;
    private final PlayerStatsDisplay statsDisplay;

    private Board board = null;
    private Player player = null;
    private boolean retry = true;

    public ActionHandler(Prompter prompter, HelpMenu helpMenu) {
        this.prompter = prompter;
        this.helpMenu = helpMenu;
        statsDisplay = new PlayerStatsDisplay(prompter);
    }

    public void promptUserForAction() {

        String userInput = null;
        boolean validInput = false;
        System.out.println("What would you like to do next?");
        System.out.println();
        System.out.println(
        "Current tool: " + (player.getCurrentTool() == Tool.FLAG ? createStringWithColorAndReset("flagging", YELLOW_BG) : createStringWithColorAndReset("clicking", GREEN_BG)) + "\n" +
        "[S] to swap to " + (player.getCurrentTool() == Tool.FLAG ? createStringWithColorAndReset("clicking", GREEN_BG) : createStringWithColorAndReset("flagging", YELLOW_BG)) + "\n" +
        "[H] for help\n" +
        "[X] to exit the game\n" +
        "[P] to view your Player stats\n" +
        "[e.g B8] coordinates to select a tile");

        System.out.println();
        userInput = prompter.prompt("> ").toUpperCase().trim();

        while(!validInput) {
            String invalidInputPrompt = "Please enter a valid input. Enter H if you need help.\n> ";
            
            // If we add more tools we will need to fix this first if statement
            if (userInput.equals("S")){
                player.swapTool();
                validInput = true;
            }
            else if (userInput.equals("H")){
                helpMenu.help();
                validInput = true;
            }
            else if (userInput.equals("X")){
                setRetry(false);
                board.setGameOver(true);
                validInput = true;
            }
            else if (userInput.equals("P")) {
                statsDisplay.show(player);
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
        int row = 0;
        int col = 0;

        boolean actionWasTaken = false;

        row = input.charAt(0) - 'A';
    
        // Substring starts at index 1 till the end of the string
        col = Integer.parseInt(input.substring(1)) - 1;

        actionWasTaken = board.doAction(row, col, player.getCurrentTool());

        return actionWasTaken;
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