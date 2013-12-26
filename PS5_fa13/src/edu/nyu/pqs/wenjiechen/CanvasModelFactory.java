package edu.nyu.pqs.wenjiechen;

/**
 * return the only one instance of canvas model
 * @author Wenjie NYU
 *
 */
public class CanvasModelFactory {
  private static final CanvasModel MODEL = new CanvasModel();

  /**
   * 
   * @return the only one instance of canvas model
   */
  public static CanvasModel getInstance() {
    return MODEL;
  }

}
