
/*
Author: Ziqi Tan
*/
public class DealerHand extends BlackjackHand {

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
		if( maxVal < 17 ) {
			return false;
		}
		else {
			return true;
		}
	}

}
