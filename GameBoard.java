import java.io.*;





/**
 * GameBoard class that is the backbone of the puzzle program. This is meant to be used for 
 * grid puzzles, and acts as a generic board. The gameBoard is kept track of in a two-dimensional
 * int array. It contains a file name that is used to identify the gameBoard, and will have different
 * file exentsions for each kind of puzzle. Has a save method that saves the puzzle into a file, and
 * a load method that loads the puzzle back when it is time to reopen it.
 * 
 * 
 * The file is saved as follows:
 * Number of Rows = Byte
 * Number of Columns = Byte
 * Number of Non-Zero Areas in the Board = Byte
 * 
 * Then, for a total number of Non-Zero areas in the Board
 * Specific Row = Byte
 * Specific Column = Byte
 * Specific Data = Integer
 * 
 * 
 * 
 */

public class GameBoard {
  public int rowSize = 0;
  public int colSize = 0;
  private String puzName = "";
  int[][] board;
  
  
  
  
  public GameBoard (int row, int col) {
    this.rowSize = row;
    this.colSize = col;
    board = new int[rowSize][colSize];
  }
  
  public GameBoard (String fileName) {
    loadBoard(fileName);
  }
  
  
  private void loadBoard(String fileName) {
    File puzz = new File (fileName);
    FileInputStream fis = null;
    DataInputStream dis = null;
    try {
      fis = new FileInputStream(puzz);
      dis = new DataInputStream(fis);
    } catch (IOException e) {
      System.out.println("Sorry, but the file cannot be found");
      try {
        dis.close();
        fis.close();
        return;
      } catch (IOException f) { 
        return;
      }
    }
    try {
      Byte row = dis.readByte();
      this.rowSize = row.intValue();
      Byte col = dis.readByte();
      this.colSize = col.intValue();
      Byte dat = dis.readByte();
      int numData = dat.intValue();
      board = new int[rowSize][colSize];
      for (int counter = 0; counter < numData; counter++) {
        Byte datRow = dis.readByte();
        Byte datCol = dis.readByte();
        int dataRow = datRow.intValue();
        int dataCol = datCol.intValue();
        int data = dis.readInt();
        board[dataRow][dataCol] = data;
      }
    } catch (IOException e) {
      System.out.println("Error occured while reading in the Puzzle"); 
      try {
        dis.close();
        fis.close();
      } catch (IOException f) {
        System.out.println("Something has gone very wrong.");
        return;
      }
    }
    try {
      dis.close();
      fis.close();
    } catch (IOException e) {
      System.out.println("Something has gone very wrong.");
        return;
    }
  }
  
  public void setName (String fileName) {
    this.puzName = fileName;
  }
  
  public void saveData() {
    File file = new File(this.puzName);
    try{
    FileOutputStream fos = new FileOutputStream(file);
    DataOutputStream dos = new DataOutputStream(fos);
    Integer rs = this.rowSize;
    Integer cs = this.colSize;
    dos.writeByte(rs.byteValue());
    dos.writeByte(cs.byteValue());
    dos.writeByte(nonZeroEntries().byteValue());
    writeNonZeros(dos);
    dos.close();
    fos.close(); 
    } catch (IOException e) {
     System.out.println("ABORT ABORT");
     return;
    }
    
  }
  
  private Integer nonZeroEntries() {
    Integer total = 0;
    for (int i = 0; i < this.rowSize; i++) {
      for (int k = 0; k < this.colSize; k++) {
        if (board[i][k] != 0)
          total+=1;
      } 
    }
    return total;
  }
  
  private void writeNonZeros(DataOutputStream dos) throws IOException{
    for (int i = 0; i < this.rowSize; i++) {
      for (int k = 0; k < this.colSize; k++) {
        if (board[i][k] != 0) {
          Integer index1 = i;
          Integer index2 = k;
          Integer data = board[i][k];
          dos.writeByte(index1.byteValue());
          dos.writeByte(index2.byteValue());
          dos.writeByte(data.byteValue());
        } 
      }
      
      
      
    }
    
  }
}