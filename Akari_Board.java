import java.util.*;
import javax.swing.*;

public class Akari_Board extends GameBoard {

  final int LIGHT = 10;
  final int IGNORE_LIGHT = 11;
  final int TRY_START = -1;
  final int ONE_LIGHT = 1;
  final int TWO_LIGHT = 2;
  final int THREE_LIGHT = 3;
  final int FOUR_LIGHT = 4;
  final int BLOCK = 6;
  final int ZERO_LIGHT = 5;
  final int EMPTY = 0;
  final int OVERFLOW = 7;

  boolean[][] lit;

  public Akari_Board() {
    super(1, 1);
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
      for (int t = 0; t < toCopy.board[i].length; t++) {
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
      addLight(light.get(index), light.get(index+1));
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
      addLight(light.get(index), light.get(index+1));
    }
    if (num == EMPTY || num == OVERFLOW)
      this.lit[row][col] = false;
    else
      this.lit[row][col] = true;

    addLitAreas(getAllTheLights());
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
      checkLitUp(light.get(litIndex), light.get(litIndex+1));
      checkLitDown(light.get(litIndex), light.get(litIndex+1));
      checkLitRight(light.get(litIndex), light.get(litIndex+1));
      checkLitLeft(light.get(litIndex), light.get(litIndex+1));
    }
    for (int i = 0; i < light.size()-1; i+=2) {
      this.addLight(light.get(i), light.get(i+1));
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
    try {
      this.solveKnownConstraints();
    } 
    catch (InvalidPuzzleException e) {
      JOptionPane.showMessageDialog(null, e.getInfo());
    }
    //this.solveKnownConstraints();
    System.out.println("PUZZLE STATUS: " + verifiedSolved());
    ArrayList<Integer> correctLights = toSolve.getAllTheLights();
    // System.out.println(this.toString());
  }

  private void solveKnownConstraints() throws InvalidPuzzleException {
    checkTop();
    checkLeft();
    checkRight();
    checkBottom();
    checkCorners();
    checkBody();
  }

  private void fourBody(int i, int t) {
    addLight(i-1, t);
    addLight(i+1, t);
    addLight(i, t-1);
    addLight(i, t+1);
  }

  private void threeBody(int i, int t) {
    if (board[i-1][t] == LIGHT) {
      board[i-1][t] = IGNORE_LIGHT;
      board[i][t]--;
      twoBody(i, t);
      board[i][t]++;
      board[i-1][t] = LIGHT;
    }

    if (board[i+1][t] == LIGHT) {
      board[i+1][t] = IGNORE_LIGHT;
      board[i][t]--;
      twoBody(i, t);
      board[i][t]++;
      board[i+1][t] = LIGHT;
    }

    if (board[i][t-1] == LIGHT) {
      board[i][t-1] = IGNORE_LIGHT;
      board[i][t]--;
      twoBody(i, t);
      board[i][t]++;
      board[i][t-1] = LIGHT;
    }

    if (board[i][t+1] == LIGHT) {
      board[i][t+1] = IGNORE_LIGHT;
      board[i][t]--;
      twoBody(i, t);
      board[i][t]++;
      board[i][t+1] = LIGHT;
    }

    if (lit[i-1][t] && !lit[i+1][t] && !lit[i][t-1] && !lit[i][t+1]) {
      addLight(i+1, t);
      addLight(i, t-1);
      addLight(i, t+1);
    }
    if (!lit[i-1][t] && lit[i+1][t] && !lit[i][t-1] && !lit[i][t+1]) {
      addLight(i-1, t);
      addLight(i, t-1);
      addLight(i, t+1);
    }
    if (!lit[i-1][t] && !lit[i+1][t] && lit[i][t-1] && !lit[i][t+1]) {
      addLight(i+1, t);
      addLight(i-1, t);
      addLight(i, t+1);
    }
    if (!lit[i-1][t] && !lit[i+1][t] && !lit[i][t-1] && lit[i][t+1]) {
      addLight(i+1, t);
      addLight(i, t-1);
      addLight(i-1, t);
    }
  }

