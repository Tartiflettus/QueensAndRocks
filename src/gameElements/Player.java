package gameElements;

import exception.InvalidColorModeException;

public class Player {
	private int number;
	private String colorMode;
	
	public Player(int number) {
		this.number = number;
		this.colorMode = "bw";
	}
	
	

	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}


	public String getColorMode() {
		return colorMode;
	}



	public void setColorMode(String colorMode) {
		this.colorMode = colorMode;
	}



	public String toString(){
		return caracToString(colorMode.charAt(number));
	}
	
	private String caracToString(char c){
		switch(c){
			case 'o':
				return "orange";
			case 'g':
				return "green";
			case 'w':
				return "white";
			case 'b':
				return "black";
			default:
				throw new InvalidColorModeException(c);
		}
	}


}
