package se.andolf.blackjack.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Thomas on 2017-04-08.
 */
public class PlayerTest {

    @Test
    public void shouldCreatePlayerWithNameThomas(){
        final Player player = new Player("Thomas");
        assertEquals("Thomas", player.getName());
    }

    @Test
    public void shouldCreateDealer(){
        final Player player = new Player("Dealer", true);
        assertEquals("Dealer", player.getName());
        assertTrue(player.isDealer());
    }

    @Test
    public void shouldCreatePlayerAndBeAbleToDealHimTwoCards(){
        final Player player = new Player("Thomas");
        player.addCard(new Card(Rank.NINE, Suit.HEARTS));
        player.addCard(new Card(Rank.SEVEN, Suit.SPADES));
        assertEquals(2, player.getHand().getCards().size());
    }

    @Test
    public void shouldReturnPlayerWantsToHit(){
        final Player player = new Player("Thomas");
        player.addCard(new Card(Rank.FIVE, Suit.HEARTS));
        player.addCard(new Card(Rank.THREE, Suit.SPADES));
        assertEquals(Choice.HIT, player.getChoice());
    }

    @Test
    public void shouldReturnPlayerWantsToStand(){
        final Player player = new Player("Thomas");
        player.addCard(new Card(Rank.JACK, Suit.HEARTS));
        player.addCard(new Card(Rank.KING, Suit.SPADES));
        assertEquals(Choice.STAND, player.getChoice());
    }

    @Test
    public void shouldReturnPlayerWantsToStandIfWayOver21(){
        final Player player = new Player("Thomas");
        player.addCard(new Card(Rank.JACK, Suit.HEARTS));
        player.addCard(new Card(Rank.KING, Suit.SPADES));
        player.addCard(new Card(Rank.QUEEN, Suit.DIAMONDS));
        assertEquals(Choice.STAND, player.getChoice());
    }
}
