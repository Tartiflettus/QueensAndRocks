package gameElements;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Board {

	// ATTENTION
	// Ceci est un squelette incomplet contenant uniquement le profil de
	// quelques m�ｾ�ｽｩthodes, dans le but de compiler la classe GameUI
	// sans erreurs
	// Il manque les getters et les setters ainsi que les classes externes
	// telles que Square, Eval, Game, Player,...

	private Game game;
	private int size;
	private int nbPieces;
	private Square[][] board;

	private int rocksPlayer0;
	private int rocksPlayer1;

	private static final int NB_ROCKS = 5;

	private int nbQueensPlayer0;
	private int nbQueensPlayer1;

	private static final int queenValue = 5;
	private static final int rockValue = 2;

	// ---------------TP1------------------------
	public Board(Game g, int size, int nbPieces, Square[][] board) {
		this.game = g;
		this.size = size;
		this.nbPieces = nbPieces;
		this.board = board;
		rocksPlayer0 = size;
		rocksPlayer1 = size;
	}

	public Board(Game g, int size) {
		this.game = g;
		this.size = size;
		this.nbPieces = 0;
		this.board = new Square[size][size];

		// init board with empty cells
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				this.board[i][j] = new Empty();
			}
		}
		rocksPlayer0 = size;
		rocksPlayer1 = size;
	}

	// default size of the board : 8
	public Board(Game g) {
		this(g, 8);
	}

	public int getRocksPlayer0() {
		return rocksPlayer0;
	}

	public void setRocksPlayer0(int rocksPlayer0) {
		this.rocksPlayer0 = rocksPlayer0;
	}

	public int getRocksPlayer1() {
		return rocksPlayer1;
	}

	public int getNumberOfRocksLeft(Player p) {
		assert (p.getNumber() == 0 || p.getNumber() == 1);
		return p.getNumber() == 0 ? rocksPlayer0 : rocksPlayer1;
	}

	public void useRock(Player p) {
		assert (p.getNumber() == 0 || p.getNumber() == 1);
		if (p.getNumber() == 0) {
			--rocksPlayer0;
		} else {
			--rocksPlayer1;
		}
	}

	public void useQueen(Player p) {
		assert (p.getNumber() == 0 || p.getNumber() == 1);
		if (p.getNumber() == 0) {
			++nbQueensPlayer0;
		} else {
			++nbQueensPlayer1;
		}
	}

	public void setRocksPlayer1(int rocksPlayer1) {
		this.rocksPlayer1 = rocksPlayer1;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNbPieces() {
		return nbPieces;
	}

	public void setNbPieces(int nbPieces) {
		this.nbPieces = nbPieces;
	}

	public Square[][] getBoard() {
		return board;
	}

	public void setBoard(Square[][] board) {
		this.board = board;
	}

	public Square getPiece(int i, int j) {
		return board[i][j];
	}

	public void setPiece(int i, int j, Square s) {
		board[i][j] = s;
		nbPieces++;
	}

	public void removePiece(int i, int j) {
		board[i][j] = game.getEmpty();
	}

	public boolean isEmpty(int i, int j) {
		return board[i][j].toString() == game.getEmpty().toString();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				sb.append(board[i][j].toString() + '\t');
			}
			sb.append('\n');
		}

		return sb.toString();
	}

	public Board clone() {
		Board ans = new Board(game, size, nbPieces, null);

		Square[][] b = new Square[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				b[i][j] = board[i][j];
			}
		}
		ans.setBoard(b);
		ans.rocksPlayer0 = rocksPlayer0;
		ans.rocksPlayer1 = rocksPlayer1;

		return ans;
	}

	public boolean isAccessible(int i, int j) {
		return (!board[i][j].blocksPassageway()) && isLineAccessible(i, j) && isColumnAccessible(i, j)
				&& isRightDiagonalAccessible(i, j) && isLeftDiagonalAccessible(i, j);
	}

	private boolean isLineAccessible(int i, int j) {
		for (int k = j - 1; k >= 0; k--) {
			if (board[i][k].blocksPassageway()) {
				return false;
			}
		}
		for (int k = j + 1; k < size; k++) {
			if (board[i][k].blocksPassageway()) {
				return false;
			}
		}
		return true;
	}

	private boolean isColumnAccessible(int i, int j) {
		for (int k = i - 1; k >= 0; k--) {
			if (board[k][j].blocksPassageway()) {
				return false;
			}
		}
		for (int k = i + 1; k < size; k++) {
			if (board[k][j].blocksPassageway()) {
				return false;
			}
		}
		return true;
	}

	private boolean isLeftDiagonalAccessible(int i, int j) {
		int lig = i + 1, col = j + 1;
		while (lig < size && col < size) {
			if (board[lig][col].blocksPassageway()) {
				return false;
			}
			lig++;
			col++;
		}
		lig = i - 1;
		col = j - 1;
		while (lig >= 0 && col >= 0) {
			if (board[lig][col].blocksPassageway()) {
				return false;
			}
			lig--;
			col--;
		}

		return true;
	}

	private boolean isRightDiagonalAccessible(int i, int j) {
		int lig = i + 1, col = j - 1;
		while (lig < size && col >= 0) {
			if (board[lig][col].blocksPassageway()) {
				return false;
			}
			lig++;
			col--;
		}
		lig = i - 1;
		col = j + 1;
		while (lig >= 0 && col < size) {
			if (board[lig][col].blocksPassageway()) {
				return false;
			}
			lig--;
			col++;
		}

		return true;
	}

	public String toStringAccess() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j].blocksPassageway()) {
					sb.append(board[i][j].toString() + '\t');
				} else if (isAccessible(i, j)) {
					sb.append(board[i][j].toString() + '\t');
				} else {
					sb.append("xx\t");
				}
			}
			sb.append('\n');
		}

		return sb.toString();
	}

	public int numberOfAccessible() {
		int res = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				res += isAccessible(i, j) ? 1 : 0;
			}
		}
		return res;
	}

	public int numberOfQueens() {
		int res = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				res += board[i][j].blocksPassageway() ? 1 : 0;
			}
		}
		return res;
	}

	public boolean placeQueen(int i, int j) {
		if (!isAccessible(i, j)) {
			return false;
		}

		board[i][j] = new Queen();
		board[i][j].setPlayer(game.getPlayer0());

		return true;
	}

	// ----------TP2-----------------------
	public boolean isSolution() {
		return numberOfQueens() == size;
	}

	public Iterable<Board> getSuccessors() {
		ArrayList<Board> res = new ArrayList<Board>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (isAccessible(i, j)) {
					Board other = this.clone();
					assert (other.placeQueen(i, j));
					// System.out.println(other.toString() + "\n----\n");
					res.add(other);
				}
			}
		}
		// System.out.println("nb succs : "+res.size());
		return res;
	}

	public Iterable<Board> getNewSuccessors() {
		ArrayList<Board> res = new ArrayList<Board>();
		for (int j = 0; j < size; j++) {
			if (isAccessible(numberOfQueens(), j)) {
				Board tmp = this.clone();
				assert (tmp.placeQueen(numberOfQueens(), j));
				res.add(tmp);
			}
		}
		return res;
	}

	public ArrayList<Board> depthFirstSearch(Board initialState) {
		if (initialState.isSolution()) {
			ArrayList<Board> res = new ArrayList<Board>();
			res.add(initialState);
			return res;
		}
		for (Board succ : initialState.getSuccessors()) {
			try {
				ArrayList<Board> res = depthFirstSearch(succ);
				res.add(initialState);
				return res;
			} catch (NoSuchElementException e) {
				// System.out.println("ﾃｩchec");
			}
		}
		throw new NoSuchElementException("DepthFirstSearch failure");
	}

	public ArrayList<Board> depthfirstSearch() {
		return depthFirstSearch(this);
	}

	public ArrayList<Board> depthFirstSearch2(Board initialState) {
		if (initialState.isSolution()) {
			ArrayList<Board> res = new ArrayList<Board>();
			res.add(initialState);
			return res;
		}
		for (Board succ : initialState.getNewSuccessors()) {
			try {
				ArrayList<Board> res = depthFirstSearch(succ);
				res.add(initialState);
				return res;
			} catch (NoSuchElementException e) {
				// System.out.println("ﾃｩchec");
			}
		}
		throw new NoSuchElementException("DepthFirstSearch failure");
	}

	public ArrayList<Board> depthFirstSearch2() {
		return depthFirstSearch2(this);
	}

	public String solutionSteps(Board b) {
		StringBuilder res = new StringBuilder();

		ArrayList<Board> sol = depthFirstSearch(b);
		for (int i = sol.size() - 1; i >= 0; i--) {
			res.append(sol.get(i).toString());
			res.append("\n *** \n");
		}

		return res.toString();
	}

	public String solutionSteps2(Board b) {
		StringBuilder res = new StringBuilder();

		ArrayList<Board> sol = depthFirstSearch2(b);
		for (int i = sol.size() - 1; i >= 0; i--) {
			res.append(sol.get(i).toString());
			res.append("\n *** \n");
		}

		return res.toString();
	}

	public int[] BoardToArray(Board b) {
		int[] res = new int[b.getSize()];

		for (int i = 0; i < b.getSize(); i++) {
			res[i] = -1;
			for (int j = 0; j < b.getSize(); j++) {
				if (b.board[i][j].blocksPassageway()) {
					res[i] = j;
				}
			}
		}
		return res;
	}

	public Board arrayToBoard(int[] b) {
		Board res = new Board(getGame(), b.length);

		for (int i = 0; i < b.length; i++) {
			if (b[i] != -1) {
				assert (res.placeQueen(i, b[i]));
			}
		}

		return res;
	}

	public Iterable<int[]> getArraySuccessors(int[] b) {
		ArrayList<int[]> res = new ArrayList<int[]>();

		Board tmp = arrayToBoard(b);
		Iterable<Board> succ = tmp.getNewSuccessors();
		for (Board cur : succ) {
			res.add(BoardToArray(cur));
		}

		return res;
	}

	public boolean isSolutionArray(int[] array) {
		if (array[array.length - 1] == -1) {
			return false;
		} else {
			// System.out.println("trouve\n");
			return true;
		}
	}

	public ArrayList<int[]> depthFirstSearchArray(int[] initialState) {
		if (isSolutionArray(initialState)) {
			ArrayList<int[]> res = new ArrayList<int[]>();
			res.add(initialState);
			return res;
		}
		for (int[] succ : getArraySuccessors(initialState)) {
			try {
				ArrayList<int[]> res = depthFirstSearchArray(succ);
				res.add(initialState);
				return res;
			} catch (NoSuchElementException e) {
				// System.out.println("ﾃｩchec");
			}
		}
		throw new NoSuchElementException("DepthFirstSearch failure");
	}

	public ArrayList<int[]> depthFirstSearchArray() {
		return depthFirstSearchArray(BoardToArray(this));
	}

	public String solutionStepsArray(int[] b) {
		StringBuilder res = new StringBuilder();

		ArrayList<int[]> sol = depthFirstSearchArray(b);
		for (int i = sol.size() - 1; i >= 0; i--) {
			res.append(sol.get(i).toString());
			res.append("\n *** \n");
		}

		return res.toString();
	}

	// ------------TP3----------------------
	public boolean isAccessible2(int i, int j, Player player) {
		return (!board[i][j].blocksPassageway() && isLineAccessible2(i, j, player) && isColumnAccessible2(i, j, player)
				&& isRightDiagonalAccessible2(i, j, player) && isLeftDiagonalAccessible2(i, j, player));
	}

	private boolean isLineAccessible2(int i, int j, Player p) {
		int road;
		for (int k = i - 1; k >= 0; k--) {
			road = rockOnTheRoad(k, j, p);
			if (road == 0)
				return false;
			else if (road == 2)
				break;
		}
		for (int k = i + 1; k < size; k++) {
			road = rockOnTheRoad(k, j, p);
			if (road == 0)
				return false;
			else if (road == 2)
				break;
		}
		return true;
	}

	// teste si c'est une reine ou un rocher
	public int rockOnTheRoad(int i, int k, Player p) {
		if (board[i][k].isRock())
			return 2;
		if (board[i][k].isQueen() && (board[i][k].getPlayer().getNumber() != p.getNumber()))
			return 0;
		return 1;
	}

	private boolean isColumnAccessible2(int i, int j, Player p) {
		int road;
		for (int k = j - 1; k >= 0; k--) {
			road = rockOnTheRoad(i, k, p);
			if (road == 0)
				return false;
			else if (road == 2)
				break;
		}
		for (int k = j + 1; k < size; k++) {
			road = rockOnTheRoad(i, k, p);
			if (road == 0)
				return false;
			else if (road == 2)
				break;
		}
		return true;
	}

	private boolean isRightDiagonalAccessible2(int i, int j, Player p) {
		int road;
		int lig = i + 1, col = j + 1;
		while (lig < size && col < size) {
			road = rockOnTheRoad(lig, col, p);
			if (road == 0)
				return false;
			else if (road == 2)
				break;
			lig++;
			col++;
		}
		lig = i - 1;
		col = j - 1;
		while (lig >= 0 && col >= 0) {
			road = rockOnTheRoad(lig, col, p);
			if (road == 0)
				return false;
			else if (road == 2)
				break;
			lig--;
			col--;
		}
		return true;
	}

	private boolean isLeftDiagonalAccessible2(int i, int j, Player p) {
		int road;
		int lig = i + 1, col = j - 1;
		while (lig < size && col >= 0) {
			road = rockOnTheRoad(lig, col, p);
			if (road == 0)
				return false;
			else if (road == 2)
				break;
			lig++;
			col--;
		}
		lig = i - 1;
		col = j + 1;
		while (lig >= 0 && col < size) {
			road = rockOnTheRoad(lig, col, p);
			if (road == 0)
				return false;
			else if (road == 2)
				break;
			lig--;
			col++;
		}
		return true;
	}

	public int numberOfAccessible2(Player p) {
		int res = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (isAccessible2(i, j, p)) {
					++res;
				}
			}
		}
		return res;
	}

	public String toStringAccess2(Player p) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (isAccessible2(i, j, p)) {
					sb.append(board[i][j].toString() + "\t");
				} else {
					if (board[i][j].blocksPassageway())
						sb.append(board[i][j].toString() + "\t");
					else
						sb.append("xx\t");
				}
			}
			sb.append('\n');
		}
		return sb.toString();
	}

	public boolean placeQueen2(int i, int j, Player player) {
		if (!isAccessible2(i, j, player)) {
			return false;
		}
		if (nbQueensPlayer0 == 0 && nbQueensPlayer1 == 0 && rocksPlayer0 == size && rocksPlayer1 == size) {
			// first move : forbidden for queen
			return false;
		}
		useQueen(player);
		setPiece(i, j, player.getNumber() == 0 ? game.getQueen0() : game.getQueen1());

		return true;
	}

	public boolean placeRock2(int i, int j, Player player) {
		if (board[i][j].blocksPassageway()) {
			return false;
		}
		if (player.getNumber() == 0 && rocksPlayer0 <= 0 || player.getNumber() == 1 && rocksPlayer1 <= 0) {
			return false;
		}
		useRock(player);
		setPiece(i, j, player.getNumber() == 0 ? game.getRock0() : game.getRock1());
		return true;
	}

	public int getScore(Player player) {
		return player.getNumber() == 0 ? (size - rocksPlayer0) * rockValue + nbQueensPlayer0 * queenValue
				: (size - rocksPlayer1) * rockValue + nbQueensPlayer1 * queenValue;
	}

	public void test2Player() {
		Board b = new Board(new Game(), 6);
		Player p1 = new Player(0);
		Player p2 = new Player(1);
		Player pActu = p1;
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		int lig, col;
		char action;

		while (true) {
			b.toStringAccess2(pActu);
			System.out.println("Saisir ligne colonne action (q/r) ou -1 pour quitter");
			lig = sc.nextInt();
			if (lig == -1) {
				break;
			}
			col = sc.nextInt();
			action = (char) sc.nextShort();

			if (action == 'q') {
				b.placeQueen2(col, lig, pActu);
			}
			pActu = pActu.getNumber() == 0 ? p2 : p1;
		}

		// Integer.parseInt(str));
	}

	// ----------------------TP4&5--------------------------
	public boolean isFinal() {
		if (nbPieces == size * size)
			return true;
		if (game.getCurrentPlayer().getNumber() == game.getPlayer0().getNumber()) {
			if (numberOfAccessible2(game.getPlayer0()) == 0 && getNumberOfRocksLeft(game.getPlayer0()) == 0)
				return true;
		}
		if (game.getCurrentPlayer().getNumber() == game.getPlayer1().getNumber()) {
			if (numberOfAccessible2(game.getPlayer1()) == 0 && getNumberOfRocksLeft(game.getPlayer1()) == 0)
				return true;
		}
		return false;
	}
	
	public boolean isFinal(Player p){
		if (nbPieces == size * size)
			return true;
		if (p.getNumber() == game.getPlayer0().getNumber()) {
			if (numberOfAccessible2(game.getPlayer0()) == 0 && getNumberOfRocksLeft(game.getPlayer0()) == 0)
				return true;
		}
		if (p.getNumber() == game.getPlayer1().getNumber()) {
			if (numberOfAccessible2(game.getPlayer1()) == 0 && getNumberOfRocksLeft(game.getPlayer1()) == 0)
				return true;
		}
		return false;
	}
	
	public Iterable<Board> getSuccessors2(Player p){
		ArrayList<Board> res = new ArrayList<Board>();
		
		for(int i=0; i < size; ++i){
			for(int j=0; j < size; ++j){
				if(isAccessible2(i, j, p)){ //queen
					Board tmp = this.clone();
					tmp.placeQueen2(i, j, p);
					res.add(tmp);
				}
				if(!board[i][j].blocksPassageway()){ //rock
					Board tmp = this.clone();
					tmp.placeRock2(i, j, p);
					res.add(tmp);
				}
			}
		}
		
		return res;
	}
	
	
	public float evaluation(Board b, Player p, int c, Eval e, Player playing){
		if(c == 0){
			return e.getEval(playing, b);
		}
		if(b.isFinal(playing)){
			return b.getScore(playing) > b.getScore(b.game.otherPlayer(playing)) ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY;
		}
		/*if(b.isFinal(b.game.otherPlayer(playing))){
			return b.getScore(playing) > b.getScore(b.game.otherPlayer(playing)) ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY;
		}*/
		
		float max = Float.NEGATIVE_INFINITY;
		for(Board elem : b.getSuccessors2(b.game.otherPlayer(playing))){
			float eval = evaluation(elem, p, c-1, e, b.game.otherPlayer(playing));
			max = max < eval ? eval : max; //récupérer le meilleur
		}
		return max;
	}
	
	
	public Board minimax(Board b, Player currentPlayer, int minimaxDepth, Eval evaluation) {
		Iterable<Board> succ = getSuccessors2(currentPlayer);
		float score_max = Float.NEGATIVE_INFINITY;
		Board e_sortie = new Board(game, size);
		float score;
		for(Board board : succ){
			score = evaluation(board, currentPlayer, minimaxDepth, evaluation, game.otherPlayer(currentPlayer));
			if(score >= score_max){
				e_sortie = board;
				score_max = score;
			}
		}
		return e_sortie;
	}

}
