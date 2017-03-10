package gameElements;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;


public class Board {
	
	//ATTENTION
	//Ceci est un squelette incomplet contenant uniquement le profil de quelques mﾃｩthodes, dans le but de compiler la classe GameUI sans erreurs
	//Il manque les getters et les setters ainsi que les classes externes telles que Square, Eval, Game, Player,...
	
	private Game game;
	private int size;
	private int nbPieces;
	private Square[][] board;
	
	
	//---------------TP1------------------------
	public Board(Game g, int size, int nbPieces, Square[][] board){
		this.game = g;
		this.size = size;
		this.nbPieces = nbPieces;
		this.board = board;
	}
	
	public Board(Game g, int size){
		this.game = g;
		this.size = size;
		this.nbPieces = 0;
		this.board = new Square[size][size];
		
		//init board with empty cells
		for(int i=0; i < size; i++){
			for(int j=0; j < size; j++){
				this.board[i][j] = new Empty();
			}
		}
	}
	
	//default size of the board : 8
	public Board(Game g){
		this(g, 8);
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
	
	public void setPiece(int i, int j, Square s){
		board[i][j] = s;
		nbPieces++;
	}
	
	public void removePiece(int i, int j){
		board[i][j] = game.getEmpty();
	}
	
	public boolean isEmpty(int i, int j){
		return board[i][j].toString() == game.getEmpty().toString();
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i < size; i++){
			for(int j=0; j < size; j++){
				sb.append(board[i][j].toString() + '\t');
			}
			sb.append('\n');
		}
		
		return sb.toString();
	}
	
	public Board clone(){
		Board ans = new Board(game, size, nbPieces, null);
		
		Square[][] b = new Square[size][size];
		for(int i=0; i < size; i++){
			for(int j=0; j < size; j++){
				b[i][j] = board[i][j];
			}
		}
		ans.setBoard(b);
		
		return ans;
	}

	public boolean isAccessible(int i, int j) {
		return (!board[i][j].blocksPassageway()) && isLineAccessible(i, j) && isColumnAccessible(i, j)
				&& isRightDiagonalAccessible(i, j) && isLeftDiagonalAccessible(i, j);
	}
	
	
	private boolean isLineAccessible(int i, int j){
		for(int k=j-1; k >= 0; k--){
			if(board[i][k].blocksPassageway()){
				return false;
			}
		}
		for(int k=j+1; k < size; k++){
			if(board[i][k].blocksPassageway()){
				return false;
			}
		}
		return true;
	}
	
	private boolean isColumnAccessible(int i, int j){
		for(int k=i-1; k >= 0; k--){
			if(board[k][j].blocksPassageway()){
				return false;
			}
		}
		for(int k=i+1; k < size; k++){
			if(board[k][j].blocksPassageway()){
				return false;
			}
		}
		return true;
	}
	
	private boolean isLeftDiagonalAccessible(int i, int j){
		int lig = i+1, col = j+1;
		while(lig < size && col < size){
			if(board[lig][col].blocksPassageway()){
				return false;
			}
			lig++; col++;
		}
		lig = i-1; col = j-1;
		while(lig >= 0 && col >= 0){
			if(board[lig][col].blocksPassageway()){
				return false;
			}
			lig--; col--;
		}
		
		return true;
	}
	
	
	private boolean isRightDiagonalAccessible(int i, int j){
		int lig = i+1, col = j-1;
		while(lig < size && col >= 0){
			if(board[lig][col].blocksPassageway()){
				return false;
			}
			lig++; col--;
		}
		lig = i-1; col = j+1;
		while(lig >= 0 && col < size){
			if(board[lig][col].blocksPassageway()){
				return false;
			}
			lig--; col++;
		}
		
		return true;
	}
	
	
	public String toStringAccess(){
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i < size; i++){
			for(int j=0; j < size; j++){
				if(board[i][j].blocksPassageway()){
					sb.append(board[i][j].toString() + '\t');
				}else if(isAccessible(i, j)){
					sb.append(board[i][j].toString() + '\t');
				}else{
					sb.append("xx\t");
				}
			}
			sb.append('\n');
		}
		
