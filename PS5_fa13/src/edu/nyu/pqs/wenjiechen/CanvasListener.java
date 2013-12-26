package edu.nyu.pqs.wenjiechen;

import java.awt.Color;

public interface CanvasListener {
  /**
   * 
   * @param x
   *          the x coordinate position
   * @param y
   *          the x coordinate position
   * @param lineWidth
   *          the width of painting line
   */
  void updatePaint(int x, int y, int lineWidth);

  /**
   * erase all contents on all canvases
   */
  void cleanCanvas();

  /**
   * 
   * @param color
   *          of painting line
   */
  void changeColor(Color color);

}
