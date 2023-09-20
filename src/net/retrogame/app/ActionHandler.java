package net.retrogame.app;

import com.apps.util.Prompter;
import net.retrogame.Board;
import net.retrogame.ConsoleColor;
import net.retrogame.Player;
import net.retrogame.TileState;

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

        String userInput = null;
        boolean validInput = false;
        System.out.println("What would you like to do next?");
        System.out.println();
        System.out.println(
        "Current tool: " + (player.isUsingFlagTool() ? "flagging" : "clicking") + "\n" +
        "[S] to swap to " + (player.isUsingFlagTool() ? "clicking" : "flagging") + "\n" +
        "[H] for help\n" +
        "[X] to exit the game\n" +
        "[e.g B8] coordinates to select a tile");

        System.out.println();
        userInput = prompter.prompt(">").toUpperCase().trim();

        while(!validInput) {
            String invalidInputPrompt = "Please enter a valid input. Enter H if you need help.";
            
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
            else if (userInput.length()==2 || userInput.length()==3){

                if (areValidCoords(userInput)) {
                    boolean actionTaken = doAction(userInput);
                    if (actionTaken) {
                        validInput = true;
                    }
                    else {
                        userInput = prompter.prompt(">").toUpperCase().trim();
                    }
                }
                else {
                    userInput = prompter.prompt(invalidInputPrompt).toUpperCase().trim();
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

        boolean done = false;

        row = input.charAt(0) - 'A';
    
        // Substring starts at index 1 till the end of the string
        col = Integer.parseInt(input.substring(1)) - 1;

        done = board.doAction(row, col, player.isUsingFlagTool());

        return done;
    }

    private boolean areValidCoords(String input) {

        boolean isValid = false;

        if (input.length()==2) {
            isValid = areValidCoordsLength2(input);
        }

        if (input.length()==3) {
            isValid = areValidCoordsLength3(input);
        }

        return isValid;
    }

    // ZR TODO: Could use the board.inBound(row, column) to validate the ints are in the boundaries
    private boolean areValidCoordsLength2(String input) {
        boolean inputIsValid = true;
        Character firstChar = input.charAt(0); //should be LETTER - rows
        Character secondChar = input.charAt(1); //should be NUMBER - cols

        if ((!Character.isDigit(secondChar)) ||
            (!Character.isAlphabetic(firstChar)) ||
            (!board.inBounds((firstChar - 'A'),((int) secondChar - '1') ) )
           ){

            inputIsValid = false;
        }

        return inputIsValid;
    }

    private boolean areValidCoordsLength3(String input) {
        boolean inputIsValid = true;
        String secondTwoChars = input.substring(1,2);
        Character firstChar = input.charAt(0);

        if((!secondTwoChars.matches("\\d{2}")) ||
           (!Character.isAlphabetic(firstChar)) ||
           (!board.inBounds(firstChar - 'A', Integer.parseInt(secondTwoChars) - '1') )
          ){
            inputIsValid = false;
        }

        return inputIsValid;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean willRetry() {
        return retry;
    }

    public void setRetry(boolean retry) {
        this.retry = retry;
    }
}