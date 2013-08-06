package se.andolf.blackjack;

import java.util.ArrayList;
import java.util.List;

import se.andolf.player.Player;
import se.andolf.statistics.StatisticsHandler;

public class Game {

	final static int PLAYERS = 3;
	final static boolean SMART_PLAYERS = false;
	private final int ROUNDS = 10000;
	
	private List<Player> playerList = null;
	private Dealer dealer;
	private Deck deck;
	private StatisticsHandler statisticsHandler;
	
	//init the game
	private void initGame() {
		dealer = new Dealer();
		deck = new Deck();
		statisticsHandler = new StatisticsHandler();
		deck.fill();
		deck.shuffle();
	}
	
	//add players to the game
	private void initPlayers(int players) {
		
		if (playerList == null) {
			playerList = new ArrayList<Player>();
		}
		
		for (int i = 1; i <= players; i++) {
			String name = Integer.toString(i);
			Player player = new Player(this, name, SMART_PLAYERS);
			statisticsHandler.createPlayerStats(name);
			playerList.add(player);
		}
		
	}
	
	//first deal 2 cards to each player, 1 to the dealer
	private void initDeal() {
		
		//first card to each player
		for (Player p : playerList) {
			p.reciveCard(deck.dealCard());
			int currentValue = p.getCurrentValue().getCurrentValue();
			if(p.getAces() > 0){
				System.out.println("Player " + p.getName() + " has: " + p.getNoOfCards() + " cards with a total value of: " + p.getCurrentValue().getBlackJackValue());
			} else {
				System.out.println("Player " + p.getName() + " has: " + p.getNoOfCards() + " cards with a total value of: " + currentValue);				
			}
		}
		
		//one card to the dealer
		if (dealer.getNoOfCards() == 0) {
			dealer.reciveCard(deck.dealCard());
			System.out.println("Dealer has: " + dealer.getCurrentValue());
		}
		
		//second card to each player
		for (Player p : playerList) {
			p.reciveCard(deck.dealCard());
			int currentValue = p.getCurrentValue().getCurrentValue();
			if(p.getAces() > 0){
				System.out.println("Player " + p.getName() + " has: " + p.getNoOfCards() + " cards with a total value of: " + p.getCurrentValue().getBlackJackValue());
			} else {
				System.out.println("Player " + p.getName() + " has: " + p.getNoOfCards() + " cards with a total value of: " + currentValue);				
			}		}		
	}
	
	//start the gameloop
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
		//checking players
		for(Player p : playerList){
			if(Checks.blackJackCheck(p.getCards())){
				System.out.println("Player " + p.getName() + " HAS BLACKJACK WIIIHUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU!!!!!!!!");
				System.out.println("---- CLEARING PLAYER " + p.getName() + "'s CARDS ----");
				statisticsHandler.addBlackJack(p.getName());
				p.clearCards();
			}
		}
	System.out.println("---- BLACKJACK CHECK HAS ENDED ----");
	}

	private void resetTable() {
		for(Player p : playerList){
			if(p.getNoOfCards() != 0){
				p.clearCards();
			}
		}
		dealer.clearCards();
	}

	private void compareHands() {
		// loop all players hands
		for (Player p : playerList) {
			// check if there is a win
			if (Checks.winCheck(p.getCurrentValue().getCurrentValue(), dealer.getCurrentValue())) {
				System.out.println("Player " + p.getName() + " WINS!");
				statisticsHandler.addWin(p.getName());
			} else {
				System.out.println("Player " + p.getName() + " LOOSES!");
				statisticsHandler.addLoss(p.getName());
			}
		}

	}
	
	//player rounds
	private void startPlayerRounds() {

		for (Player p : playerList) {
			boolean playing = true;
			if(!p.getCards().isEmpty()){
				while (playing) {
					
					//asking player to return a choice
					int choice = p.getChoice();
					
					// if choice is 0 give card
					if (choice == 0) {
						System.out.println("player: " + p.getName() + " yells out HIT ME!");
						p.reciveCard(deck.dealCard());
						int currentValue = p.getCurrentValue().getCurrentValue();
						if(p.getAces() > 0){
							System.out.println("Player " + p.getName() + " has: " + p.getNoOfCards() + " cards with a total value of: " + p.getCurrentValue().getBlackJackValue());
						} else {
							System.out.println("Player " + p.getName() + " has: " + p.getNoOfCards() + " cards with a total value of: " + currentValue);				
						}
					}
					
					// if choice is 1 stay
					if (choice == 1) {
						System.out.println("player " + p.getName() + " says I'LL STAND!");
						playing = false;
					}
					
					// bust check!
					if (Checks.bustCheck(p.getCurrentValue().getCurrentValue())) {
						System.out.println("Player " + p.getName() + " is bust!");
						System.out.println("---- CLEARING PLAYER " + p.getName() + "'s CARDS ----");
						statisticsHandler.addBusted(p.getName());
						p.clearCards();
						playing = false;
					}
				}				
			}
		}
	}
	
	//dealer round
	private void dealerPlays() {

		boolean playing = true;

		while (playing) {

			int choice = dealer.makeChoice();

			// if choice is 0 give card
			if (choice == 0) {
				System.out.println(dealer.getName() + " pulls a card");
				dealer.reciveCard(deck.dealCard());
				System.out.println(dealer.getName() + " has " + dealer.getNoOfCards() + " cards with a total value of: " + dealer.getCurrentValue());
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

	public int getSuitedCardsOnTable() {
		int suitedCards = 0;
		for (Player p : playerList) {
			suitedCards += p.getSuitedCards();
		}
		return suitedCards;
	}
	
	public Dealer getDealer() {
		return dealer;
	}
	
	public Deck getDeck() {
		return deck;
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
