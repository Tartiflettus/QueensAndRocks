package gameElements;

public class Eval0 implements Eval {

	public Eval0() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public float getEval(Player player, Board b) {
		return (float)b.getScore(player) - (float)b.getScore(b.getGame().otherPlayer(player));
	}

}