  private void twoBody( int i, int t) {
    if (board[i-1][t] == LIGHT) {
      board[i-1][t] = IGNORE_LIGHT;
      board[i][t]--;
      oneBody(i, t);
      board[i][t]++;
      board[i-1][t] = LIGHT;
    }

    if (board[i+1][t] == LIGHT) {
      board[i+1][t] = IGNORE_LIGHT;
      board[i][t]--;
      oneBody(i, t);
      board[i][t]++;
      board[i+1][t] = LIGHT;
    }

    if (board[i][t-1] == LIGHT) {
      board[i][t-1] = IGNORE_LIGHT;
      board[i][t]--;
      oneBody(i, t);
      board[i][t]++;
      board[i][t-1] = LIGHT;
    }

    if (board[i][t+1] == LIGHT) {
      board[i][t+1] = IGNORE_LIGHT;
      board[i][t]--;
      oneBody(i, t);
      board[i][t]++;
      board[i][t+1] = LIGHT;
    }

    if (!lit[i-1][t] && !lit[i+1][t] && lit[i][t-1] && lit[i][t+1]) {
      addLight(i-1, t);
      addLight(i+1, t);
    }
    if (!lit[i-1][t] && lit[i+1][t] && !lit[i][t-1] && lit[i][t+1]) {
      addLight(i-1, t);
      addLight(i, t-1);
    }
    if (!lit[i-1][t] && lit[i+1][t] && lit[i][t-1] && !lit[i][t+1]) {
      addLight(i-1, t);
      addLight(i, t+1);
    }
    if (lit[i-1][t] && !lit[i+1][t] && !lit[i][t-1] && lit[i][t+1]) {
      addLight(i+1, t);
      addLight(i, t-1);
    }
    if (lit[i-1][t] && !lit[i+1][t] && lit[i][t-1] && !lit[i][t+1]) {
      addLight(i+1, t);
      addLight(i, t+1);
    }
    if (lit[i-1][t] && lit[i+1][t] && !lit[i][t-1] && !lit[i][t+1]) {
      addLight(i, t-1);
      addLight(i, t+1);
    }
  }

  private void oneBody(int i, int t) {
    if (board[i-1][t] == LIGHT) {
      return;
    }

    if (board[i+1][t] == LIGHT) {
      return;
    }

    if (board[i][t-1] == LIGHT) {
      return;
    }

    if (board[i][t+1] == LIGHT) {
      return;
    }

    if (!lit[i-1][t] && lit[i+1][t] && lit[i][t-1] && lit[i][t+1]) {
      addLight(i-1, t);
    }
    if (lit[i-1][t] && !lit[i+1][t] && lit[i][t-1] && lit[i][t+1]) {
      addLight(i+1, t);
    }
    if (lit[i-1][t] && lit[i+1][t] && !lit[i][t-1] && lit[i][t+1]) {
      addLight(i, t-1);
    }
    if (lit[i-1][t] && lit[i+1][t] && lit[i][t-1] && !lit[i][t+1]) {
      addLight(i, t+1);
    }
  }

