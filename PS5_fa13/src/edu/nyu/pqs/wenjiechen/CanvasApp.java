package edu.nyu.pqs.wenjiechen;

/**
 * create two windows, user can paint at each canvas when press and drag mouse.
 * 
 */
public class CanvasApp {
  public static void main(String[] args) {
    CanvasModel model = CanvasModelFactory.getInstance();
    CanvasView view = CanvasView.getInstance(model);
    CanvasView view2 = CanvasView.getInstance(model);
  }
}