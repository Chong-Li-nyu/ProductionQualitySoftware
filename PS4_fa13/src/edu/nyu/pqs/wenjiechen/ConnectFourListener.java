package edu.nyu.pqs.wenjiechen;

import edu.nyu.pqs.wenjiechen.Color;
import edu.nyu.pqs.wenjiechen.Status;

interface ConnectFourListener {
  void gameStart();

  void updateBoardView(Color[][] board);

  void turnMissMatch(Color player);
  
  void invalidStep(Color player);
  
  void gameOver(Status sta);
  
  void changeTurn(Color turn);
}
