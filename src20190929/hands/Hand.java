package hands;

import java.util.ArrayList;
import java.util.List;

import blackjack.Card;
import blackjack.Status;

/*
Author: Ziqi Tan
*/
public abstract class Hand {
	protected List<Card> cards;
	protected Status status;
	
	public Hand( ) {
		this.cards = new ArrayList<Card>();
		this.status = Status.DEFAULT;
	}
	
	public abstract void addCard( Card card );
	
	public List<Card> getCards() {
		return this.cards;
	}
	
	public Status getStatus() {
		return this.status;
	}
	
	public void setStatus(Status sta) {
		this.status = sta;
	}
	
	public List<Card> getHand() {
		return this.cards;
	}
	
	protected abstract void updateStatus();
}
