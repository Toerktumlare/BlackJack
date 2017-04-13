package se.andolf.blackjack;

import org.junit.Test;
import se.andolf.blackjack.api.*;
import se.andolf.blackjack.handler.DeckHandler;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static se.andolf.blackjack.api.Deal.ALL;
import static se.andolf.blackjack.api.GameState.Checks.BLACKJACK;
import static se.andolf.blackjack.api.GameState.*;

/**
 * @author Thomas on 2017-04-08.
 */
public class GameTest {

    @Test
    public void shouldAddTwoPlayersAndCreateADealer(){
        final Game game = new GameBuilder().addPlayer(new Player("Thomas")).addPlayer(new Player("Frida")).build();
        assertEquals(2, game.getPlayers().size());
        assertNotNull(game.getDealer());
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
        game.deal(Deal.PLAYERS);

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
        game.deal(Deal.DEALER);
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
        game.run(FIRST_DEAL);

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

        game.run(FIRST_DEAL);
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

        game.run(FIRST_DEAL);
        game.run(PLAYERS);

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
        game.run(FIRST_DEAL);
        game.run(BLACKJACK);

        assertEquals(0, player.getHands().size());
        assertEquals(1, player2.getHands().size());
    }

    @Test
    public void shouldPlayTheDealer(){
        final List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.SEVEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.NINE, Suit.SPADES));
        cards.add(new Card(Rank.SEVEN, Suit.SPADES));
        cards.add(new Card(Rank.SEVEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.THREE, Suit.SPADES));
        final Deck deck = new Deck(cards);
        final Player player = new Player("Thomas");
        final Game game = new GameBuilder()
                .setDeckHandler(new DeckHandler(deck))
                .addPlayer(player)
                .build();
        game.run(FIRST_DEAL);
        game.run(DEALER);

        assertEquals(19, game.getDealer().getHand().getValue());
    }
}
