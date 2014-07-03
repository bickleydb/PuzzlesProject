public class BasicThread extends Thread {

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


  Akari_Board toSolve;
  public BasicThread (Akari_Board toSolve) {
    super("BasicThread");
    this.toSolve = toSolve;

    run();
  }


  public void run() {
    try {
      checkTop();
      checkLeft();
      checkRight();
      checkBottom();
      checkCorners();
      checkBody();
    } 
    catch (Exception e) {
    }
  }

  private void fourBody(int i, int t) {
    if ((toSolve.board[i+1][t] < LIGHT && toSolve.board[i+1][t] > EMPTY) || (toSolve.board[i-1][t] < LIGHT && toSolve.board[i-1][t] > EMPTY)  || 
      (toSolve.board[i][t-1] < LIGHT && toSolve.board[i][t-1] > EMPTY)  || (toSolve.board[i][t+1] < LIGHT && toSolve.board[i][t+1] > EMPTY)) {
    }
    toSolve.addLight(i-1, t);
    toSolve.addLight(i+1, t);
    toSolve.addLight(i, t-1);
    toSolve.addLight(i, t+1);
  }

  private void threeBody(int i, int t) {
    if (toSolve.board[i-1][t] == LIGHT) {
      toSolve.board[i-1][t] = IGNORE_LIGHT;
      toSolve.board[i][t]--;
      twoBody(i, t);
      toSolve.board[i][t]++;
      toSolve.board[i-1][t] = LIGHT;
    }

    if (toSolve.board[i+1][t] == LIGHT) {
      toSolve.board[i+1][t] = IGNORE_LIGHT;
      toSolve.board[i][t]--;
      twoBody(i, t);
      toSolve.board[i][t]++;
      toSolve.board[i+1][t] = LIGHT;
    }

    if (toSolve.board[i][t-1] == LIGHT) {
      toSolve.board[i][t-1] = IGNORE_LIGHT;
      toSolve.board[i][t]--;
      twoBody(i, t);
      toSolve.board[i][t]++;
      toSolve.board[i][t-1] = LIGHT;
    }

    if (toSolve.board[i][t+1] == LIGHT) {
      toSolve.board[i][t+1] = IGNORE_LIGHT;
      toSolve.board[i][t]--;
      twoBody(i, t);
      toSolve.board[i][t]++;
      toSolve.board[i][t+1] = LIGHT;
    }

    if (toSolve.lit[i-1][t] && !toSolve.lit[i+1][t] && !toSolve.lit[i][t-1] && !toSolve.lit[i][t+1]) {
      toSolve.addLight(i+1, t);
      toSolve.addLight(i, t-1);
      toSolve.addLight(i, t+1);
    }
    if (!toSolve.lit[i-1][t] && toSolve.lit[i+1][t] && !toSolve.lit[i][t-1] && !toSolve.lit[i][t+1]) {
      toSolve.addLight(i-1, t);
      toSolve.addLight(i, t-1);
      toSolve.addLight(i, t+1);
    }
    if (!toSolve.lit[i-1][t] && !toSolve.lit[i+1][t] && toSolve.lit[i][t-1] && !toSolve.lit[i][t+1]) {
      toSolve.addLight(i+1, t);
      toSolve.addLight(i-1, t);
      toSolve.addLight(i, t+1);
    }
    if (!toSolve.lit[i-1][t] && !toSolve.lit[i+1][t] && !toSolve.lit[i][t-1] && toSolve.lit[i][t+1]) {
      toSolve.addLight(i+1, t);
      toSolve.addLight(i, t-1);
      toSolve.addLight(i-1, t);
    }
  }

  private void twoBody( int i, int t) {
    if (toSolve.board[i-1][t] == LIGHT) {
      toSolve.board[i-1][t] = IGNORE_LIGHT;
      toSolve.board[i][t]--;
      oneBody(i, t);
      toSolve.board[i][t]++;
      toSolve.board[i-1][t] = LIGHT;
    }

    if (toSolve.board[i+1][t] == LIGHT) {
      toSolve.board[i+1][t] = IGNORE_LIGHT;
      toSolve.board[i][t]--;
      oneBody(i, t);
      toSolve.board[i][t]++;
      toSolve.board[i+1][t] = LIGHT;
    }

    if (toSolve.board[i][t-1] == LIGHT) {
      toSolve.board[i][t-1] = IGNORE_LIGHT;
      toSolve.board[i][t]--;
      oneBody(i, t);
      toSolve.board[i][t]++;
      toSolve.board[i][t-1] = LIGHT;
    }

    if (toSolve.board[i][t+1] == LIGHT) {
      toSolve.board[i][t+1] = IGNORE_LIGHT;
      toSolve.board[i][t]--;
      oneBody(i, t);
      toSolve.board[i][t]++;
      toSolve.board[i][t+1] = LIGHT;
    }

    if (!toSolve.lit[i-1][t] && !toSolve.lit[i+1][t] && toSolve.lit[i][t-1] && toSolve.lit[i][t+1]) {
      toSolve.addLight(i-1, t);
      toSolve.addLight(i+1, t);
    }
    if (!toSolve.lit[i-1][t] && toSolve.lit[i+1][t] && !toSolve.lit[i][t-1] && toSolve.lit[i][t+1]) {
      toSolve.addLight(i-1, t);
      toSolve.addLight(i, t-1);
    }
    if (!toSolve.lit[i-1][t] && toSolve.lit[i+1][t] && toSolve.lit[i][t-1] && !toSolve.lit[i][t+1]) {
      toSolve.addLight(i-1, t);
      toSolve.addLight(i, t+1);
    }
    if (toSolve.lit[i-1][t] && !toSolve.lit[i+1][t] && !toSolve.lit[i][t-1] && toSolve.lit[i][t+1]) {
      toSolve.addLight(i+1, t);
      toSolve.addLight(i, t-1);
    }
    if (toSolve.lit[i-1][t] && !toSolve.lit[i+1][t] && toSolve.lit[i][t-1] && !toSolve.lit[i][t+1]) {
      toSolve.addLight(i+1, t);
      toSolve.addLight(i, t+1);
    }
    if (toSolve.lit[i-1][t] && toSolve.lit[i+1][t] && !toSolve.lit[i][t-1] && !toSolve.lit[i][t+1]) {
      toSolve.addLight(i, t-1);
      toSolve.addLight(i, t+1);
    }
  }

  private void oneBody(int i, int t) {
    if (toSolve.board[i-1][t] == LIGHT) {
      return;
    }

    if (toSolve.board[i+1][t] == LIGHT) {
      return;
    }

    if (toSolve.board[i][t-1] == LIGHT) {
      return;
    }

    if (toSolve.board[i][t+1] == LIGHT) {
      return;
    }

    if (!toSolve.lit[i-1][t] && toSolve.lit[i+1][t] && toSolve.lit[i][t-1] && toSolve.lit[i][t+1]) {
      toSolve.addLight(i-1, t);
    }
    if (toSolve.lit[i-1][t] && !toSolve.lit[i+1][t] && toSolve.lit[i][t-1] && toSolve.lit[i][t+1]) {
      toSolve.addLight(i+1, t);
    }
    if (toSolve.lit[i-1][t] && toSolve.lit[i+1][t] && !toSolve.lit[i][t-1] && toSolve.lit[i][t+1]) {
      toSolve.addLight(i, t-1);
    }
    if (toSolve.lit[i-1][t] && toSolve.lit[i+1][t] && toSolve.lit[i][t-1] && !toSolve.lit[i][t+1]) {
      toSolve.addLight(i, t+1);
    }
  }

  private void checkBody() {
    for (int i = 1; i < toSolve.board.length-1; i++) {
      for (int t = 1; t < toSolve.board[i].length-1; t++) {
        if (toSolve.board[i][t] == FOUR_LIGHT) {
          fourBody(i, t);
        }

        if (toSolve.board[i][t] == THREE_LIGHT) {
          threeBody(i, t);
        }

        if (toSolve.board[i][t] == TWO_LIGHT) {
          twoBody(i, t);
        }

        if (toSolve.board[i][t] == ONE_LIGHT) {
          oneBody(i, t);
        }

        if (toSolve.board[i][t] == ZERO_LIGHT) {
          toSolve.board[i-1][t] = EMPTY;
          toSolve.board[i+1][t] = EMPTY;
          toSolve.board[i][t-1] = EMPTY;
          toSolve.board[i][t+1] = EMPTY;
        }
      }
    }
    toSolve.addLitAreas(toSolve.getAllTheLights());
  }

  private void checkCorners() {
    if (toSolve.board[0][0] == ONE_LIGHT) {
      if (toSolve.lit[0][1] && !toSolve.lit[1][0])
        toSolve.addLight(1, 0);
      if (!toSolve.lit[0][1] && toSolve.lit[1][0])
        toSolve.addLight(0, 1);
    }

    if (toSolve.board[0][0] == TWO_LIGHT) {
      toSolve.addLight(1, 0);
      toSolve.addLight(0, 1);
    }

    if (toSolve.board[0][0] == ZERO_LIGHT) {
      toSolve.board[0][1] = EMPTY;
      toSolve.board[1][0] = EMPTY;
    }

    if (toSolve.board[0][toSolve.board[0].length-1] == ONE_LIGHT) {
      if (toSolve.lit[0][toSolve.board[0].length-2] && !toSolve.lit[1][toSolve.board[0].length-1])
        toSolve.addLight(1, toSolve.board[0].length-1);
      if (!toSolve.lit[0][toSolve.board[0].length-2] && toSolve.lit[1][toSolve.board[0].length-1])
        toSolve.addLight(0, toSolve.board[0].length-2);
    }

    if (toSolve.board[0][toSolve.board[0].length-1] == TWO_LIGHT) {
      toSolve.addLight(1, toSolve.board[0].length-1);
      toSolve.addLight(0, toSolve.board[0].length-2);
    }

    if (toSolve.board[0][toSolve.board[0].length-1] == ZERO_LIGHT) {
      toSolve.board[0][toSolve.board[0].length-2] = EMPTY;
      toSolve.board[1][toSolve.board[0].length-1] = EMPTY;
    }

    if (toSolve.board[toSolve.board.length-1][0] == ONE_LIGHT) {
      if (toSolve.lit[toSolve.board.length-2][0] && !toSolve.lit[toSolve.board.length-1][1])
        toSolve.addLight(toSolve.board.length-1, 1);
      if (!toSolve.lit[toSolve.board.length-2][0] && toSolve.lit[toSolve.board.length-1][1])
        toSolve.addLight(toSolve.board.length-2, 0);
    }

    if (toSolve.board[toSolve.board.length-1][0] == TWO_LIGHT) {
      toSolve.addLight(toSolve.board.length-1, 1);
      toSolve.addLight(toSolve.board.length-2, 0);
    }

    if (toSolve.board[toSolve.board.length-1][0] == ZERO_LIGHT) {
      toSolve.board[toSolve.board.length-1][1] = EMPTY;
      toSolve.board[toSolve.board.length-2][0] = EMPTY;
    }

    if (toSolve.board[toSolve.board.length-1][toSolve.board[toSolve.board.length-1].length-1] == ONE_LIGHT) {
      if (toSolve.lit[toSolve.board.length-2][toSolve.board[toSolve.board.length-2].length-1] && !toSolve.lit[toSolve.board.length-1][toSolve.board[toSolve.board.length-1].length-2])
        toSolve.addLight(toSolve.board.length-1, toSolve.board[toSolve.board.length-1].length-2);
      if (!toSolve.lit[toSolve.board.length-2][toSolve.board[toSolve.board.length-2].length-1] && toSolve.lit[toSolve.board.length-1][toSolve.board[toSolve.board.length-1].length-2])
        toSolve.addLight(toSolve.board.length-2, toSolve.board[toSolve.board.length-2].length-1);
    }

    if  (toSolve.board[toSolve.board.length-1][toSolve.board[toSolve.board.length-1].length-1] == TWO_LIGHT) {
      toSolve.addLight(toSolve.board.length-1, toSolve.board[toSolve.board.length-1].length-2);
      toSolve.addLight(toSolve.board.length-2, toSolve.board[toSolve.board.length-2].length-1);
    }

    if  (toSolve.board[toSolve.board.length-1][toSolve.board[toSolve.board.length-1].length-1] == ZERO_LIGHT) {
      toSolve.board[toSolve.board.length-1][toSolve.board[toSolve.board.length-1].length-2] = EMPTY;
      toSolve.board[toSolve.board.length-2][toSolve.board[toSolve.board.length-2].length-1] = EMPTY;
    }


    toSolve.addLitAreas(toSolve.getAllTheLights());
  }
  private void leftOne(int i) {
    System.out.println("FOUND ONE at " + i + "," + 0); 
    if (toSolve.board[0][i-1]==LIGHT || toSolve.board[1][i]==LIGHT || toSolve.board[0][i+1]==LIGHT) {
      System.out.println("LEFT: " + toSolve.board[0][i-1]);
      System.out.println("BELOW: " + toSolve.board[1][i]);
      System.out.println("RIGHT: " + toSolve.board[0][i+1]);
      System.out.println("One is satisfied");
    }
    if (!toSolve.lit[0][i-1] && toSolve.lit[1][i] && toSolve.lit[0][i+1]) {
      toSolve.addLight(0, i-1);
      toSolve.addLitAreas(toSolve.getAllTheLights());
    }
    if (toSolve.lit[0][i-1] && !toSolve.lit[1][i] && toSolve.lit[0][i+1]) {
      toSolve.addLight(1, i);
      toSolve.addLitAreas(toSolve.getAllTheLights());
    }
    if (toSolve.lit[0][i-1] && toSolve.lit[1][i] && !toSolve.lit[0][i+1]) {
      toSolve.addLight(0, i+1);
      toSolve.addLitAreas(toSolve.getAllTheLights());
    }
    toSolve.addLitAreas(toSolve.getAllTheLights());
  }

  private void leftTwo(int i) {
    if (toSolve.board[0][i-1] == LIGHT || toSolve.board[0][i+1] == LIGHT || toSolve.board[1][i] == LIGHT) {
      toSolve.board[0][i]--;
      if (toSolve.board[0][i-1] == LIGHT) {
        toSolve.board[0][i-1] = IGNORE_LIGHT;
        leftOne(i);
        toSolve.board[0][i-1] = LIGHT;
      }
      if (toSolve.board[1][i] == LIGHT) {
        toSolve.board[1][i] = IGNORE_LIGHT;
        leftOne(i);
        toSolve.board[1][i] = LIGHT;
      }
      if (toSolve.board[0][i+1] == LIGHT) {
        toSolve.board[0][i+1] = IGNORE_LIGHT;
        leftOne(i);
        toSolve.board[0][i+1] = LIGHT;
      }
      toSolve.board[0][i] = TWO_LIGHT;
    }
  }

  private void leftThree(int i) throws InvalidPuzzleException {
    if ((toSolve.board[0][i-1] < LIGHT && toSolve.lit[0][i-1] == true) || (toSolve.board[1][i] < LIGHT && toSolve.lit[1][i]==true) || (toSolve.board[0][i+1] < LIGHT && toSolve.lit[0][i+1] == true)) {
      System.out.println("LEFT: " + toSolve.board[0][i-1]);
      System.out.println("BELOW: " + toSolve.board[1][i]);
      System.out.println("RIGHT: " + toSolve.board[0][i+1]);
      throw new InvalidPuzzleException("Puzzle configuration is invalid. Constrant located at Row: " + i + " and Column " + 0 + " cannot be satisfied."+ "PENIS");
    }
    toSolve.addLight(0, i-1);
    toSolve.addLight(1, i);
    toSolve.addLight(0, i+1);
    toSolve.addLitAreas(toSolve.getAllTheLights());
  }

  private void leftFour(int i) throws InvalidPuzzleException {
    throw new InvalidPuzzleException("Puzzle configuration is invalid. Constrant located at Row: " + i + " and Column " + 0 + " cannot be satisfied.");
  }

  private void checkLeft() throws InvalidPuzzleException {
    for (int i = 1; i < toSolve.board[0].length-1; i++) {
      if (toSolve.board[0][i] == FOUR_LIGHT)
        leftFour(i);
      if (toSolve.board[0][i] == THREE_LIGHT) {
        leftThree(i);
      }
      if (toSolve.board[0][i] == TWO_LIGHT) {
        leftTwo(i);
      }
      if (toSolve.board[0][i] == ONE_LIGHT) {
        leftOne(i);
      }

      if (toSolve.board[0][i] == ZERO_LIGHT) {
        toSolve.board[0][i+1] = EMPTY;
        toSolve.board[0][i-1] = EMPTY;
        toSolve.board[1][i] = EMPTY;
      }
    }
  }



  private void topOne(int i) {
    System.out.println("FOUND ONE at " + i + "," + 0);
    if (toSolve.board[i-1][0]==LIGHT || toSolve.board[i][1]==LIGHT || toSolve.board[i+1][0]==LIGHT) {
      System.out.println("LEFT: " + toSolve.board[i-1][0]);
      System.out.println("BELOW: " + toSolve.board[i][1]);
      System.out.println("RIGHT: " + toSolve.board[i+1][0]);
      System.out.println("One is satisfied");
    }
    if (!toSolve.lit[i-1][0] && toSolve.lit[i][1] && toSolve.lit[i+1][0]) {
      toSolve.addLight(i-1, 0);
      toSolve.addLitAreas(toSolve.getAllTheLights());
    }
    if (toSolve.lit[i-1][0] && !toSolve.lit[i][1] && toSolve.lit[i+1][0]) {
      toSolve.addLight(i, 1);
      toSolve.addLitAreas(toSolve.getAllTheLights());
    }
    if (toSolve.lit[i-1][0] && toSolve.lit[i][1] && !toSolve.lit[i+1][0]) {
      toSolve.addLight(i+1, 0);
      toSolve.addLitAreas(toSolve.getAllTheLights());
    }
    toSolve.addLitAreas(toSolve.getAllTheLights());
  }

  private void rightOne(int i) {
    if (toSolve.board[toSolve.board.length-1][i-1]==LIGHT || toSolve.board[toSolve.board.length-2][i]==LIGHT || toSolve.board[toSolve.board.length-1][i+1]==LIGHT) {
      System.out.println("right: " + toSolve.board[toSolve.board.length-1][i-1]);
      System.out.println("BELOW: " + toSolve.board[1][i]);
      System.out.println("RIGHT: " + toSolve.board[toSolve.board.length-1][i+1]);
      System.out.println("One is satisfied");
    }
    if (!toSolve.lit[toSolve.board.length-1][i-1] && toSolve.lit[toSolve.board.length-2][i] && toSolve.lit[toSolve.board.length-1][i+1]) {
      toSolve.addLight(toSolve.board.length-1, i-1);
      toSolve.addLitAreas(toSolve.getAllTheLights());
    }
    if (toSolve.lit[toSolve.board.length-1][i-1] && !toSolve.lit[toSolve.board.length-2][i] && toSolve.lit[toSolve.board.length-1][i+1]) {
      toSolve.addLight(toSolve.board.length-2, i);
      toSolve.addLitAreas(toSolve.getAllTheLights());
    }
    if (toSolve.lit[toSolve.board.length-1][i-1] && toSolve.lit[toSolve.board.length-2][i] && !toSolve.lit[toSolve.board.length-1][i+1]) {
      toSolve.addLight(toSolve.board.length-1, i+1);
      toSolve.addLitAreas(toSolve.getAllTheLights());
    }
    toSolve.addLitAreas(toSolve.getAllTheLights());
  }

  private void rightTwo(int i) {
    if (toSolve.board[toSolve.board.length-1][i-1] == LIGHT || toSolve.board[toSolve.board.length-1][i+1] == LIGHT || toSolve.board[toSolve.board.length-2][i] == LIGHT) {
      toSolve.board[toSolve.board.length-1][i]--;
      if (toSolve.board[toSolve.board.length-1][i-1] == LIGHT) {
        toSolve.board[toSolve.board.length-1][i-1] = IGNORE_LIGHT;
        rightOne(i);
        toSolve.board[toSolve.board.length-1][i-1] = LIGHT;
      }
      if (toSolve.board[toSolve.board.length-2][i] == LIGHT) {
        toSolve.board[toSolve.board.length-2][i] = IGNORE_LIGHT;
        rightOne(i);
        toSolve.board[toSolve.board.length-2][i] = LIGHT;
      }
      if (toSolve.board[toSolve.board.length-1][i+1] == LIGHT) {
        toSolve.board[toSolve.board.length-1][i+1] = IGNORE_LIGHT;
        rightOne(i);
        toSolve.board[toSolve.board.length-1][i+1] = LIGHT;
      }
      toSolve.board[toSolve.board.length-1][i] = TWO_LIGHT;
    }
  }

  private void rightThree(int i) throws InvalidPuzzleException {
    if ((toSolve.board[toSolve.board.length-1][i-1] < LIGHT && toSolve.lit[toSolve.board.length-1][i-1] == true) || (toSolve.board[1][i] < LIGHT && toSolve.lit[1][i]==true) || (toSolve.board[toSolve.board.length-1][i+1] < LIGHT && toSolve.lit[toSolve.board.length-1][i+1] == true)) {
      System.out.println("right: " + toSolve.board[toSolve.board.length-1][i-1]);
      System.out.println("BELOW: " + toSolve.board[1][i]);
      System.out.println("RIGHT: " + toSolve.board[toSolve.board.length-1][i+1]);
      //throw new InvalidPuzzleException("Puzzle configuration is invalid. Constrant located at Row: " + i + " and Column " + (toSolve.board.length-1) + " cannot be satisfied."+ "PENIS");
    }
    toSolve.addLight(toSolve.board.length-1, i-1);
    toSolve.addLight(toSolve.board.length-2, i);
    toSolve.addLight(toSolve.board.length-1, i+1);
    toSolve.addLitAreas(toSolve.getAllTheLights());
  }

  private void rightFour(int i) throws InvalidPuzzleException {
    throw new InvalidPuzzleException("Puzzle configuration is invalid. Constrant located at Row: " + i + " and Column " + (toSolve.board.length-1) + " cannot be satisfied.");
  }

  private void checkRight() throws InvalidPuzzleException {
    for (int i = 1; i < toSolve.board[0].length-1; i++) {
      if (toSolve.board[toSolve.board.length-1][i] == FOUR_LIGHT)
        rightFour(i);
      if (toSolve.board[toSolve.board.length-1][i] == THREE_LIGHT) {
        rightThree(i);
      }
      if (toSolve.board[toSolve.board.length-1][i] == TWO_LIGHT) {
        rightTwo(i);
      }
      if (toSolve.board[toSolve.board.length-1][i] == ONE_LIGHT) {
        rightOne(i);
      }
      if (toSolve.board[toSolve.board.length-1][i] == ZERO_LIGHT) {
        toSolve.board[toSolve.board.length-2][i] = EMPTY;
        toSolve.board[toSolve.board.length-1][i+1] = EMPTY;
        toSolve.board[toSolve.board.length-1][i-1] = EMPTY;
      }
    }
  }



  private void topTwo(int i) {
    if (toSolve.board[i-1][0] == LIGHT || toSolve.board[i+1][0] == LIGHT || toSolve.board[i][1] == LIGHT) {
      toSolve.board[i][0]--;
      if (toSolve.board[i-1][0] == LIGHT) {
        toSolve.board[i-1][0] = IGNORE_LIGHT;
        topOne(i);
        toSolve.board[i-1][0] = LIGHT;
      }
      if (toSolve.board[i][1] == LIGHT) {
        toSolve.board[i][1] = IGNORE_LIGHT;
        topOne(i);
        toSolve.board[i][1] = LIGHT;
      }
      if (toSolve.board[i+1][0] == LIGHT) {
        toSolve.board[i+1][0] = IGNORE_LIGHT;
        topOne(i);
        toSolve.board[i+1][0] = LIGHT;
      }
      toSolve.board[i][0] = TWO_LIGHT;
    }
  }

  private void topThree(int i) throws InvalidPuzzleException {
    if ((toSolve.board[i-1][0] < LIGHT && toSolve.lit[i-1][0] == true) || (toSolve.board[i][1] < LIGHT && toSolve.lit[i][1]==true) || (toSolve.board[i+1][0] < LIGHT && toSolve.lit[i+1][0] == true)) {
      System.out.println("LEFT: " + toSolve.board[i-1][0]);
      System.out.println("BELOW: " + toSolve.board[i][1]);
      System.out.println("RIGHT: " + toSolve.board[i+1][0]);
      throw new InvalidPuzzleException("Puzzle configuration is invalid. Constrant located at Row: " + i + " and Column " + 0 + " cannot be satisfied."+ "PENIS");
    }
    toSolve.addLight(i-1, 0);
    toSolve.addLight(i, 1);
    toSolve.addLight(i+1, 0);
    toSolve.addLitAreas(toSolve.getAllTheLights());
  }

  private void topFour(int i) throws InvalidPuzzleException {
    throw new InvalidPuzzleException("Puzzle configuration is invalid. Constrant located at Row: " + i + " and Column " + 0 + " cannot be satisfied.");
  }

  private void checkTop() throws InvalidPuzzleException {
    for (int i = 1; i < toSolve.board.length-1; i++) {
      if (toSolve.board[i][0] == FOUR_LIGHT)
        topFour(i);
      if (toSolve.board[i][0] == THREE_LIGHT) {
        topThree(i);
      }
      if (toSolve.board[i][0] == TWO_LIGHT) {
        topTwo(i);
      }
      if (toSolve.board[i][0] == ONE_LIGHT) {
        topOne(i);
      }
      if (toSolve.board[i][0] == ZERO_LIGHT) {
        toSolve.board[i-1][0] = EMPTY;
        toSolve.board[i+1][0] = EMPTY;
        toSolve.board[i][1] = EMPTY;
      }
    }
  }
  private void bottomOne(int i) {
    if (toSolve.board[i-1][toSolve.board.length-1]==LIGHT || toSolve.board[i][toSolve.board.length-2]==LIGHT || toSolve.board[i+1][toSolve.board.length-1]==LIGHT) {
      System.out.println("LEFT: " + toSolve.board[i-1][toSolve.board.length-1]);
      System.out.println("BELOW: " + toSolve.board[i][toSolve.board.length-2]);
      System.out.println("RIGHT: " + toSolve.board[i+1][toSolve.board.length-1]);
      System.out.println("One is satisfied");
    }
    if (!toSolve.lit[i-1][toSolve.board.length-1] && toSolve.lit[i][toSolve.board.length-2] && toSolve.lit[i+1][toSolve.board.length-1]) {
      toSolve.addLight(i-1, toSolve.board.length-1);
      toSolve.addLitAreas(toSolve.getAllTheLights());
    }
    if (toSolve.lit[i-1][toSolve.board.length-1] && !toSolve.lit[i][toSolve.board.length-2] && toSolve.lit[i+1][toSolve.board.length-1]) {
      toSolve.addLight(i, toSolve.board.length-2);
      toSolve.addLitAreas(toSolve.getAllTheLights());
    }
    if (toSolve.lit[i-1][toSolve.board.length-1] && toSolve.lit[i][toSolve.board.length-2] && !toSolve.lit[i+1][toSolve.board.length-1]) {
      toSolve.addLight(i+1, toSolve.board.length-1);
      toSolve.addLitAreas(toSolve.getAllTheLights());
    }
    toSolve.addLitAreas(toSolve.getAllTheLights());
  }

  private void bottomTwo(int i) {
    if (toSolve.board[i-1][toSolve.board.length-1] == LIGHT || toSolve.board[i+1][toSolve.board.length-1] == LIGHT || toSolve.board[i][toSolve.board.length-2] == LIGHT) {
      toSolve.board[i][toSolve.board.length-1]--;
      if (toSolve.board[i-1][toSolve.board.length-1] == LIGHT) {
        toSolve.board[i-1][toSolve.board.length-1] = IGNORE_LIGHT;
        bottomOne(i);
        toSolve.board[i-1][toSolve.board.length-1] = LIGHT;
      }
      if (toSolve.board[i][toSolve.board.length-2] == LIGHT) {
        toSolve.board[i][toSolve.board.length-2] = IGNORE_LIGHT;
        bottomOne(i);
        toSolve.board[i][toSolve.board.length-2] = LIGHT;
      }
      if (toSolve.board[i+1][toSolve.board.length-1] == LIGHT) {
        toSolve.board[i+1][toSolve.board.length-1] = IGNORE_LIGHT;
        bottomOne(i);
        toSolve.board[i+1][toSolve.board.length-1] = LIGHT;
      }
      toSolve.board[i][toSolve.board.length-1] = TWO_LIGHT;
    }
  }

  private void bottomThree(int i) throws InvalidPuzzleException {
    if ((toSolve.board[i-1][toSolve.board[i].length-1] < LIGHT && toSolve.lit[i-1][toSolve.board[i].length-1] == true) || (toSolve.board[i][toSolve.board[i].length-2] < LIGHT && toSolve.lit[i][toSolve.board[i].length-2]==true) || (toSolve.board[i+1][toSolve.board[i].length-1] < LIGHT && toSolve.lit[i+1][toSolve.board[i].length-1] == true)) {
      System.out.println("LEFT: " + toSolve.board[i-1][toSolve.board.length-1]);
      System.out.println("BELOW: " + toSolve.board[i][toSolve.board.length-2]);
      System.out.println("RIGHT: " + toSolve.board[i+1][toSolve.board.length-1]);
      throw new InvalidPuzzleException("Puzzle configuration is invalid. Constrant located at Row: " + i + " and Column " + (toSolve.board.length-1) + " cannot be satisfied."+ "PENIS");
    }
    toSolve.addLight(i-1, toSolve.board.length-1);
    toSolve.addLight(i, toSolve.board.length-2);
    toSolve.addLight(i+1, toSolve.board.length-1);
    toSolve.addLitAreas(toSolve.getAllTheLights());
  }

  private void bottomFour(int i) throws InvalidPuzzleException {
    throw new InvalidPuzzleException("Puzzle configuration is invalid. Constrant located at Row: " + i + " and Column " + (toSolve.board.length-1) + " cannot be satisfied.");
  }

  private void checkBottom() throws InvalidPuzzleException {
    for (int i = 1; i < toSolve.board.length-1; i++) {
      if (toSolve.board[i][toSolve.board[i].length-1] == FOUR_LIGHT)
        bottomFour(i);
      if (toSolve.board[i][toSolve.board[i].length-1] == THREE_LIGHT) {
        bottomThree(i);
      }
      if (toSolve.board[i][toSolve.board[i].length-1] == TWO_LIGHT) {
        bottomTwo(i);
      }
      if (toSolve.board[i][toSolve.board[i].length-1] == ONE_LIGHT) {
        leftOne(i);
      }
      if (toSolve.board[i][toSolve.board[i].length-1] == ZERO_LIGHT) {
        toSolve.board[i][toSolve.board.length-2] =EMPTY;
        toSolve.board[i+1][toSolve.board.length-1] = EMPTY;
        toSolve.board[i-1][toSolve.board.length-1] = EMPTY;
      }
    }
  }
}

