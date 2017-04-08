package se.andolf.blackjack;

import org.junit.Test;
import se.andolf.blackjack.api.*;
import se.andolf.blackjack.api.exception.GameException;
import se.andolf.blackjack.handler.DeckHandler;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static se.andolf.blackjack.api.Deal.ALL;
import static se.andolf.blackjack.api.Deal.DEALER;
import static se.andolf.blackjack.api.Deal.PLAYERS;

/**
 * @author Thomas on 2017-04-08.
 */
public class GameTest {

    @Test(expected = GameException.class)
    public void shouldThrowExceptionIfTryingToStartGameWithoutPlayers() throws GameException {
        final Game game = new GameBuilder().build();
        game.start();
    }

    @Test
    public void shouldAddTwoPlayersAndCreateADealerLast(){
        final Game game = new GameBuilder().addPlayer(new Player("Thomas")).addPlayer(new Player("Frida")).build();
        assertEquals(2, game.getPlayers().stream().filter(p -> !p.isDealer()).count());
        assertEquals(3, game.getPlayers().size());
        assertTrue(game.getPlayers().get(2).isDealer());
    }

    @Test
    public void shouldDealOnlyPlayerOneCard(){
        final List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.SEVEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.NINE, Suit.HEARTS));
        final Deck deck = new Deck(cards);
        final Player thomas = new Player("Thomas");
        final Player frida = new Player("Frida");
        final Game game = new GameBuilder()
                .setDeckHandler(new DeckHandler(deck))
                .addPlayer(thomas)
                .addPlayer(frida)
                .build();
        game.deal(PLAYERS);

        assertEquals(7, game.getPlayer(thomas.getId()).getHand().getCards().stream().findFirst().get().getValue());
        assertEquals(9, game.getPlayer(frida.getId()).getHand().getCards().stream().findFirst().get().getValue());
        assertTrue(game.getDealer().getHands().isEmpty());
    }

    @Test
    public void shouldADealAllOneCards(){
        final List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.SEVEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.NINE, Suit.HEARTS));
        cards.add(new Card(Rank.TWO, Suit.DIAMONDS));
        final Deck deck = new Deck(cards);
        final Player thomas = new Player("Thomas");
        final Player frida = new Player("Frida");
        final Game game = new GameBuilder()
                .setDeckHandler(new DeckHandler(deck))
                .addPlayer(thomas)
                .addPlayer(frida)
                .build();
        game.deal(ALL);

        assertEquals(7, game.getPlayer(thomas.getId()).getHand().getCards().stream().findFirst().get().getValue());
        assertEquals(9, game.getPlayer(frida.getId()).getHand().getCards().stream().findFirst().get().getValue());
        assertEquals(2, game.getDealer().getHand().getCards().stream().findFirst().get().getValue());
    }

    @Test
    public void shouldDealOnlyTheDealerOneCard(){
        final List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.SEVEN, Suit.DIAMONDS));
        final Deck deck = new Deck(cards);
        final Player player = new Player("Thomas");
        final Game game = new GameBuilder()
                .setDeckHandler(new DeckHandler(deck))
                .addPlayer(player)
                .build();
        game.deal(DEALER);
        assertEquals(7, game.getDealer().getHand().getCards().stream().findFirst().get().getValue());
    }

    @Test
    public void shouldSetGameInStartingState(){
        final List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.SEVEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.NINE, Suit.SPADES));
        cards.add(new Card(Rank.ACE, Suit.HEARTS));
        final Deck deck = new Deck(cards);
        final Player player = new Player("Thomas");
        final Game game = new GameBuilder()
                .setDeckHandler(new DeckHandler(deck))
                .addPlayer(player)
                .build();
        game.init();

        assertEquals(2, game.getPlayer(player.getId()).getHand().getCards().size());
        assertEquals(1, game.getDealer().getHand().getCards().size());
    }
}
