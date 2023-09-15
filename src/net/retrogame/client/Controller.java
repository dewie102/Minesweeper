package net.retrogame.client;

class Controller {
    public boolean retry = false;

    public void newGame() {
        welcome();
        instantiateBoard();
        //while (!gameOver) {
        //    play();
        //    board.isGameOver();
        //}
        goodbye();
        promptUserForRetry();

    }

    private void welcome() {

    }

    private void instantiateBoard() {

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