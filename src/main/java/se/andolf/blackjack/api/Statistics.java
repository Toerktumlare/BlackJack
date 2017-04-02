package se.andolf.blackjack.api;

/**
 * @author Thomas on 2017-04-02.
 */
public class Statistics {

    private int blackJacks = 0;
    private int busts = 0;
    private int wins = 0;
    private int losses = 0;

    public int getBlackJacks() {
        return blackJacks;
    }

    public void addBlackJack() {
        blackJacks++;
    }

    public int getBusts() {
        return busts;
    }

    public void addBust() {
        busts++;
    }

    public int getWins() {
        return wins;
    }

    public void addWin() {
        wins++;
    }

    public int getLosses() {
        return losses;
    }

    public void addLoss() {
        losses++;
    }
}
