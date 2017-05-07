package se.andolf.blackjack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.andolf.blackjack.api.GameState;
import se.andolf.blackjack.api.Player;

import static se.andolf.blackjack.api.GameState.*;

/**
 * @author Thomas on 2017-03-04.
 */
public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    private Main() {
    }

    public static void main(String[] args) {

        final Game game = new Game.GameBuilder(new Player("Thomas")).run();
        logger.info("");
        logger.info("---- PRINTING PLAYER STATISTICS ----");
        game.getPlayers().stream().forEach(player -> logger.info(player.toString()));
    }
}
