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
    private final static HelpMenu helpMenu = new HelpMenu(prompter);
    private final ActionHandler handler = new ActionHandler(prompter, helpMenu);

    private Board board;
    private Player player;
    private boolean retry = true;

    public void execute() {
        welcome();
        Console.pause(2000);
        helpMenu.help();

        while(willRetry()) {
            newGame();
        }

        goodbye();
    }

    private void welcome() {
        try {
            String welcome = Files.readString(Path.of("resources/MinesweeperLogo"));
            System.out.println(welcome);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newGame() {
        Console.clear();
        createDefaultBoard();
        createUser();
        handler.setBoard(board);
        handler.setPlayer(player);

        while (!board.isGameOver()) {
            play();
        }

        Console.clear();
        board.showBoard(); // Added a show board to show that you hit a bomb
        System.out.println();
        gameOverMessage();
        promptUserForRetry();
    }

    private void createDefaultBoard() {
        board = new Board();
        board.instantiateBoard();
    }

    private void createUser() {
        player = new Player("Josh");
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
            try {
                message = Files.readString(Path.of("resources/VictoryFanfare"));
                System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                message = Files.readString(Path.of("resources/Oops"));
                System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        System.out.println("Total time played: " + board.getPlayTime());
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

    private void goodbye() {
        System.out.println();
        System.out.println("Thank you for playing!");
        //TODO: ascii art sendoff
    }

    public boolean willRetry() {
        return retry;
    }

    private void setRetry(boolean retry) {
        this.retry = retry;
    }

}