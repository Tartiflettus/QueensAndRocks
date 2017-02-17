package exception;

public class InvalidColorModeException extends RuntimeException {
	char c;

	public InvalidColorModeException(char c) {
		this.c = c;
	}
	
	@Override
	public String getMessage(){
		return "Invalid character : \'"+ c +"\'";
	}

}
