package graphics;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gameElements.*;

public class GameUI {
	private Board b; //le plateau de jeu
	private int players; //une manière de coder le mode de jeu en cours
	private JButton[][] buttons;// le tableau de boutons utilisé pour l'interface
	private boolean showingAccess;//booléen pour afficher ou pas les cases accessibles
	private JFrame frame;//fenêtre de rendu
	private int middleWidth;//coordonnées du milieu de l'écran
	private int middleHeight;
	private Player currentPlayer;//Joueur qui a la main dans le jeu à 2
	private boolean choiceWindowRunning;// boolean permettant de gérer la fenêtre de choix de la pièce à placer dans le jeu à 2
	private ArrayList<Board> solution;// solution générée par la recherche en profondeur
	private int solutionNumber;//compteur pour savoir où on en est dans la solution
	private int minimaxDepth;//pour passer la profondeur de minimax à l'interface
	private Eval evaluation;//pour passer eval0 à l'interface

	//-------------Constructeur------------
	public GameUI(Board b, int players) {
		super();
		this.b = b;
		this.players = players;
		this.buttons = new JButton[b.getSize()][b.getSize()];
		this.showingAccess= true;
		this.frame = new JFrame();
		this.middleWidth=GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width/2;
		this.middleHeight=GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height/2;
		this.currentPlayer=b.getGame().getPlayer0();
		this.choiceWindowRunning=false;
		this.solution=new ArrayList<Board>();
		this.solutionNumber=-2;
		this.minimaxDepth=2;
		this.evaluation=new Eval0();
		
	}

	//-------------------Getters et setters---------------
	public int getMinimaxDepth() {
		return minimaxDepth;
	}

	public void setMinimaxDepth(int minimaxDepth) {
		this.minimaxDepth = minimaxDepth;
	}

