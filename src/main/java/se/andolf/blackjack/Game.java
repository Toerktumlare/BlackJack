package se.andolf.blackjack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.andolf.blackjack.api.*;
import se.andolf.blackjack.api.exception.GameException;
import se.andolf.blackjack.brain.DumbBrain;
import se.andolf.blackjack.handler.DeckHandler;
import se.andolf.blackjack.util.Checks;

import java.util.List;
import java.util.stream.IntStream;

import static se.andolf.blackjack.api.Choice.*;
import static se.andolf.blackjack.api.Deal.*;
import static se.andolf.blackjack.api.Deal.ALL;

public class Game {

    private static final Logger logger = LogManager.getLogger(Game.class);

	private List<Player> players;
	private Player dealer;
    private GameStatistic gameStatistic;
    private DeckHandler deckHandler;

	public Game(List<Player> players, DeckHandler deckHandler) {
		dealer = new Player("Dealer", new DumbBrain(), true);
		this.deckHandler = deckHandler;
        this.players = players;
        gameStatistic = new GameStatistic();
	}

	public void blackJackCheck() {

		for (Player player : players) {
			for (int i = 0; i < player.getHands().size(); i++) {

				if (Checks.isBlackJack(player.getHand(i))) {

					logger.info("Player " + player.getName() + " HAS BLACKJACK WIIIHUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU!!!!!!!!");
					logger.info("---- CLEARING PLAYER " + player.getName() + "'s CARDS ----");
					player.clearHand(i);

                    player.getStatistics().addBlackJack();
                    player.getStatistics().addWin();
				}
			}
		}
	}

	public void winCheck() {

		for (Player player : players) {
			for (int i = 0; i < player.getHands().size(); i++) {

				player.setCurrentHand(i);

				if (Checks.hasWon(player.getHand().getValue(), dealer.getHand().getValue())) {

					logger.info("Player " + player.getName() + " WINS!");

                    player.getStatistics().addWin();

				} else {

					logger.info("Player " + player.getName() + " LOOSES!");

                    player.getStatistics().addLoss();

				}
			}
		}
	}

	public void startPlayerRounds() {
		for (Player player : players) {
			for (int i = 0; i < player.getHands().size(); i++) {
				if (!player.getHands().isEmpty()) {
					player.setCurrentHand(i);
					startPlayingSelectedHand(player);
				}
			}
		}
	}

	private void startPlayingSelectedHand(Player player) {

		boolean playing = true;
		while (playing) {

			final Choice playerChoice = player.getChoice();

			if (playerChoice == HIT) {
				player.addCard(deckHandler.getCard());
				playing = checkBust(player);
			}
			else if (playerChoice == STAND) {
				playing = false;
			}
			else if (playerChoice == SPLIT) {
				doubleCards(player);
			}
		}
	}

	private void doubleCards(Player player) {
		final Card secondCard = player.getHand().getCards().get(1);
		
		player.addHand(secondCard);
		
		player.removeCardFromCurrentHand(1);
		
		player.addCard(deckHandler.getCard());
		player.setCurrentHand(player.getCurrentHandIndex()+1);
		player.addCard(deckHandler.getCard());
		player.setCurrentHand(player.getCurrentHandIndex()-1);

		gameStatistic.addDouble();
	}

	private boolean checkBust(Player player) {
		if (Checks.isBust(player.getHand().getValue())) {

			logger.info("Dealer says brain " + player.getName() + " is bust!");
			logger.info("---- CLEARING PLAYER " + player.getName() + "'s CARDS ----");
			player.removeCurrentHand();

			player.getStatistics().addBust();
			player.getStatistics().addLoss();

			return false;
		}
		return true;
	}

	public void startDealerRound() {
		boolean playing = true;
		while (playing) {

			final Choice dealersChoice = dealer.getChoice();

			if (dealersChoice == HIT) {
				logger.info(dealer.getName() + " pulls a card");
				deal(DEALER);
			}

			else if (dealersChoice == STAND) {
				logger.info(dealer.getName() + " says I'LL STAND!");
				playing = false;
			}

			if (Checks.isBust(dealer.getHand().getValue())) {
				logger.info(dealer.getName() + " is bust!");
				playing = false;
			}
		}
	}

	public void clearCards() {
        players.stream().forEach(player -> player.getHands().clear());
	}

    void deal(Deal deal) {
        deal(deal, 1);
    }

    private void deal(Deal who, int cards) {
        switch (who) {
            case DEALER:
                dealer.addCard(deckHandler.getCard());
                break;
            case PLAYERS:
                players.stream().forEach(p -> p.addCard(deckHandler.getCard()));
                break;
            default:
                players.stream().forEach(p -> p.addCard(deckHandler.getCard()));
                dealer.addCard(deckHandler.getCard());
        }
    }

    public Player getPlayer(String id){
        return players.stream().filter(p -> p.getId().equals(id)).distinct().findFirst().orElse(null);
    }

    Player getDealer() {
        return dealer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    void play(Player player) {
        boolean isPlaying = true;
        while (isPlaying) {
            final Choice choice = player.getChoice();
            if (choice == HIT) {
                player.addCard(deckHandler.getCard());
            }
            else if (choice == STAND) {
                isPlaying = false;
            }
        }
    }

    public void play(List<Player> players) {
        players.stream().forEach(this::play);
    }

    public void checkBlackJack(List<Player> players) {
        players.stream().filter(player -> !player.isDealer()).forEach(player -> {
            final List<Hand> hands = player.getHands();
            IntStream.range(0, hands.size()).forEach(i -> {
                if(Checks.isBlackJack(hands.get(i))){
                    player.clearHand(i);
                }
            });
        });
    }

    public void run(GameState state) {
        switch (state) {
            case FIRST_DEAL:
                deal(ALL);
                deal(PLAYERS);
                break;
            case PLAYERS:
                play(players);
                break;
            case DEALER:
                startDealerRound();
                break;
            case CLEAR:
                clearCards();
                break;
        }
    }

    public void run(GameState.Checks checks) {
        switch (checks) {
            case ALL:
                break;
            case BLACKJACK:
                checkBlackJack(players);
                break;
            case TWENTYONE:
                break;
            case WIN:
                winCheck();
                break;
            case BUST:

                break;
        }
    }
}