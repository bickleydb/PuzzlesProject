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


  public Akari_Board (int row, int col) {
    super(row, col); 
    lit = new boolean[row][col];
  }

  public Akari_Board (String fileName) {
    super(fileName);
    lit = new boolean[board.length][board[0].length];
  }
  
  public int[][] getBoard() {
    return board;
  }
  
  public boolean[][] getLit() {
    return lit;
  }


  public void addLight(int row, int col) {
    this.board[row][col] = LIGHT;
    addLitAreas();
  }

  public void remove(int row, int col) {
    this.board[row][col] = EMPTY;
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
  }


  ArrayList<Integer> getAllTheLights() {
    ArrayList<Integer> light = new ArrayList<Integer>();
    for (int  rowIndex = 0; rowIndex < rowSize; rowIndex++) {
      for (int colIndex = 0; colIndex < colSize; colIndex++) {
        if (board[rowIndex][colIndex] == LIGHT) {
          light.add(rowIndex);
          light.add(colIndex);
        }
      }
    }
    return light;
  }
  private void addLitAreas() {
    ArrayList<Integer> light = getAllTheLights();
    for (int litIndex = 0; litIndex < light.size()-1; litIndex+=2) {
      checkLitUp(light.get(litIndex),light.get(litIndex+1));
      checkLitDown(light.get(litIndex),light.get(litIndex+1));
      checkLitRight(light.get(litIndex),light.get(litIndex+1));
      checkLitLeft(light.get(litIndex),light.get(litIndex+1));
      
      
    }
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
}
  
  
  
