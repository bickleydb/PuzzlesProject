import java.util.*;

public class Akari_Solver {

	public static boolean validPuzzle (Akari_Board board) {
		boolean ok = true;
		ArrayList<Integer> lights = board.getAllTheLights();
		board.clearLightsAndLit();
		if (!checkBody(board)|| !checkRight(board) || !checkUp(board) || !checkBottom(board))
			ok = false;
		board.addLitAreas(lights);
		return ok;
	}


	private static boolean checkBody(Akari_Board grid) {
		for (int i = 1; i < grid.board.length-1; i++) {
			for (int t = 1; t < grid.board[i].length-1; t++) {
				int currentSquare = grid.board[i][t];
				int numBlanks = 0;

				if (grid.board[i-1][t] == AkariConstants.EMPTY || grid.board[i-1][t] == AkariConstants.LIGHT)
					numBlanks++;
				if (grid.board[i+1][t] == AkariConstants.EMPTY || grid.board[i+1][t] == AkariConstants.LIGHT)
					numBlanks++;
				if (grid.board[i][t-1] == AkariConstants.EMPTY || grid.board[i][t-1] == AkariConstants.LIGHT)
					numBlanks++;
				if (grid.board[i][t+1] == AkariConstants.EMPTY || grid.board[i][t+1] == AkariConstants.LIGHT)
					numBlanks++;
				

				if (i != 1) {
					if (grid.board[i-2][t] == AkariConstants.ZERO_LIGHT)
						numBlanks--;
				}

				if (i != grid.board.length-2) {
					if (grid.board[i+2][t] == AkariConstants.ZERO_LIGHT)
						numBlanks--;
				}

				if (t !=  1) {
					if (grid.board[i][t-2] == AkariConstants.ZERO_LIGHT)
						numBlanks--;
				}

				if (t != grid.board[i].length-2) {
					if (grid.board[i][t+2] == AkariConstants.ZERO_LIGHT)
						numBlanks--;
				}

				if (currentSquare > numBlanks)
					return false;
			}
		}

	return true;	
	} 

	private static boolean checkLeft(Akari_Board grid) {
		int numBlanks = 0;
		for (int i = 1; i < grid.board.length-1; i++) {
				int currentSquare = grid.board[i][0];

				if (grid.board[i-1][0] == AkariConstants.EMPTY || grid.board[i-1][0] == AkariConstants.LIGHT)
					numBlanks++;
				if (grid.board[i+1][0] == AkariConstants.EMPTY || grid.board[i+1][0] == AkariConstants.LIGHT)
					numBlanks++;
				if (grid.board[i][1] == AkariConstants.EMPTY || grid.board[i][1] == AkariConstants.LIGHT)
					numBlanks++;

				if (i == 2){
					if(grid.board[0][0] == AkariConstants.ZERO_LIGHT)
						numBlanks--;
				} else if (i == grid.board.length-3) {
					if(grid.board[grid.board.length-1][0] == AkariConstants.ZERO_LIGHT)
						numBlanks--;
				} else{
					if (grid.board[i][2] == AkariConstants.ZERO_LIGHT)
						numBlanks--;
				}


				if (grid.board[i+2][2] == AkariConstants.ZERO_LIGHT)
						numBlanks--;
				if (grid.board[i-2][0] == AkariConstants.ZERO_LIGHT)
						numBlanks--;

				if(numBlanks < currentSquare)
					return false;
		}
   		 return true;  
	}	

	private static boolean checkRight(Akari_Board board) {
  
 	   return true;  
	}

	private static boolean checkUp (Akari_Board board) {
   	 return true;  
	}		

	private static boolean checkBottom (Akari_Board board) {
 	   return true;  
	}

	
	


}
