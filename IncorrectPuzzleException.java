import java.util.*;

public class IncorrectPuzzleException extends Exception{
	String message; 

	public IncorrectPuzzleException (String message) {
		super(message);
		this.message = message;
	}
	public IncorrectPuzzleException() {
		super();
	}	

	public String getInfo() {
	return this.message;
	}




}
