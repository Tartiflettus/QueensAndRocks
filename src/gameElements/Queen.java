package gameElements;

public class Queen implements Square {
	Player player;

	public Queen() {
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
		return "Q"+player.getNumber();
	}
	
	@Override
	public boolean blocksPassageway(){
		return true;
	}
	
	@Override
	public boolean isRock(){
		return false;
	}
	
	@Override
	public boolean isQueen(){
		return true;
	}

}
