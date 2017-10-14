package se.andolf.blackjack.api;

/**
 * @author Thomas on 2017-09-16.
 */
public class Statistics {

    private final Results statisticsResults;

    public Statistics(Results statisticsResults) {
        this.statisticsResults = statisticsResults;
    }

    public Results getGame() {
        return statisticsResults;
    }
}
