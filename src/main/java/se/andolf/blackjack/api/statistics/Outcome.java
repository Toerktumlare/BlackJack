package se.andolf.blackjack.api.statistics;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Thomas on 2017-09-16.
 */
public class Outcome {

    private static final Logger logger = LogManager.getLogger(Outcome.class);

    private PlayerStatistic dealer;
    private GameStatistics game;

    public Outcome() {
        this.dealer = new PlayerStatistic();
        this.game = new GameStatistics();
    }

    public PlayerStatistic getDealer() {
        return dealer;
    }

    public GameStatistics getGame() {
        return game;
    }

    @Override
    public String toString() {
        return "Outcome { " +
                "wins = " + game.getWins() +
                ", losses = " + game.getLosses() +
                ", rounds = " + game.getRounds() + " }";
    }

    public void print() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        try {
            logger.info(mapper.writeValueAsString(this));
        } catch (JsonProcessingException e) {
            throw new InternalError(e);
        }
    }
}
