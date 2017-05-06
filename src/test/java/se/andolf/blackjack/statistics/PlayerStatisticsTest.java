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
public class PlayerStatisticsTest {

    @Before
    public void init(){
        Arrays.stream(Players.values()).forEach(Players::reset);
    }

    @Test
    public void shouldRegisterAWinForThePlayer(){
        final Game game = new Game.GameBuilder(DeckBuilder.get(Decks.Scenarios.PLAYER_WINS), Players.PLAYER_1.getPlayer()).run();

        assertEquals(1, game.getPlayer(Players.PLAYER_1.getId()).getStatistics().getWins());
        assertEquals(1, game.getStatistics().getRounds());
    }

    @Test
    public void shouldRegisterALossForThePlayer(){
        final Game game = new Game.GameBuilder(DeckBuilder.get(Decks.Scenarios.PLAYER_LOSS), Players.PLAYER_1.getPlayer()).run();
        assertEquals(1, game.getPlayer(Players.PLAYER_1.getPlayer().getId()).getStatistics().getLosses());
        assertEquals(1, game.getStatistics().getRounds());
    }

    @Test
    public void shouldRegisterADraw(){
        final Game game = new Game.GameBuilder(DeckBuilder.get(Decks.Scenarios.PLAYER_DRAW), Players.PLAYER_1.getPlayer()).run();
        assertEquals(1, game.getPlayer(Players.PLAYER_1.getPlayer().getId()).getStatistics().getDraws());
        assertEquals(1, game.getStatistics().getRounds());
    }
}
