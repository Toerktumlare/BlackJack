package se.andolf.blackjack;

import org.junit.Test;
import se.andolf.blackjack.api.*;
import se.andolf.blackjack.api.exception.GameException;
import se.andolf.blackjack.handler.DeckHandler;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
    public void shouldAddTwoPlayersAndDealEachPlayerTwoCards(){
        final List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.SEVEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.NINE, Suit.HEARTS));
        final Deck deck = new Deck(cards);

        final Game game = new GameBuilder()
                .setDeckHandler(new DeckHandler(deck))
                .addPlayer(new Player("Thomas"))
                .addPlayer(new Player("Frida"))
                .build();
        game.deal(PLAYERS);

        game.getPlayers().stream().filter(p -> !p.isDealer()).forEach(player -> {
            if(player.getName().equals("Thomas"))
                assertEquals(7, player.getHand().getCards().stream().findFirst().get().getValue());
            if(player.getName().equals("Frida")) {
                assertEquals(9, player.getHand().getCards().stream().findFirst().get().getValue());
            }
        });
    }

    @Test
    public void shouldAddTwoPlayersAndDealAllPlayersAndDealerOneCards(){
        final List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.SEVEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.NINE, Suit.HEARTS));
        cards.add(new Card(Rank.TWO, Suit.DIAMONDS));
        final Deck deck = new Deck(cards);

        final Game game = new GameBuilder()
                .setDeckHandler(new DeckHandler(deck))
                .addPlayer(new Player("Thomas"))
                .addPlayer(new Player("Frida"))
                .build();
        game.deal(ALL);

        game.getPlayers().stream().filter(p -> !p.isDealer()).forEach(player -> {
            if(player.getName().equals("Thomas"))
                assertEquals(7, player.getHand().getCards().stream().findFirst().get().getValue());
            if(player.getName().equals("Frida"))
                assertEquals(9, player.getHand().getCards().stream().findFirst().get().getValue());
            if(player.getName().equals("Dealer"))
                assertEquals(2, player.getHand().getCards().stream().findFirst().get().getValue());
        });
    }

    @Test
    public void shouldAddOnePlayersAndDealTheDealerOneCard(){
        final List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.SEVEN, Suit.DIAMONDS));
        final Deck deck = new Deck(cards);

        final Game game = new GameBuilder()
                .setDeckHandler(new DeckHandler(deck))
                .addPlayer(new Player("Thomas"))
                .build();
        game.deal(DEALER);

        game.getPlayers().stream().filter(Player::isDealer).findFirst().ifPresent(p ->
            assertEquals(7, p.getHand().getCards().stream().findFirst().get().getValue())
        );
    }

    @Test
    public void shouldSetGameInStartingState(){
        final List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.SEVEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.NINE, Suit.SPADES));
        cards.add(new Card(Rank.ACE, Suit.HEARTS));
        final Deck deck = new Deck(cards);

        final Game game = new GameBuilder()
                .setDeckHandler(new DeckHandler(deck))
                .addPlayer(new Player("Thomas"))
                .build();
        game.init();

        game.getPlayers().stream().forEach(p -> {
            game.getPlayers().stream()
                    .filter(player -> !player.isDealer())
                    .findFirst()
                    .ifPresent(player ->
                        assertEquals(2, player.getHand().getCards().size())
                    );
            game.getPlayers().stream()
                    .filter(Player::isDealer)
                    .findFirst()
                    .ifPresent(dealer ->
                            assertEquals(1, dealer.getHand().getCards().size())
                    );
        });
    }
}
