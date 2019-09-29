package players;
/*
Author: Ziqi Tan
*/
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