  private void checkBody() {
    for (int i = 1; i < board.length-1; i++) {
      for (int t = 1; t < board[i].length-1; t++) {
        if (board[i][t] == FOUR_LIGHT) {
          fourBody(i, t);
        }

        if (board[i][t] == THREE_LIGHT) {
          threeBody(i, t);
        }

        if (board[i][t] == TWO_LIGHT) {
          twoBody(i, t);
        }

        if (board[i][t] == ONE_LIGHT) {
          oneBody(i, t);
        }

        if (board[i][t] == ZERO_LIGHT) {
          board[i-1][t] = EMPTY;
          board[i+1][t] = EMPTY;
          board[i][t-1] = EMPTY;
          board[i][t+1] = EMPTY;
        }
      }
    }
    addLitAreas(getAllTheLights());
  }
  private void checkCorners() {
    if (board[0][0] == ONE_LIGHT) {
      if (lit[0][1] && !lit[1][0])
        addLight(1, 0);
      if (!lit[0][1] && lit[1][0])
        addLight(0, 1);
    }

    if (board[0][0] == TWO_LIGHT) {
      addLight(1, 0);
      addLight(0, 1);
    }

    if (board[0][0] == ZERO_LIGHT) {
      board[0][1] = EMPTY;
      board[1][0] = EMPTY;
    }

    if (board[0][board[0].length-1] == ONE_LIGHT) {
      if (lit[0][board[0].length-2] && !lit[1][board[0].length-1])
        addLight(1, board[0].length-1);
      if (!lit[0][board[0].length-2] && lit[1][board[0].length-1])
        addLight(0, board[0].length-2);
    }

    if (board[0][board[0].length-1] == TWO_LIGHT) {
      addLight(1, board[0].length-1);
      addLight(0, board[0].length-2);
    }

    if (board[0][board[0].length-1] == ZERO_LIGHT) {
      board[0][board[0].length-2] = EMPTY;
      board[1][board[0].length-1] = EMPTY;
    }

    if (board[board.length-1][0] == ONE_LIGHT) {
      if (lit[board.length-2][0] && !lit[board.length-1][1])
        addLight(board.length-1, 1);
      if (!lit[board.length-2][0] && lit[board.length-1][1])
        addLight(board.length-2, 0);
    }

    if (board[board.length-1][0] == TWO_LIGHT) {
      addLight(board.length-1, 1);
      addLight(board.length-2, 0);
    }

    if (board[board.length-1][0] == ZERO_LIGHT) {
      board[board.length-1][1] = EMPTY;
      board[board.length-2][0] = EMPTY;
    }

    if (board[board.length-1][board[board.length-1].length-1] == ONE_LIGHT) {
      if (lit[board.length-2][board[board.length-2].length-1] && !lit[board.length-1][board[board.length-1].length-2])
        addLight(board.length-1, board[board.length-1].length-2);
      if (!lit[board.length-2][board[board.length-2].length-1] && lit[board.length-1][board[board.length-1].length-2])
        addLight(board.length-2, board[board.length-2].length-1);
    }

    if  (board[board.length-1][board[board.length-1].length-1] == TWO_LIGHT) {
      addLight(board.length-1, board[board.length-1].length-2);
      addLight(board.length-2, board[board.length-2].length-1);
    }

    if  (board[board.length-1][board[board.length-1].length-1] == ZERO_LIGHT) {
      board[board.length-1][board[board.length-1].length-2] = EMPTY;
      board[board.length-2][board[board.length-2].length-1] = EMPTY;
    }


    addLitAreas(getAllTheLights());
  }
  private void leftOne(int i) {
    System.out.println("FOUND ONE at " + i + "," + 0); 
    if (board[0][i-1]==LIGHT || board[1][i]==LIGHT || board[0][i+1]==LIGHT) {
      System.out.println("LEFT: " + board[0][i-1]);
      System.out.println("BELOW: " + board[1][i]);
      System.out.println("RIGHT: " + board[0][i+1]);
      System.out.println("One is satisfied");
    }
    if (!lit[0][i-1] && lit[1][i] && lit[0][i+1]) {
      addLight(0, i-1);
      addLitAreas(getAllTheLights());
    }
    if (lit[0][i-1] && !lit[1][i] && lit[0][i+1]) {
      addLight(1, i);
      addLitAreas(getAllTheLights());
    }
    if (lit[0][i-1] && lit[1][i] && !lit[0][i+1]) {
      addLight(0, i+1);
      addLitAreas(getAllTheLights());
    }
    addLitAreas(getAllTheLights());
  }

  private void leftTwo(int i) {
    if (board[0][i-1] == LIGHT || board[0][i+1] == LIGHT || board[1][i] == LIGHT) {
      board[0][i]--;
      if (board[0][i-1] == LIGHT) {
        board[0][i-1] = IGNORE_LIGHT;
        leftOne(i);
        board[0][i-1] = LIGHT;
      }
      if (board[1][i] == LIGHT) {
        board[1][i] = IGNORE_LIGHT;
        leftOne(i);
        board[1][i] = LIGHT;
      }
      if (board[0][i+1] == LIGHT) {
        board[0][i+1] = IGNORE_LIGHT;
        leftOne(i);
        board[0][i+1] = LIGHT;
      }
      board[0][i] = TWO_LIGHT;
    }
  }

  private void leftThree(int i) throws InvalidPuzzleException {
    if ((board[0][i-1] < LIGHT && lit[0][i-1] == true) || (board[1][i] < LIGHT && lit[1][i]==true) || (board[0][i+1] < LIGHT && lit[0][i+1] == true)) {
      System.out.println("LEFT: " + board[0][i-1]);
      System.out.println("BELOW: " + board[1][i]);
      System.out.println("RIGHT: " + board[0][i+1]);
      throw new InvalidPuzzleException("Puzzle configuration is invalid. Constrant located at Row: " + i + " and Column " + 0 + " cannot be satisfied."+ "PENIS");
    }
    addLight(0, i-1);
    addLight(1, i);
    addLight(0, i+1);
    addLitAreas(getAllTheLights());
  }

