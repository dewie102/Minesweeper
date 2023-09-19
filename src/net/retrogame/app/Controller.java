package net.retrogame.app;

import com.apps.util.Prompter;
import net.retrogame.Board;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import com.apps.util.Console;
import net.retrogame.ConsoleColor;
import net.retrogame.TileState;

//TODO: include some

public class Controller {
    private final static Prompter prompter = new Prompter(new Scanner(System.in));
    private final static HelpMenu helpMenu = new HelpMenu(prompter);


    private Board board;
    private boolean retry = true;
    private boolean playerIsClicking = true;

    public void execute() {
        welcome();
        Console.pause(2000);
        helpMenu.help();

        while(willRetry()) {
            newGame();
        }

        goodbye();
    }

    public void newGame() {
        Console.clear();
        createDefaultBoard();

        while (!board.isGameOver()) {
            play();
        }

        Console.clear();
        board.showBoard(); // Added a show board to show that you hit a bomb
        System.out.println();
        gameOverMessage();
        promptUserForRetry();
    }

    private void welcome() {
        try {
            String welcome = Files.readString(Path.of("MinesweeperLogo"));
            System.out.println(welcome);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDefaultBoard() {
        board = new Board();
        board.instantiateBoard();
    }

    private void play() {
        Console.clear();
        System.out.println();
        board.showBoard();
        System.out.println();
        promptUserForAction();
    }

    //TODO: remove repetition in Strings
    private void promptUserForAction() {
        String userInput = null;
        boolean validInput = false;
        System.out.println("What would you like to do next?");
        System.out.println();

        System.out.println("You are currently "+toolVerbPresentTense()+".");
        System.out.println();
        userInput = prompter.prompt("Enter the coordinates of the tile you would like to "+ toolVerb() +
                " in row-column order with no spaces (e.g. B8)," +
                " or enter [S] to [S]wap over to "+oppositeToolVerbPresentTense()+" mode.\n" +
                "Enter [H]elp to view the key and game instructions." ).toUpperCase().trim();

        while(!validInput) {
            if (userInput.equals("S")){
                setPlayerIsClicking(!isPlayerClicking());
                validInput = true;
            }
            else if (userInput.equals("H")){
                helpMenu.help();
                validInput = true;
            }
            else if (userInput.length()==2 || userInput.length()==3){//TODO: make sure max columns is 99 and max rows is 26

                if (areValidCoords(userInput)) {
                    boolean actionTaken = doAction(userInput);
                    if (actionTaken) {
                        validInput = true;
                    }
                    else {
                        userInput = prompter.prompt("").toUpperCase().trim();
                    }
                }
                else {
                    // ZR: Should this use the oppositeToolVerbPresentTense()?
                    userInput = prompter.prompt("Please enter a valid input. Valid inputs are " +
                            "the coordinates of the tile you would like to "+ toolVerb() +
                            " in row-column order with no spaces (e.g. B8)," +
                            " or [S] to [S]wap over to "+oppositeToolVerbPresentTense()+" mode.\n" +
                            "Enter [H]elp to view the key and game instructions.").toUpperCase().trim();
                }
            }
            else {
                // ZR: Should this use the oppositeToolVerbPresentTense()?
                userInput = prompter.prompt("Please enter a valid input. Valid inputs are " +
                        "the coordinates of the tile you would like to "+ toolVerb() +
                        " in row-column order with no spaces (e.g. B8)," +
                        " or [S] to [S]wap over to "+oppositeToolVerbPresentTense()+" mode.\n" +
                        "Enter [H]elp to view the key and game instructions.").toUpperCase().trim();
            }
        }
    }

    private boolean doAction(String input) {
        int row = 0;
        int col = 0;

        boolean done = false;
        
        row = input.charAt(0) - 'A';

        if (input.length() == 2) {
            // By subtracting by '1' instead of '0' we don't have the off by one logic to remember!
            col = input.charAt(1) - '1';
        }
        else {
            col = Integer.parseInt(input.substring(1, 2)) - 1;
        }

        done = board.doAction(row, col, isPlayerClicking());

        return done;
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

    //TODO: remove redundancy - checkFirstChar()?
    // ZR TODO: Could use the board.inBound(row, column) to validate the ints are in the boundaries
    private boolean areValidCoordsLength2(String input) {
        boolean inputIsValid = true;
        Character firstChar = input.charAt(0); //should be LETTER
        Character secondChar = input.charAt(1); //should be NUMBER

             //fail scenario 1 - first character is not a digit
        if ((!Character.isDigit(secondChar)) ||
            //fail scenario 2 - first character is a digit, but it's not in the range of valid columns
            (!(0 < Character.getNumericValue(secondChar) && Character.getNumericValue(secondChar) <= board.getColumns())) ||
            //fail scenario 3 - first char is good, but second char is not Alpha
            (!Character.isAlphabetic(firstChar)) ||
            //fail scenario 4 - first char is good, and second char is alpha, but it's not in the valid range
            (!('A' <= firstChar && firstChar <= ('A' + (board.getRows() - 1 ))))) {

            inputIsValid = false;
        }

        return inputIsValid;
    }

    private boolean areValidCoordsLength3(String input) {
        boolean inputIsValid = true;
        String secondTwoChars = input.substring(1,2);
        Character firstChar = input.charAt(0);

           //fail scenario 1 - the second two characters are not both digits
        if((!secondTwoChars.matches("\\d{2}")) ||
           //fail scenario 2 - the second two characters are both digits, but they aren't in a good range
           (0 < Integer.parseInt(secondTwoChars) && Integer.parseInt(secondTwoChars) <= board.getRows()) ||
           //fail scenario 3 - the second two char are good, but the 1st char isn't alphabetic
           (!Character.isAlphabetic(firstChar)) ||
           //fail scenario 4 - the second two char are good, and the 1st char is alpha, but it's not in the valid range
           (!('A' <= firstChar && firstChar <= ('A' + (board.getRows() - 1 ))))) {
            inputIsValid = false;
        }

        return inputIsValid;
    }

    //TODO: these next three methods are a little extra. Why must flagging require the extra g.
    //TODO: ... string builder?
    private String toolVerbPresentTense() {
        String tool = null;
        if (isPlayerClicking()) {
            tool = TileState.UNCOVERED + "clicking";
        }
        else {
            tool = TileState.FLAGGED + "flagging";
        }
        return tool + ConsoleColor.RESET_COLOR;
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

    //TODO: might have to move the wasWon flag into Board after all. Sad.
    private void gameOverMessage() {
        String message = null;
        System.out.println();
        if (board.wasGameWon()){
            try {
                message = Files.readString(Path.of("VictoryFanfare"));
                System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                message = Files.readString(Path.of("Oops"));
                System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void goodbye() {
        System.out.println();
        System.out.println("Thank you for playing!");
        //TODO: ascii art sendoff
    }

    private void promptUserForRetry() {

        String userInput = null;
        boolean validInput = false;

        System.out.println();
        userInput = prompter.prompt("Would you like to play again? Enter [Y] to start a new game " +
                "or [N]o to quit. ");
        while(!validInput) {
            if (userInput.toUpperCase().trim().equals("Y")) {
                setRetry(true);
                validInput = true;
            } else if (userInput.toUpperCase().trim().equals("N")) {
                setRetry(false);
                validInput = true;
            } else {
                prompter.prompt("Please enter a valid option: [Y] to start a new game, or [N] to quit. ");
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