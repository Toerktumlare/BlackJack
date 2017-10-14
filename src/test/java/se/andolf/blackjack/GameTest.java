package se.andolf.blackjack;

import org.junit.Test;
import se.andolf.blackjack.api.Results;
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
        final Results results = play().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_WINS)).then().getGame();
        assertEquals(1, results.getRounds());
    }

    @Test
    public void shouldPlayTwoRounds(){
        final Results results = play().rounds(2).then().getGame();
        assertEquals(2, results.getRounds());
    }

    @Test
    public void shouldPlaySimpleGamePlayerWins(){
        final Results results = play().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_WINS)).then().getGame();
        assertEquals(1, results.getWins());
    }

    @Test
    public void shouldPlaySimpleGamePlayerLooses(){
        final Results results = play().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_LOSS)).then().getGame();
        assertEquals(1, results.getLosses());
    }

    @Test
    public void shouldPlaySimpleGamePlayerDraws(){
        final Results results = play().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_DRAW)).then().getGame();
        assertEquals(1, results.getDraws());
    }

    @Test
    public void shouldPlaySimpleGamePlayerWinsWithBlackjackOnFirstDraw(){
        final Results results = play().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_BLACKJACK_AFTER_DEAL)).then().getGame();
        assertEquals(1, results.getWins());
        assertEquals(1, results.getBlackJacks());
    }

    @Test
    public void shouldPlaySingleGamePlayerWinsWithBlackjackDealerHasTen(){
        final Results results = play().deck(DeckBuilder.get(Decks.Scenarios.PLAYER_BLACKJACK_DEALER_TEN)).then().getGame();
        assertEquals(1, results.getWins());
    }
}
