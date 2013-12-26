package edu.nyu.pqs.wenjiechen;

import static edu.nyu.pqs.wenjiechen.Color.EMPTY;
import static edu.nyu.pqs.wenjiechen.Color.RED;
import static edu.nyu.pqs.wenjiechen.Color.YELLOW;
import static edu.nyu.pqs.wenjiechen.Status.RED_WIN;
import static edu.nyu.pqs.wenjiechen.Status.YELLOW_WIN;
import static edu.nyu.pqs.wenjiechen.Status.DRAW;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConnectFourModelTest {
  private ConnectFourModel model;
  private Color[][] board;
  private Color[][] cmp;

  @Before
  public void setup() {
    model = ConnectFourModel.getInstance();
  }

  @Test
  public void testSetTurn() {
    model.setTurn(YELLOW);
    Assert.assertEquals(YELLOW, model.getTurn());
  }

  @Test
  public void testRestart() {
    board = new Color[][] {
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, RED, RED, RED, EMPTY } };
    model.setTurn(RED);
    model.setBoard(board);
    model.placePiece(0, 3, RED);
    model.restart();
    cmp = model.getBoard();
    Assert.assertEquals(EMPTY, cmp[5][3]);
    Assert.assertEquals(EMPTY, cmp[5][4]);
    Assert.assertEquals(EMPTY, cmp[5][5]);
  }

  @Test
  public void testChangeTurn() {
    board = new Color[][] {
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, RED, RED, RED, EMPTY } };
    model.setTurn(RED);
    model.setBoard(board);
    model.placePiece(0, 3, RED);
    Assert.assertEquals(YELLOW, model.getTurn());
  }

  @Test
  public void testAfterWinNotChangeTurn() {
    board = new Color[][] {
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, RED, RED, RED, EMPTY } };
    model.setTurn(RED);
    model.setBoard(board);
    model.placePiece(0, 2, RED);
    Assert.assertEquals(RED_WIN, model.getGameStatus());
    Assert.assertEquals(RED, model.getTurn());
  }

  @Test
  public void testDropHorizon() {
    board = new Color[][] {
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, RED, RED, RED, EMPTY } };
    model.setTurn(RED);
    model.setBoard(board);
    model.placePiece(0, 2, RED);
    cmp = model.getBoard();
    Assert.assertEquals(RED, cmp[5][2]);
  }

  @Test
  public void testDropVertical() {
    board = new Color[][] {
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, RED, RED, RED, EMPTY } };
    model.setTurn(YELLOW);
    model.setBoard(board);
    model.placePiece(0, 3, YELLOW);
    cmp = model.getBoard();
    Assert.assertEquals(YELLOW, cmp[4][3]);
  }

  @Test
  public void testDropPieceTwice() {
    board = new Color[][] {
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, RED, RED, RED, EMPTY } };
    model.setTurn(YELLOW);
    model.setBoard(board);
    model.placePiece(0, 3, YELLOW);
    model.placePiece(0, 3, RED);
    cmp = model.getBoard();
    Assert.assertEquals(YELLOW, cmp[4][3]);
    Assert.assertEquals(RED, cmp[3][3]);
  }

  @Test
  public void testColumnFull() {
    board = new Color[][] { { EMPTY, EMPTY, EMPTY, RED, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, RED, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, RED, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, YELLOW, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, YELLOW, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, RED, RED, RED, EMPTY } };
    model.setTurn(YELLOW);
    model.setBoard(board);
    model.placePiece(0, 3, YELLOW);
    cmp = model.getBoard();
    Assert.assertEquals(RED, cmp[5][3]);
    Assert.assertEquals(YELLOW, model.getTurn());
  }

  @Test
  public void testConnectionHorizon() {
    board = new Color[][] {
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, RED, RED, RED, EMPTY } };
    model.setTurn(RED);
    model.setBoard(board);
    model.placePiece(0, 2, RED);
    Assert.assertEquals(RED_WIN, model.getGameStatus());
  }

  @Test
  public void testConnectionVertical() {
    board = new Color[][] {
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, YELLOW, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, YELLOW, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, YELLOW, RED, RED, EMPTY } };
    model.setTurn(YELLOW);
    model.setBoard(board);
    model.placePiece(0, 3, YELLOW);
    Assert.assertEquals(YELLOW_WIN, model.getGameStatus());
  }

  @Test
  public void testConnectionDiagonal() {
    board = new Color[][] {
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, YELLOW, RED, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, YELLOW, YELLOW, RED, EMPTY, EMPTY },
        { EMPTY, EMPTY, YELLOW, YELLOW, RED, RED, EMPTY } };
    model.setTurn(RED);
    model.setBoard(board);
    model.placePiece(0, 2, RED);
    Assert.assertEquals(RED_WIN, model.getGameStatus());
  }

  @Test
  public void testComputerPlaceConnectionHorizon() {
    board = new Color[][] {
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, RED, RED, RED, EMPTY } };
    model.enableComputer(RED);
    model.setTurn(YELLOW);
    model.setBoard(board);
    model.placePiece(0, 1, YELLOW);
    Assert.assertEquals(RED_WIN, model.getGameStatus());
  }

  @Test
  public void testComputerPlaceConnectionVertical() {
    board = new Color[][] {
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, YELLOW, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, YELLOW, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, YELLOW, RED, RED, EMPTY } };
    model.enableComputer(YELLOW);
    model.setTurn(RED);
    model.setBoard(board);
    model.placePiece(0, 1, RED);
    Assert.assertEquals(YELLOW_WIN, model.getGameStatus());
  }

  @Test
  public void testComputerPlaceConnectionDiagonal() {
    board = new Color[][] {
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, YELLOW, RED, EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, YELLOW, YELLOW, RED, EMPTY, EMPTY },
        { EMPTY, EMPTY, YELLOW, YELLOW, RED, RED, EMPTY } };
    model.enableComputer(RED);
    model.setTurn(YELLOW);
    model.setBoard(board);
    model.placePiece(0, 0, YELLOW);
    Assert.assertEquals(RED_WIN, model.getGameStatus());
  }

  @Test
  public void testGameOverDraw() {
    board = new Color[][] { { YELLOW, RED, YELLOW, EMPTY, RED, RED, RED },
        { YELLOW, RED, RED, YELLOW, RED, RED, RED },
        { YELLOW, RED, YELLOW, YELLOW, RED, RED, RED },
        { RED, YELLOW, RED, RED, YELLOW, YELLOW, YELLOW },
        { RED, YELLOW, YELLOW, YELLOW, RED, YELLOW, RED },
        { YELLOW, RED, YELLOW, RED, RED, RED, RED } };
    model.setTurn(YELLOW);
    model.setBoard(board);
    model.setPieceNum(41);
    model.placePiece(0, 3, YELLOW);
    Assert.assertEquals(DRAW, model.getGameStatus());
  }

}