  private void leftFour(int i) throws InvalidPuzzleException {
    throw new InvalidPuzzleException("Puzzle configuration is invalid. Constrant located at Row: " + i + " and Column " + 0 + " cannot be satisfied.");
  }

  private void checkLeft() throws InvalidPuzzleException {
    for (int i = 1; i < board[0].length-1; i++) {
      if (board[0][i] == FOUR_LIGHT)
        leftFour(i);
      if (board[0][i] == THREE_LIGHT) {
        leftThree(i);
      }
      if (board[0][i] == TWO_LIGHT) {
        leftTwo(i);
      }
      if (board[0][i] == ONE_LIGHT) {
        leftOne(i);
      }

      if (board[0][i] == ZERO_LIGHT) {
        board[0][i+1] = EMPTY;
        board[0][i-1] = EMPTY;
        board[1][i] = EMPTY;
      }
    }
  }



  private void topOne(int i) {
    System.out.println("FOUND ONE at " + i + "," + 0);
    if (board[i-1][0]==LIGHT || board[i][1]==LIGHT || board[i+1][0]==LIGHT) {
      System.out.println("LEFT: " + board[i-1][0]);
      System.out.println("BELOW: " + board[i][1]);
      System.out.println("RIGHT: " + board[i+1][0]);
      System.out.println("One is satisfied");
    }
    if (!lit[i-1][0] && lit[i][1] && lit[i+1][0]) {
      addLight(i-1, 0);
      addLitAreas(getAllTheLights());
    }
    if (lit[i-1][0] && !lit[i][1] && lit[i+1][0]) {
      addLight(i, 1);
      addLitAreas(getAllTheLights());
    }
    if (lit[i-1][0] && lit[i][1] && !lit[i+1][0]) {
      addLight(i+1, 0);
      addLitAreas(getAllTheLights());
    }
    addLitAreas(getAllTheLights());
  }

  private void rightOne(int i) {
    if (board[board.length-1][i-1]==LIGHT || board[board.length-2][i]==LIGHT || board[board.length-1][i+1]==LIGHT) {
      System.out.println("right: " + board[board.length-1][i-1]);
      System.out.println("BELOW: " + board[1][i]);
      System.out.println("RIGHT: " + board[board.length-1][i+1]);
      System.out.println("One is satisfied");
    }
    if (!lit[board.length-1][i-1] && lit[board.length-2][i] && lit[board.length-1][i+1]) {
      addLight(board.length-1, i-1);
      addLitAreas(getAllTheLights());
    }
    if (lit[board.length-1][i-1] && !lit[board.length-2][i] && lit[board.length-1][i+1]) {
      addLight(board.length-2, i);
      addLitAreas(getAllTheLights());
    }
    if (lit[board.length-1][i-1] && lit[board.length-2][i] && !lit[board.length-1][i+1]) {
      addLight(board.length-1, i+1);
      addLitAreas(getAllTheLights());
    }
    addLitAreas(getAllTheLights());
  }

  private void rightTwo(int i) {
    if (board[board.length-1][i-1] == LIGHT || board[board.length-1][i+1] == LIGHT || board[board.length-2][i] == LIGHT) {
      board[board.length-1][i]--;
      if (board[board.length-1][i-1] == LIGHT) {
        board[board.length-1][i-1] = IGNORE_LIGHT;
        rightOne(i);
        board[board.length-1][i-1] = LIGHT;
      }
      if (board[board.length-2][i] == LIGHT) {
        board[board.length-2][i] = IGNORE_LIGHT;
        rightOne(i);
        board[board.length-2][i] = LIGHT;
      }
      if (board[board.length-1][i+1] == LIGHT) {
        board[board.length-1][i+1] = IGNORE_LIGHT;
        rightOne(i);
        board[board.length-1][i+1] = LIGHT;
      }
      board[board.length-1][i] = TWO_LIGHT;
    }
  }

