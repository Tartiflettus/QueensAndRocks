package gameElements;

public class Rock implements Square {
	Player player;

	public Rock() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public void setPlayer(Player p) {
		player = p;
	}
	
	@Override
	public String toString(){
		return "R"+player.getNumber();
	}
	
	@Override
	public boolean blocksPassageway(){
		return true;
	}
	
	@Override
	public boolean isQueen(){
		return false;
	}
}
