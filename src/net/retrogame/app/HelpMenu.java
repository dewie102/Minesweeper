package net.retrogame.app;
import com.apps.util.Console;
import com.apps.util.Prompter;
import static net.retrogame.ConsoleColor.*;
import static net.retrogame.ConsoleDisplayUtil.*;


class HelpMenu {
    private final Prompter prompter;
    private final String menuString;

    public HelpMenu(Prompter prompter, FileLoader fileLoader) {
        this.prompter = prompter;
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

    private void displayMenu() {
        System.out.println(menuString);
    }

    private void displayBoardKey(){

        System.out.println(
                "\t\t---------\n" +
                "\t\tBoard Key\n" +
                "\t\t---------\n" +
                "\t\t" + createStringWithDefaultColorAndReset("GRAY")      + " tiles are still covered\n" +
	            "\t\t" + createStringWithColorAndReset("YELLOW", YELLOW_BG) + " tiles have been flagged\n" +
                "\t\t" + createStringWithColorAndReset("RED", RED_BG)       + " tiles are bombs\n" +
                "\t\t" + createStringWithColorAndReset("GREEN", GREEN_BG) + " tiles have been uncovered and are NOT bombs\n");
    }

    private void exitMenu() {
        boolean validInput = false;

        while(!validInput) {
            String userInput = prompter.prompt("Type [C] to [C]ontinue.\n> ").toUpperCase().trim();

            if ("C".equals(userInput)) {
                validInput = true;
            }

        }
    }

}