  private void rightThree(int i) throws InvalidPuzzleException {
    if ((board[board.length-1][i-1] < LIGHT && lit[board.length-1][i-1] == true) || (board[1][i] < LIGHT && lit[1][i]==true) || (board[board.length-1][i+1] < LIGHT && lit[board.length-1][i+1] == true)) {
      System.out.println("right: " + board[board.length-1][i-1]);
      System.out.println("BELOW: " + board[1][i]);
      System.out.println("RIGHT: " + board[board.length-1][i+1]);
      //throw new InvalidPuzzleException("Puzzle configuration is invalid. Constrant located at Row: " + i + " and Column " + (board.length-1) + " cannot be satisfied."+ "PENIS");
    }
    addLight(board.length-1, i-1);
    addLight(board.length-2, i);
    addLight(board.length-1, i+1);
    addLitAreas(getAllTheLights());
  }

  private void rightFour(int i) throws InvalidPuzzleException {
    throw new InvalidPuzzleException("Puzzle configuration is invalid. Constrant located at Row: " + i + " and Column " + (board.length-1) + " cannot be satisfied.");
  }

  private void checkRight() throws InvalidPuzzleException {
    for (int i = 1; i < board[0].length-1; i++) {
      if (board[board.length-1][i] == FOUR_LIGHT)
        rightFour(i);
      if (board[board.length-1][i] == THREE_LIGHT) {
        rightThree(i);
      }
      if (board[board.length-1][i] == TWO_LIGHT) {
        rightTwo(i);
      }
      if (board[board.length-1][i] == ONE_LIGHT) {
        rightOne(i);
      }
      if (board[board.length-1][i] == ZERO_LIGHT) {
        board[board.length-2][i] = EMPTY;
        board[board.length-1][i+1] = EMPTY;
        board[board.length-1][i-1] = EMPTY;
      }
    }
  }



  private void topTwo(int i) {
    if (board[i-1][0] == LIGHT || board[i+1][0] == LIGHT || board[i][1] == LIGHT) {
      board[i][0]--;
      if (board[i-1][0] == LIGHT) {
        board[i-1][0] = IGNORE_LIGHT;
        topOne(i);
        board[i-1][0] = LIGHT;
      }
      if (board[i][1] == LIGHT) {
        board[i][1] = IGNORE_LIGHT;
        topOne(i);
        board[i][1] = LIGHT;
      }
      if (board[i+1][0] == LIGHT) {
        board[i+1][0] = IGNORE_LIGHT;
        topOne(i);
        board[i+1][0] = LIGHT;
      }
      board[i][0] = TWO_LIGHT;
    }
  }

  private void topThree(int i) throws InvalidPuzzleException {
    if ((board[i-1][0] < LIGHT && lit[i-1][0] == true) || (board[i][1] < LIGHT && lit[i][1]==true) || (board[i+1][0] < LIGHT && lit[i+1][0] == true)) {
      System.out.println("LEFT: " + board[i-1][0]);
      System.out.println("BELOW: " + board[i][1]);
      System.out.println("RIGHT: " + board[i+1][0]);
      throw new InvalidPuzzleException("Puzzle configuration is invalid. Constrant located at Row: " + i + " and Column " + 0 + " cannot be satisfied."+ "PENIS");
    }
    addLight(i-1, 0);
    addLight(i, 1);
    addLight(i+1, 0);
    addLitAreas(getAllTheLights());
  }

  private void topFour(int i) throws InvalidPuzzleException {
    throw new InvalidPuzzleException("Puzzle configuration is invalid. Constrant located at Row: " + i + " and Column " + 0 + " cannot be satisfied.");
  }

  private void checkTop() throws InvalidPuzzleException {
    for (int i = 1; i < board.length-1; i++) {
      if (board[i][0] == FOUR_LIGHT)
        topFour(i);
      if (board[i][0] == THREE_LIGHT) {
        topThree(i);
      }
      if (board[i][0] == TWO_LIGHT) {
        topTwo(i);
      }
      if (board[i][0] == ONE_LIGHT) {
        topOne(i);
      }
      if (board[i][0] == ZERO_LIGHT) {
        board[i-1][0] = EMPTY;
        board[i+1][0] = EMPTY;
        board[i][1] = EMPTY;
      }
    }
  }
  private void bottomOne(int i) {
    if (board[i-1][board.length-1]==LIGHT || board[i][board.length-2]==LIGHT || board[i+1][board.length-1]==LIGHT) {
      System.out.println("LEFT: " + board[i-1][board.length-1]);
      System.out.println("BELOW: " + board[i][board.length-2]);
      System.out.println("RIGHT: " + board[i+1][board.length-1]);
      System.out.println("One is satisfied");
    }
    if (!lit[i-1][board.length-1] && lit[i][board.length-2] && lit[i+1][board.length-1]) {
      addLight(i-1, board.length-1);
      addLitAreas(getAllTheLights());
    }
    if (lit[i-1][board.length-1] && !lit[i][board.length-2] && lit[i+1][board.length-1]) {
      addLight(i, board.length-2);
      addLitAreas(getAllTheLights());
    }
    if (lit[i-1][board.length-1] && lit[i][board.length-2] && !lit[i+1][board.length-1]) {
      addLight(i+1, board.length-1);
      addLitAreas(getAllTheLights());
    }
    addLitAreas(getAllTheLights());
  }

