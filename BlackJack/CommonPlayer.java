

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/*
Author: Ziqi Tan
*/
public class CommonPlayer extends BlackjackPlayer {
	
	private List<PlayerHand> playerHand;
	
	public CommonPlayer(String n) {
		super(n);
		// TODO Auto-generated constructor stub
		this.playerHand = new ArrayList<PlayerHand>();
		this.playerHand.add(new PlayerHand());
		this.playerHand.get(0).setStake();
	}
	
	public List<PlayerHand> getPlayerHand() {
		return this.playerHand;
	}

	@Override
	public void receiveCards(Card card, int whichHand) {
		// TODO Auto-generated method stub			
		this.playerHand.get(whichHand).addCard(card);		
	}

	@Override
	public boolean showAvailableActions( int whichHand ) {
		// TODO Auto-generated method stub
		System.out.print("hand" + whichHand + ": ");
		Status sta = this.playerHand.get(whichHand).getStatus();
		boolean hasNextAction = false;
		switch(sta) {
			case SPLIT:
				hasNextAction = true;
				System.out.println("1: hit, 2: stand, 3: double up, 4: split. \n"
						+ "[1/2/3/4]:");
				break;
			case DEFAULT:
				hasNextAction = true;
				System.out.print("1: hit, 2: stand, 3: double up. \n"
						+ "[1/2/3]:");
				break;
			case NATURAL:
				System.out.println("Natural Blackjack!");
				break;
			case BLACKJACK:
				System.out.println("Blackjack!");
				break;
			case BUST:
				System.out.println("BUST!");
				break;
			/*case HASDOUBLE:
				System.out.println("Double!");
				break;*/
			case HASSTAND:
				System.out.println("Stand!");
				break;
			default:
				;				
		}

		return hasNextAction;
	}
	
	public int inputActions(int whichHand) {
		
		Status sta = this.playerHand.get(whichHand).getStatus();
		Scanner scan = new Scanner(System.in);
		int choice = 1;
		try{
	         choice = scan.nextInt();	
	         if( sta == Status.SPLIT ) {
	        	 if( choice > 4 || choice < 1) {
		        	 choice = 1;
		         }
	         }
	         if( sta == Status.DEFAULT ) {
	        	 if( choice > 3 || choice < 1) {
		        	 choice = 1;
		         }
	         }
	    }
		catch(InputMismatchException e){
	         System.out.println("Exception thrown  :" + e);
	    }
		
		return choice;
		
	}

	
	public void hit(Card c, int whichHand) {
		this.receiveCards(c, whichHand);
		this.playerHand.get(whichHand).printValue();
	}
	
	public void stand(int whichHand) {
		this.playerHand.get(whichHand).setStatus(Status.HASSTAND);
		this.playerHand.get(whichHand).printValue();
	}
	
	public void doubleUp(Card c, int whichHand) {
		this.playerHand.get(whichHand).doubleStake();
		this.receiveCards(c, whichHand);
		if( this.playerHand.get(whichHand).getStatus() == Status.DEFAULT ) {
			this.playerHand.get(whichHand).setStatus(Status.HASSTAND);
		}		
		// this.playerHand.get(whichHand).setStatus(Status.HASDOUBLE);
		this.playerHand.get(whichHand).printValue();
	}
	
	public void split(int whichHand) {
		// Get the card you want to split.
		Card splitCard = this.playerHand.get(whichHand).getCards().get(1);
		// New a player hand.
		this.playerHand.add(new PlayerHand());
		// Get current new hand index.
		int newHandIndex = this.playerHand.size() - 1;
		// Add separated card into new hand.
		int sameStake = this.playerHand.get(whichHand).getStake();
		this.playerHand.get(newHandIndex).setStake(sameStake);
		this.receiveCards(splitCard, newHandIndex);
		// Remove the card that you have split from the original hand.
		this.playerHand.get(whichHand).getCards().remove(1);
		
		// Deal card on original hand and new hand.
		
	}

	@Override
	public void newHand() {
		// TODO Auto-generated method stub
		this.playerHand = new ArrayList<PlayerHand>();
		this.playerHand.add(new PlayerHand());
		this.playerHand.get(0).setStake();
	}
	
}
