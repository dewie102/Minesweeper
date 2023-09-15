package net.retrogame.client;

import com.apps.util.Prompter;
import net.retrogame.Board;

import java.sql.SQLOutput;
import java.util.Locale;
import java.util.Scanner;

class Controller {
    private final static Prompter prompter = new Prompter(new Scanner(System.in));

    private boolean retry = false;
    private Board board;
    private boolean playerIsClicking = true; //TODO: move this to a Player class if we need one


    public void newGame() {

        welcome();
        createDefaultBoard();
        while (!board.isGameOver()) {
            play();
        }
        goodbye();
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

    private void promptUserForAction() {
        String userInput = null;
        boolean validInput = false;
        System.out.println("What would you like to do next?");

        System.out.println("You are currently "+toolVerbPresentTense()+".");
        userInput = prompter.prompt("Enter the coordinates of the tile you would like to "+ toolVerb() +
                " in column-row order with no spaces (e.g. B8)," +
                " or enter [S] to [S]wap over to "+toolVerbPresentTense()+" mode.");

        while(!validInput) {
            if (userInput.toUpperCase().trim().equals("S")){
                setPlayerIsClicking(!isPlayerClicking());
                validInput = true;
            }
            else if (userInput.trim().length()==2){
                //TODO: figure out what to do here.... could still be invalid :(
                //might have to be a nested loop. SAD.
            }
            else {
                userInput = prompter.prompt("Please enter a valid input. Valid inputs are " +
                        "the coordinates of the tile you would like to "+ toolVerb() +
                        " in column-row order with no spaces (e.g. B8)," +
                        " or [S] to [S]wap over to "+toolVerbPresentTense()+" mode.");
            }
        }
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


    public void coordOrToolSwap() {

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