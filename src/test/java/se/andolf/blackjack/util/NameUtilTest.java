package se.andolf.blackjack.util;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;

/**
 * @author Thomas on 2017-10-22.
 */
public class NameUtilTest {

    @Test
    public void shouldReturnRandomNameFromListOfRandomNames() {
        final String name = NamesUtil.getName();
        final List<String> names = NamesUtil.getNames();
        assertThat(names, hasItem(name));
    }

    @Test
    public void shouldReturn100Names() {
        assertThat(NamesUtil.getNames().size(), equalTo(100));
    }
}
