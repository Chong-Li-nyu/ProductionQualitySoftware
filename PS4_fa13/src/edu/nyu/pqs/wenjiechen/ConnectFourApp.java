package edu.nyu.pqs.wenjiechen;

import static edu.nyu.pqs.wenjiechen.Color.RED;
import static edu.nyu.pqs.wenjiechen.Color.YELLOW;

public class ConnectFourApp {

  /**
   * create two windows, one for red player, the other for yellow player. Press
   * "play with AI" button will close a window and start a game with computer.
   * 
   * @param args
   */
  public static void main(String[] args) {
    ConnectFourModel model = ConnectFourModelSingleton.getInstance();
    try {
      ConnectFourView.getInstance(model, RED);
      ConnectFourView.getInstance(model, YELLOW);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}