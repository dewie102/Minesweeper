package net.retrogame.app;

import com.apps.util.Prompter;
import net.retrogame.Board;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import com.apps.util.Console;
import net.retrogame.Difficulty;
import net.retrogame.DifficultyTuple;
import net.retrogame.Player;

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
            DifficultyTuple difficultyTuple = promptUserForDifficulty();
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
        handler.setPlayer(player);
    }

    public void newGame(DifficultyTuple difficulty) {
        Console.clear();
        createBoard(difficulty);
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
        handler.promptUserForAction();
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
                handler.setRetry(true);
                validInput = true;
            } else if (userInput.toUpperCase().trim().equals("N")) {
                handler.setRetry(false);
                validInput = true;
            } else {
                prompter.prompt("Please enter a valid option: [Y] to start a new game, or [N] to quit. ");
            }
        }
    }
    
    private DifficultyTuple promptUserForDifficulty() {
        DifficultyTuple result = null;
        String userInput;
        boolean validInput = false;
        
        System.out.println();
        userInput = prompter.prompt(buildDifficultyPrompt() + "> ").trim();
        
        while(!validInput) {
            if(userInput.matches("\\d")) {
                switch(userInput) {
                    case "1":
                        result = Difficulty.BEGINNER.getDifficultyDefaults();
                        validInput = true;
                        break;
                    case "2":
                        result = Difficulty.INTERMEDIATE.getDifficultyDefaults();
                        validInput = true;
                        break;
                    case "3":
                        result = Difficulty.EXPERT.getDifficultyDefaults();
                        validInput = true;
                        break;
                    case "4":
                        result = getCustomInputFromUser();
                        validInput = true;
                        break;
                }
            }
            
            if(!validInput) {
                userInput = prompter.prompt("Please enter a valid option from the menu.\n> ");
            }
        }
        
        return result;
    }
    
    private String buildDifficultyPrompt() {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Please choose the level difficulty\n");
    
        int count = 1;
        for(Difficulty difficulty : Difficulty.values()) {
            DifficultyTuple difficultyTuple = difficulty.getDifficultyDefaults();
            prompt.append("[").append(count++).append("] ").append(difficulty).append(": Rows: ")
                    .append(difficultyTuple.numberOfRows);
            prompt.append(" Columns: ").append(difficultyTuple.numberOfColumns);
            prompt.append(" Bombs: ").append(difficultyTuple.numberOfBombs);
            prompt.append("\n");
        }
    
        prompt.append("[").append(count).append("] ").append("Custom\n");
        
        return prompt.toString();
    }
    
    private DifficultyTuple getCustomInputFromUser() {
        DifficultyTuple result;
        
        int row = promptUserForNumberToMaxValue("Please choose number of rows ", 26);
        int column = promptUserForNumberToMaxValue("Please choose number of columns ", 99);
        int bombs = promptUserForNumberToMaxValue("Please choose number of bombs ", (row*column) - 1);
        
        result = new DifficultyTuple(row, column, bombs);
        
        return result;
    }
    
    private int promptUserForNumberToMaxValue(String prompt, int maxValue) {
        int result = 1;
    
        String userInput;
        boolean validInput = false;
    
        System.out.println();
        userInput = prompter.prompt(prompt + "(max  " + maxValue + "): > ").trim();
    
        while(!validInput) {
            if(userInput.matches("[0-9]+")) {
                result = Integer.parseInt(userInput);
                
                if(1 <= result && result <= maxValue) {
                    validInput = true;
                }
            }
            
            if(!validInput) {
                userInput = prompter.prompt("Please enter a valid number between [1 and " + maxValue + "]\n> ");
            }
        }
        
        return result;
    }

    private void goodbye() {
        System.out.println();
        System.out.println("Thank you for playing!");
        //TODO: ascii art sendoff
    }

}