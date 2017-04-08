package se.andolf.blackjack.api;

/**
 * @author Thomas on 2017-04-02.
 */
public class PlayerStatistic extends Statistic {

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