  private void bottomTwo(int i) {
    if (board[i-1][board.length-1] == LIGHT || board[i+1][board.length-1] == LIGHT || board[i][board.length-2] == LIGHT) {
      board[i][board.length-1]--;
      if (board[i-1][board.length-1] == LIGHT) {
        board[i-1][board.length-1] = IGNORE_LIGHT;
        bottomOne(i);
        board[i-1][board.length-1] = LIGHT;
      }
      if (board[i][board.length-2] == LIGHT) {
        board[i][board.length-2] = IGNORE_LIGHT;
        bottomOne(i);
        board[i][board.length-2] = LIGHT;
      }
      if (board[i+1][board.length-1] == LIGHT) {
        board[i+1][board.length-1] = IGNORE_LIGHT;
        bottomOne(i);
        board[i+1][board.length-1] = LIGHT;
      }
      board[i][board.length-1] = TWO_LIGHT;
    }
  }

  private void bottomThree(int i) throws InvalidPuzzleException {
    if ((board[i-1][board[i].length-1] < LIGHT && lit[i-1][board[i].length-1] == true) || (board[i][board[i].length-2] < LIGHT && lit[i][board[i].length-2]==true) || (board[i+1][board[i].length-1] < LIGHT && lit[i+1][board[i].length-1] == true)) {
      System.out.println("LEFT: " + board[i-1][board.length-1]);
      System.out.println("BELOW: " + board[i][board.length-2]);
      System.out.println("RIGHT: " + board[i+1][board.length-1]);
      throw new InvalidPuzzleException("Puzzle configuration is invalid. Constrant located at Row: " + i + " and Column " + (board.length-1) + " cannot be satisfied."+ "PENIS");
    }
    addLight(i-1, board.length-1);
    addLight(i, board.length-2);
    addLight(i+1, board.length-1);
    addLitAreas(getAllTheLights());
  }

  private void bottomFour(int i) throws InvalidPuzzleException {
    throw new InvalidPuzzleException("Puzzle configuration is invalid. Constrant located at Row: " + i + " and Column " + (board.length-1) + " cannot be satisfied.");
  }

  private void checkBottom() throws InvalidPuzzleException {
    for (int i = 1; i < board.length-1; i++) {
      if (board[i][board[i].length-1] == FOUR_LIGHT)
        bottomFour(i);
      if (board[i][board[i].length-1] == THREE_LIGHT) {
        bottomThree(i);
      }
      if (board[i][board[i].length-1] == TWO_LIGHT) {
        bottomTwo(i);
      }
      if (board[i][board[i].length-1] == ONE_LIGHT) {
        leftOne(i);
      }
      if (board[i][board[i].length-1] == ZERO_LIGHT) {
        board[i][board.length-2] =EMPTY;
        board[i+1][board.length-1] = EMPTY;
        board[i-1][board.length-1] = EMPTY;
      }
    }
  }






  public String toString() {
    String returnStr = "";
    for (int i = 0; i < board.length; i++) {
      for (int t = 0; t < board[i].length; t++) {
        returnStr = returnStr + "[ " + lit[i][t] + " , " + board[i][t] +"] ";
      }
      returnStr = returnStr + "/n";
    }
    return returnStr;
  }

  private boolean verifiedSolved() {
    for (int i = 0; i < lit.length; i++) {
      for (int t = 0; t < lit[0].length; t++) {
        if (lit[i][t] == false && board[i][t] == EMPTY)
          return false;
      }
    }
    return true;
  }
}

