
/*
Author: Ziqi Tan
*/
public class Dealer extends BlackjackPlayer {
	
	private DealerHand dealerHand;
	
	public Dealer(String n) {
		super(n);
		// TODO Auto-generated constructor stub
		this.dealerHand = new DealerHand();
		this.cash = 3000;
	}
	
	public DealerHand getDealerHand() {
		return this.dealerHand;
	}

	@Override
	public void receiveCards(Card card, int whichHand ) {
		// TODO Auto-generated method stub
		dealerHand.addCard(card);
	}

	@Override
	public boolean showAvailableActions(int whichHand) {

		Status sta = this.dealerHand.getStatus();
		boolean hasNextAction = false;
		switch(sta) {
			case DEFAULT:
				hasNextAction = true;
				break;
			/*case SPLIT:
				hasNextAction = true;
				break;*/
			case NATURAL:
				System.out.println("Natural blackjack!");
				break;
			case BLACKJACK:
				System.out.println("Blackjack!");
			case BUST:
				System.out.println("BUST!");
				break;
			default:
				;
		}
		
		return hasNextAction;
		
	}

	@Override
	public int inputActions(int whichHand) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void hit(Card c) {
		this.dealerHand.addCard(c);
		System.out.print("Hit! Dealer's hand: ");
		this.dealerHand.printValue();
	}
	
	public void stand() {
		this.dealerHand.setStatus(Status.HASSTAND);
		System.out.print("Stand! Dealer's hand: ");
		this.dealerHand.printValue();
	}
	
	/**
	 * Method: compare()
	 * Compare the value between dealer hand and player hand.
	 * */
	public int compare(PlayerHand playerHand) {
		// Return 1: Dealer wins.
		// Return -1: Player wins.
		// Return 0: Draw.
		System.out.print("Dealer's hand: ");
		dealerHand.printValue();
		// System.out.println(dealerHand.getStatus());
		System.out.println(playerHand.getStatus());
		Status playerStatus = playerHand.getStatus();
		
		switch(playerStatus) {
			case FOLD:
				return 2;
			case BUST:
				return 1;
			case NATURAL:
				if( dealerHand.getStatus() == Status.NATURAL ) {
					return 1;
				}
				return -1;
			case BLACKJACK:
				if( dealerHand.getStatus() == Status.NATURAL ) {
					return 1;
				}
				else if( dealerHand.getStatus() == Status.BLACKJACK ) {
					return 0;
				}
				else {
					return -1;
				}
			default:
				// Has stand/ Has double
				if( playerHand.isSuperBlackjack() ) {
					return -1;
				}
				switch(dealerHand.getStatus()) {
					case BUST:
						return -1;
					case NATURAL:
						return 1;
					case BLACKJACK:
						return 1;
					default:
						int val[] = playerHand.getValue();
						int playerValue = (Math.max(val[0], val[1]) > BlackjackGame.blackjackValue) ? Math.min(val[0], val[1]) : Math.max(val[0], val[1]);
						val = dealerHand.getValue();
						int dealerValue = (Math.max(val[0], val[1]) > BlackjackGame.blackjackValue) ? Math.min(val[0], val[1]) : Math.max(val[0], val[1]);
						if( dealerValue > playerValue ) {
							return 1;
						}
						else if( dealerValue == playerValue ){
							return 0;
						}
						else {
							return -1;
						}
				}
		}
		
		
	}

	@Override
	public void newHand() {
		// TODO Auto-generated method stub
		this.dealerHand = new DealerHand();
	}

}