	public Eval getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Eval evaluation) {
		this.evaluation = evaluation;
	}

	public GameUI(Board b){
		this(b,11);
	}

	public GameUI(){
		this(new Board(),11);
	}



	public Board getB() {
		return b;
	}

	public void setB(Board b) {
		this.b = b;
	}

	public double getPlayers() {
		return players;
	}

	public void setPlayers(int players) {
		this.players = players;
	}



	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}



	public boolean isShowingAccess() {
		return showingAccess;
	}

	public void setShowingAccess(boolean showingAccess) {
		this.showingAccess = showingAccess;
	}
	

	public boolean isChoiceWindowRunning() {
		return choiceWindowRunning;
	}

	public void setChoiceWindowRunning(boolean choiceWindowRunning) {
		this.choiceWindowRunning = choiceWindowRunning;
	}
	

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	public void changePlayer(){
		if(this.getCurrentPlayer()==b.getGame().getPlayer0()){
			this.setCurrentPlayer(b.getGame().getPlayer1());
		}
		else{	
			this.setCurrentPlayer(b.getGame().getPlayer0());
		}
	}
	

	public ArrayList<Board> getSolution() {
		return solution;
	}

	public void setSolution(ArrayList<Board> solution) {
		this.solution = solution;
	}

	public int getSolutionNumber() {
		return solutionNumber;
	}

	public void setSolutionNumber(int solutionNumber) {
		this.solutionNumber = solutionNumber;
	}
	
	//-----------Méthodes---------------
	
	//retourne l'image correspondant à une case
	public ImageIcon squareToPic(Square s){

		String st = "blank";
		if(!(s instanceof Empty)){
			if(s instanceof Queen){
				st="queen";
			}
			else if(s instanceof Rock){
				st="rock";
			}
			st = s.getPlayer().toString()+st;
		}
		URL url = getClass().getResource("/"+st+".png") ;
		return new ImageIcon(Toolkit.getDefaultToolkit().getImage(url)) ; 

	}

	//Met à joue le tableau de boutons représentant le plateau
	public void updateButtons(){
		for(int i=0;i<b.getSize();i++){
			for( int j=0; j<b.getSize();j++){
				if(players/10!=2){
					if(showingAccess && b.getPiece(i, j) instanceof Empty && !b.isAccessible(i, j)){
						URL url = getClass().getResource("/red.png") ;
						buttons[i][j]=new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(url)));
					}
					else{
						buttons[i][j]=new JButton(this.squareToPic(b.getPiece(i, j)));
					}
				}
				else{
					if(showingAccess && b.getPiece(i, j) instanceof Empty && !b.isAccessible2(i, j,currentPlayer)){
						URL url = getClass().getResource("/red.png") ;
						buttons[i][j]=new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(url)));
					}
					else{
						buttons[i][j]=new JButton(this.squareToPic(b.getPiece(i, j)));
					}
				}
			}
		}
	}

	//renvoie un paneau contenant les boutons
	public JPanel toButtons(){
		this.updateButtons();
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(b.getSize(),b.getSize()));
		for(int i=0;i<b.getSize();i++){
			for( int j=0; j<b.getSize();j++){

				buttons[i][j].addActionListener(new ButtonClick(this, i, j));
				p.add(buttons[i][j]);
			}
		}
		p.setSize(1000, 1000);
		return p;
	}

	//idem dans la version 2 joueurs (ajoute les scores et le nombre de rochers pour chaque joueur, ainsi que le joueur courant)
	public JPanel toButtons2(){
		this.updateButtons();
		JPanel p=new JPanel();
		p.setLayout(new BorderLayout());
		p.add(toButtons(),BorderLayout.CENTER);
		p.add(new JLabel("Current: "+currentPlayer.toString()+"    SCORES   "+b.getGame().getPlayer0().toString()+"("+b.getNumberOfRocksLeft(b.getGame().getPlayer0())+"): "+b.getScore(b.getGame().getPlayer0())+"   "+b.getGame().getPlayer1().toString()+"("+b.getNumberOfRocksLeft(b.getGame().getPlayer1())+"): "+b.getScore(b.getGame().getPlayer1())),BorderLayout.SOUTH);
		return p;
	}
	
	//Affichages pour la fin d'une partie à 2 joueurs
	public void endGame2Players(){
		if(getB().isFinal()){
			JOptionPane.showMessageDialog(null, "There is no more available square.", "Game Over", JOptionPane.ERROR_MESSAGE);
			if(getB().getScore(getB().getGame().getPlayer0())>getB().getScore(getB().getGame().getPlayer1())){
				JOptionPane.showMessageDialog(null, "Congratulations to player "+getB().getGame().getPlayer0().toString()+" who wins!" , "Game Over", JOptionPane.ERROR_MESSAGE);
			}
			else if(getB().getScore(getB().getGame().getPlayer0())<getB().getScore(getB().getGame().getPlayer1())){
				JOptionPane.showMessageDialog(null, "Congratulations to player "+getB().getGame().getPlayer1().toString()+" who wins!", "Game Over", JOptionPane.ERROR_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(null, "It's a tie...", "Game Over", JOptionPane.ERROR_MESSAGE);
			}
		}

	}
	
	//procédure d'affichage finale
	public void finishFrame(){
		JPanel jp;

		if((int) players/10==2){
			jp = this.toButtons2();
			frame.setContentPane(jp);
			frame.setBounds(frame.getBounds().x,frame.getBounds().y,500,500+50);
		}
		else{
			jp = this.toButtons();
			frame.setContentPane(jp);
			frame.setBounds(frame.getBounds().x,frame.getBounds().y,50*b.getSize(),50*b.getSize());
		}
		frame.revalidate();

	}

	//fenêtre de choix de mode de jeu
	public void choosePlayers(){
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(6,1));
		JButton jb0 = new JButton("Simple display");
		jb0.addActionListener(new ButtonPlayers(this, -1));
		p.add(jb0);
		JButton jb1 = new JButton("Play solo");
		jb1.addActionListener(new ButtonPlayers(this, 11));
		p.add(jb1);
		JButton jb2 = new JButton("Play against the computer");
		jb2.addActionListener(new ButtonPlayers(this, 21));
		p.add(jb2);
		JButton jb3 = new JButton("Play turn by turn 2 players game");
		jb3.addActionListener(new ButtonPlayers(this, 22));
		p.add(jb3);
		JButton jb4 = new JButton("Let computer play alone");
		jb4.addActionListener(new ButtonPlayers(this, 10));
		p.add(jb4);
		JButton jb5 = new JButton("Let computer play against himself");
		jb5.addActionListener(new ButtonPlayers(this, 20));
		p.add(jb5);
		frame.setContentPane(p);
		frame.setBounds(middleWidth,middleHeight,400,300);
	}

	//fenêtre de choix de visibilité
	public void chooseVisibility(){
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2,1));
		JButton jb1 = new JButton("Display available moves");
		jb1.addActionListener(new ButtonAccess(this, true));
		p.add(jb1);
		JButton jb2 = new JButton("Hide available moves");
		jb2.addActionListener(new ButtonAccess(this, false));
		p.add(jb2);
		frame.setContentPane(p);
		frame.setBounds(frame.getBounds().x,frame.getBounds().y,250,100);
		frame.revalidate();
	}

	//méthode pour lancer le jeu
	public void launch(){
		choosePlayers();
		frame.setTitle("Queens and Rocks");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	//recherche en profondeur 
	public void calculateOneSolution(){
		try {
			solution=b.depthFirstSearch(b);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		solutionNumber=solution.size()-1;
	}


	//Classe pour gérer les actions lorsqu'on clique un bouton
	public class ButtonClick implements ActionListener{

		private GameUI g;
		private int i,j;

		public ButtonClick(GameUI g, int i, int j){
			this.g=g;
			this.i=i;
			this.j=j;
		}



		@Override
		public void actionPerformed(ActionEvent arg0) {
			Board b;
			switch (players)
			{
			case -1:
				//Affichage simple
				break;
			case 10:
				// Ordi seul
				if(g.getSolutionNumber()<-1){
					g.calculateOneSolution();
				}
				if(g.getSolutionNumber()>-1){
					g.setB(g.getSolution().get(g.getSolutionNumber()));
					g.setSolutionNumber(g.getSolutionNumber()-1);
					g.getFrame().setContentPane(g.toButtons());
					g.getFrame().revalidate();
				}
				break;
			case 20:
				//Ordi seul à deux
				b= g.getB().minimax(g.getB(), g.getCurrentPlayer(), minimaxDepth, evaluation);
				g.setB(b);
				g.changePlayer();
				g.getFrame().setContentPane(g.toButtons2());
				g.getFrame().revalidate();
				endGame2Players();
				break;
			case 21:
				//Jeu contre l'ordi
				if(g.getCurrentPlayer()==g.getB().getGame().getPlayer0()){
					if(!g.isChoiceWindowRunning()){
						JFrame jf = new JFrame();
						JPanel p = new JPanel();
						p.setLayout(new GridLayout(3,1));
						JButton jb1 = new JButton("Place a queen");
						jb1.addActionListener(new ButtonChoosePiece(g, i, j, jf, 0, g.getCurrentPlayer()));
						p.add(jb1);
						JButton jb2 = new JButton("Place a rock");
						jb2.addActionListener(new ButtonChoosePiece(g, i, j, jf, 1, g.getCurrentPlayer()));
						p.add(jb2);
						JButton jb3 = new JButton("Cancel");
						jb3.addActionListener(new ButtonChoosePiece(g, i, j, jf, 2, g.getCurrentPlayer()));
						p.add(jb3);
						jf.setContentPane(p);
						jf.setBounds(frame.getBounds().x,frame.getBounds().y,300,100);
						jf.validate();
						jf.setTitle("Queens and Rocks");
						jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						jf.setVisible(true);
						jf.setResizable(false);
						g.setChoiceWindowRunning(true);
					}
				}
				else{
					b= g.getB().minimax(g.getB(), g.getCurrentPlayer(), minimaxDepth, evaluation);
					g.setB(b);
					g.changePlayer();
					g.getFrame().setContentPane(g.toButtons2());
					g.getFrame().revalidate();
					endGame2Players();
				}
				break;
			case 22:
				//Jeu à deux
				if(!g.isChoiceWindowRunning()){
					JFrame jf = new JFrame();
					JPanel p = new JPanel();
					p.setLayout(new GridLayout(3,1));
					JButton jb1 = new JButton("Place a queen");
					jb1.addActionListener(new ButtonChoosePiece(g, i, j, jf, 0, g.getCurrentPlayer()));
					p.add(jb1);
					JButton jb2 = new JButton("Place a rock");
					jb2.addActionListener(new ButtonChoosePiece(g, i, j, jf, 1, g.getCurrentPlayer()));
					p.add(jb2);
					JButton jb3 = new JButton("Cancel");
					jb3.addActionListener(new ButtonChoosePiece(g, i, j, jf, 2, g.getCurrentPlayer()));
					p.add(jb3);
					jf.setContentPane(p);
					jf.setBounds(frame.getBounds().x,frame.getBounds().y,300,100);
					jf.validate();
					jf.setTitle("Queens and Rocks");
					jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					jf.setVisible(true);
					jf.setResizable(false);
					g.setChoiceWindowRunning(true);
				}
				break;
			default:
				//Jeu seul
				boolean trying=g.getB().placeQueen(i, j);
				if(!trying){
					JOptionPane.showMessageDialog(null, "This square is not valid!", "Unvalid square", JOptionPane.ERROR_MESSAGE); 
				}
				else{
					g.getFrame().setContentPane(g.toButtons());
					g.getFrame().revalidate();
					if(g.getB().numberOfAccessible()==0){
						JOptionPane.showMessageDialog(null, "There is no more available square.", "Game Over", JOptionPane.ERROR_MESSAGE);
						if(g.getB().numberOfQueens()==g.getB().getSize()){
							JOptionPane.showMessageDialog(null, "Congratulations, you succesfully placed all the queens!", "Game Over", JOptionPane.ERROR_MESSAGE);
						}
						else{
							JOptionPane.showMessageDialog(null, "You didn't place all the queens. Try again!", "Game Over", JOptionPane.ERROR_MESSAGE);
						}

					}
				}
			}
		}

	}

	//Classe pour les boutons de choix du mode
	public class ButtonPlayers implements ActionListener{

		private GameUI g;
		private int p;

		public ButtonPlayers(GameUI g, int players){

			this.g=g;
			this.p=players;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			g.setPlayers(p);
			g.chooseVisibility();
		}

	}


	//Classe pour les boutons de choix d'accessibilité
	public class ButtonAccess implements ActionListener{

		private GameUI g;
		private boolean a;

		public ButtonAccess(GameUI g, boolean a){
			this.g=g;
			this.a=a;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			g.setShowingAccess(a);
			g.finishFrame();
		}

	}


	//Classe pour les boutons de choix du type de pièce posée
	public class ButtonChoosePiece implements ActionListener{

		private GameUI g;
		private int i,j, piece;
		private Player player;
		private JFrame f;

		public ButtonChoosePiece(GameUI g, int i, int j, JFrame f,int piece,Player player){
			this.g=g;
			this.i=i;
			this.j=j;
			this.f=f;
			this.piece=piece;
			this.player=player;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			boolean trying;
			if(piece==2){
				f.setVisible(false);
				f.dispose();
				g.setChoiceWindowRunning(false);
			}
			else{
				if(piece==0){
					trying = g.getB().placeQueen2(i, j, player);
				}
				else{
					trying = g.getB().placeRock2(i, j, player);
				}
				if(!trying){
					JOptionPane.showMessageDialog(null, "This square is not valid!", "Unvalid square", JOptionPane.ERROR_MESSAGE); 
				}
				else{
					f.setVisible(false);
					f.dispose();
					g.setChoiceWindowRunning(false);
					g.changePlayer();
					g.getFrame().setContentPane(g.toButtons2());
					g.getFrame().revalidate();
					endGame2Players();
				}
			}

		}

	}
}

