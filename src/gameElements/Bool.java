package gameElements;

public class Bool {
	private boolean rock;
	private boolean queen;
	
	public Bool(boolean bo){
		rock = bo;
		queen = bo;
	}
	
	public void setRock(boolean bo){
		rock = bo;
	}
	
	public boolean getRock(){
		return rock;
	}
	
	public void setQueen(boolean bo){
		queen = bo;
	}
	
	public boolean getQueen(){
		return queen;
	}


}
