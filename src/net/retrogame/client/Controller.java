package net.retrogame.client;

import net.retrogame.Board;

class Controller {
    private boolean retry = false;
    private Board board;
    private boolean playerIsClicking = true; //TODO: move this to a Player class if we need one

    public void newGame() {
        boolean gameOver = board.isGameOver();

        welcome();
        createDefaultBoard();
        while (!gameOver) {
            play();
            gameOver = board.isGameOver();
        }
        goodbye();
        promptUserForRetry();

    }

    private void welcome() {

    }

    // TODO: asking user for input
    private void createDefaultBoard() {
        // Will ask user at some point but for now take defaults
        board = new Board(9, 9, 9);
        board.instantiateBoard();
    }

    private void play() {
        board.showBoard();
        promptUserForAction();
    }

    public void promptUserForAction() {

    }

    public void coordOrToolSwap() {

    }

    private void goodbye() {

    }

    private void promptUserForRetry() {
        String userInput = null;
        //sout prompt for user
        //scanner scans and saves in userInput
        //parse userInput for boolean value
        //setRetry(parsed value)

    }

    public boolean willRetry() {
        return retry;
    }

    public void setRetry(boolean retry) {
        this.retry = retry;
    }
}