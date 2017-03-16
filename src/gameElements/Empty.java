package gameElements;

public class Empty implements Square {
	Player player;

	public Empty() {
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
		return "..";
	}
	
	@Override
	public boolean blocksPassageway(){
		return false;
	}
	
	@Override
	public boolean isQueen(){
		return false;
	}

}
