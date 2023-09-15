package net.retrogame.client;

class Main {

    public static void main(String[] args) {
        Controller mainController = new Controller();

        while(mainController.willRetry()) {
            mainController.newGame();
        }

    }

}