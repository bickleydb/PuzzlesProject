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
final int BLOCK = 5;
final int EMPTY = 0;
final int OVERFLOW = 6;


//Board Specific Variables
Akari_Board puzzle;
int row = 10;
int col = 10;
int tryNum = -1;
int[][] board = new int[row][col];
boolean[][] lit = new boolean[row][col];


void setup() {
  //Makes the window the right size
  size((int)(2*OFFSET+row*PIX_TO_CM),(int)(2*OFFSET+col*PIX_TO_CM));
   puzzle = new Akari_Board(row,col);
  drawBoard();
 
}

void drawBoard() {
  board = puzzle.getBoard();
  lit = puzzle.getLit();
  for (int rowIndex = 0; rowIndex  < row; rowIndex++) {
    for (int colIndex = 0; colIndex < col; colIndex++) {
      switch (board[colIndex][rowIndex]) {
        
        case EMPTY: //If the grid square does not contain anything
          if (lit[colIndex][rowIndex])
            fill(0,100,0); //If its filled be different color
          else
            fill(255,255,255); //If not filled, be white
          rect((int)(OFFSET+rowIndex*PIX_TO_CM),(int)(OFFSET+colIndex*PIX_TO_CM),(int)PIX_TO_CM,(int)PIX_TO_CM);
          break;
        case ONE_LIGHT: //If the grid square will have an adjacent light
          fill(0,0,0); //Make it black
          rect((int)(OFFSET+rowIndex*PIX_TO_CM),(int)(OFFSET+colIndex*PIX_TO_CM),(int)PIX_TO_CM,(int)PIX_TO_CM);
          break;
        case TWO_LIGHT: //If the grid square will have two adjacent lights
          fill(0,0,0);
          rect((int)(OFFSET+rowIndex*PIX_TO_CM),(int)(OFFSET+colIndex*PIX_TO_CM),(int)PIX_TO_CM,(int)PIX_TO_CM);
          break;
         case THREE_LIGHT: //Three adjacent lights
          fill(0,0,0);
          rect((int)(OFFSET+rowIndex*PIX_TO_CM),(int)(OFFSET+colIndex*PIX_TO_CM),(int)PIX_TO_CM,(int)PIX_TO_CM);
          break;
         case FOUR_LIGHT: //Four adjacent lights
          fill(0,0,0);
          rect((int)(OFFSET+rowIndex*PIX_TO_CM),(int)(OFFSET+colIndex*PIX_TO_CM),(int)PIX_TO_CM,(int)PIX_TO_CM);
          break;
          case BLOCK: //Block that prevents lights
          fill(0,0,0);
          rect((int)(OFFSET+rowIndex*PIX_TO_CM),(int)(OFFSET+colIndex*PIX_TO_CM),(int)PIX_TO_CM,(int)PIX_TO_CM);
          break;
          case LIGHT: //If it is a light
          fill(28,239,199);
          ellipse((int)(OFFSET+rowIndex*PIX_TO_CM+PIX_TO_CM/2),(int)(OFFSET+colIndex*PIX_TO_CM+PIX_TO_CM/2),(int)PIX_TO_CM,(int)PIX_TO_CM);
          
          
      }
    }
  }
}

void draw() { 
  //ZEY METHOD.... IT DOES NOTHZING
}


void mousePressed() {
  if (mouseX < OFFSET || mouseY < OFFSET || mouseX > width-OFFSET || mouseY > height-OFFSET)
    return; //Don't bother doing anything if it is outside the playing area
  int rowClick = findRow(); //Gets row num
  int colClick = findCol(); //Gets col num
  if (mouseButton == RIGHT) {
    board[rowClick][colClick]++;
    puzzle.addConst(rowClick,colClick,board[rowClick][colClick]);
    if (board[rowClick][colClick] == OVERFLOW) {
    board[rowClick][colClick] = 0;
     puzzle.addConst(rowClick,colClick,0);
    }
  } else {
    if(board[rowClick][colClick] ==0) {
      puzzle.addLight(rowClick,colClick);
    }
     else
       board[rowClick][colClick] = EMPTY;
  }
  drawBoard(); //Updates the board
}



int findRow() {
  int yRow = (int)((mouseY-OFFSET)/PIX_TO_CM);
  System.out.println(yRow);
  return yRow;
}

int findCol() {
  int xCol = (int)((mouseX-OFFSET)/PIX_TO_CM);
  System.out.println(xCol);
  return xCol;
}


