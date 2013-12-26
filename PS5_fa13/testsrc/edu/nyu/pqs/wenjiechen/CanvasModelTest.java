package edu.nyu.pqs.wenjiechen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CanvasModelTest {
  private CanvasModel model;

  @Before
  public void setup() {
    model = new CanvasModel();
  }

  @Test
  public void testSetColor() {
    model.setColor(Color.BLUE);
    Assert.assertEquals(Color.BLUE, model.getColor());
  }

  @Test
  public void testIncreaseLineWidth() {
    model.increaselineWidth();
    Assert.assertEquals(6, model.getLineWidth());
  }

  @Test
  public void testDecreaseLineWidth() {
    model.decreaselineWidth();
    Assert.assertEquals(4, model.getLineWidth());
  }

  @Test
  public void testDecreaseLineWidthToOne() {
    model.setLineWidth(1);
    model.decreaselineWidth();
    Assert.assertEquals(1, model.getLineWidth());
  }

  @Test
  public void testCleanCanvas() {
    model.cleanCanvas();
    Assert.assertEquals(Color.RED, model.getColor());
  }

  @Test
  public void testDimension() {
    Assert.assertEquals(new Dimension(600, 600), model.getDimension());
  }

  @Test
  public void setLastPoint() {
    model.setLastPoint(new Point(100, 100));
    Assert.assertEquals(new Point(100, 100), model.getLastPoint());
  }

  @Test
  public void mouseDragged() {
    model.setLastPoint(new Point(99, 99));
    model.mouseDragged(100, 100);
    Assert.assertEquals(new Point(100, 100), model.getLastPoint());
  }

  @Test
  public void testMousePressed() {
    model.mousePressed(new Point(200, 200));
    Assert.assertEquals(new Point(200, 200), model.getLastPoint());
  }
}
