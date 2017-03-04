package se.andolf.blackjack.handler;

import org.junit.Test;
import se.andolf.blackjack.handler.DeckHandler;

import static org.junit.Assert.assertEquals;

/**
 * @author Thomas on 2017-03-04.
 */
public class DeckHandlerTest {

    @Test
    public void shouldGenerateDefaultValueOfDecks(){
        final int expected = 312;
        final DeckHandler deckHandler = new DeckHandler();
        final int cardsLeft = deckHandler.getCardsLeft();

        assertEquals(expected, cardsLeft);
    }

}
