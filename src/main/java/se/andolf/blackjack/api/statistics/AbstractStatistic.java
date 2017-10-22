package se.andolf.blackjack.api.statistics;

/**
 * @author Thomas on 2017-04-08.
 */
public abstract class AbstractStatistic {

    private int rounds = 0;
    private int hands = 0;
    private int doubles = 0;
    private int blackJacks = 0;
    private int busts = 0;
    private int wins = 0;
    private int losses = 0;
    private int draws = 0;

    public int getLosses() {
        return losses;
    }

    public void addLoss() {
        losses++;
    }

    public int getWins() {
        return wins;
    }

    public void addWin() {
        wins++;
    }

    public int getBusts() {
        return busts;
    }

    public void addBust() {
        busts++;
    }

    public int getBlackJacks() {
        return blackJacks;
    }

    public void addBlackJack() {
        blackJacks++;
    }

    public int getDoubles() {
        return doubles;
    }

    public void addDouble() {
        doubles++;
    }

    public int getHands() {
        return hands;
    }

    public void addHand() {
        hands++;
    }

    public int getRounds() {
        return rounds;
    }

    public void addRound() {
        rounds++;
    }

    public void addDraw(){
        draws++;
    }

    public int getDraws() {
        return draws;
    }
}
