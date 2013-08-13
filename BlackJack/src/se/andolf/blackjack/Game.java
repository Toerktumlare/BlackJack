package se.andolf.blackjack;

import java.util.ArrayList;
import java.util.List;

import se.andolf.player.Hand;
import se.andolf.player.Player;
import se.andolf.statistics.StatisticsHandler;

public class Game {

	final static int PLAYERS = 1;
	final static boolean SMART_PLAYERS = true;
	private final int ROUNDS = 1000;

	private List<Player> playerList = new ArrayList<Player>();
	private Dealer dealer;
	private Deck deck;
	private StatisticsHandler statisticsHandler;

	// init the game
	private void initGame() {
		dealer = new Dealer();
		deck = new Deck();
		statisticsHandler = new StatisticsHandler();
		deck.fill();
		deck.shuffle();
	}

	// add players to the game
	private void initPlayers(int players) {

		for (int i = 1; i <= players; i++) {
			String name = Integer.toString(i);
			Player player = new Player(this, name, SMART_PLAYERS);
			statisticsHandler.createPlayerStats(name);
			playerList.add(player);
		}

	}

	// first deal 2 cards to each player, 1 to the dealer
	private void initDeal() {

		dealAllPlayersOneCardEach();

		dealDealerOneCard();

		dealAllPlayersOneCardEach();
	}

	private void dealAllPlayersOneCardEach() {

		for (Player player : playerList) {
			player.reciveCard(deck.dealCard(), 0);
			int currentValue = player.getHandValueObject(whatHand).getCurrentValue();
			
			String name = player.getName();
			if (player.getAces() > 0) {
				System.out.println("Player " + name + " has: " + player.getNoOfCards() + " cards with a total value of: " + player.getHandValueObject(whatHand).getBlackJackValue());
			} else {
				System.out.println("Player " + name + " has: " + player.getNoOfCards() + " cards with a total value of: " + currentValue);
			}
		}
	}

	private void dealDealerOneCard() {
		dealer.reciveCard(deck.dealCard());
		System.out.println("Dealer has: " + dealer.getCurrentValue());
	}

	// start the gameloop
	private void start() {
		int i = 0;
		while (i < ROUNDS) {

			System.out.println();
			System.out.println("---- INITIALIZING FIRST DEAL ----");
			initDeal();

			System.out.println();
			System.out.println("---- CHECKING BLACKJACKS ----");
			checkBlackJacks();

			System.out.println();
			System.out.println("---- INITIAL DEAL ENDED, STARTING PLAYER ROUNDS ----");
			startPlayerRounds();

			System.out.println();
			System.out.println("---- CHECKING BLACKJACKS ----");
			checkBlackJacks();

			System.out.println();
			System.out.println("---- INITIAL PLAYER ROUNDS ENDED, STARTING DEALERS ROUND ----");
			dealerPlays();
			System.out.println();
			System.out.println("---- COMPARING HANDS ----");
			compareHands();
			System.out.println();
			System.out.println("---- GAME OVER RESETTING GAME ----");
			resetTable();
			statisticsHandler.addRound();
			i++;
		}
		System.out.println("---- PRINTING GAME STATISTICS ----");
		statisticsHandler.printGameStatistics();
		System.out.println("---- PRINTING PLAYER STATISTICS ----");
		statisticsHandler.printPlayerStatistics();
	}

	private void checkBlackJacks() {

		for (Player player : playerList) {

			List<Hand> hands = player.getHands();

			for (int whatHand = 0; whatHand < hands.size(); whatHand++) {

				if (Checks.blackJackCheck(hands.get(whatHand))) {

					System.out.println("Player " + player.getName() + " HAS BLACKJACK WIIIHUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU!!!!!!!!");
					System.out.println("---- CLEARING PLAYER " + player.getName() + "'s CARDS ----");

					statisticsHandler.addBlackJack(player.getName());

					hands.remove(whatHand);
				}
			}
		}
		System.out.println("---- BLACKJACK CHECK HAS ENDED ----");
	}

	private void compareHands() {

		for (Player player : playerList) {

			List<Hand> hands = player.getHands();

			for (int whatHand = 0; whatHand < hands.size(); whatHand++) {

				int playerCurrentValue = player.getHandValueObject(whatHand).getCurrentValue();
				int dealerCurrentValue = dealer.getCurrentValue();

				String name = player.getName();
				if (Checks.winCheck(playerCurrentValue, dealerCurrentValue)) {

					System.out.println("Player " + name + " WINS!");
					statisticsHandler.addWin(name);

				} else {

					System.out.println("Player " + name + " LOOSES!");
					statisticsHandler.addLoss(name);

				}
			}
		}

	}

	// player rounds
	private void startPlayerRounds() {

		// go to each player
		for (Player player : playerList) {

			List<List<Card>> playerHands = player.getHands();

			// if player has any hands
			if (!playerHands.isEmpty()) {

				for (int hand = 0; hand < playerHands.size(); hand++) {

					startPlayingCurrentHand(player, hand);
				}
			}
		}
	}

	private void startPlayingCurrentHand(Player player, int whatHand) {

		boolean playing = true;
		String name = player.getName();

		while (playing) {

			int choice = player.getChoice(whatHand);

			// if choice is 0 give card
			if (choice == 0) {

				player.reciveCard(deck.dealCard(), whatHand);

				int currentValue = player.getHandValueObject(whatHand).getCurrentValue();
				int noOfCards = player.getNoOfCards();

				if (player.getAces() > 0) {
					System.out.println("Player " + name + " has: " + noOfCards + " cards with a total value of: " + player.getCurrentValueObject().getBlackJackValue());
				} else {
					System.out.println("Player " + name + " has: " + noOfCards + " cards with a total value of: " + currentValue);
				}
			}

			// if choice is 1 stay
			if (choice == 1) {
				playing = false;
			}

			// after each choice make a bust check!
			playing = bustCheck(player, whatHand);
		}
	}

	private boolean bustCheck(Player player, int whatHand) {
		if (Checks.bustCheck(player.getHandValueObject(whatHand).getCurrentValue())) {

			String name = player.getName();

			System.out.println("Player " + name + " is bust!");
			System.out.println("---- CLEARING PLAYER " + name + "'s CARDS ----");
			player.clearHand(whatHand);

			statisticsHandler.addBusted(name);

			return false;
		}
		return true;
	}

	// dealer round
	private void dealerPlays() {

		boolean playing = true;

		while (playing) {

			int choice = dealer.makeChoice();

			// if choice is 0 give card
			if (choice == 0) {

				System.out.println(dealer.getName() + " pulls a card");
				dealDealerOneCard();

			}

			// if choice is 1 stay
			if (choice == 1) {
				System.out.println(dealer.getName() + " says I'LL STAND!");
				playing = false;
			}

			// bust check!
			if (Checks.bustCheck(dealer.getCurrentValue())) {
				System.out.println(dealer.getName() + " is bust!");
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

	private void resetTable() {
		for (Player p : playerList) {
			if (p.getNoOfCards() != 0) {
				p.clearCards();
			}
		}
		dealer.clearCards();
	}

	public static void main(String[] args) {

		Game game = new Game();

		System.out.println("---- INITIALIZING GAME ----");
		game.initGame();

		System.out.println("---- INITIALIZING PLAYERS ----");
		game.initPlayers(PLAYERS);

		System.out.println("---- STARTING GAME ----");
		game.start();
	}

}
