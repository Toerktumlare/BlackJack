package se.andolf.blackjack;

import org.junit.Test;

/**
 * @author Thomas on 2017-03-04.
 */
public class DeckHandlerTest {

    @Test
    public void shouldGenerateOneDeck(){
        final int noOfDecks = 1;
        final DeckHandler deckHandler = new DeckHandler(noOfDecks);
    }

}
