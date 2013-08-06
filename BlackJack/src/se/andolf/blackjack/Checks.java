package se.andolf.blackjack;

import java.util.List;

public final class Checks {

	static boolean bustCheck(int currentValue) {
		if (currentValue <= 21) {
			return false;
		}
		return true;
	}

	static boolean blackJackCheck(List<List<Card>> Hands) {
		for(List<Card> cards : Hands){
			
			//if no cards no check
			if(!cards.isEmpty()){
				
				//get value of first 2 cards
				int card1 = cards.get(0).getValue();
				int card2 = cards.get(1).getValue();
				
				//if first card ace, second suited or other way around then BJ = true
				if(card1 == 1 && card2 == 10 || card1 == 10 && card2 == 1){
					return true;
				}
				
				//checking total value of cards
				int currentValue = 0;
				for(Card c : cards){
					currentValue += c.getValue();
				}
				if(currentValue == 21){
					return true;
				}
				return false;			
			}			
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
