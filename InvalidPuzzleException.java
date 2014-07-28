import java.util.*;

public class InvalidPuzzleException extends Exception {
  String mes;
	InvalidPuzzleException() {
		super();
	}
	
	InvalidPuzzleException(String message) {
		super(message);
                this.mes = message;
	}

      public String getInfo() {
        return this.mes;
      }

}
