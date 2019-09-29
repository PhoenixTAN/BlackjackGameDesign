package hands;

import java.util.Arrays;

import blackjack.Card;
import blackjack.Status;

/*
Author: Ziqi Tan
*/
public class BlackjackHand extends Hand implements CommonRule {
	
	private int value[];
	
	public BlackjackHand() {
		super();
		this.value = new int[2];
	}
	
	public void addCard( Card card ) {
		this.cards.add(card);
		updateValue();
		// printValue();
		updateStatus();
	}
	
	public int[] getValue() {				
		return this.value;
	}
	
	protected void updateStatus() {
		if( isNaturalBlackjack() ) {
			this.status = Status.NATURAL;
		}
		else if( isBlackjack() ) {
			this.status = Status.BLACKJACK;
		}
		else if( isBust() ) {
			this.status = Status.BUST;
		}
		else if( canSplit() ) {
			this.status = Status.SPLIT;
		}
		else {
			this.status = Status.DEFAULT;
		}
		
	}
	
	/* 
	 * Get every possible value of this hand.
	 * A can represent 1/11.
	 * J, Q, K represent 10.
	 * Be careful that if you have three A.
	 * 
	 */
	private void updateValue() {
		// You can have only two Aces, you are blackjack.
		this.value[0] = 0;
		this.value[1] = 0;
		boolean hasA = false;
		for( Card c : cards ) {
			if( c.getFace().equals("A") ) {
				if( hasA ) {
					this.value[0] += 1;				
				}
				else {
					this.value[0] += 11;
					hasA = true;
				}
				this.value[1] += 1;
			}
			else if( c.getFace().equals("J") || c.getFace().equals("Q") || c.getFace().equals("K") ) {
				this.value[0] += 10;
				this.value[1] += 10;
			}
			else {
				this.value[0] += Integer.parseInt(c.getFace());
				this.value[1] += Integer.parseInt(c.getFace());
			}
		}		
	}
	
	public void printValue() {
		System.out.print("[ ");
		for( Card c : cards ) {
			System.out.print( c.getSuit() + " " + c.getFace() + " ");
		}
		System.out.print("]");
		System.out.println(Arrays.toString(this.getValue()));
	}

	
	@Override
	public boolean isBlackjack() {
		// TODO Auto-generated method stub
		for( int i = 0; i < this.value.length; i++ ) {
			if( value[i] == 21 ) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isNaturalBlackjack() {
		// TODO Auto-generated method stub
		for( int i = 0; i < this.value.length; i++ ) {
			if( value[i] == 21 && this.cards.size() == 2 ) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isBust() {
		// TODO Auto-generated method stub
		int minValue = Integer.MAX_VALUE;
		for( int i = 0; i < this.value.length; i++ ) {
			if( value[i] < minValue ) {
				minValue = value[i];
			}
		}
		if( minValue > 21 ) {
			return true;
		}
		return false;

	}
	
	public boolean canSplit() {
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
	}

	
}
