package net.retrogame.app;

import com.apps.util.Prompter;
import net.retrogame.Board;

import java.io.File;
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
    private PlayerStatsDisplay statsDisplay;

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
        System.out.println(fileLoader.getWelcomeString());

        Console.pause(500);
        System.out.println("Enter your name:");
        String userInput = prompter.prompt("> ");

        createUser(userInput);
        actionHandler.setPlayer(player);
        statsDisplay = new PlayerStatsDisplay(prompter);
    }

    public void newGame(DifficultyTuple difficulty) {
        Console.clear();
        createBoard(difficulty);
        actionHandler.setBoard(board);
        player.setTotalGamesPlayed(player.getTotalGamesPlayed()+1);
        player.saveToCSVFile();
        while (!board.isGameOver()) {
            play();
        }

        //If user entered X to exit directly, all of this will be skipped.
        if (actionHandler.willRetry()) {
            Console.clear();
            board.showBoard();
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
        File userStats = new File("data/"+name+"-stats.csv");
        boolean existsAlready = userStats.exists();

        if(existsAlready) {
            loadUser(name);

        }
        else {
            player = new Player(name);
        }
    }

    private void loadUser(String name) {
        try {
            String fileContents = Files.readString(Path.of("data/" + name + "-stats.csv"));
            String[] savedFields = fileContents.split(",");

            String userName = savedFields[0];
            int totalPlayed = Integer.parseInt(savedFields[1]);
            int totalWon = Integer.parseInt(savedFields[2]);
            long bestTimeB = Long.parseLong(savedFields[3]);
            long bestTimeI = Long.parseLong(savedFields[4]);
            long bestTimeE = Long.parseLong(savedFields[5]);

            player = new Player (userName, totalPlayed, totalWon, bestTimeB, bestTimeI, bestTimeE);
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void play() {
        Console.clear();
        System.out.println();
        board.showBoard();
        System.out.println();
        actionHandler.promptUserForAction();
    }

    private void gameOverMessage() {
        System.out.println();
        if (board.wasGameWon()){
            player.setTotalWins(player.getTotalWins() + 1);
            player.updateBestTime(board);
            System.out.println(fileLoader.getWinString());
        }
        else {
            System.out.println(fileLoader.getLoseString());
        }
    
        System.out.println("Total time played: " + board.getPlayTime());
        player.saveToCSVFile();
    }

    private void promptUserForRetry() {
        boolean validInput = false;

        String menuOptions = "[Y]es to start a new game \n" +
                             "[N]o to quit \n" +
                             "[P] to view your current statistics";
        
        System.out.println();
        System.out.println("Would you like to play again? \n" + menuOptions);
        
        String userInput = prompter.prompt("> ").toUpperCase().trim();
        while(!validInput) {
            if ("Y".equals(userInput)) {
                actionHandler.setRetry(true);
                validInput = true;
            } else if ("N".equals(userInput)) {
                actionHandler.setRetry(false);
                validInput = true;
            } else if ("P".equals(userInput)) {
                Console.clear();
                statsDisplay.show(player);
                promptUserForRetry();
                validInput = true;
            }
            else {
                System.out.println("Please enter a valid option:\n" + menuOptions);
                userInput = prompter.prompt("> ");
            }
        }
    }

    private void goodbye() {
        System.out.println();
        System.out.println("Thank you for playing!");
    }

}