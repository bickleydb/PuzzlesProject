import javax.swing.*;
/*
The Gameboard is set up like Row and Column.
 Row = Y
 Col = X
 
 */

//Constants for creating the GameBoard
final double PIX_TO_CM = 37.7952;
final int OFFSET = 50;

//Constants for solving Akari
final int LIGHT = 10;
final int TRY_START = -1;
final int ONE_LIGHT = 1;
final int TWO_LIGHT = 2;
final int THREE_LIGHT = 3;
final int FOUR_LIGHT = 4;
final int BLOCK = 6;
final int EMPTY = 0;
final int OVERFLOW = 7;
final int ZERO_LIGHT = 5;


static boolean debug = false;

//Board Specific Variables
Akari_Board puzzle;

private int contains(String[] arr, String stri) {
  int i = 0;
 for( String s: arr){
   if (stri.equals(s))
     return i;
     i++;
 }
 return -1;
}

void setup() {
  String jobToDo = "";
  puzzle = new Akari_Board();
  String[] answers = new String[8];
  answers[0] = "load";
  answers[1] = "l";
  answers[2] = "open";
  answers[3] = "o";
  answers[4] = "new";
  answers[5] = "n";
  answers[6] = "create";
  answers[7] = "c";
  
  do{
    if (debug)
      System.out.println("Recieving user input");
  jobToDo = JOptionPane.showInputDialog("Would you like to load a puzzle or create a new puzzle?");
  jobToDo = jobToDo.toLowerCase();
  
  } while (contains(answers,jobToDo)== -1);
  
  if (contains(answers,jobToDo) >= 0 && contains(answers,jobToDo)<4) {
    if (debug)
      System.out.println("Loading Puzzle");
    loadPuzzle();
    this.puzzle.addLitAreas(this.puzzle.getAllTheLights());
  } else {
    if(debug)
    System.out.println("New Puzzle");
  String rowStr = JOptionPane.showInputDialog("How many rows are there?");
  String colStr = JOptionPane.showInputDialog("How many columns are there?");
  int r = Integer.parseInt(rowStr);
  int c = Integer.parseInt(colStr);
  puzzle = new Akari_Board(r,c);
  }
  if(debug)
  System.out.println("GUI Setup");
  background(50,0,0); 
  //Makes the window the right size
  size((int)(2*OFFSET+puzzle.rowSize*PIX_TO_CM), (int)(2*OFFSET+puzzle.colSize*PIX_TO_CM));
  drawBoard();
  makeButtons();
}

void makeButtons() {
  if(debug)
    System.out.println("Making Buttons");
  fill(200,200,200);
  rect(2*width/12,10,width/6,25);
  rect(5*width/12,10,width/6,25);
  rect(8*width/12,10,width/6,25); 
}

void drawBoard() {
  if (debug)
    System.out.println("Drawing Buttons");
  int[][]board;
  boolean[][] lit;
  board = puzzle.getBoard();
  lit = puzzle.getLit();
  for (int rowIndex = 0; rowIndex  < puzzle.rowSize; rowIndex++) {
    for (int colIndex = 0; colIndex < puzzle.colSize; colIndex++) {
      switch (board[rowIndex][colIndex]) {
      case EMPTY: //If the grid square does not contain anything
        if (lit[rowIndex][colIndex])
          fill(0, 100, 0); //If its filled be different color
        else
          fill(255, 255, 255); //If not filled, be white
        rect((int)(OFFSET+rowIndex*PIX_TO_CM), (int)(OFFSET+colIndex*PIX_TO_CM), (int)PIX_TO_CM, (int)PIX_TO_CM);
        break;
      case ONE_LIGHT: //If the grid square will have an adjacent light
        fill(0, 0, 0); //Make it black
        rect((int)(OFFSET+rowIndex*PIX_TO_CM), (int)(OFFSET+colIndex*PIX_TO_CM), (int)PIX_TO_CM, (int)PIX_TO_CM);
        fill(255,255,255);
        textSize(27);
        text("1",(int)(OFFSET+rowIndex*PIX_TO_CM+PIX_TO_CM/4),(int)(OFFSET+colIndex*PIX_TO_CM+3*PIX_TO_CM/4));
        break;
      case TWO_LIGHT: //If the grid square will have two adjacent lights
        fill(0, 0, 0);
        rect((int)(OFFSET+rowIndex*PIX_TO_CM), (int)(OFFSET+colIndex*PIX_TO_CM), (int)PIX_TO_CM, (int)PIX_TO_CM);
        fill(255,255,255);
        textSize(27);
        text("2",(int)(OFFSET+rowIndex*PIX_TO_CM+PIX_TO_CM/4),(int)(OFFSET+colIndex*PIX_TO_CM+3*PIX_TO_CM/4));
        break;
      case THREE_LIGHT: //Three adjacent lights
        fill(0, 0, 0);
        rect((int)(OFFSET+rowIndex*PIX_TO_CM), (int)(OFFSET+colIndex*PIX_TO_CM), (int)PIX_TO_CM, (int)PIX_TO_CM);
        fill(255,255,255);
        textSize(27);
        text("3",(int)(OFFSET+rowIndex*PIX_TO_CM+PIX_TO_CM/4),(int)(OFFSET+colIndex*PIX_TO_CM+3*PIX_TO_CM/4));
        break;
      case FOUR_LIGHT: //Four adjacent lights
        fill(0, 0, 0);
        rect((int)(OFFSET+rowIndex*PIX_TO_CM), (int)(OFFSET+colIndex*PIX_TO_CM), (int)PIX_TO_CM, (int)PIX_TO_CM);
        fill(255,255,255);
        textSize(27);
        text("4",(int)(OFFSET+rowIndex*PIX_TO_CM+PIX_TO_CM/4),(int)(OFFSET+colIndex*PIX_TO_CM+3*PIX_TO_CM/4));
        break;
      case BLOCK: //Block that prevents lights
        fill(0, 0, 0);
        rect((int)(OFFSET+rowIndex*PIX_TO_CM), (int)(OFFSET+colIndex*PIX_TO_CM), (int)PIX_TO_CM, (int)PIX_TO_CM);
        break;
      case ZERO_LIGHT:
      fill(0, 0, 0);
        rect((int)(OFFSET+rowIndex*PIX_TO_CM), (int)(OFFSET+colIndex*PIX_TO_CM), (int)PIX_TO_CM, (int)PIX_TO_CM);
        fill(255,255,255);
        textSize(27);
        text("0",(int)(OFFSET+rowIndex*PIX_TO_CM+PIX_TO_CM/4),(int)(OFFSET+colIndex*PIX_TO_CM+3*PIX_TO_CM/4));
        break;
      case LIGHT: //If it is a light
       // System.out.println("FOUND LIGHT");
        fill(0, 100, 0); //If its filled be different color
        rect((int)(OFFSET+rowIndex*PIX_TO_CM), (int)(OFFSET+colIndex*PIX_TO_CM), (int)PIX_TO_CM, (int)PIX_TO_CM);
        fill(28, 239, 199);
        ellipse((int)(OFFSET+rowIndex*PIX_TO_CM+PIX_TO_CM/2), (int)(OFFSET+colIndex*PIX_TO_CM+PIX_TO_CM/2), (int)PIX_TO_CM, (int)PIX_TO_CM);
      }
    }
  }
}

