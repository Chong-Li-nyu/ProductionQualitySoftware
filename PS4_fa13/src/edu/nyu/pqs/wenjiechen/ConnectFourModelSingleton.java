package edu.nyu.pqs.wenjiechen;

public class ConnectFourModelSingleton {
  private static final ConnectFourModel MODEL = ConnectFourModel.getInstance();

  public static ConnectFourModel getInstance() {
    return MODEL;
  }

}
