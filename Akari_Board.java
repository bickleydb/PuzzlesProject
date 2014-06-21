import java.util.*;

public class Akari_Board extends GameBoard {

  final int LIGHT = 10;
  final int TRY_START = -1;
  final int ONE_LIGHT = 1;
  final int TWO_LIGHT = 2;
  final int THREE_LIGHT = 3;
  final int FOUR_LIGHT = 4;
  final int BLOCK = 5;
  final int EMPTY = 0;
  final int OVERFLOW = 6;

  boolean[][] lit;

  public Akari_Board() {
    super(1,1);
    lit = new boolean[1][1];
  }
  
  public Akari_Board (int row, int col) {
    super(row, col); 
    lit = new boolean[row][col];
  }

  public Akari_Board (String fileName) {
    super(fileName);
    lit = new boolean[board.length][board[0].length];
  }
  
  public Akari_Board (Akari_Board toCopy) {
    super(toCopy);
    lit = new boolean[this.rowSize][this.colSize];
    for (int i = 0; i < toCopy.board.length; i++) {
      for (int t = 0; t < toCopy.board[0].length; t++) {
        this.lit[i][t] = toCopy.lit[i][t];
      }
    }
  }
  
  public int[][] getBoard() {
    return board;
  }
  
  public void setLit(boolean[][] l) {
   this.lit = l; 
  }
  
  public boolean[][] getLit() {
    return lit;
  }


  public void addLight(int row, int col) {
   // System.out.println("Entering addlight");
    this.board[row][col] = LIGHT;
   // System.out.println(this.board[row][col]);
   // addLitAreas();
   // System.out.println("Leaving addlight");
   // solve();
  }
  
  public void removeLight (int row, int col) {
    this.board[row][col] = EMPTY;
    //this.lit = new boolean[this.rowSize][this.colSize];
    //clearLightsAndLit();
    ArrayList<Integer> light = getAllTheLights();
    addLitAreas(light);
    
  }

  public void remove(int row, int col) {
    this.board[row][col] = EMPTY;
    ArrayList<Integer> light = getAllTheLights();
    clearLightsAndLit();
    for (int index = 0; index < light.size()-1; index+=2) {
      addLight(light.get(index),light.get(index+1));
    }
  }

  public void addBlock(int row, int col) {
    this.board[row][col] = BLOCK;
  }

  public void addConst (int row, int col, int num) {
    this.board[row][col] = num;
    ArrayList<Integer> light = getAllTheLights();
    clearLightsAndLit();
    for (int index = 0; index < light.size()-1; index+=2) {
      addLight(light.get(index),light.get(index+1));
    }
    if (num == EMPTY || num == OVERFLOW)
     this.lit[row][col] = false;
    else
      this.lit[row][col] = true;
  }


 public ArrayList<Integer> getAllTheLights() {
   // System.out.println("LIGHTS");
    ArrayList<Integer> light = new ArrayList<Integer>();
    for (int  rowIndex = 0; rowIndex < rowSize; rowIndex++) {
      for (int colIndex = 0; colIndex < colSize; colIndex++) {
        if (board[rowIndex][colIndex] == LIGHT) {
          light.add(rowIndex);
          light.add(colIndex);
        //  System.out.println("ROW" + rowIndex);
        // System.out.println("COL" + colIndex);
        }
      }
    }
    //System.out.println("LIGHT " + light.size());
    return light;
  }
  public void addLitAreas(ArrayList<Integer> light) {
   // System.out.println("ENTERING LIT AREAS");
    clearLightsAndLit();
    for (int litIndex = 0; litIndex < light.size()-1; litIndex+=2) {
      checkLitUp(light.get(litIndex),light.get(litIndex+1));
      checkLitDown(light.get(litIndex),light.get(litIndex+1));
      checkLitRight(light.get(litIndex),light.get(litIndex+1));
      checkLitLeft(light.get(litIndex),light.get(litIndex+1));
    }
    for (int i = 0; i < light.size()-1; i+=2) {
     this.addLight(light.get(i),light.get(i+1));
    }
    //System.out.println("LEAVING LIT AREAS\n");
  }
  
