package blackjack;

import java.util.InputMismatchException;
import java.util.Scanner;

/*
Author: Ziqi Tan
*/
/**
 * For re-usability
 * Every card game have a deck and need to set the game mode.
 **/
public class Game {
	private Deck deck;
	private int numOfPlayers;
	private int numOfDecks;
	private Scanner scan;
		
	public Game() {
		this.scan = new Scanner(System.in);
		initializeGame();
		this.deck = new Deck(this.numOfDecks, this.numOfPlayers);
		endGame();
	}
	
	public void initializeGame() {
		System.out.println("Blackjack !\nDealer is an unstoppable AI.");		
		this.numOfDecks = inputNumOfDecks();
		this.numOfPlayers = inputNumOfPlayers();		
	}
	
	private int inputNumOfDecks() {
				
		int choice;
		System.out.print("How many decks do you want? [1-4]: ");
		choice = 1;
		try{
	         choice = scan.nextInt();	
	         if( choice > 4 || choice < 1) {
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
		System.out.print("How many players do you have? [1-3]: ");
		choice = 1;
		try{
	         choice = scan.nextInt();	
	         if( choice > 3 || choice < 1 ) {
	        	 choice = 1;
	         }
	    }
		catch(InputMismatchException e){
	         System.out.println("Exception thrown  :" + e);
	    }		
		return choice;		
	}
	
	private void endGame() {
		scan.close();
	}
	

}
