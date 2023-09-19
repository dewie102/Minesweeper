package net.retrogame.app;
import com.apps.util.Console;
import com.apps.util.Prompter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


class HelpMenu {
    private final Prompter prompter;

    public HelpMenu(Prompter prompter) {
        this.prompter = prompter;
    }

    public void help(){
        Console.clear();
        displayMenu();
        System.out.println();
        exitMenu();
    }

    private void displayMenu() {
        try {
            String help = Files.readString(Path.of("resources/Help-Menu.txt"));
            System.out.println(help);
        } catch (IOException e) {
            e.printStackTrace();
        }
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