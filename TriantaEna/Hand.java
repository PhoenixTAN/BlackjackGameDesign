

import java.util.ArrayList;
import java.util.List;


/*
Author: Ziqi Tan
A general abstract Hand class for card playing game.
It can be re-used conveniently.
Every card game has a list of cards, and the getter and setter for these cards.
*/
public abstract class Hand {
	protected List<Card> cards;
	
	public Hand( ) {
		this.cards = new ArrayList<Card>();
		// this.status = Status.DEFAULT;
	}
	
	public abstract void addCard( Card card );
	
	public List<Card> getCards() {
		return this.cards;
	}
	
	public List<Card> getHand() {
		return this.cards;
	}
	
}
