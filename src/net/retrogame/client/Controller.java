package net.retrogame.client;

import net.retrogame.Board;

class Controller {
    private boolean retry = false;
    private Board board = new Board(9, 9, 10);
    private boolean isClicking = true;

    public void newGame() {
        boolean gameOver = board.isGameOver();

        welcome();
        instantiateBoard();
        while (!gameOver) {
            play();
            gameOver = board.isGameOver();
        }
        goodbye();
        promptUserForRetry();

    }

    private void welcome() {

    }

    private void instantiateBoard() {

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

    }

    public boolean willRetry() {
        return retry;
    }

    public void setRetry(boolean retry) {
        this.retry = retry;
    }
}