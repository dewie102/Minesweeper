package net.retrogame.app;
import com.apps.util.Console;
import com.apps.util.Prompter;
import static net.retrogame.ConsoleColor.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;


class HelpMenu {
    private final Prompter prompter;
    private final String menuString = readInMenu();

    public HelpMenu(Prompter prompter) {
        this.prompter = prompter;
    }

    public void help(){
        Console.clear();
        displayMenu();
        System.out.println();
        displayBoardKey();
        System.out.println();
        exitMenu();
    }

    private String readInMenu() {
        String help = null;

        try {
            help = Files.readString(Path.of("resources/Help-Menu.txt"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return help;
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
	            "\t\t" + YELLOW_BG + BLACK_FG +"YELLOW" + RESET_COLOR + " tiles have been flagged\n" +
                "\t\t" + RED_BG + BLACK_FG + "RED" + RESET_COLOR + " tiles are bombs\n" +
                "\t\t" + GREEN_BG + BLACK_FG + "GREEN" + RESET_COLOR + " tiles have been uncovered and are NOT bombs\n");
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