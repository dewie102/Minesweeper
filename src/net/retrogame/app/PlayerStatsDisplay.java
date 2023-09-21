package net.retrogame.app;

import com.apps.util.Prompter;
import net.retrogame.Player;

class PlayerStatsDisplay {

    private Player player;
    private final Prompter prompter;

    public PlayerStatsDisplay(Prompter prompter) {
        this.prompter = prompter;
    }

    public void show(Player player) {
        setPlayer(player);

        printOut();
        readyToContinue();
    }

    private void printOut() {

        System.out.println(
                "Player: " + player.getName() + "\n" +
                "\tTotal Games Played:       " + player.getTotalGamesPlayed() + "\n" +
                "\tTotal Wins:               " + player.getTotalWins() + "\n" +
                "\tTotal Loses:              " + (player.getTotalGamesPlayed() - player.getTotalWins()) + "\n" +
                "\tBest Time [Beginner]:     " + player.getBestTimeB() + "\n" +
                "\tBest Time [Intermediate]: " + player.getBestTimeI() + "\n" +
                "\tBest Time [Expert]:       " + player.getBestTimeE()
        );
    }

    private void readyToContinue() {
        String userInput = null;
        boolean validInput = false;
        System.out.println("Enter [C] when you are ready to continue.");

        while(!validInput) {
            userInput = prompter.prompt("> ").toUpperCase().trim();

            if (userInput.equals("C")) {
                validInput = true;
            }

        }

    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}