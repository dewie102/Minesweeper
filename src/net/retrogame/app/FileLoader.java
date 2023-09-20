package net.retrogame.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class FileLoader {

    private String helpMenu = null;
    private String welcomeString = null;
    private String loseString = null;
    private String winString = null;

    public FileLoader(){
        instantiateFiles();
    }

    private void instantiateFiles() {
        helpMenu = readInHelpMenu();
        welcomeString = readInWelcome();
        loseString = readInBoom();
        winString = readInVictory();
    }

    private String readInHelpMenu(){
        String help = null;

        try {
            help = Files.readString(Path.of("resources/Help-Menu.txt"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return help;
    }

    private String readInWelcome() {
        String welcome = null;

        try {
            welcome = Files.readString(Path.of("resources/MinesweeperLogo"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return welcome;
    }

    private String readInBoom() {
        String boom = null;

        try {
            boom = Files.readString(Path.of("resources/Oops"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return boom;
    }

    private String readInVictory() {
        String win = null;

        try {
            win = Files.readString(Path.of("resources/VictoryFanfare"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return win;
    }


    public String getHelpMenu() {
        return helpMenu;
    }

    public String getWelcomeString() {
        return welcomeString;
    }

    public String getLoseString() {
        return loseString;
    }

    public String getWinString() {
        return winString;
    }
}