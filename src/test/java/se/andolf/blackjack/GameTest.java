package se.andolf.blackjack;

import org.junit.Before;
import org.junit.Test;
import se.andolf.blackjack.api.Deal;
import se.andolf.blackjack.util.DeckBuilder;
import se.andolf.blackjack.util.Decks;
import se.andolf.blackjack.util.Players;

import java.util.Arrays;

import static org.junit.Assert.*;
import static se.andolf.blackjack.api.GameState.*;
import static se.andolf.blackjack.api.GameState.Checks.BLACKJACK;
import static se.andolf.blackjack.api.GameState.Checks.BUST;

/**
 * @author Thomas on 2017-04-08.
 */
public class GameTest {

    @Before
    public void init(){
        Arrays.stream(Players.values()).forEach(Players::reset);
    }

    @Test
    public void shouldAddTwoPlayersAndCreateADealer(){
        final Game game = new Game.GameBuilder(Players.PLAYER_1.getPlayer(), Players.PLAYER_2.getPlayer()).run();
        assertEquals(2, game.getPlayers().size());
        assertNotNull(game.getDealer());
    }


    @Test
    public void shouldDealOnlyPlayersOneCard(){
        final Game game = new Game.GameBuilder(DeckBuilder.get(Decks.TWO_CARDS), Players.PLAYER_1.getPlayer(), Players.PLAYER_2.getPlayer()).run(Deal.PLAYERS);
        assertEquals(7, game.getPlayer(Players.PLAYER_1.getId()).getHand().getCards().stream().findFirst().get().getValue());
        assertEquals(9, game.getPlayer(Players.PLAYER_2.getId()).getHand().getCards().stream().findFirst().get().getValue());
        assertTrue(game.getDealer().getHands().isEmpty());
    }

    @Test
    public void shouldADealAllOneCards(){
        final Game game = new Game.GameBuilder(DeckBuilder.get(Decks.THREE_CARDS), Players.PLAYER_1.getPlayer(), Players.PLAYER_2.getPlayer()).run(Deal.ALL);
        assertEquals(7, game.getPlayer(Players.PLAYER_1.getId()).getHand().getCards().stream().findFirst().get().getValue());
        assertEquals(9, game.getPlayer(Players.PLAYER_2.getId()).getHand().getCards().stream().findFirst().get().getValue());
        assertEquals(2, game.getDealer().getHand().getCards().stream().findFirst().get().getValue());
    }

    @Test
    public void shouldDealOnlyTheDealerOneCard(){
        final Game game = new Game.GameBuilder(DeckBuilder.get(Decks.SINGLE_CARD), Players.PLAYER_1.getPlayer()).run(Deal.DEALER);
        assertEquals(7, game.getDealer().getHand().getCards().stream().findFirst().get().getValue());
    }

    @Test
    public void shouldSetGameInStartingState(){
        final Game game = new Game.GameBuilder(DeckBuilder.get(Decks.SINGLE_CARD), Players.PLAYER_1.getPlayer()).run(FIRST_DEAL);
        assertEquals(2, game.getPlayer(Players.PLAYER_1.getId()).getHand().getCards().size());
        assertEquals(1, game.getDealer().getHand().getCards().size());
    }

    @Test
    public void playPlayerHandsUntilStand() {
        final Game game = new Game.GameBuilder(DeckBuilder.get(Decks.Scenarios.TWO_PLAYERS_UNTIL_BOTH_STAND), Players.PLAYER_1.getPlayer(), Players.PLAYER_2.getPlayer())
                .run(FIRST_DEAL);

        assertEquals(17, game.getPlayer(Players.PLAYER_1.getId()).getHand().getValue());
        assertEquals(19, game.getPlayer(Players.PLAYER_2.getId()).getHand().getValue());
    }

    @Test
    public void playAllPlayerHandsUntilStand() {
        final Game game = new Game.GameBuilder(DeckBuilder.get(Decks.Scenarios.FOUR_PLAYERS_ALL_STAND),
                Players.PLAYER_1.getPlayer(),
                Players.PLAYER_2.getPlayer(),
                Players.PLAYER_3.getPlayer(),
                Players.PLAYER_4.getPlayer())
                .run(FIRST_DEAL);
        game.run(PLAYERS);

        assertEquals(24, game.getPlayer(Players.PLAYER_1.getId()).getHand().getValue());
        assertEquals(21, game.getPlayer(Players.PLAYER_2.getId()).getHand().getValue());
        assertEquals(19, game.getPlayer(Players.PLAYER_3.getId()).getHand().getValue());
        assertEquals(20, game.getPlayer(Players.PLAYER_4.getId()).getHand().getValue());
    }

    @Test
    public void shouldClearPlayerCardsIfBlackJackDuringCheck(){

        final Game game = new Game.GameBuilder(DeckBuilder.get(Decks.Scenarios.TWO_PLAYERS_ONE_BLACKJACK), Players.PLAYER_1.getPlayer(), Players.PLAYER_2.getPlayer()).run(FIRST_DEAL);
        game.run(BLACKJACK);
        assertEquals(0, game.getPlayer(Players.PLAYER_1.getId()).getHands().size());
        assertEquals(1, game.getPlayer(Players.PLAYER_2.getId()).getHands().size());
    }

    @Test
    public void shouldPlayTheDealer(){
        final Game game = new Game.GameBuilder(DeckBuilder.get(Decks.FIVE_CARDS), Players.PLAYER_1.getPlayer()).run(FIRST_DEAL);
        game.run(DEALER);

        assertEquals(19, game.getDealer().getHand().getValue());
    }

    @Test
    public void shouldBustCheckDealerAndPlayers(){
        final Game game = new Game.GameBuilder(DeckBuilder.get(Decks.Scenarios.ONE_PLAYER_BOTH_DEALER_AND_PLAYER_BUST), Players.PLAYER_1.getPlayer())
                .run(FIRST_DEAL);
        game.run(PLAYERS);
        game.run(DEALER);
        game.run(BUST);

        assertEquals(0, game.getPlayer(Players.PLAYER_1.getId()).getHands().size());
        assertEquals(0, game.getDealer().getHands().size());
    }

    @Test
    public void shouldClearAllCards(){
        final Game game = new Game.GameBuilder(DeckBuilder.get(Decks.THREE_CARDS), Players.PLAYER_1.getPlayer()).run(FIRST_DEAL);
        game.run(FIRST_DEAL);
        game.run(CLEAR);
        assertEquals(0, game.getPlayer(Players.PLAYER_1.getId()).getHands().size());
        assertEquals(0, game.getDealer().getHands().size());
    }
}
