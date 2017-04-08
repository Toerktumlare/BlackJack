package se.andolf.blackjack.api;

/**
 * @author Thomas on 2017-04-08.
 */
public class GameStatistic extends Statistic {

    int players = 0;

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "GameStatistic {" +
                "rounds=" + getRounds() +
                ", hands=" + getHands() +
                ", doubles=" + getDoubles() +
                ", blackJacks=" + getBlackJacks() +
                ", busts=" + getBusts() +
                ", wins=" + getWins() +
                ", losses=" + getLosses() +
                '}';
    }
}
