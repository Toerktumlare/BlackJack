package se.andolf.blackjack.api.statistics;

/**
 * @author Thomas on 2017-04-02.
 */
public class PlayerStatistic extends AbstractStatistic {

    @Override
    public String toString() {
        return "PlayerStatistic {\n" +
                "\tblackJacks=" + getBlackJacks() +
                ", busts=" + getBusts() +
                ", wins=" + getWins() +
                ", losses=" + getLosses() +
                "\n}";
    }
}
