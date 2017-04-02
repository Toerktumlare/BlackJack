package se.andolf.blackjack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Thomas on 2017-03-04.
 */
public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        final Game game = new Game();
        logger.info("---- INITIALIZING PLAYERS ----");
        game.initPlayers();
        logger.info("---- STARTING GAME ----");
        game.start();
    }
}
