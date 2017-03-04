package se.andolf.blackjack;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.andolf.blackjack.api.*;
import se.andolf.blackjack.util.Checks;
import se.andolf.blackjack.statistics.StatisticsHandler;

public class Game {

    private static final Logger logger = LogManager.getLogger(Game.class);

    final static boolean SMART_PLAYER = true;
	final static boolean DUMB_PLAYER = false;
	final static int ROUNDS = 100;
	final static int PLAYER_POSITIONS = 8;

	private List<Player> playerList = new ArrayList<Player>();
	private Dealer dealer;
	private Deck deck;
	private StatisticsHandler statisticsHandler;

	public Game() {
		dealer = new Dealer();
		deck = new Deck();
		statisticsHandler = new StatisticsHandler();
	}

	public void initPlayers(int players) {

			Player player = new Player(SMART_PLAYER);
			player.setName("Thomas");
			player.setPlayerPositions(1);
			statisticsHandler.createPlayerStats(player.getName());
			playerList.add(player);
	}

	private void FirstDeal() {
		dealAllPlayersOneCardEach();
		dealDealerOneCard();
		dealAllPlayersOneCardEach();
	}

	private void dealAllPlayersOneCardEach() {
		
		for (Player player : playerList) {
			for (int i = 0; i < player.getPlayerPositions(); i++) {
					player.setCurrentHand(i);
					player.reciveCard(deck.getCard());
					logger.info("Player " + player.getName() + " has: " + player.getCurrentHandNoOfCards() + " cards with a total value of: " + player.getHandValueObject().getCurrentValue());					
			}
		}
	}

	private void dealDealerOneCard() {
		dealer.reciveCard(deck.getCard());
		logger.info("Dealer has: " + dealer.getCurrentValue());
	}

	// start the gameloop
	public void start() {
		int played = 0;
		while (played < ROUNDS) {
			
			logger.info("");
			logger.info("---- INITIALIZING HANDS ----");
			initHands();
			logger.info("");
			logger.info("---- INITIALIZING FIRST DEAL ----");
			FirstDeal();

			logger.info("");
			logger.info("---- CHECKING BLACKJACKS ----");
			checkBlackJacks();
			logger.info("---- BLACKJACK CHECK HAS ENDED ----");

			logger.info("");
			logger.info("---- INITIAL DEAL ENDED, STARTING PLAYER ROUNDS ----");
			startPlayerRounds();
			logger.info("---- PLAYER ROUNDS ENDED ----");

			logger.info("");
			logger.info("---- CHECKING BLACKJACKS ----");
			checkBlackJacks();
			logger.info("---- BLACKJACK CHECK HAS ENDED ----");

			logger.info("");
			logger.info("---- INITIAL PLAYER ROUNDS ENDED, STARTING DEALERS ROUND ----");
			startDealerRound();
			logger.info("");
			logger.info("---- COMPARING HANDS ----");
			compareHands();
			logger.info("");
			logger.info("---- GAME OVER RESETTING GAME ----");
			removeAllCardsOnTable();
			logger.info("---- GAME RESET ----");
			statisticsHandler.addRound();
			played++;
		}
		logger.info("");
		logger.info("---- PRINTING GAME STATISTICS ----");
		statisticsHandler.printGameStatistics();
		logger.info("");
		logger.info("---- PRINTING PLAYER STATISTICS ----");
		statisticsHandler.printPlayerStatistics();
	}

	private void initHands() {
		for (Player player : playerList) {
			for(int i = 0; i < player.getPlayerPositions(); i++){
				player.initHand();
				statisticsHandler.addHand();
			}
		}		
	}

