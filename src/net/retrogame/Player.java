package net.retrogame;

import net.retrogame.tile.Tool;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Player {
    private String name;
    private int totalGamesPlayed = 0; //updated every call to newGame()
    private int totalWins = 0; //updated when the ending message is displayed
    private long bestTimeB = 0;
    private long bestTimeI = 0;
    private long bestTimeE = 0;

    private Tool currentTool = Tool.CLICK;

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, int totalPlayed, int totalWins, long bestTimeB, long bestTimeI, long bestTimeE) {
        setName(name);
        setTotalGamesPlayed(totalPlayed);
        setTotalWins(totalWins);
        setBestTimeB(bestTimeB);
        setBestTimeI(bestTimeI);
        setBestTimeE(bestTimeE);
    }

    public void swapTool() {
        if (getCurrentTool().equals(Tool.CLICK)) {
            setCurrentTool(Tool.FLAG);
        } else if (getCurrentTool().equals(Tool.FLAG)) {
            setCurrentTool(Tool.CLICK);
        }
    }

    public void updateBestTime(Board board) {
        long playTime = board.getPlayTime();
        String level = getDifficulty(board);

        if (!(level == null)) {
            switch (level) {
                case "BEGINNER":
                    if (getBestTimeB() == 0 || playTime < getBestTimeB()) {
                        setBestTimeB(playTime);
                    }
                    break;
                case "INTERMEDIATE":
                    if (getBestTimeI() == 0 || playTime < getBestTimeI()) {
                        setBestTimeI(playTime);
                    }
                    break;
                case "EXPERT":
                    if (getBestTimeE() == 0 || playTime < getBestTimeE()) {
                        setBestTimeE(playTime);
                    }
                //custom sized board best time are not saved.
            }
        }
    }

    private String getDifficulty(Board board) {
        String result = null;
        DifficultyTuple tuple = new DifficultyTuple(board.getRows(), board.getColumns(), board.getNumberOfBombs());

        for (Difficulty diff : Difficulty.values()) {
            if (diff.getDifficultyDefaults().equals(tuple)) {
                result = diff.toString();
            }
        }

        return result;
    }

    public void saveToCSVFile() {
        try(PrintWriter out = new PrintWriter (new FileWriter("data/"+getName()+"-stats.csv"))) {
            out.printf("%s,%s,%s,%s,%s,%s",getName(),getTotalGamesPlayed(),getTotalWins(),getBestTimeB(),getBestTimeI(),getBestTimeE());
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void loadFromCSVFile() {

    }


    public int getLoses() {
        return getTotalGamesPlayed() - getTotalWins();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public void setTotalGamesPlayed(int totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(int totalWins) {
        this.totalWins = totalWins;
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public void setCurrentTool(Tool tool) {
        this.currentTool = tool;
    }

    public long getBestTimeB() {
        return bestTimeB;
    }

    public long getBestTimeI() {
        return bestTimeI;
    }

    public long getBestTimeE() {
        return bestTimeE;
    }

    public void setBestTimeB(long bestTimeB) {
        this.bestTimeB = bestTimeB;
    }

    public void setBestTimeI(long bestTimeI) {
        this.bestTimeI = bestTimeI;
    }

    public void setBestTimeE(long bestTimeE) {
        this.bestTimeE = bestTimeE;
    }

    @Override
    public String toString() {
        return String.format("%s: name=%s, totalGamesPlayed=%s, totalWins=%s, bestTime=%s, usingFlagTool=%s",
                getClass().getSimpleName(),
                getName(),
                getTotalGamesPlayed(),
                getTotalWins(),
                getBestTimeB(),
                getCurrentTool());
    }
}
