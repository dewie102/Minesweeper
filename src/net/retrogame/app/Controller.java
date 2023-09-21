package net.retrogame.app;

import com.apps.util.Prompter;
import net.retrogame.Board;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import com.apps.util.Console;
import net.retrogame.DifficultyTuple;
import net.retrogame.Player;

public class Controller {

    private final static Prompter prompter = new Prompter(new Scanner(System.in));
    private final static FileLoader fileLoader = new FileLoader();
    private final static HelpMenu helpMenu = new HelpMenu(prompter, fileLoader);
    private final ActionHandler actionHandler = new ActionHandler(prompter, helpMenu);
    private final BoardDifficultyHandler difficultyHandler = new BoardDifficultyHandler(prompter);

    private Board board;
    private Player player;

    public void execute() {
        welcome();
        Console.pause(2000);
        helpMenu.help();
        
        while(actionHandler.willRetry()) {
            DifficultyTuple difficultyTuple = difficultyHandler.promptUserForDifficulty();
            newGame(difficultyTuple);
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

        createUser("Josh");
        actionHandler.setPlayer(player);
    }

    public void newGame(DifficultyTuple difficulty) {
        Console.clear();
        createBoard(difficulty);
        actionHandler.setBoard(board);
        player.setTotalGamesPlayed(player.getTotalGamesPlayed()+1);
        while (!board.isGameOver()) {
            play();
        }

        //If user entered X to exit directly, all of this will be skipped.
        if (actionHandler.willRetry()) {
            Console.clear();
            board.showBoard(); // Added a show board to show that you hit a bomb
            System.out.println();
            gameOverMessage();
            promptUserForRetry();
        }
    }

    private void createBoard(DifficultyTuple difficulty) {
        board = new Board(difficulty.numberOfRows, difficulty.numberOfColumns, difficulty.numberOfBombs);
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
        actionHandler.promptUserForAction();
    }

    private void gameOverMessage() {
        String message = null;
        System.out.println();
        if (board.wasGameWon()){
            player.setTotalWins(player.getTotalWins() + 1);
            player.updateBestTime(board.getPlayTime());
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
                actionHandler.setRetry(true);
                validInput = true;
            } else if (userInput.toUpperCase().trim().equals("N")) {
                actionHandler.setRetry(false);
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

}