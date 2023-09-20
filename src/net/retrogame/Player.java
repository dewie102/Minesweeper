package net.retrogame;

public class Player {
    private String name;
    private int totalGamesPlayed = 0; //updated every call to newGame()
    private int totalWins = 0; //updated when the ending message is displayed
    private int score = 0;
    private boolean usingFlagTool = false;
    private long bestTime = 0;

    public Player(String name) {
        this.name = name;
    }

    public void swapTool() {
        setUsingFlagTool(!isUsingFlagTool());
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

    public int getScore() {
        return score;
    }

    private void setScore(int score) {
        this.score = score;
    }

    public boolean isUsingFlagTool() {
        return usingFlagTool;
    }

    public void setUsingFlagTool(boolean toolFlag) {
        usingFlagTool = toolFlag;
    }

    @Override
    public String toString() {
        return String.format("%s: name=%s, totalGamesPlayed=%s, totalWins=%s, score=%s, bestTime=%s, usingFlagTool=%s",
                getClass().getSimpleName(),
                getName(),
                getTotalGamesPlayed(),
                getTotalWins(),
                getScore(),
                getBestTime(),
                isUsingFlagTool());
    }

    public void updateBestTime(long playTime) {
        if (getTotalWins()==1 || playTime < bestTime) { //if this is the very first win then this is your best time
            bestTime = playTime;
        }
    }

    private long getBestTime() {
        return bestTime;
    }
}
