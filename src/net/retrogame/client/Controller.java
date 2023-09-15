package net.retrogame.client;

import com.apps.util.Prompter;
import net.retrogame.Board;
import java.util.Locale;
import java.util.Scanner;

class Controller {
    private final static Prompter prompter = new Prompter(new Scanner(System.in));

    private boolean retry = true;
    private Board board;
    private boolean playerIsClicking = true; //TODO: move this to a Player class if we need one


    public void newGame() {

        welcome();
        createDefaultBoard();
        while (!board.isGameOver()) {
            play();
        }
        goodbye(); //TODO: swap these, and only call goodbye if retry is F?
        promptUserForRetry();

    }

    private void welcome() {
        System.out.println("Welcome to MineSweeper!");
        //TODO: ascii art greeting
    }

    // TODO: asking user for input
    private void createDefaultBoard() {
        // Will ask user at some point but for now take defaults
        board = new Board(9, 9, 9);
        board.instantiateBoard();
    }

    private void play() {
        board.showBoard();
        promptUserForAction();
    }

    //TODO: remove repetition in Strings
    private void promptUserForAction() {
        String userInput = null;
        boolean validInput = false;
        System.out.println("What would you like to do next?");

        System.out.println("You are currently "+toolVerbPresentTense()+".");
        userInput = prompter.prompt("Enter the coordinates of the tile you would like to "+ toolVerb() +
                " in column-row order with no spaces (e.g. 8B)," +
                " or enter [S] to [S]wap over to "+oppositeToolVerbPresentTense()+" mode.").toUpperCase().trim();

        while(!validInput) {
            if (userInput.equals("S")){
                setPlayerIsClicking(!isPlayerClicking());
                validInput = true;
            }
            else if (userInput.length()==2 || userInput.length()==3){//TODO: make sure max columns is 99 and max rows is 26

                if (areValidCoords(userInput)) {
                    board.doAction(userInput);
                    validInput = true;
                }
                else {
                    userInput = prompter.prompt("Please enter a valid input. Valid inputs are " +
                            "the coordinates of the tile you would like to "+ toolVerb() +
                            " in column-row order with no spaces (e.g. 8B)," +
                            " or [S] to [S]wap over to "+toolVerbPresentTense()+" mode.").toUpperCase().trim();
                }
            }
            else {
                userInput = prompter.prompt("Please enter a valid input. Valid inputs are " +
                        "the coordinates of the tile you would like to "+ toolVerb() +
                        " in column-row order with no spaces (e.g. 8B)," +
                        " or [S] to [S]wap over to "+toolVerbPresentTense()+" mode.").toUpperCase().trim();
            }
        }
    }

    /*
     * Valid coordinates are anything that is within the row/column set.
     * If the coord points to a Tile that has already been clicked that will be handed
     * by doAction
     */
    private boolean areValidCoords(String input) {

        boolean isValid = false;
        String firstChar = null;
        String secondChar = null;
        String thirdChar = null;


        //TODO: determine how to interpret whether coords are valid or not
        if (input.length()==2) {
            firstChar = String.valueOf(input.charAt(0)); //should be at number between 1 and the num columns
            secondChar = String.valueOf(input.charAt(1)); //should be a letter between A and the num rows
        }

        if (input.length()==3) {
            String row = input.substring(0,1);
            String col = String.valueOf(input.charAt(3));
        }

        return isValid;
    }


    private String toolVerbPresentTense() {
        String tool = null;
        if (isPlayerClicking()) {
            tool = "clicking";
        }
        else {
            tool = "flagging";
        }
        return tool;
    }

    private String oppositeToolVerbPresentTense() {
        String tool = null;
        if (!isPlayerClicking()) {
            tool = "clicking";
        }
        else {
            tool = "flagging";
        }
        return tool;
    }

    private String toolVerb() {
        String tool = null;
        if (isPlayerClicking()) {
            tool = "click";
        }
        else {
            tool = "flag";
        }
        return tool;
    }

    private void goodbye() {
        System.out.println("Thank you for playing!");
        //TODO: ascii art sendoff
    }

    private void promptUserForRetry() {
        String userInput = null;
        boolean validInput = false;

        userInput = prompter.prompt("Would you like to play again? Enter [Y] to start a new game" +
                "or [N]o to quit.");
        while(!validInput) {
            if (userInput.toUpperCase().trim().equals("Y")) {
                setRetry(true);
                validInput = true;
            } else if (userInput.toUpperCase().trim().equals("N")) {
                setRetry(false);
                validInput = true;
            } else {
                prompter.prompt("Please enter a valid option: [Y] to start a new game, or [N] to quit.");
            }
        }
    }

    public boolean willRetry() {
        return retry;
    }

    private void setRetry(boolean retry) {
        this.retry = retry;
    }

    public boolean isPlayerClicking() {
        return playerIsClicking;
    }

    public void setPlayerIsClicking(boolean playerIsClicking) {
        this.playerIsClicking = playerIsClicking;
    }
}