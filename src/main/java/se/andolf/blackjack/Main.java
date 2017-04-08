package se.andolf.blackjack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.andolf.blackjack.api.GameBuilder;
import se.andolf.blackjack.api.Player;
import se.andolf.blackjack.api.exception.GameException;

/**
 * @author Thomas on 2017-03-04.
 */
public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        final Game game = new GameBuilder()
                .addPlayer(new Player("Thomas"))
                .build();
        logger.info("---- STARTING GAME ----");

        try {
            game.start();
        } catch (GameException e) {
            logger.error(e);
        }
    }
}
