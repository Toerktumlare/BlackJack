package se.andolf.blackjack.util;

import se.andolf.blackjack.api.Player;

/**
 * @author Thomas on 2017-05-06.
 */
public enum Players {
    PLAYER_1("Thomas"),
    PLAYER_2("Frida"),
    PLAYER_3("Adders"),
    PLAYER_4("Pelle");

    private Player player;

    Players(String name) {
        this.player = new Player(name);
    }

    public Player getPlayer() {
        return player;
    }

    public String getId() {
        return player.getId();
    }

    public void reset(){
        this.player = new Player(player.getName());
    }
}
