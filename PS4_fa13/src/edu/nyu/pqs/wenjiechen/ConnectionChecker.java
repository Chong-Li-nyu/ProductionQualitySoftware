package edu.nyu.pqs.wenjiechen;

class ConnectionChecker {
  private static final int ROW = 6;
  private static final int COL = 7;

  public static boolean check(Color[][] board, Color turn) {
    for (int r = 0; r < ROW; ++r) {
      for (int c = 0; c < COL; ++c) {
        if (checkHorizon(board, turn, r, c) == true
            || checkVertical(board, turn, r, c) == true
            || checkDiagonal(board, turn, r, c) == true) {
          // win, game over
          return true;
        }
      }
    } 
    return false;
  }

  private static boolean checkHorizon(Color[][] board, Color turn, int row,
      int col) {
    if (board[row][col] != turn) {
      return false;
    }
    int cLeft = col - 3;
    int cRight = col + 3;
    boolean left = false;
    boolean right = false;
    if (cLeft >= 0) {
      left = true;
      for (int i = cLeft; i < col; ++i) {
        if (board[row][i] != turn) {
          left = false;
          break;
        }
      }
    }
    if (cRight <= 6) {
      right = true;
      for (int i = col; i <= cRight; ++i) {
        if (board[row][i] != turn) {
          right = false;
        }
      }
    }
    if (left == true || right == true) {
      return true;
    } else {
      return false;
    }
  }

  private static boolean checkVertical(Color[][] board, Color turn, int row,
      int col) {
    if (board[row][col] != turn) {
      return false;
    }
    int rUp = row - 3;
    int rDown = row + 3;
    boolean up = false;
    boolean down = false;
    if (rUp >= 0) {
      up = true;
      for (int i = rUp; i < row; i++) {
        if (board[i][col] != turn) {
          up = false;
        }
      }
    }
    if (rDown <= 5) {
      down = true;
      for (int i = row; i <= rDown; i++) {
        if (board[i][col] != turn) {
          down = false;
        }
      }
    }
    if (up || down) {
      return true;
    } else {
      return false;
    }
  }

  private static boolean checkDiagonal(Color[][] board, Color turn, int row,
      int col) {
    if (board[row][col] != turn) {
      return false;
    }
    int rUpLeft = row - 3;
    int cUpLeft = col - 3;
    boolean upLeft = false;
    if (rUpLeft >= 0 && cUpLeft >= 0) {
      upLeft = true;
      for (int i = 0; i < 4; i++) {
        if (board[rUpLeft + i][cUpLeft + i] != turn) {
          upLeft = false;
        }
      }
    }

    int rDownLeft = row + 3;
    int cDownLeft = col - 3;
    boolean downLeft = false;
    if (rDownLeft <= 5 && cDownLeft >= 0) {
      downLeft = true;
      int r = rDownLeft;
      int c = cDownLeft;
      while (c < col) {
        if (board[r--][c++] != turn) {
          downLeft = false;
        }
      }
    }

    int rUpRight = row - 3;
    int cUpRight = col + 3;
    boolean upRight = false;
    if (rUpRight >= 0 && cUpRight <= 6) {
      upRight = true;
      int r = row;
      int c = col;
      while (c <= cUpRight) {
        if (board[r--][c++] != turn) {
          upRight = false;
        }
      }
    }

    int rDownRight = row + 3;
    int cDownRight = col + 3;
    boolean downRight = false;
    if (rDownRight <= 5 && cDownRight <= 6) {
      downRight = true;
      int r = row;
      int c = col;
      while (c <= cDownRight) {
        if (board[r++][c++] != turn) {
          downRight = false;
        }
      }
    }

    if (upLeft || downLeft || upRight || downRight) {
      return true;
    } else {
      return false;
    }
  }

}