package se.andolf.blackjack.statistics;

import org.junit.Before;
import org.junit.Test;
import se.andolf.blackjack.Game;
import se.andolf.blackjack.util.DeckBuilder;
import se.andolf.blackjack.util.Decks;
import se.andolf.blackjack.util.Players;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * @author Thomas on 2017-04-13.
 */
public class PlayerOutcomeTest {

    @Before
    public void init(){
        Arrays.stream(Players.values()).forEach(Players::reset);
    }

//    @Test
//    public void shouldRegisterARegularWinForThePlayer(){
//        final Game game = new Game.GameBuilder(DeckBuilder.get(Decks.Scenarios.PLAYER_WINS), Players.PLAYER_1.getPlayer()).run();
//
//        assertEquals(1, game.getPlayer(Players.PLAYER_1.getId()).getOutcome().getWins());
//        assertEquals(1, game.getOutcome().getRounds());
//    }
//
//    @Test
//    public void shouldRegisterARegularLossForThePlayer(){
//        final Game game = new Game.GameBuilder(DeckBuilder.get(Decks.Scenarios.PLAYER_LOSS), Players.PLAYER_1.getPlayer()).run();
//        assertEquals(1, game.getPlayer(Players.PLAYER_1.getPlayer().getId()).getOutcome().getLosses());
//        assertEquals(1, game.getOutcome().getRounds());
//    }
//
//    @Test
//    public void shouldRegisterARegularDraw(){
//        final Game game = new Game.GameBuilder(DeckBuilder.get(Decks.Scenarios.PLAYER_DRAW), Players.PLAYER_1.getPlayer()).run();
//        assertEquals(1, game.getPlayer(Players.PLAYER_1.getPlayer().getId()).getOutcome().getDraws());
//        assertEquals(1, game.getOutcome().getRounds());
//    }
//
//    @Test
//    public void shouldRegisterABlackJackForThePlayerAfterDeal(){
//        final Game game = new Game.GameBuilder(DeckBuilder.get(Decks.Scenarios.PLAYER_BLACKJACK_AFTER_DEAL), Players.PLAYER_1.getPlayer()).run();
//        assertEquals(1, game.getOutcome().getWins());
//        assertEquals(1, game.getOutcome().getBlackJacks());
//        assertEquals(0, game.getOutcome().getLosses());
//        assertEquals(1, game.getPlayers().get(0).getOutcome().getWins());
//        assertEquals(1, game.getPlayers().get(0).getOutcome().getBlackJacks());
//        assertEquals(0, game.getPlayers().get(0).getOutcome().getLosses());
//        assertEquals(0, game.getDealer().getOutcome().getWins());
//        assertEquals(0, game.getDealer().getOutcome().getBlackJacks());
//    }
//
//    @Test
//    public void shouldPlayAndWinIfPlayerHasBlackJackAndDealerHas10(){
//        final Result result = new Game.GameBuilder(DeckBuilder.get(Decks.Scenarios.PLAYER_BLACKJACK_DEALER_TEN), Players.PLAYER_1.getPlayer()).run();
//        assertEquals(1, result.getPlayers().get(0).getHands().size());
//        assertEquals(1, result.getDealer().getHands().size());
//        assertEquals(1, result.getOutcome().getWins());
//        assertEquals(1, result.getOutcome().getBlackJacks());
//        assertEquals(1, result.getPlayers().get(0).getOutcome().getWins());
//        assertEquals(1, result.getPlayers().get(0).getOutcome().getBlackJacks());
//        assertEquals(0, result.getDealer().getOutcome().getWins());
//        assertEquals(0, result.getGame().getDealer().getOutcome().getBlackJacks());
//    }
}
