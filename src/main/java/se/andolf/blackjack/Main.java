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

        final Game game = new Game.GameBuilder(new Player("Thomas"))
                .run();
        logger.info("---- STARTING GAME ----");
        int rounds = 100;
        int played = 0;
        while (played < rounds) {
            logger.info("");
            logger.info("---- INITIALIZING FIRST DEAL ----");
            game.run(FIRST_DEAL);

            logger.info("");
            logger.info("---- CHECKING BLACKJACKS ----");
            game.run(GameState.Checks.BLACKJACK);
            logger.info("---- BLACKJACK CHECK HAS ENDED ----");

            logger.info("");
            logger.info("---- INITIAL DEAL ENDED, STARTING PLAYER sounds ----");
            game.run(PLAYERS);
            logger.info("---- PLAYER sounds ENDED ----");

            logger.info("");
            logger.info("---- CHECKING BLACKJACKS ----");
            game.run(GameState.Checks.BLACKJACK);
            logger.info("---- BLACKJACK CHECK HAS ENDED ----");

            logger.info("");
            logger.info("---- INITIAL PLAYER ROUNDS ENDED, STARTING DEALERS ROUND ----");
            game.run(DEALER);
            logger.info("");
            logger.info("---- COMPARING HANDS ----");
            game.run(GameState.Checks.WIN);
            logger.info("");
            logger.info("---- GAME OVER RESETTING GAME ----");
            game.run(CLEAR);
            logger.info("---- GAME RESET ----");
            played++;
        }
        logger.info("");
        logger.info("---- PRINTING PLAYER STATISTICS ----");
        game.getPlayers().stream().forEach(player -> logger.info(player.toString()));
    }
}
