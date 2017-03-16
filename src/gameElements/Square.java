package gameElements;

public interface Square {
	
	public Player getPlayer();
	public void setPlayer(Player p);
	
	public boolean blocksPassageway();
	public boolean isQueen();
	public boolean isRock();
}
