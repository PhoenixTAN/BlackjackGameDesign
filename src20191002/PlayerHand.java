

import java.util.InputMismatchException;
import java.util.Scanner;

/*
Author: Ziqi Tan
*/

public class PlayerHand extends BlackjackHand {
	
	private int stake;
	
	public PlayerHand() {
		super();
		this.stake = 0;
	}
	
	public int getStake() {
		return this.stake;
	}
	
	public void doubleStake() {
		// Player will check if there is enough money.
		this.stake *= 2;
	}
	
	public void setStake() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please input chips: 5, 10, 50, 100, 300, 500. [5/10/50/100/300/500]:");
		int choice = 5;
		try{
	         choice = scan.nextInt();	
	         if( choice != 5 && choice != 10 && choice != 50 && choice != 100 && choice != 300 && choice != 500 ) {
	        	 choice = 100;
	         }
	    }
		catch(InputMismatchException e){
	         System.out.println("Exception thrown  :" + e);
	    }
		this.stake = choice;
	}
	
	public void setStake(int chip) {
		this.stake = chip;
	}

	/*public boolean canSplit() {
		// TODO Auto-generated method stub
		if( this.cards.size() == 2 ) {
			String face1 = cards.get(0).getFace();
			String face2 = cards.get(1).getFace();
			
			// Pair of 1 and 1, 2 and 2, ... 10 by 10
			if( face1.equals(face2) ) {				
				return true;
			}
			
			// Combination of [10, J, Q, K]
			boolean f1_10 = false;
			boolean f2_10 = false;
			if( face1.equals("10") || face1.equals("J") || face1.equals("Q") || face1.equals("K") ) {
				f1_10 = true;
			}
			if( face2.equals("10") || face2.equals("J") || face2.equals("Q") || face2.equals("K") ) {
				f2_10 = true;
			}
			if( f1_10 && f2_10 ) {
				return true;
			}
			
		}
		return false;
	}*/
	
}
