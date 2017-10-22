package se.andolf.blackjack;

import org.junit.Test;
import se.andolf.blackjack.api.Outcome;
import se.andolf.blackjack.api.Player;
import se.andolf.blackjack.util.DeckBuilder;
import se.andolf.blackjack.util.Decks;

import static org.junit.Assert.assertEquals;
import static se.andolf.blackjack.Game.settings;

/**
 * @author Thomas on 2017-04-08.
 */
public class GameTest {

    @Test
    public void shouldPlayASingleRound(){
        final Outcome outcome = settings().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_WINS)).play();
        assertEquals(1, outcome.getRounds());
    }

    @Test
    public void shouldPlayTwoRounds(){
        final Outcome results = settings().rounds(2).play();
        assertEquals(2, results.getRounds());
    }

    @Test
    public void playerWins(){
        final Outcome outcome = settings().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_WINS)).play();
        assertEquals(1, outcome.getWins());
    }

    @Test
    public void playerLooses(){
        final Outcome outcome = settings().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_LOSS)).play();
        assertEquals(1, outcome.getLosses());
    }

    @Test
    public void playerDraws(){
        final Outcome outcome = settings().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_DRAW)).play();
        assertEquals(1, outcome.getDraws());
    }

    @Test
    public void playerWinsWithBlackjackOnFirstDraw(){
        final Outcome outcome = settings().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_BLACKJACK_AFTER_DEAL)).play();
        assertEquals(1, outcome.getWins());
        assertEquals(1, outcome.getBlackJacks());
    }

    @Test
    public void shouldPlaySingleGamePlayerWinsWithBlackjackDealerHasTen(){
        final int wins = settings().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_BLACKJACK_DEALER_TEN)).play().getWins();
        assertEquals(1, wins);
    }

    @Test
    public void dealerWinsWithBlackJackAfterFirstDeal() {
        final Outcome outcome = settings().deck(DeckBuilder.get(Decks.Scenarios.DEALER_BLACKJACK_AFTER_INIT_DEAL)).play();
        assertEquals(1, outcome.getBlackJacks());
        assertEquals(1, outcome.getDealer().getBlackJacks());
    }

    @Test
    public void playerLoosesIfHeIsBust() {
        final Outcome outcome = settings().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_BUSTS)).play();
        assertEquals(1, outcome.getBusts());
        assertEquals(1, outcome.getLosses());
    }

    @Test
    public void test() {
        settings().players(2).play().print();
    }
}
