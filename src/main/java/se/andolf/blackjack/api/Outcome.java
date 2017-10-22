package se.andolf.blackjack.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Thomas on 2017-09-16.
 */
public class Outcome extends AbstractStatistic {

    private static final Logger logger = LogManager.getLogger(Outcome.class);

    private PlayerStatistic dealer;

    public Outcome() {
        this.dealer = new PlayerStatistic();
    }

    public PlayerStatistic getDealer() {
        return dealer;
    }

    @Override
    public String toString() {
        return "Outcome { " +
                "wins = " + getWins() +
                ", losses = " + getLosses() +
                ", rounds = " + getRounds() + " }";
    }

    public void print() {
        logger.info(toString());
    }
}
