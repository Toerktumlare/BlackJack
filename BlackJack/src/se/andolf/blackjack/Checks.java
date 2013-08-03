package se.andolf.blackjack;

import java.util.List;

public final class Checks {

	static boolean bustCheck(int currentValue) {
		if (currentValue <= 21) {
			return false;
		}
		return true;
	}

	static boolean blackJackCheck(List<Card> Cards) {
		if(!Cards.isEmpty()){
			int card1 = Cards.get(0).getValue();
			int card2 = Cards.get(1).getValue();
			
			if(card1 == 1 && card2 == 10){
				return true;
			}
			if(card1 == 10 && card2 == 1){
				return true;
			}
			
			int valueOfCards = 0;
			for(Card c : Cards){
				valueOfCards += c.getValue();
			}
			if(valueOfCards == 21){
				return true;
			}
			return false;			
		}
		return false;
	}

	static boolean winCheck(int playerValue, int dealerValue) {

		//player higher than dealer and deler is below 21 - PLAYER WINS
		if(playerValue > dealerValue && dealerValue <= 21) {
			return true;
		}

		//player is bust, dealer is bust - PLAYER LOOSES
		if(playerValue == 0 && dealerValue == 0) {
			return false;
		}

		//player has same as dealer - DRAW
		if(playerValue == dealerValue) {
			System.out.println("Its a draw!");
		}
		
		//if player is under 21 and dealer is equal or higher than 22 
		if(playerValue < 21 && dealerValue >= 22){
			return true;
		}
		
		//if all fails, player looses
		return false;
	}
}
