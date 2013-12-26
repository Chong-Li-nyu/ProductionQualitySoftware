package edu.nyu.pqs.wenjiechen;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static edu.nyu.pqs.wenjiechen.Color.YELLOW;
import static edu.nyu.pqs.wenjiechen.Color.RED;
import static edu.nyu.pqs.wenjiechen.Color.EMPTY;
import static edu.nyu.pqs.wenjiechen.Status.MARCH;
import static edu.nyu.pqs.wenjiechen.Status.DRAW;
import static edu.nyu.pqs.wenjiechen.Status.YELLOW_WIN;
import static edu.nyu.pqs.wenjiechen.Status.RED_WIN;

class ConnectFourModel {
  private static final int COL = 7;
  private static final int ROW = 6;
  // private static final ConnectFourModel MODEL = new ConnectFourModel();

  private Color[][] board = new Color[ROW][COL];
  private Color turn = RED;
  private Status gameStatus = MARCH;
  private int pieceNum = 0;
  private boolean enableComputer = false;
  private Color computer;

  private List<ConnectFourListener> listeners = new LinkedList<ConnectFourListener>();

  private ConnectFourModel() {
    start();
  }

  /**
   * 
   * @return a instance of connectFourModel. this is singleton pattern
   */
  public static ConnectFourModel getInstance() {
    return new ConnectFourModel();
  }

  /**
   * start a new game.
   */
  public void restart() {
    start();
  }

  /**
   * 
   * @param listener
   *          add it to this model
   */
  public void register(ConnectFourListener listener) {
    listeners.add(listener);
  }

  /**
   * 
   * @param listener
   *          remove this listener from model
   */
  public void unregister(ConnectFourListener listener) {
    listeners.remove(listener);
  }

  /**
   * 
   * @param computerIn
   *          set computer's color
   */
  public void enableComputer(Color computerIn) {
    enableComputer = true;
    this.computer = computerIn;
    // System.out.println("computer color: " + computer);
  }

  /**
   * 
   * @return game status. only for test
   */
  Status getGameStatus() {
    return gameStatus;
  }

  /**
   * 
   * @param boardIn
   *          set march board. only for test
   */
  void setBoard(Color[][] boardIn) {
    for (int r = 0; r < 6; r++) {
      for (int c = 0; c < 7; c++) {
        board[r][c] = boardIn[r][c];
      }
    }
  }

  /**
   * 
   * @param turn
   *          set game's turn. only for test
   */
  void setTurn(Color turn) {
    this.turn = turn;
  }

  /**
   * 
   * @return a copy version of board. only for test
   */
  Color[][] getBoard() {
    Color[][] retBoard = new Color[ROW][COL];
    copyBoard(retBoard);
    return retBoard;
  }

  /**
   * 
   * @return the color of current player. only for test
   */
  Color getTurn() {
    return turn;
  }

  /**
   * 
   * @param n
   *          set the number of current pieces on board. only for test
   */
  void setPieceNum(int n) {
    pieceNum = n;
  }

  /**
   * 
   * @return the number of current pieces on board
   */
  public int getPieceNum() {
    return pieceNum;
  }

  /**
   * put a piece on gaming board
   * 
   * @param row
   *          row of position of piece
   * @param col
   *          col of position of piece
   * @param player
   *          player's color
   */
  public void placePiece(int row, int col, Color player) {
    if (gameStatus != MARCH || pieceNum >= 42) {
      fireGameOver(gameStatus);
      return;
    }
    if (player != turn) {
      fireTurnMissMatch(player);
      return;
    }
    if (stepValidation(col) == false) {
      fireInvalidStep(player);
      return;
    }
    putPiece(board, col, player);
    fireUpdateBoardView();
    updateStatus();
    if (gameStatus == MARCH) {
      fireChangeTurn();
    }
    if (enableComputer) {
      computerMove();
    }
  }

  private void putPiece(Color[][] board, int col, Color player) {
    // board[0][] is up, board[ROW-1][] is bottom
    for (int i = (ROW - 1); i >= 0; --i) {
      if (board[i][col] == EMPTY) {
        board[i][col] = player;
        break;
      }
    }
  }

  private void updateStatus() {
    // check draw
    pieceNum++;
    if (pieceNum == 42) {
      gameStatus = DRAW;
      fireGameOver(gameStatus);
      return;
    }
    // check connection
    if (ConnectionChecker.check(board, turn)) {
      // win, game over
      gameStatus = (turn == YELLOW) ? YELLOW_WIN : RED_WIN;
      fireGameOver(gameStatus);
      return;
    }
    // change turn
    turn = (turn == YELLOW) ? RED : YELLOW;
  }

  private void copyBoard(Color[][] tmpBoard) {
    for (int r = 0; r < ROW; r++) {
      for (int c = 0; c < COL; ++c) {
        tmpBoard[r][c] = board[r][c];
      }
    }
  }

  private void computerMove() {
    if (pieceNum >= 42) {
      return;
    }
    if (turn != computer) {
      return;
    }
    Color[][] exploreBoard = new Color[ROW][COL];
    int col = 0;
    boolean connection = false;
    for (int i = 0; i < COL; ++i) {
      copyBoard(exploreBoard);
      if (stepValidation(i) == true) {
        putPiece(exploreBoard, i, computer);
        if (ConnectionChecker.check(exploreBoard, computer)) {
          col = i;
          connection = true;
          // System.out.println("AI: connect success: " + col);
          break;
        }
      }
    } // for 
    if (connection == false) {
      do {
        Random rand = new Random();
        col = rand.nextInt(7);
      } while (stepValidation(col) == false);
    }
    // System.out.println("AI: put in : " + col);
    putPiece(board, col, computer);
    fireUpdateBoardView();
    // update status
    if (++pieceNum == 42) {
      gameStatus = DRAW;
      fireGameOver(gameStatus);
      return;
    }
    if (connection == true) {
      gameStatus = (computer == YELLOW) ? YELLOW_WIN : RED_WIN;
      fireGameOver(gameStatus);
      return;
    }
    turn = (turn == YELLOW) ? RED : YELLOW;
    if (gameStatus == MARCH) {
      fireChangeTurn();
    }
  }

  private void start() {
    turn = RED;
    gameStatus = MARCH;
    pieceNum = 0;
    fireGameStart();
    initBoard();
    if (enableComputer) {
      computerMove();
    }
  }

  private void initBoard() {
    for (int i = 0; i < ROW; ++i) {
      for (int j = 0; j < COL; ++j) {
        board[i][j] = EMPTY;
      }
    }
    fireUpdateBoardView();
  }

  // board[0][] is up
  // board[5][] is bottom
  private boolean stepValidation(int col) {
    if (board[0][col] != EMPTY) {
      return false;
    }
    return true;
  }

  private void fireGameOver(Status sta) {
    for (ConnectFourListener listener : listeners) {
      listener.gameOver(sta);
    }
  }

  private void fireInvalidStep(Color player) {
    for (ConnectFourListener listener : listeners) {
      listener.invalidStep(player);
    }
  }

  private void fireTurnMissMatch(Color player) {
    for (ConnectFourListener listener : listeners) {
      listener.turnMissMatch(player);
    }
  }

  private void fireGameStart() {
    for (ConnectFourListener listener : listeners) {
      listener.gameStart();
    }
  }

  private void fireUpdateBoardView() {
    for (ConnectFourListener listener : listeners) {
      listener.updateBoardView(board);
    }
  }

  private void fireChangeTurn() {
    for (ConnectFourListener listener : listeners) {
      listener.changeTurn(turn);
    }
  }
}