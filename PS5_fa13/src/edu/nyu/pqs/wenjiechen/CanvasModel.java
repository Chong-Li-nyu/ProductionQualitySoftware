package edu.nyu.pqs.wenjiechen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * a painting canvas model. User can set paint color and the width of line
 * 
 * @author Wenjie NYU
 * 
 */
class CanvasModel {
  private Color lineColor;
  private int lineWidth;
  private Dimension d;
  private Point lastPoint;

  private List<CanvasListener> listeners = new ArrayList<CanvasListener>();

  /**
   * only for test. Please use ConvasModelFactory to get an instance.
   */
  public CanvasModel() {
    lineColor = Color.RED;
    lineWidth = 5;
    d = new Dimension(600, 600);
  }

  /**
   * only for test, can't be used for other purpose.
   * 
   * @return the dimension of canvas
   */
  public Dimension getDimension() {
    return d;
  }

  /**
   * Only for test, can't be used for other purpose.
   * 
   * @return color of line
   */
  public Color getColor() {
    return lineColor;
  }

  /**
   * when mouse drag on a canvas, draw a point for the dragged point
   * 
   * @param x
   *          the x coordinate position, when mouse dragging
   * @param y
   *          the y coordinate position, when mouse dragging
   */
  public void mouseDragged(int x, int y) {
    double xD = x - lastPoint.getX();
    double yD = y - lastPoint.getY();
    double delta = Math.max(Math.abs(xD), Math.abs(yD));
    double xInc = xD / delta;
    double yInc = yD / delta;

    double xS = lastPoint.getX();
    double yS = lastPoint.getY();

    for (int i = 0; i < delta; i++) {
      Point ip = new Point((int) xS, (int) yS);
      drawPoint(ip);
      xS += xInc;
      yS += yInc;
    }

    drawPoint(new Point(x, y));
    lastPoint = new Point(x, y);
  }

  /**
   * 
   * @param color
   *          of painting line
   */
  public void setColor(Color color) {
    lineColor = color;
    fireChangeColor();
  }

  /**
   * erase all content on all canvases
   */
  public void cleanCanvas() {
    lineColor = Color.RED;
    fireCleanCanvas();
  }

  /**
   * Only for test, can't be used for other purpose.
   * 
   * @param w
   *          the width of painting line
   */
  public void setLineWidth(int w) {
    lineWidth = w;
  }

  /**
   * Only for test, can't be used for other purpose.
   * 
   * @return width of line
   */
  public int getLineWidth() {
    return lineWidth;
  }

  /**
   * 
   * @param p
   *          position of a mouse when it's pressed
   */
  public void mousePressed(Point p) {
    lastPoint = p;
    drawPoint(p);
  }

  /**
   * increase the width of painting line
   */
  public void increaselineWidth() {
    ++lineWidth;
  }

  /**
   * decrease the width of painting line. the minimum is one
   */
  public void decreaselineWidth() {
    if (lineWidth > 1) {
      --lineWidth;
    }
  }

  /**
   * only for test, can't be used for other purpose.
   * 
   * @param p
   *          the last point when a mouse event happened
   */
  public void setLastPoint(Point p) {
    lastPoint = p;
  }

  /**
   * Only for test, can't be used for other purpose.
   * 
   * @return last point when mouse event happened
   */
  public Point getLastPoint() {
    return lastPoint;
  }

  /**
   * draw a square, the width of the square is the painting line width
   * 
   * @param p
   *          the center of square
   */
  public void drawPoint(Point p) {
    fireUpdatePaint((int) (p.getX() - lineWidth / 2),
        (int) (p.getY() - lineWidth / 2));
  }

  private void fireUpdatePaint(int x, int y) {
    for (CanvasListener listener : listeners) {
      listener.updatePaint(x, y, lineWidth);
    }
  }

  private void fireChangeColor() {
    for (CanvasListener listener : listeners) {
      listener.changeColor(lineColor);
    }
  }

  private void fireCleanCanvas() {
    for (CanvasListener listener : listeners) {
      listener.cleanCanvas();
    }
  }

  /**
   * add listener on this model
   * 
   * @param listener
   */
  public void register(CanvasListener listener) {
    listeners.add(listener);
  }

  /**
   * remove listener on this model
   * 
   * @param listener
   */
  public void unregister(CanvasListener listener) {
    listeners.remove(listener);
  }

}
