import java.awt.Dimension;
import java.awt.DisplayMode;

import gameElements.Board;
import gameElements.Game;
import graphics.GameUI;


public class Main {

	private Main() {
	}
	
	private static void display(Board b){
		GameUI gui = new GameUI(b);
		gui.launch();
	}
	
	private static void testSolo(Board board){
		
		
		board.placeQueen(0, 0);
		board.placeQueen(7, 6);
		
		System.out.println(board.toStringAccess());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Game g = new Game();
		g.setColorMode("wb");
		Board board = new Board(g);
		
		testSolo(board);
		
		display(board);
	}

}
