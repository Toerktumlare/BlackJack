package se.andolf.blackjack;

import java.util.ArrayList;
import java.util.List;

import se.andolf.player.Player;
import se.andolf.statistics.StatisticsHandler;

public class Game {

	final static int PLAYERS = 1;
	final static boolean SMART_PLAYER = false;
	final static int ROUNDS = 100;
	final static int PLAYER_POSITIONS = 8;

	private List<Player> playerList = new ArrayList<Player>();
	private Dealer dealer;
	private Deck deck;
	private StatisticsHandler statisticsHandler;

	private void initGame() {
		dealer = new Dealer();
		deck = new Deck();
		statisticsHandler = new StatisticsHandler();
		deck.fill();
		deck.shuffle();
	}

	private void initPlayers(int players) {

		for (int i = 1; i <= players; i++) {
			Player player = new Player(this, SMART_PLAYER);
			player.setName("Thomas");
			player.setPlayerPositions(3);
			statisticsHandler.createPlayerStats(player.getName());
			playerList.add(player);
		}

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
					System.out.println("Player " + player.getName() + " has: " + player.getCurrentHandNoOfCards() + " cards with a total value of: " + player.getHandValueObject().getCurrentValue());					
			}
		}
	}

	private void dealDealerOneCard() {
		dealer.reciveCard(deck.getCard());
		System.out.println("Dealer has: " + dealer.getCurrentValue());
	}

	// start the gameloop
	private void start() {
		int played = 0;
		while (played < ROUNDS) {
			
			System.out.println();
			System.out.println("---- INITIALIZING HANDS ----");
			initHands();
			System.out.println();
			System.out.println("---- INITIALIZING FIRST DEAL ----");
			FirstDeal();

			System.out.println();
			System.out.println("---- CHECKING BLACKJACKS ----");
			checkBlackJacks();
			System.out.println("---- BLACKJACK CHECK HAS ENDED ----");

			System.out.println();
			System.out.println("---- INITIAL DEAL ENDED, STARTING PLAYER ROUNDS ----");
			startPlayerRounds();
			System.out.println("---- PLAYER ROUNDS ENDED ----");

			System.out.println();
			System.out.println("---- CHECKING BLACKJACKS ----");
			checkBlackJacks();
			System.out.println("---- BLACKJACK CHECK HAS ENDED ----");

			System.out.println();
			System.out.println("---- INITIAL PLAYER ROUNDS ENDED, STARTING DEALERS ROUND ----");
			startDealerRound();
			System.out.println();
			System.out.println("---- COMPARING HANDS ----");
			compareHands();
			System.out.println();
			System.out.println("---- GAME OVER RESETTING GAME ----");
			removeAllCardsOnTable();
			System.out.println("---- GAME RESET ----");
			statisticsHandler.addRound();
			played++;
		}
		System.out.println("---- PRINTING GAME STATISTICS ----");
		statisticsHandler.printGameStatistics();
		System.out.println("---- PRINTING PLAYER STATISTICS ----");
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

				if (Checks.blackJackCheck(player.getAllHands().get(i))) {

					System.out.println("Player " + player.getName() + " HAS BLACKJACK WIIIHUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU!!!!!!!!");
					System.out.println("---- CLEARING PLAYER " + player.getName() + "'s CARDS ----");
					player.clearCurrentHand();

					statisticsHandler.addBlackJack(player.getName());
				}
			}
		}
	}

	private void compareHands() {

		for (Player player : playerList) {
			for (int i = 0; i < player.getAllHands().size(); i++) {

				player.setCurrentHand(i);

				if (Checks.winCheck(player.getCurrentHand().getCurrentHandTotalValue(), dealer.getCurrentValue())) {

					System.out.println("Player " + player.getName() + " WINS!");
					statisticsHandler.addWin(player.getName());

				} else {

					System.out.println("Player " + player.getName() + " LOOSES!");
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
				System.out.println("Player " + player.getName() + " has: " + player.getCurrentHandNoOfCards() + " cards with a total value of: " + player.getHandValueObject().getCurrentValue());
				playing = bustCheck(player);
			}
			if (playerChoice == 1) {
				playing = false;
			}
		}
	}

	private boolean bustCheck(Player player) {
		if (Checks.bustCheck(player.getCurrentHand().getCurrentHandTotalValue())) {

			System.out.println("Dealer says player " + player.getName() + " is bust!");
			System.out.println("---- CLEARING PLAYER " + player.getName() + "'s CARDS ----");
			player.removeCurrentHand();
			statisticsHandler.addBusted(player.getName());

			return false;
		}
		return true;
	}

	private void startDealerRound() {

		boolean playing = true;

		while (playing) {

			int choice = dealer.getChoice();

			if (choice == 0) {
				System.out.println(dealer.getName() + " pulls a card");
				dealDealerOneCard();
			}

			else if (choice == 1) {
				System.out.println(dealer.getName() + " says I'LL STAND!");
				playing = false;
			}

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

	private void removeAllCardsOnTable() {
		for (Player player : playerList) {
			player.getAllHands().clear();
		}
		dealer.getCards().clear();
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
