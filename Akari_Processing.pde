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
GameBoard puzzle;
int row = 10;
int col = 10;
int tryNum = -1;
int[][] board = new int[row][col];
boolean[][] lit = new boolean[row][col];


void setup() {
  //Makes the window the right size
  size((int)(2*OFFSET+row*PIX_TO_CM),(int)(2*OFFSET+col*PIX_TO_CM));
  drawBoard();
  puzzle = new GameBoard(row,col);
}

void drawBoard() {
  for (int rowIndex = 0; rowIndex  < row; rowIndex++) {
    for (int colIndex = 0; colIndex < col; colIndex++) {
      switch (board[colIndex][rowIndex]) {
        case EMPTY:
          if (lit[colIndex][rowIndex])
            fill(0,100,0);
          else
            fill(255,255,255);
          rect((int)(OFFSET+rowIndex*PIX_TO_CM),(int)(OFFSET+colIndex*PIX_TO_CM),(int)PIX_TO_CM,(int)PIX_TO_CM);
          break;
        case ONE_LIGHT:
          fill(0,0,0);
          rect((int)(OFFSET+rowIndex*PIX_TO_CM),(int)(OFFSET+colIndex*PIX_TO_CM),(int)PIX_TO_CM,(int)PIX_TO_CM);
          break;
        case TWO_LIGHT:
          fill(0,0,0);
          rect((int)(OFFSET+rowIndex*PIX_TO_CM),(int)(OFFSET+colIndex*PIX_TO_CM),(int)PIX_TO_CM,(int)PIX_TO_CM);
          break;
         case THREE_LIGHT:
          fill(0,0,0);
          rect((int)(OFFSET+rowIndex*PIX_TO_CM),(int)(OFFSET+colIndex*PIX_TO_CM),(int)PIX_TO_CM,(int)PIX_TO_CM);
          break;
         case FOUR_LIGHT:
          fill(0,0,0);
          rect((int)(OFFSET+rowIndex*PIX_TO_CM),(int)(OFFSET+colIndex*PIX_TO_CM),(int)PIX_TO_CM,(int)PIX_TO_CM);
          break;
          case BLOCK:
          fill(0,0,0);
          rect((int)(OFFSET+rowIndex*PIX_TO_CM),(int)(OFFSET+colIndex*PIX_TO_CM),(int)PIX_TO_CM,(int)PIX_TO_CM);
          break;
          case LIGHT:
          fill(28,239,199);
          ellipse((int)(OFFSET+rowIndex*PIX_TO_CM+PIX_TO_CM/2),(int)(OFFSET+colIndex*PIX_TO_CM+PIX_TO_CM/2),(int)PIX_TO_CM,(int)PIX_TO_CM);
          
          
      }
    }
  }
}

void draw() { 
}


void mousePressed() {
  if (mouseX < OFFSET || mouseY < OFFSET || mouseX > width-OFFSET || mouseY > height-OFFSET)
    return;
  int rowClick = findRow();
  int colClick = findCol();
  if (mouseButton == RIGHT) {
    board[rowClick][colClick]++;
    if (board[rowClick][colClick] == OVERFLOW)
    board[rowClick][colClick] = 0;
  } else {
    if(board[rowClick][colClick] ==0) {
       board[rowClick][colClick] = LIGHT;
       updateLighting();
    }
     else
       board[rowClick][colClick] = EMPTY;
  }
  drawBoard();

}

ArrayList<Integer> getLights() {
  ArrayList<Integer> light = new ArrayList<Integer>();
 for (int rowIndex = 0; rowIndex < row; rowIndex++) {
   for (int colIndex = 0; colIndex < col; colIndex++) {
     if (board[rowIndex][colIndex] == LIGHT) {
       light.add(rowIndex);
       light.add(colIndex);
     }
   }
 } 
}

void updateLighting() {
  ArrayList<Integer> light = getLights();
 
 for (int lightIndex = 0; lightIndex < light.size()-1; lightIndex+=2) {
   checkUpLight();
   checkDownLight();
   checkLeftLight();
   checkRightLight();
   
 }
}

void checkUpLight() {
  
  
  
}


void checkDownLight() {
  
  
}


void checkLeftLight() {
  
  
}


void checkRightLight() {
  
  
  
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


