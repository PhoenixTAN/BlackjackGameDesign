
/*
Author: Ziqi Tan
*/
public class Card {
	
	protected String face; // 1, 2, 3, 4, 5, 6, 7, 8 ,9, 10, J, Q, K
	protected String suit; // heart, spade, club, diamond	
	private boolean dealt;
	
	public Card(String f, String s) {
		this.face = f;
		this.suit = s;
		this.dealt = false;		
	}
	
	public String getFace() {
		return this.face;
	}
	
	public String getSuit() {
		return this.suit;
	}
		
	public void setFace(String f) {
		this.face = f;
	}
	
	public void setSuit(String s) {
		this.suit = s;
	}
		
	public boolean getDealt() {
		return this.dealt;
	}
	
	public void setDealt() {
		this.dealt = true;
	}
	
}
