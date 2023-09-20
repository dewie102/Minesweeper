package net.retrogame.app;

import com.apps.util.Prompter;
import net.retrogame.Board;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import com.apps.util.Console;
import net.retrogame.ConsoleColor;
import net.retrogame.Player;
import net.retrogame.TileState;

public class Controller {

    private final static Prompter prompter = new Prompter(new Scanner(System.in));
    private final static FileLoader fileLoader = new FileLoader();
    private final static HelpMenu helpMenu = new HelpMenu(prompter, fileLoader);
    private final ActionHandler handler = new ActionHandler(prompter, helpMenu);

    private Board board;
    private Player player;

    public void execute() {
        welcome();
        Console.pause(2000);
        helpMenu.help();

        while(handler.willRetry()) {
            newGame();
        }

        goodbye();
    }

    private void welcome() {
        System.out.println(fileLoader.getWelcomeString());

        //pause
        //Enter your name to continue:
        //createUser uses that name

        createUser("Josh");
        handler.setPlayer(player);

    }

    public void newGame() {
        Console.clear();
        createDefaultBoard();
        handler.setBoard(board);
        player.setTotalGamesPlayed(player.getTotalGamesPlayed()+1);
        while (!board.isGameOver()) {
            play();
        }

        //If user entered X to exit directly, all of this will be skipped.
        if (handler.willRetry()) {
            Console.clear();
            board.showBoard(); // Added a show board to show that you hit a bomb
            System.out.println();
            gameOverMessage();
            promptUserForRetry();
        }


    }

    private void createDefaultBoard() {
        board = new Board();
        board.instantiateBoard();
    }

    private void createUser(String name) {
        player = new Player(name);
    }

    private void play() {
        Console.clear();
        System.out.println();
        board.showBoard();
        System.out.println();
        handler.promptUserForAction();
    }

    private void gameOverMessage() {
        String message = null;
        System.out.println();
        if (board.wasGameWon()){
            player.setTotalWins(player.getTotalWins() + 1);
            player.updateBestTime(board.getPlayTime());
            System.out.println(fileLoader.getWinString());
        }
        else {
            System.out.println(fileLoader.getLoseString());
        }
    
        System.out.println("Total time played: " + board.getPlayTime());
    }

    private void promptUserForRetry() {

        String userInput = null;
        boolean validInput = false;

        System.out.println();
        System.out.println("Would you like to play again? Enter [Y]es to start a new game " +
                "or [N]o to quit.");
        userInput = prompter.prompt("> ");
        while(!validInput) {
            if (userInput.toUpperCase().trim().equals("Y")) {
                handler.setRetry(true);
                validInput = true;
            } else if (userInput.toUpperCase().trim().equals("N")) {
                handler.setRetry(false);
                validInput = true;
            } else {
                prompter.prompt("Please enter a valid option: [Y] to start a new game, or [N] to quit.\n> ");
            }
        }
    }

    private void goodbye() {
        System.out.println();
        System.out.println("Thank you for playing!");
        //TODO: ascii art sendoff
    }

}