  private void clearLightsAndLit() {
     for (int  rowIndex = 0; rowIndex < rowSize; rowIndex++) {
      for (int colIndex = 0; colIndex < colSize; colIndex++) {
        lit[rowIndex][colIndex] = false;
        if (board[rowIndex][colIndex] == LIGHT) {
          board[rowIndex][colIndex] = EMPTY;
        }
      }
     }
  }
  
  private void checkLitUp(int rowNum, int colNum) {
    for (int index = rowNum; index >= 0; index--) {
      if (board[index][colNum] > EMPTY && board[index][colNum] < LIGHT)
        return;
      lit[index][colNum] = true;
    }
  }
  
  private void checkLitDown(int rowNum, int colNum) {
    for (int index = rowNum; index < rowSize; index++) {
      if (board[index][colNum] > EMPTY && board[index][colNum] < LIGHT)
        return;
      lit[index][colNum] = true;
    }
  }
  
 private void checkLitLeft(int rowNum, int colNum) {
    for (int index = colNum; index >= 0; index--) {
      if (board[rowNum][index] > EMPTY && board[rowNum][index] < LIGHT)
        return;
      lit[rowNum][index] = true;
    }
  }
  
   private void checkLitRight(int rowNum, int colNum) {
    for (int index = colNum; index < colSize; index++) {
      if (board[rowNum][index] > EMPTY && board[rowNum][index] < LIGHT)
        return;
      lit[rowNum][index] = true;
    }
  }
  
  
  public void solve() {
    Akari_Board toSolve = new Akari_Board(this);
    System.out.println("PUZZLE STATUS: " + verifiedSolved());
    this.solveKnownConstraints();
    ArrayList<Integer> correctLights = toSolve.getAllTheLights();
    
    
    
    
    
    
    
  }
  
  private void solveKnownConstraints() {
    //checkTop();
    checkLeft();
    //checkRight();
    //checkBottom();
    //checkCorners();
    
    
  }
  
  private void checkLeft() {
    for (int i = 1; i < board[0].length-1; i++) {
      switch (board[0][i]) {
        case (ONE_LIGHT):
           if (board[0][i-1] == LIGHT || board[0][i+1] == LIGHT || board[1][i] == LIGHT) {
             break;
           }
           if (lit[0][i-1] && lit[0][i+1] && !lit[1][i]) {
              addLight(1,i);
              addLitAreas(getAllTheLights());
           }
           if (lit[0][i-1] && !lit[0][i+1] && lit[1][i]) {
              addLight(0,i+1);
              addLitAreas(getAllTheLights());
           }
           if (!lit[0][i-1] && lit[0][i+1] && lit[1][i]) {
              addLight(0,i-1);
              addLitAreas(getAllTheLights());
           }
           break;
           
        case (TWO_LIGHT):
          if ((board[0][i-1] == LIGHT && board[0][i+1] == LIGHT) || (board[0][i-1] == LIGHT && board[1][i] == LIGHT) || (board[1][i] == LIGHT && board[0][i+1] == LIGHT)) {
            System.out.println("TWO CLOSED");
            break;
          }
          if (board[0][i-1] == LIGHT || board[0][i+1] == LIGHT || board[1][i] == LIGHT){
             System.out.println("TWO ONE");
             board[0][i]--;
             checkLeft();
             board[0][i]++;
             break;
          }
        if (lit[0][i-1] && !lit[0][i+1] && !lit[1][i]) {
              addLight(1,i);
              addLight(0,i+1);
              addLitAreas(getAllTheLights());
           }
           if (!lit[0][i-1] && !lit[0][i+1] && lit[1][i]) {
              addLight(0,i+1);
              addLight(0,i-1);
              addLitAreas(getAllTheLights());
           }
           if (!lit[0][i-1] && lit[0][i+1] && lit[1][i]) {
              addLight(0,i-1);
              addLitAreas(getAllTheLights());
           }
          break;
        
      }  
    }
  }
  
  private boolean verifiedSolved() {
   for (int i = 0; i < lit.length; i++) {
    for (int t = 0; t < lit[0].length; t++) {
      if (lit[i][t] == false)
      return false;
      
    }
  }
  return true;

  }
  
}
  

