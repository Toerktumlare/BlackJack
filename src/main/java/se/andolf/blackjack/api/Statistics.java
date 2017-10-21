package se.andolf.blackjack.api;

/**
 * @author Thomas on 2017-09-16.
 */
public class Statistics extends AbstractStatistic{

    private PlayerStatistic dealer;

    public Statistics() {
        this.dealer = new PlayerStatistic();
    }

    public PlayerStatistic getDealer() {
        return dealer;
    }
}
