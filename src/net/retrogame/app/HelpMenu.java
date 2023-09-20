package net.retrogame.app;
import com.apps.util.Console;
import com.apps.util.Prompter;
import static net.retrogame.ConsoleColor.*;
import static net.retrogame.ConsoleDisplayUtil.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;


class HelpMenu {
    private final Prompter prompter;
    private final FileLoader fileLoader;
    private final String menuString;

    public HelpMenu(Prompter prompter, FileLoader fileLoader) {
        this.prompter = prompter;
        this.fileLoader = fileLoader;
        this.menuString = fileLoader.getHelpMenu();
    }

    public void help(){
        Console.clear();
        displayMenu();
        System.out.println();
        displayBoardKey();
        System.out.println();
        exitMenu();
    }

    //TODO: only read the file once
    private void displayMenu() {
        System.out.println(menuString);
    }

    private void displayBoardKey(){

        System.out.println(
                "\t\t---------\n" +
                "\t\tBoard Key\n" +
                "\t\t---------\n" +
                "\t\tBLACK tiles are still covered\n" +
	            "\t\t" + createStringWithColorAndReset("YELLOW", YELLOW_BG) + " tiles have been flagged\n" +
                "\t\t" + createStringWithColorAndReset("RED", RED_BG) + " tiles are bombs\n" +
                "\t\t" + createStringWithColorAndReset("GREEN", GREEN_BG) + " tiles have been uncovered and are NOT bombs\n");
    }

    private void exitMenu() {
        String userInput = null;
        boolean validInput = false;

        while(!validInput) {
            userInput = prompter.prompt("Type [C] to [C]ontinue. ").toUpperCase().trim();

            if (userInput.equals("C")) {
                validInput = true;
            }

        }
    }

}