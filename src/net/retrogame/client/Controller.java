package net.retrogame.client;

import com.apps.util.Prompter;
import net.retrogame.Board;

import java.sql.SQLOutput;
import java.util.Scanner;

class Controller {
    private final static Prompter prompter = new Prompter(new Scanner(System.in));

    private boolean retry = false;
    private String userInput;
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

    public void promptUserForAction() {
        System.out.println("What would you like to do next?");

        System.out.println("You are currently "+toolVerbPresentTense()+".");
        userInput = prompter.prompt("Enter the coordinates of the tile you would like to "+ toolVerb() +
                ", or enter [S] to [S]wap over to "+toolVerbPresentTense()+" mode.");
    }

    public String toolVerbPresentTense() {
        String tool = null;
        if (playerIsClicking) {
            tool = "clicking";
        }
        else {
            tool = "flagging";
        }
        return tool;
    }

    public String toolVerb() {
        String tool = null;
        if (playerIsClicking) {
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
        boolean validInput = false;

        userInput = prompter.prompt("Would you like to play again? Enter [Y] to start a new game" +
                "or [N]o to quit.");
        while(!validInput) {
            if (userInput.equals("Y")) {
                setRetry(true);
                validInput = true;
            } else if (userInput.equals("N")) {
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

    public void setRetry(boolean retry) {
        this.retry = retry;
    }
}