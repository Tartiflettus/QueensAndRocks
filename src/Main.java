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
		
		System.out.println(board.toStringAccess());
	}
	
	
	private static void testComputer(Board board){
		System.out.println(board.solutionSteps(board));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Game g = new Game();
		g.setColorMode("wb");
		Board board = new Board(g, 4);
		
		//testSolo(board);
		testComputer(board);
		
		display(board);
	}

}
