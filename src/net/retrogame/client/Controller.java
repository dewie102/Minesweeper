package net.retrogame.client;

import com.apps.util.Prompter;
import net.retrogame.Board;
import java.util.Locale;
import java.util.Scanner;
import com.apps.util.Console;

//TODO: include some

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
        promptUserForRetry();
        goodbye(); //TODO: swap these, and only call goodbye if retry is F?


    }

    private void welcome() {
        System.out.println("Welcome to MineSweeper!");
        //TODO: ascii art greeting
    }

    // TODO: asking user for input
    private void createDefaultBoard() {
        // Will ask user at some point but for now take defaults
        board = new Board(9, 9, 10);
        board.instantiateBoard();
    }

    private void play() {
        Console.clear();
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
                    //board.doAction(userInput);
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

        if (input.length()==2) {
            isValid = areValidCoordsLength2(input);
        }

        if (input.length()==3) {
            isValid = areValidCoordsLength3(input);
        }

        return isValid;
    }

    private boolean areValidCoordsLength2(String input) {

        boolean inputIsValid = true;
        Character firstChar = input.charAt(0);
        Character secondChar = input.charAt(1);

        //fail scenario 1 - first character is not a digit
        if (!Character.isDigit(firstChar)) {
               inputIsValid = false;
        }
           //what the HECK
        //fail scenario 2 - first character is a digit, but it's not in the range of valid columns
        else if(!(0 < Character.getNumericValue(firstChar) && Character.getNumericValue(firstChar) <= board.getColumns())){
               inputIsValid = false;
        }
        //fail scenario 3 - first char is good, but second char is not Alpha
        else if (!Character.isAlphabetic(secondChar)) {
            inputIsValid = false;
        }
        //fail scenario 4 - first char is good, and second char is alpha, but it's not in the valid range
        else if (!('A' <= secondChar && secondChar <= ('A' + (board.getRows() - 1 )))) {
            inputIsValid = false;
        }

        //if you never hit a fail scenario this stayed true
        return inputIsValid;
    }

    private boolean areValidCoordsLength3(String input) {
        boolean inputIsValid = true;
        String firstTwoChars = input.substring(0,1);
        Character thirdChar = input.charAt(2);

        //fail scenario 1 - the first two characters are not both digits
        if(!input.matches("\\d{2}")) {
            inputIsValid = false;
        }
        //fail scenario 2 - the first two characters are both digits, but they aren't in a good range
        else if (0 < Integer.parseInt(firstTwoChars) && Integer.parseInt(firstTwoChars) <= board.getRows()){
            inputIsValid = false;
        }
        //fail scenario 3 - the first two char are good, but the 3rd char isn't alphabetic
        else if(!Character.isAlphabetic(thirdChar)) {
            inputIsValid = false;
        }
        //fail scenario 4 - the first two char are good, and the 3rd char is alpha, but it's not in the valid range
        else if(!('A' <= thirdChar && thirdChar <= ('A' + (board.getRows() - 1 )))) {
            inputIsValid = false;
        }

        return inputIsValid;
    }

    //TODO: these next three methods are a litte extra. Why must flagging require the extra g.
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