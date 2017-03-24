package gameElements;


/**
 * 
 * @author creusel1u
 * Singleton
 */

public class Game {
	private Player player0;
	private Queen queen0;
	private Rock rock0;
	private Player player1;
	private Queen queen1;
	private Rock rock1;
	private Empty empty;
	private Player currentPlayer;

	public Game() {
		player0 = new Player(0);
		queen0 = new Queen();
		rock0 = new Rock();
		queen0.setPlayer(player0);
		rock0.setPlayer(player0);
		player1 = new Player(1);
		queen1 = new Queen();
		rock1 = new Rock();
		queen1.setPlayer(player1);
		rock1.setPlayer(player1);
		empty = new Empty();
		currentPlayer = player0;
	}
	
	

	public Player getPlayer0() {
		return player0;
	}

	public Queen getQueen0() {
		return queen0;
	}

	public Rock getRock0() {
		return rock0;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Queen getQueen1() {
		return queen1;
	}

	public Rock getRock1() {
		return rock1;
	}

	public Empty getEmpty() {
		return empty;
	}

	
	//set color mode for the 2 players
	public void setColorMode(String colorMode){
		player0.setColorMode(colorMode);
		player1.setColorMode(colorMode);
	}
	
	//returns an instance of the other player
	public Player otherPlayer(Player p){
		return p.equals(player0) ? player1 : player0;
	}
	
	public void setCurrentPlayer(Player p){
		currentPlayer = p;
	}
	
	public Player getCurrentPlayer(){
		return currentPlayer;
	}
	
}
