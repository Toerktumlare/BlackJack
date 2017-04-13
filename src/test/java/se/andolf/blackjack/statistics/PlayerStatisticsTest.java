package se.andolf.blackjack.statistics;

import org.junit.Test;
import se.andolf.blackjack.Game;
import se.andolf.blackjack.api.*;
import se.andolf.blackjack.handler.DeckHandler;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static se.andolf.blackjack.api.GameState.GAME;

/**
 * @author Thomas on 2017-04-13.
 */
public class PlayerStatisticsTest {

    @Test
    public void shouldRegisterAWinForThePlayer(){
        final List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.SEVEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.NINE, Suit.SPADES));
        cards.add(new Card(Rank.SEVEN, Suit.SPADES));
        cards.add(new Card(Rank.SEVEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.EIGHT, Suit.SPADES));
        final Deck deck = new Deck(cards);
        final Player player = new Player("Thomas");
        final Game game = new GameBuilder()
                .setDeckHandler(new DeckHandler(deck))
                .addPlayer(player)
                .build();
        game.run();

        assertEquals(1, game.getPlayer(player.getId()).getStatistics().getWins());
        assertEquals(1, game.getStatistics().getRounds());
    }

    @Test
    public void shouldRegisterALossForThePlayer(){
        final List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.SEVEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.NINE, Suit.SPADES));
        cards.add(new Card(Rank.SEVEN, Suit.SPADES));
        cards.add(new Card(Rank.THREE, Suit.DIAMONDS));
        cards.add(new Card(Rank.QUEEN, Suit.SPADES));
        final Deck deck = new Deck(cards);
        final Player player = new Player("Thomas");
        final Game game = new GameBuilder()
                .setDeckHandler(new DeckHandler(deck))
                .addPlayer(player)
                .build();
        game.run();

        assertEquals(1, game.getPlayer(player.getId()).getStatistics().getLosses());
        assertEquals(1, game.getStatistics().getRounds());
    }

    @Test
    public void shouldRegisterADraw(){
        final List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.KING, Suit.DIAMONDS));
        cards.add(new Card(Rank.JACK, Suit.SPADES));
        cards.add(new Card(Rank.QUEEN, Suit.SPADES));
        cards.add(new Card(Rank.JACK, Suit.DIAMONDS));
        final Deck deck = new Deck(cards);
        final Player player = new Player("Thomas");
        final Game game = new GameBuilder()
                .setDeckHandler(new DeckHandler(deck))
                .addPlayer(player)
                .build();
        game.run();

        assertEquals(1, game.getPlayer(player.getId()).getStatistics().getDraws());
        assertEquals(1, game.getStatistics().getRounds());
    }
}
