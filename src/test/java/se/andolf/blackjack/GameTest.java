package se.andolf.blackjack;

import org.junit.Test;
import se.andolf.blackjack.api.Statistics;
import se.andolf.blackjack.util.DeckBuilder;
import se.andolf.blackjack.util.Decks;

import static org.junit.Assert.assertEquals;
import static se.andolf.blackjack.Game.play;

/**
 * @author Thomas on 2017-04-08.
 */
public class GameTest {

    @Test
    public void shouldPlayASingleRound(){
        final Statistics statistics = play().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_WINS)).then();
        assertEquals(1, statistics.getRounds());
    }

    @Test
    public void shouldPlayTwoRounds(){
        final Statistics results = play().rounds(2).then();
        assertEquals(2, results.getRounds());
    }

    @Test
    public void playerWins(){
        final Statistics statistics = play().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_WINS)).then();
        assertEquals(1, statistics.getWins());
    }

    @Test
    public void playerLooses(){
        final Statistics statistics = play().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_LOSS)).then();
        assertEquals(1, statistics.getLosses());
    }

    @Test
    public void playerDraws(){
        final Statistics statistics = play().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_DRAW)).then();
        assertEquals(1, statistics.getDraws());
    }

    @Test
    public void playerWinsWithBlackjackOnFirstDraw(){
        final Statistics statistics = play().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_BLACKJACK_AFTER_DEAL)).then();
        assertEquals(1, statistics.getWins());
        assertEquals(1, statistics.getBlackJacks());
    }

    @Test
    public void shouldPlaySingleGamePlayerWinsWithBlackjackDealerHasTen(){
        final int wins = play().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_BLACKJACK_DEALER_TEN)).then().getWins();
        assertEquals(1, wins);
    }

    @Test
    public void dealerWinsWithBlackJackAfterFirstDeal() {
        final Statistics statistics = play().deck(DeckBuilder.get(Decks.Scenarios.DEALER_BLACKJACK_AFTER_INIT_DEAL)).then();
        assertEquals(1, statistics.getBlackJacks());
        assertEquals(1, statistics.getDealer().getBlackJacks());
    }

    @Test
    public void playerLoosesIfHeIsBust() {
        final Statistics statistics = play().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_BUSTS)).then();
        assertEquals(1, statistics.getBusts());
        assertEquals(1, statistics.getLosses());
    }
}
