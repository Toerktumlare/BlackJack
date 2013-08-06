package se.andolf.player;


public class SmartBrain implements Brain{
	
//	private final int DECK_SIZE = 20;

	@Override
	public int getChoice(int currentValue) {
		// TODO Smart Brain that takes more things into concideration
		
//		int dealerValue = game.getDealer().getCurrentValue();
//		int cardsLeft = game.getDeck().getDeckSize();
//		int suitedCards = game.getSuitedCardsOnTable();

		// return 1 if no card, 0 if card

		// if player has 16 and dealer has ace - CARD
		// if(getCurrentValue() == 16 || dealerValue == 1){
		// return 0;
		// }
		//
		// if player has lower than 16 - CARD
		if (currentValue < 16) {
			return 0;
		}
		//
		// //if player has 16 or lower, dealer has ace and decksize is lower
		// than factor - CARD
		// if(getCurrentValue() <= 16 || dealerValue == 1 || cardsLeft <
		// DECK_SIZE_FACTOR){
		// return 0;
		// }
		//
		// if player has 16 or higher - STAND
		if (currentValue >= 16) {
			return 1;
		}

		else {
			return 1;
		}
		
	}

}
