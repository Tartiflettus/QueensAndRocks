import java.awt.Dimension;
import java.awt.DisplayMode;
import java.util.Date;
import java.util.NoSuchElementException;

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
	
	
	private static void testComputerAux(Board board){
		{
			Date d = new Date();
			long t = d.getTime();
			String res = "";
			try{
				res = board.solutionSteps(board);
				//System.out.println("solution :\n" + res);
				System.out.println("il y a une solution");
			}
			catch(NoSuchElementException e){
				System.out.println("Pas de solution");
				System.out.println(res);
			}
			d = new Date();
			System.out.println("Temps mis pour " + board.getSize()+ " : " + (d.getTime() - t));
		}
		{
			Date d = new Date();
			long t = d.getTime();
			String res = "";
			try{
				res = board.solutionSteps2(board);
				//System.out.println("solution :\n" + res);
				System.out.println("il y a une solution");
			}
			catch(NoSuchElementException e){
				System.out.println("Pas de solution");
				System.out.println(res);
			}
			d = new Date();
			System.out.println("Temps mis pour " + board.getSize()+ " (2) : " + (d.getTime() - t));
		}
		
	}
	
	
	private static void testComputer(Board board){
		for(int i=3; i <= 12; i++){
			System.gc();
			board = new Board(board.getGame(), i);
			testComputerAux(board);
		}
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
		
		//display(board);
	}

}
