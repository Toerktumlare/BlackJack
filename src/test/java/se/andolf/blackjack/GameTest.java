package se.andolf.blackjack;

import org.junit.Test;
import se.andolf.blackjack.api.*;
import se.andolf.blackjack.api.exception.GameException;
import se.andolf.blackjack.handler.DeckHandler;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static se.andolf.blackjack.api.Deal.*;

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

    @Test
    public void playPlayerHandsUntilStand() {
        final List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.SEVEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.NINE, Suit.SPADES));
        cards.add(new Card(Rank.KING, Suit.HEARTS));
        cards.add(new Card(Rank.KING, Suit.DIAMONDS));
        cards.add(new Card(Rank.KING, Suit.CLUBS));
        final Deck deck = new Deck(cards);
        final Player player = new Player("Thomas");
        final Player player2 = new Player("Frida");
        final Game game = new GameBuilder()
                .setDeckHandler(new DeckHandler(deck))
                .addPlayer(player)
                .addPlayer(player2)
                .build();

        game.init();
        game.play(player);
        game.play(player2);

        assertEquals(17, player.getHand().getValue());
        assertEquals(19, player2.getHand().getValue());
    }

    @Test
    public void playAllPlayerHandsUntilStand() {
        final List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.SEVEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.NINE, Suit.SPADES));
        cards.add(new Card(Rank.KING, Suit.HEARTS));
        cards.add(new Card(Rank.KING, Suit.DIAMONDS));
        cards.add(new Card(Rank.KING, Suit.CLUBS)); //Dealers card
        cards.add(new Card(Rank.SEVEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.FIVE, Suit.DIAMONDS));
        cards.add(new Card(Rank.NINE, Suit.SPADES));
        cards.add(new Card(Rank.KING, Suit.HEARTS));

        // Round starts here
        cards.add(new Card(Rank.KING, Suit.DIAMONDS));
        cards.add(new Card(Rank.SEVEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.FOUR, Suit.DIAMONDS));
        final Deck deck = new Deck(cards);
        final Player player = new Player("Thomas");
        final Player player2 = new Player("Frida");
        final Player player3 = new Player("Kalle");
        final Player player4 = new Player("Anders");
        final Game game = new GameBuilder()
                .setDeckHandler(new DeckHandler(deck))
                .addPlayer(player)
                .addPlayer(player2)
                .addPlayer(player3)
                .addPlayer(player4)
                .build();

        game.init();
        game.play(game.getPlayers());

        assertEquals(24, player.getHand().getValue());
        assertEquals(21, player2.getHand().getValue());
        assertEquals(19, player3.getHand().getValue());
        assertEquals(20, player4.getHand().getValue());
    }

    @Test
    public void shouldClearPlayerCardsIfBlackJackDuringCheck(){
        final List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.KING, Suit.DIAMONDS));
        cards.add(new Card(Rank.NINE, Suit.SPADES));
        cards.add(new Card(Rank.KING, Suit.HEARTS));
        cards.add(new Card(Rank.ACE, Suit.DIAMONDS));
        cards.add(new Card(Rank.KING, Suit.CLUBS));

        final Deck deck = new Deck(cards);
        final Player player = new Player("Thomas");
        final Player player2 = new Player("Frida");
        final Game game = new GameBuilder()
                .setDeckHandler(new DeckHandler(deck))
                .addPlayer(player)
                .addPlayer(player2)
                .build();
        game.init();

        game.checkBlackJack(game.getPlayers());

        assertEquals(0, player.getHands().size());
        assertEquals(1, player2.getHands().size());
    }
}
