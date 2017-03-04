package se.andolf.blackjack;

/**
 * @author Thomas on 2017-03-04.
 */
public class Main {

    final static int PLAYERS = 1;

    public static void main(String[] args) {

        Game game = new Game();
        System.out.println("---- INITIALIZING PLAYERS ----");
        game.initPlayers(PLAYERS);
        System.out.println("---- STARTING GAME ----");
        game.start();
    }
}