		return sb.toString();
	}
	

	public int numberOfAccessible() {
		int res = 0;
		for(int i=0; i < size; i++){
			for(int j=0; j < size; j++){
				res += isAccessible(i, j) ? 1 : 0;
			}
		}
		return res;
	}

	public int numberOfQueens() {
		int res = 0;
		for(int i=0; i < size; i++){
			for(int j=0; j < size; j++){
				res += board[i][j].blocksPassageway() ? 1 : 0;
			}
		}
		return res;
	}
	
	public boolean placeQueen(int i, int j) {
		if(!isAccessible(i, j)){
			return false;
		}
		
		board[i][j] = new Queen();
		board[i][j].setPlayer(game.getPlayer0());
		
		return true;
	}
	
	//----------TP2-----------------------
	public boolean isSolution(){
		return numberOfQueens() == size;
	}
	
	public Iterable<Board> getSuccessors(){
		ArrayList<Board> res = new ArrayList<Board>();
		for(int i=0; i < size; i++){
			for(int j=0; j < size; j++){
				if(isAccessible(i, j)){
					Board other = this.clone();
					assert(other.placeQueen(i, j));
					//System.out.println(other.toString() + "\n----\n");
					res.add(other);
				}
			}
		}
		//System.out.println("nb succs : "+res.size());
		return res;
	}
	
	public Iterable<Board> getNewSuccessors(){
		ArrayList<Board> res= new ArrayList<Board>();
		for(int j=0; j < size; j++){
			if(isAccessible(numberOfQueens(), j)){
				Board tmp = this.clone();
				assert(tmp.placeQueen(numberOfQueens(), j));
				res.add(tmp);
			}
		}
		return res;
	}
	
	public ArrayList<Board> depthFirstSearch(Board initialState) {
		if(initialState.isSolution()){
			ArrayList<Board> res = new ArrayList<Board>();
			res.add(initialState);
			return res;
		}
		for(Board succ : initialState.getSuccessors()){
			try{
				ArrayList<Board> res = depthFirstSearch(succ);
				res.add(initialState);
				return res;
			}
			catch(NoSuchElementException e){
				//System.out.println("échec");
			}
		}
		throw new NoSuchElementException("DepthFirstSearch failure");
	}
	
	
	public ArrayList<Board> depthfirstSearch(){
		return depthFirstSearch(this);
	}
	
	
	public ArrayList<Board> depthFirstSearch2(Board initialState){
		if(initialState.isSolution()){
			ArrayList<Board> res = new ArrayList<Board>();
			res.add(initialState);
			return res;
		}
		for(Board succ : initialState.getNewSuccessors()){
			try{
				ArrayList<Board> res = depthFirstSearch(succ);
				res.add(initialState);
				return res;
			}
			catch(NoSuchElementException e){
				//System.out.println("échec");
			}
		}
		throw new NoSuchElementException("DepthFirstSearch failure");
	}
	
	public ArrayList<Board> depthFirstSearch2(){
		return depthFirstSearch2(this);
	}
	
	
	public String solutionSteps(Board b){
		StringBuilder res = new StringBuilder();
		
		ArrayList<Board> sol = depthFirstSearch(b);
		for(int i=sol.size()-1; i >= 0; i--){
			res.append(sol.get(i).toString());
			res.append("\n *** \n");
		}

		return res.toString();
	}
	
	public String solutionSteps2(Board b){
		StringBuilder res = new StringBuilder();
		
		ArrayList<Board> sol = depthFirstSearch2(b);
		for(int i=sol.size()-1; i >= 0; i--){
			res.append(sol.get(i).toString());
			res.append("\n *** \n");
		}

		return res.toString();
	}
	
	
	public int[] BoardToArray(Board b){
		int[] res = new int[b.getSize()];
		
		for(int i=0; i < b.getSize(); i++){
			res[i] = -1;
			for(int j=0; j < b.getSize(); j++){
				if(b.board[i][j].blocksPassageway()){
					res[i] = j;
				}
			}
		}
		return res;
	}
	
	public Board arrayToBoard(int[] b){
		Board res = new Board(getGame(), b.length);
		
		for(int i=0; i < b.length; i++){
			if(b[i] != -1){
				assert(res.placeQueen(i, b[i]));
			}
		}
		
		return res;
	}
	
	
	public Iterable<int[]> getArraySuccessors(int[] b){
		ArrayList<int[]> res = new ArrayList<int[]>();
		
		Board tmp = arrayToBoard(b);
		Iterable<Board> succ = tmp.getNewSuccessors();
		for(Board cur : succ){
			res.add(BoardToArray(cur));
		}
		
		return res;
	}
	
	public boolean isSolutionArray(int[] array){
		if(array[array.length-1] == -1){
			return false;
		}
		else{
			//System.out.println("trouve\n");
			return true;
		}
	}
	
	
	public ArrayList<int[]> depthFirstSearchArray(int[] initialState){
		if(isSolutionArray(initialState)){
			ArrayList<int[]> res = new ArrayList<int[]>();
			res.add(initialState);
			return res;
		}
		for(int[] succ : getArraySuccessors(initialState)){
			try{
				ArrayList<int[]> res = depthFirstSearchArray(succ);
				res.add(initialState);
				return res;
			}
			catch(NoSuchElementException e){
				//System.out.println("échec");
			}
		}
		throw new NoSuchElementException("DepthFirstSearch failure");
	}
	
	public ArrayList<int[]> depthFirstSearchArray(){
		return depthFirstSearchArray(BoardToArray(this));
	}
	
	public String solutionStepsArray(int[] b){
		StringBuilder res = new StringBuilder();
		
		ArrayList<int[]> sol = depthFirstSearchArray(b);
		for(int i=sol.size()-1; i >= 0; i--){
			res.append(sol.get(i).toString());
			res.append("\n *** \n");
		}

		return res.toString();
	}
	
	
	//------------TP3----------------------
	public boolean isAccessible2(int i, int j, Player currentPlayer) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public boolean placeQueen2(int i, int j, Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean placeRock2(int i, int j, Player player) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public int getNumberOfRocksLeft(Player player){
		// TODO Auto-generated method stub
		return 0;  
	}
	
	public int getScore(Player player){
		// TODO Auto-generated method stub
		return 0;
	}



	//----------------------TP4&5--------------------------
	public boolean isFinal() {
		// TODO Auto-generated method stub
		return false;
	}

	public Board minimax(Board b, Player currentPlayer, int minimaxDepth, Eval evaluation) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	





	
	

}
