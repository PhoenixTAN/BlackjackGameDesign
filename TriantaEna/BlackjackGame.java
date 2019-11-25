import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/*
Author: Ziqi Tan
*/
public class BlackjackGame extends Game {

	public final static int blackjackValue = 31;
	private static int MaxDecks = 4;
	private static int MaxPlayers = 6;
	
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
	}
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		System.out.println("Trianta Ena!");
		System.out.println("Banker is an unstoppable AI.");
		System.out.println("For the convenience of testing, we have set up the player name and the cash. No need to bother.");
		System.out.println("Each player has 1000 bucks and banker has 3000 bucks.");
		this.numOfDecks = inputNumOfDecks();
		this.numOfPlayers = inputNumOfPlayers();
		shuffle();
		this.shuffleThreshold = numOfDecks * 52 / 2;				
		initializePlayers();
		initializeDealer();
	}
	
	/**
	 * Method: startPlay
	 * Modified by Jiaqian Sun, Ziqi Tan
	 * Game Logic can be modified here.
	 * */
	@Override
	public void startPlay() {
		// TODO Auto-generated method stub
		dealer.receiveCards(dealCard(), 0);
		// Show dealer's card.
		System.out.println("Dealer's hand: [" + dealer.getDealerHand().getCards().get(0).getSuit() + " " +
				dealer.getDealerHand().getCards().get(0).getFace()+ " "+  "] \n");
		
		// Every players get two cards.
		for( int i = 0; i < commonPlayers.length; i++ ) {	
			// Check if this player runs out of money
			if( commonPlayers[i].getCash() <= 0 ) {
				System.out.println("Run out of cash! Go home, boy!");
				continue;
			}
			// Player receives his/her first card.
			commonPlayers[i].receiveCards(dealCard(), 0);
			System.out.print("Player" + i + "'s hand: ");
			commonPlayers[i].getPlayerHand().get(0).printValue();
			
			System.out.println("Choose to 1: bet or 2: fold. [1/2]:");
			int choice = commonPlayers[i].betOrFold();
			if( choice == 1 ) {
				// Check if there is enough money.
				
				int bet = commonPlayers[i].getPlayerHand().get(0).setStake();
				if( bet > commonPlayers[i].getCash() ) {
					System.out.println("Not enought cash! All in for you! Ahhh~ ");
					commonPlayers[i].getPlayerHand().get(0).setStake(commonPlayers[i].getCash());
				}
				else {
					commonPlayers[i].getPlayerHand().get(0).setStake(bet);					
				}
								
				System.out.println("Come on! My tiger!");
				commonPlayers[i].receiveCards(dealCard(), 0);
				commonPlayers[i].receiveCards(dealCard(), 0);
				System.out.print("Player" + i + "'s hand: ");
				commonPlayers[i].getPlayerHand().get(0).printValue();
				System.out.println();
			}
			else {
				commonPlayers[i].getPlayerHand().get(0).setStatus(Status.FOLD);
				System.out.println("God bless you!\n");
			}
			// System.out.println(commonPlayers[i].getPlayerHand().get(0).getStatus());
		}
		
		// Every player take action.
		for( int i = 0; i < commonPlayers.length; i++ ) {	
			// Check if this player runs out of money
			if( commonPlayers[i].getCash() <= 0 ) {
				System.out.println("Run out of cash! Go home, boy!");
				continue;
			}
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
				else if( res == -1 ){
					System.out.println("Player" + i + " wins!");
					dealer.setCash(-stake);
					commonPlayers[i].setCash(stake);
				}
				else {
					System.out.println("Player has folded! ");
				}
				System.out.println("Player" + i + "'s cash: " + commonPlayers[i].getCash());
				System.out.println("Dealer's cash: " + dealer.getCash());
			}
		}
		
		if( needShuffle() ) {
			shuffle();
		}
	}

	@Override
	public void endPlay() {
		// TODO Auto-generated method stub
		this.scan.close();
		System.out.println("Exit...");
	}
	
	private void shuffle() {
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

	private void initializePlayers() {
		this.commonPlayers = new CommonPlayer[numOfPlayers];
		for( int i = 0; i < numOfPlayers; i++ ) {
			// System.out.print("Player" + i + " ");
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
	 * Modified by Ziqi Tan
	 * Add if( this.needNewBanker() ) { ... }
	 * Add this if-then part can rotate the banker.
	 * */
	private void newRoundInitialize() {
		this.dealer.newHand();
		// Every player need set stake.
		for( int i = 0; i < commonPlayers.length; i++ ) {
			// System.out.print("Player" + i + " ");
			commonPlayers[i].newHand();
		}
		// Add this if-then part can rotate the banker.
		if( this.needNewBanker() ) {
			System.out.println("A new banker will be born.");
			System.out.println("For the sake of fun, the wealthiest player will be the banker.");
			this.rotateBanker();
		}
	}
	
	/**
	 * Method: oneMoreRound()
	 * Ask whether the player wants another round.
	 * */
	private boolean oneMoreRound() {
		this.showCurrentCashList();
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
	
	/**
	 * Method: showCurrentCashList()
	 * Created by Jiaqian Sun
	 * Show everyone's current cash.
	 * */
	private void showCurrentCashList() {
		System.out.println("\n"+ "After this round:");
		System.out.println("Dealer's cash: "+ dealer.getCash());
		for( int i = 0; i < commonPlayers.length; i++ ) {
			System.out.println("Player" + i + "'s cash: " + commonPlayers[i].getCash());
		}
	}
	
	/**
	 * Method: rotateBanker
	 * Created by Ziqi Tan
	 * */
	private void rotateBanker() {
		int whichPlayer = -1;
		int maxCash = -1;
		for( int i = 0; i < commonPlayers.length; i++ ) {
			if( commonPlayers[i].getCash() > maxCash ) {
				maxCash = commonPlayers[i].getCash();
				whichPlayer = i;
			}
		}
		if( whichPlayer != -1 ) {
			int dealerCash = dealer.getCash();
			int newBankerCash = commonPlayers[whichPlayer].getCash();
			dealer.rotateCash(newBankerCash);
			commonPlayers[whichPlayer].rotateCash(dealerCash);
		}
	}
	
	/**
	 * Method: needNewBanker()
	 * Created by Ziqi Tan
	 * */
	private boolean needNewBanker() {
		// Banker went bankrupt.
		if( dealer.getCash() <= 0 ) {
			return true;
		}
		// One of the players has more money than the current bank.
		for( int i = 0; i < commonPlayers.length; i++ ) {
			if( commonPlayers[i].getCash() > dealer.getCash() ) {
				return true;
			}
		}
		return false;
	}
	

}
