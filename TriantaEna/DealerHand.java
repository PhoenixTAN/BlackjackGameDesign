
/*
Author: Ziqi Tan
*/
public class DealerHand extends BlackjackHand {
	
	private static int dealerLimitation = 27;
	
	public DealerHand() {
		super();
	}
	/**
	 * Method: canStand()
	 * */
	public boolean canStand() {
		// TODO Auto-generated method stub
		int val[] = this.getValue();
		int maxVal = Math.max(val[0], val[1]);
		if( maxVal < dealerLimitation ) {
			return false;
		}
		else {
			return true;
		}
	}

}
