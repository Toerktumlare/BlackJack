package se.andolf.blackjack.api;

/**
 * @author Thomas on 2017-04-08.
 */
public class Results extends AbstractStatistic {

    int players = 0;

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "Results {" +
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