	private void checkBlackJacks() {

		for (Player player : playerList) {
			for (int i = 0; i < player.getAllHands().size(); i++) {

				player.setCurrentHand(i);

				if (Checks.isBlackJack(player.getAllHands().get(i))) {

					logger.info("Player " + player.getName() + " HAS BLACKJACK WIIIHUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU!!!!!!!!");
					logger.info("---- CLEARING PLAYER " + player.getName() + "'s CARDS ----");
					player.clearCurrentHand();

					statisticsHandler.addBlackJack(player.getName());
					statisticsHandler.addWin(player.getName());
				}
			}
		}
	}

	private void compareHands() {

		for (Player player : playerList) {
			for (int i = 0; i < player.getAllHands().size(); i++) {

				player.setCurrentHand(i);

				if (Checks.hasWon(player.getCurrentHand().getCurrentHandTotalValue(), dealer.getCurrentValue())) {

					logger.info("Player " + player.getName() + " WINS!");
					statisticsHandler.addWin(player.getName());

				} else {

					logger.info("Player " + player.getName() + " LOOSES!");
					statisticsHandler.addLoss(player.getName());

				}
			}
		}

	}

	private void startPlayerRounds() {
		for (Player player : playerList) {
			for (int i = 0; i < player.getAllHands().size(); i++) {
				if (!player.getAllHands().isEmpty()) {
					player.setCurrentHand(i);
					startPlayingSelectedHand(player);
				}
			}
		}
	}

	// hit = 0, stay = 1, double = 2
	private void startPlayingSelectedHand(Player player) {

		boolean playing = true;
		while (playing) {

			int playerChoice = player.getPlayerChoice();

			if (playerChoice == 0) {
				player.reciveCard(deck.getCard());
				logger.info("Player " + player.getName() + " has: " + player.getCurrentHandNoOfCards() + " cards with a total value of: " + player.getHandValueObject().getCurrentValue());
				playing = bustCheck(player);
			}
			else if (playerChoice == 1) {
				playing = false;
			}
			else if (playerChoice == 2) {
				doubleCards(player);
			}
		}
	}

	private void doubleCards(Player player) {
		Card secondCard = player.getCurrentHand().getCards().get(1);
		
		player.initSecondHandWithCard(secondCard);
		
		player.removeCardFromCurrentHand(1);
		
		player.reciveCard(deck.getCard());
		player.setCurrentHand(player.getCurrentHandIndex()+1);
		player.reciveCard(deck.getCard());
		player.setCurrentHand(player.getCurrentHandIndex()-1);
				
		statisticsHandler.addDouble();
	}

	private boolean bustCheck(Player player) {
		if (Checks.isBust(player.getCurrentHand().getCurrentHandTotalValue())) {

			logger.info("Dealer says brain " + player.getName() + " is bust!");
			logger.info("---- CLEARING PLAYER " + player.getName() + "'s CARDS ----");
			player.removeCurrentHand();
			statisticsHandler.addBusted(player.getName());
			statisticsHandler.addLoss(player.getName());

			return false;
		}
		return true;
	}

	private void startDealerRound() {
		boolean playing = true;
		while (playing) {

			int dealersChoice = dealer.getChoice();

			if (dealersChoice == 0) {
				logger.info(dealer.getName() + " pulls a card");
				dealDealerOneCard();
			}

			else if (dealersChoice == 1) {
				logger.info(dealer.getName() + " says I'LL STAND!");
				playing = false;
			}

			if (Checks.isBust(dealer.getCurrentValue())) {
				logger.info(dealer.getName() + " is bust!");
				playing = false;
			}
		}
	}

	public List<Player> getPlayerList() {
		return playerList;
	}

	// public int getSuitedCardsOnTable() {
	// int suitedCards = 0;
	// for (Player p : playerList) {
	// suitedCards += p.getSuitedCards();
	// }
	// return suitedCards;
	// }

	public Dealer getDealer() {
		return dealer;
	}

	public Deck getDeck() {
		return deck;
	}

	private void removeAllCardsOnTable() {
		for (Player player : playerList) {
			player.getAllHands().clear();
		}
		dealer.getCards().clear();
	}


}