package se.andolf.blackjack;

import org.junit.Test;
import se.andolf.blackjack.api.statistics.Outcome;
import se.andolf.blackjack.util.DeckBuilder;
import se.andolf.blackjack.util.Decks;

import static org.junit.Assert.assertEquals;
import static se.andolf.blackjack.Game.play;
import static se.andolf.blackjack.Game.settings;

/**
 * @author Thomas on 2017-04-08.
 */
public class GameTest {

    @Test
    public void shouldPlayASingleRound(){
        final Outcome outcome = settings().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_WINS)).play();
        assertEquals(1, outcome.getGame().getRounds());
    }

    @Test
    public void shouldPlayTwoRounds(){
        final Outcome results = settings().rounds(2).play();
        assertEquals(2, results.getGame().getRounds());
    }

    @Test
    public void playerWins(){
        final Outcome outcome = settings().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_WINS)).play();
        assertEquals(1, outcome.getGame().getWins());
    }

    @Test
    public void playerLooses(){
        final Outcome outcome = settings().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_LOSS)).play();
        assertEquals(1, outcome.getGame().getLosses());
    }

    @Test
    public void playerDraws(){
        final Outcome outcome = settings().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_DRAW)).play();
        assertEquals(1, outcome.getGame().getDraws());
    }

    @Test
    public void playerWinsWithBlackjackOnFirstDraw(){
        final Outcome outcome = settings().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_BLACKJACK_AFTER_DEAL)).play();
        assertEquals(1, outcome.getGame().getWins());
        assertEquals(1, outcome.getGame().getBlackJacks());
    }

    @Test
    public void shouldPlaySingleGamePlayerWinsWithBlackjackDealerHasTen(){
        final int wins = settings().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_BLACKJACK_DEALER_TEN)).play().getGame().getWins();
        assertEquals(1, wins);
    }

    @Test
    public void dealerWinsWithBlackJackAfterFirstDeal() {
        final Outcome outcome = settings().deck(DeckBuilder.get(Decks.Scenarios.DEALER_BLACKJACK_AFTER_INIT_DEAL)).play();
        assertEquals(1, outcome.getGame().getBlackJacks());
        assertEquals(1, outcome.getDealer().getBlackJacks());
    }

    @Test
    public void playerLoosesIfHeIsBust() {
        final Outcome outcome = settings().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_BUSTS)).play();
        assertEquals(1, outcome.getGame().getBusts());
        assertEquals(1, outcome.getGame().getLosses());
    }

    @Test
    public void test() {
        play().print();
    }
}
