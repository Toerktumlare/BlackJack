package se.andolf.blackjack.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Thomas on 2017-09-16.
 */
public class Statistics extends AbstractStatistic {

    private static final Logger logger = LogManager.getLogger(Statistics.class);


    private PlayerStatistic dealer;

    public Statistics() {
        this.dealer = new PlayerStatistic();
    }

    public PlayerStatistic getDealer() {
        return dealer;
    }

    @Override
    public String toString() {
        return "Statistics { " +
                "wins = " + getWins() +
                ", losses = " + getLosses() +
                ", rounds = " + getRounds() + " }";
    }

    public void print() {
        logger.info(toString());
    }
}
