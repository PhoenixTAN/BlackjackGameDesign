import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/*
Author: Ziqi Tan
*/
public class BlackjackGame extends Game {
	
	private static int MaxDecks = 4;
	private static int MaxPlayers = 3;
	
	private int numOfPlayers;
	private int numOfDecks;
	private Scanner scan;
	
	private Dealer dealer;
	private CommonPlayer[] commonPlayers;
	private Card card[][];
	private int remainCards; // The number of cards that have not been dealt.
	private int shuffleThreshold;
	
	public BlackjackGame() {
		this.scan = new Scanner(System.in);
		initialize();				
		while( true ) {
			startPlay();
			if( !oneMoreRound() ) {
				break;
			}
			newRoundInitialize();
		}		
		endPlay();
		// 
	}
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		System.out.println("Blackjack !\nDealer is an unstoppable AI.");		
		this.numOfDecks = inputNumOfDecks();
		this.numOfPlayers = inputNumOfPlayers();
		shuffle(numOfDecks);
		this.shuffleThreshold = numOfDecks * 52 / 2;				
		initializePlayers(numOfPlayers);
		initializeDealer();
	}

	@Override
	public void startPlay() {
		// TODO Auto-generated method stub
		// Dealer get two cards, one of which is not visible.
		dealer.receiveCards(dealCard(), 0);
		dealer.receiveCards(dealCard(), 0);
		
		// Show dealer's card.
		System.out.println("Dealer's hand: [" + dealer.getDealerHand().getCards().get(0).getSuit() + " " +
				dealer.getDealerHand().getCards().get(0).getFace()+ " "+  ", ? ] \n");
		// System.out.println(dealer.getDealerHand().getStatus());
		
		// Every players get two cards.
		for( int i = 0; i < commonPlayers.length; i++ ) {			
			commonPlayers[i].receiveCards(dealCard(), 0);
			commonPlayers[i].receiveCards(dealCard(), 0);
			// commonPlayers[i].receiveCards(new Card("J", "diamond"), 0);
			// commonPlayers[i].receiveCards(new Card("J", "heart"), 0);
			System.out.print("Player" + i + "'s hand: ");
			commonPlayers[i].getPlayerHand().get(0).printValue();
			// System.out.println(commonPlayers[i].getPlayerHand().get(0).getStatus());
		}
		
		// Every player take action.
		for( int i = 0; i < commonPlayers.length; i++ ) {			
			// Show all available actions for each hand.
			System.out.print("Player" + i + "'s turn: ");
			for( int j = 0; j < commonPlayers[i].getPlayerHand().size(); j++ ) {
								
				boolean hasNextAction = commonPlayers[i].showAvailableActions(j);
				while(hasNextAction) {
					
					int choice = commonPlayers[i].inputActions(j);
					System.out.print("Player" + i + "'s hand" + j + ": ");
					switch(choice) {
						case 1:							
							commonPlayers[i].hit(dealCard(), j);
							break;
						case 2:
							commonPlayers[i].stand(j);
							break;
						case 3:
							commonPlayers[i].doubleUp(dealCard(), j);
							break;
						case 4:
							commonPlayers[i].split(j);
							commonPlayers[i].hit(dealCard(), j);							
							commonPlayers[i].hit(dealCard(), j+1);
							 // We need to continue to make decision.
							break;
						default:
							;
					
					}
					hasNextAction = commonPlayers[i].showAvailableActions(j);

				}
			
			}
		}
		
		// Dealer's turn.
		boolean hasNextAction = dealer.showAvailableActions(0);
		while( hasNextAction ) {
			if( !dealer.getDealerHand().canStand() ) {
				dealer.hit(dealCard());
			}
			else {
				dealer.stand();
			}
			hasNextAction = dealer.showAvailableActions(0);
		}
		
		// Every player duel with dealer.
		for( int i = 0; i < commonPlayers.length; i++ ) {			
			// Show all available actions for each hand.
			for( int j = 0; j < commonPlayers[i].getPlayerHand().size(); j++ ) {
				int res = dealer.compare(commonPlayers[i].getPlayerHand().get(j));	
				int stake = commonPlayers[i].getPlayerHand().get(j).getStake();
				if( res == 1 ) {
					System.out.println("Dealer wins!");					
					dealer.setCash(stake);
					commonPlayers[i].setCash(-stake);				
				}
				else if( res == 0 ) {
					System.out.println("Draw!");
				}
				else {
					System.out.println("Player" + i + " wins!");
					dealer.setCash(-stake);
					commonPlayers[i].setCash(stake);
				}
				System.out.println("Player" + i + "'s cash: " + commonPlayers[i].getCash());
				System.out.println("Dealer's cash: " + dealer.getCash());
			}
		}
		
		
		if( needShuffle() ) {
			int numOfDecks = this.card.length / 13;
			shuffle(numOfDecks);
		}
	}

	@Override
	public void endPlay() {
		// TODO Auto-generated method stub
		this.scan.close();
		System.out.println("Exit...");
	}
	
	private void shuffle( int numOfDecks ) {
		System.out.println("Shuffling...");
		card = new Card[13*numOfDecks][4];
		this.remainCards = numOfDecks * 52;
		// 1~10, J, Q, K [13]
		// heart, spade, club, diamond [4]
		for( int i = 0; i < card.length; i++ ) {
			card[i][0] = new Card(Integer.toString(i%13+1), "heart");
			card[i][1] = new Card(Integer.toString(i%13+1), "spade");
			card[i][2] = new Card(Integer.toString(i%13+1), "club");
			card[i][3] = new Card(Integer.toString(i%13+1), "diamond");
			switch(i%13+1) {
				case 1:
					card[i][0].setFace("A");
					card[i][1].setFace("A");
					card[i][2].setFace("A");
					card[i][3].setFace("A");
					break;
				case 11:
					card[i][0].setFace("J");
					card[i][1].setFace("J");
					card[i][2].setFace("J");
					card[i][3].setFace("J");
					break;
				case 12:
					card[i][0].setFace("Q");
					card[i][1].setFace("Q");
					card[i][2].setFace("Q");
					card[i][3].setFace("Q");
					break;
				case 13:
					card[i][0].setFace("K");
					card[i][1].setFace("K");
					card[i][2].setFace("K");
					card[i][3].setFace("K");
					break;
			}
		}
		
	}

	private Card dealCard() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		int faceMin = 1;
		int faceMax = this.card.length;
		//System.out.println("length" + this.card.length);
		int suitMin = 1;
		int suitMax = 4;
		
		int randomFace;
		int randomSuit;
		
		while( true ) {
			randomFace = rand.nextInt(faceMax-faceMin+1) + faceMin;
			randomSuit = rand.nextInt(suitMax-suitMin+1) + suitMin;
			//System.out.println(randomFace + " " +randomSuit);
			if( !card[randomFace-1][randomSuit-1].getDealt() ) {
				card[randomFace-1][randomSuit-1].setDealt();
				break;
			}			
		}

		// System.out.println("Deal: " + face + " " + suit);
		this.remainCards--;
		
		return card[randomFace-1][randomSuit-1];
	}

	private void initializePlayers(int numOfPlayers) {
		this.commonPlayers = new CommonPlayer[numOfPlayers];
		for( int i = 0; i < numOfPlayers; i++ ) {
			System.out.print("Player" + i + " ");
			this.commonPlayers[i] = new CommonPlayer("Player " + Integer.toString(i) );
		}
	}

	private void initializeDealer() {
		this.dealer = new Dealer("Unbeatable AI dealer");
	}

	private boolean needShuffle() {
		if( this.remainCards <= this.shuffleThreshold ) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method: newRoundInitialize()
	 * For a new round, the player hands and the dealer hand should be initialized.
	 * */
	private void newRoundInitialize() {
		this.dealer.newHand();
		// Every player need set stake.
		for( int i = 0; i < commonPlayers.length; i++ ) {
			System.out.print("Player" + i + " ");
			commonPlayers[i].newHand();
		}
	}
	
	/**
	 * Method: oneMoreRound()
	 * Ask whether the player wants another round.
	 * */
	private boolean oneMoreRound() {
		
		System.out.println("Do you want one more round? [y/n]: ");
		Scanner scan = new Scanner(System.in);
		String choice = "n";
		try{
	         choice = scan.nextLine();	
	         if( choice.equals("y") ) {
	        	 return true;
	         }
	         else {
	        	 return false;
	         }
	    }
		catch(InputMismatchException e){
	         System.out.println("Exception thrown  :" + e);
	    }		
		return false;
	}
	
	private int inputNumOfDecks() {
		
		int choice;
		System.out.print("How many decks do you want? [1-" + MaxDecks + "]: ");
		choice = 1;
		try{
	         choice = scan.nextInt();	
	         if( choice > MaxDecks || choice < 1) {
	        	 choice = 1;
	         }
	    }
		catch(InputMismatchException e){
	         System.out.println("Exception thrown  :" + e);
	    }
		return choice;
	}
	
	private int inputNumOfPlayers() {
		
		int choice;
		System.out.print("How many players do you have? [1-" + MaxPlayers + "]: ");
		choice = 1;
		try{
	         choice = scan.nextInt();	
	         if( choice > MaxPlayers || choice < 1 ) {
	        	 choice = 1;
	         }
	    }
		catch(InputMismatchException e){
	         System.out.println("Exception thrown  :" + e);
	    }		
		return choice;		
	}

}
