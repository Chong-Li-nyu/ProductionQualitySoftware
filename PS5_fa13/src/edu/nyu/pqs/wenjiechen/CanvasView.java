package edu.nyu.pqs.wenjiechen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * the view of a painting canvas model. User can create any number of views
 * 
 * @author Wenjie NYU
 * 
 */
public class CanvasView implements CanvasListener {
  private Canvas canvas;
  private final CanvasModel model;
  private JFrame frame;
  private JPanel buttonPanel;

  private CanvasView(final CanvasModel model) {
    this.model = model;
    model.register(this);
    canvas = new Canvas(model.getDimension());
    buttonPanel = new JPanel();
    addButtons();
    addMouseListeners();
    frame = new JFrame();
    frame.setSize(new Dimension(650, 630));
    frame.add(buttonPanel, BorderLayout.SOUTH);
    frame.add(canvas);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  /**
   * 
   * @param model
   *          the view registered on this model
   * @return instance of canvas view
   */
  public static CanvasView getInstance(final CanvasModel model) {
    return new CanvasView(model);
  }

  private void addMouseListeners() {
    canvas.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        model.mousePressed(e.getPoint());
      }
    });

    canvas.addMouseMotionListener(new MouseMotionAdapter() {

      @Override
      public void mouseDragged(MouseEvent e) {
        model.mouseDragged(e.getX(), e.getY());
      }
    });

  }

  private void addButtons() {
    buttonPanel.setLayout(new GridLayout(1, 8));
    JButton clean = new RButton("Clean");
    clean.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.cleanCanvas();
      }
    });
    buttonPanel.add(clean);

    JButton increaseLineSize = new RButton("Up");
    increaseLineSize.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.increaselineWidth();
      }
    });
    buttonPanel.add(increaseLineSize);

    JButton decreaseLineSize = new RButton("Down");
    decreaseLineSize.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.decreaselineWidth();
      }
    });
    buttonPanel.add(decreaseLineSize);

    JButton red = new RButton("");
    red.setBackground(Color.RED);
    red.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.setColor(Color.RED);
      }
    });
    buttonPanel.add(red);

    JButton black = new RButton("");
    black.setBackground(Color.BLACK);
    black.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.setColor(Color.BLACK);
      }
    });
    buttonPanel.add(black);

    JButton green = new RButton("");
    green.setBackground(Color.GREEN);
    green.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.setColor(Color.GREEN);
      }
    });
    buttonPanel.add(green);

    JButton yellow = new RButton("");
    yellow.setBackground(Color.YELLOW);
    yellow.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.setColor(Color.YELLOW);
      }
    });
    buttonPanel.add(yellow);

    JButton blue = new RButton("");
    blue.setBackground(Color.BLUE);
    blue.setForeground(Color.WHITE);
    blue.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.setColor(Color.BLUE);
      }
    });
    buttonPanel.add(blue);

    JButton pink = new RButton("");
    pink.setBackground(Color.PINK);
    pink.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.setColor(Color.PINK);
      }
    });
    buttonPanel.add(pink);
  }

  @Override
  public void updatePaint(int x, int y, int lineWidth) {
    canvas.drawPoint(x, y, lineWidth);
  }

  @Override
  public void cleanCanvas() {
    canvas.cleanCanvas();
  }

  @Override
  public void changeColor(Color color) {
    canvas.changeColor(color);
  }

  private class Canvas extends JPanel {
    private static final long serialVersionUID = -3903398086094292786L;
    Image image;
    Graphics gra;

    Canvas(Dimension d) {
      super();
      setSize(d);
    }

    public void cleanCanvas() {
      changeColor(Color.WHITE);
      gra.fillRect(0, 0, getWidth(), getHeight());
      repaint();
      changeColor(Color.RED);
    }

    public void changeColor(Color color) {
      gra.setColor(color);
    }

    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);

      if (image == null) {
        image = createImage(getWidth(), getHeight());
        gra = (Graphics) image.getGraphics();
        cleanCanvas();
      }

      Rectangle r = g.getClipBounds();
      g.drawImage(image, r.x, r.y, r.width + r.x, r.height + r.y, r.x, r.y,
          r.width + r.x, r.height + r.y, null);
    }

    void drawPoint(int x, int y, int lineWidth) {
      gra.fillRect(x, y, lineWidth, lineWidth);
      repaint();
    }
  }

  private static class RButton extends JButton {
    private static final long serialVersionUID = -3022241814763414754L;

    RButton(String str) {
      super(str);
      Dimension size = getPreferredSize();
      size.width = size.height = Math.max(size.width, size.height);
      setPreferredSize(size);
      setContentAreaFilled(false);
    }

    protected void paintComponent(Graphics g) {
      if (getModel().isArmed()) {
        g.setColor(java.awt.Color.lightGray);
      } else {
        g.setColor(getBackground());
      }
      g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
      super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
      g.setColor(getForeground());
      g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
    }

    Shape shape;

    public boolean contains(int x, int y) {
      if (shape == null || !shape.getBounds().equals(getBounds())) {
        shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
      }
      return shape.contains(x, y);
    }
  }
}