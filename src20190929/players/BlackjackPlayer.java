package players;

import blackjack.Card;

/*
Author: Ziqi Tan
*/
public abstract class BlackjackPlayer extends GamePlayer {
	
	protected int cash;
	
	public BlackjackPlayer(String n) {
		super(n);
		// TODO Auto-generated constructor stub
		this.cash = 5000;
	}
	
	public int getCash() {
		return this.cash;
	}
	
	public void setCash( int c ) {
		this.cash += c;
	}
	
	public abstract void receiveCards(Card card, int whichHand);
	
	public abstract boolean showAvailableActions(int whichHand);
	
	public abstract int inputActions(int whichHand);
	
	public abstract void newHand();
	
}