void draw() { 
  //ZEY METHOD.... IT DOES NOTHZING
}

void savePuzzle() {
    String newPuzName = JOptionPane.showInputDialog("What do you want to save this file as?");
    newPuzName= newPuzName+".aki";
    this.puzzle.setName(newPuzName);
    this.puzzle.saveData();
  
}

void loadPuzzle() {
  int[][] board;
  System.out.println("asdf");
  String toLoad = JOptionPane.showInputDialog("What file should be read in?");
  this.puzzle.loadBoard("Puzzle11.aki");
   size((int)(2*OFFSET+puzzle.rowSize*PIX_TO_CM), (int)(2*OFFSET+puzzle.colSize*PIX_TO_CM));
  board = puzzle.getBoard();
  //this.puzzle.addLitAreas();
 // drawBoard();
  
}

void clearPuzzle() {
  
}


void mousePressed() {
  int[][] board = puzzle.getBoard();
  boolean[][] lit = puzzle.getLit();
  if (mouseX < OFFSET || mouseY < OFFSET || mouseX > width-OFFSET || mouseY > height-OFFSET) {
    if (mouseX > 2*width/12 && mouseX < 2*width/12+width/6 && mouseY > 10 && mouseY < 25) {
      savePuzzle();
    }
    
   if (mouseX > 5*width/12 && mouseX < 5*width/12+width/6 && mouseY > 10 && mouseY < 25) {
      loadPuzzle();
      drawBoard();
    }
    
    if (mouseX > 8*width/12 && mouseX < 8*width/12+width/6 && mouseY > 10 && mouseY < 25) {
      puzzle.solve();
      drawBoard();
    }
    return;
  }
  int colClick = findRow(); //Gets row num
  int rowClick = findCol(); //Gets col num
  if (mouseButton == RIGHT) {
    board[rowClick][colClick]++;
    puzzle.addConst(rowClick, colClick, board[rowClick][colClick]);
    if (board[rowClick][colClick] == OVERFLOW) {
      board[rowClick][colClick] = 0;
      puzzle.addConst(rowClick, colClick, 0);
    }
  } 
  else {
    if (board[rowClick][colClick] == EMPTY && lit[rowClick][colClick] == false) {
      puzzle.addLight(rowClick, colClick);
      puzzle.addLitAreas(this.puzzle.getAllTheLights());
    }
    else if (board[rowClick][colClick] == LIGHT) {
      puzzle.removeLight(rowClick,colClick);
      //this.lit = puzzle.getLit();
    }
    else
      board[rowClick][colClick] = EMPTY;
  }
  drawBoard(); //Updates the board
}



int findRow() {
  int yRow = (int)((mouseY-OFFSET)/PIX_TO_CM);
  return yRow;
}

int findCol() {
  int xCol = (int)((mouseX-OFFSET)/PIX_TO_CM);
  return xCol;
}

