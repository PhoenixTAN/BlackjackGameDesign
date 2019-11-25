

/*
Author: Ziqi Tan
*/
public abstract class BlackjackPlayer extends GamePlayer {
	
	protected int cash;
	
	public BlackjackPlayer(String n) {
		super(n);
		// TODO Auto-generated constructor stub
		this.cash = 1000;
	}
	
	public int getCash() {
		return this.cash;
	}
	
	public void setCash( int c ) {
		this.cash += c;
	}
	
	// 
	public void rotateCash( int c) {
		this.cash = c;
	}
	
	public boolean isBankrupt() {
		if( this.cash <= 0 ) {
			return true;
		}
		return false;
	}
	
	public abstract void receiveCards(Card card, int whichHand);
	
	public abstract boolean showAvailableActions(int whichHand);
	
	public abstract int inputActions(int whichHand);
	
	public abstract void newHand();
	
}
