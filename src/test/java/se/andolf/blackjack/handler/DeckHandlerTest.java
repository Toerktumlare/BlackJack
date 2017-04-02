package se.andolf.blackjack.handler;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Thomas on 2017-03-04.
 */
public class DeckHandlerTest {

    @Test
    public void shouldGenerateDefaultValueOfDecks(){
        final int expected = 312;
        final DeckHandler deckHandler = new DeckHandler();
        final int cardsLeft = deckHandler.size();

        assertEquals(expected, cardsLeft);
    }
}
