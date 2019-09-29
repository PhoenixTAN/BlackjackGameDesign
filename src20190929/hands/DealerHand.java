package hands;

import blackjack.Card;

/*
Author: Ziqi Tan
*/
public class DealerHand extends BlackjackHand implements DealerRule {

	public DealerHand() {
		super();
	}
	
	@Override
	public boolean canStand() {
		// TODO Auto-generated method stub
		int val[] = this.getValue();
		int maxVal = Math.max(val[0], val[1]);
		if( maxVal < 17 ) {
			return false;
		}
		else {
			return true;
		}
	}

}
