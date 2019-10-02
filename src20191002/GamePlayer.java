/*
Author: Ziqi Tan
*/

/**
 * A general abstract gamePlayer class.
 * */
public abstract class GamePlayer {
	protected String name;
	
	public GamePlayer( String n ) {
		this.name = n;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName( String n) {
		this.name = n;
	}
}
