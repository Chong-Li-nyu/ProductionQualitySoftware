package edu.nyu.pqs.wenjiechen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import static edu.nyu.pqs.wenjiechen.Color.YELLOW;
import static edu.nyu.pqs.wenjiechen.Color.RED;
import static edu.nyu.pqs.wenjiechen.Color.EMPTY;
import static edu.nyu.pqs.wenjiechen.Status.MARCH;
import static edu.nyu.pqs.wenjiechen.Status.DRAW;
import static edu.nyu.pqs.wenjiechen.Status.YELLOW_WIN;
import static edu.nyu.pqs.wenjiechen.Status.RED_WIN;

class ConnectFourView implements ConnectFourListener {
  private static int windowNum = 0;
  private static final int ROW = 6;
  private static final int COL = 7;
  private final Color player;
  private final ConnectFourModel model;
  private JFrame frame;
  private JPanel header;
  private JPanel boardPanel;
  private final RButton[][] board = new RButton[ROW][COL];
  private final JTextArea notice;
  private JButton start;
  private JButton playAI;
  private JPanel buttonsPanel;
  private final ConnectFourView cfv = this;

  private ConnectFourView(ConnectFourModel modelSet, Color playerSet) {
    windowNum++;
    model = modelSet;
    player = playerSet;
    model.register(this);
    frame = new JFrame("Connect Four. Palyer: " + player);
    header = new JPanel(new BorderLayout());
    boardPanel = new JPanel(new GridLayout(ROW, COL));
    notice = new JTextArea(10, 20);
    notice.append("Press \"START\" button to kick off!\n");
    start = new JButton("START");
    start.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.restart();
      }
    });
    playAI = new JButton("play with AI");
    playAI.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // set model ai
        if (windowNum == 2) {
          windowNum--;
          model.unregister(cfv);
          model.enableComputer(player);
          frame.dispose();
        }
      }
    });
    buttonsPanel = new JPanel(new GridLayout(2, 1));
    buttonsPanel.add(playAI);
    buttonsPanel.add(start);
    ActionListener cellClickHandler = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int row = ((RButton) e.getSource()).row;
        int col = ((RButton) e.getSource()).col;
        model.placePiece(row, col, player);
      }
    };

    for (int r = 0; r < ROW; ++r) {
      for (int c = 0; c < COL; ++c) {
        RButton cell = new RButton(r, c);
        cell.addActionListener(cellClickHandler);
        boardPanel.add(cell);
        board[r][c] = cell;
      }
    }

    header.add(new JScrollPane(notice), BorderLayout.CENTER);
    header.add(buttonsPanel, BorderLayout.EAST);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 600);
    frame.add(header, BorderLayout.NORTH);
    frame.add(boardPanel, BorderLayout.CENTER);
    frame.setVisible(true);
  }

  /**
   * 
   * @param modelSet
   *          model of ConnectFour
   * @param playerSet
   *          player's color
   * @return an instance of ConnectFourView, which implements
   *         ConnectFourListener
   * @throws Exception
   *           the number of player cannot be more than two
   */
  public static ConnectFourView getInstance(ConnectFourModel modelSet,
      Color playerSet) throws Exception {
    if (windowNum < 2) {
      return new ConnectFourView(modelSet, playerSet);
    }
    throw new Exception("Error: can't create more than two views");
  }

  /**
   * info game start
   */
  @Override
  public void gameStart() {
    notice.append("Start a new game! Red plays first.\n");
  }

  /**
   * update board's view
   * 
   * @param modelBoard
   *          the board of model
   */
  @Override
  public void updateBoardView(Color[][] modelBoard) {
    for (int r = 0; r < ROW; ++r) {
      for (int c = 0; c < COL; ++c) {
        switch (modelBoard[r][c]) {
        case YELLOW:
          board[r][c].setBackground(java.awt.Color.YELLOW);
          break;
        case RED:
          board[r][c].setBackground(java.awt.Color.RED);
          break;
        case EMPTY:
          board[r][c].setBackground(java.awt.Color.WHITE);
          break;
        }
      }
    }
  }

  /**
   * info it's opponent's turn
   * 
   * @param player
   *          who is attempting to put piece
   */
  @Override
  public void turnMissMatch(Color player) {
    if (player == this.player) {
      JOptionPane.showMessageDialog(frame,
          "It's not your turn! Please wait the opponent go first!");
    }
  }

  /**
   * @param player
   *          who is attempting to put piece
   */
  @Override
  public void invalidStep(Color player) {
    if (player == this.player) {
      JOptionPane.showMessageDialog(frame,
          "this Column is full, you can't put piece in this Column!");
    }
  }

  /**
   * @param sta
   *          game status
   */
  @Override
  public void gameOver(Status sta) {
    switch (sta) {
    case DRAW:
      JOptionPane.showMessageDialog(frame,
          "GAME OVER: Draw.\npress \"START\" button to restart.");
      break;
    case YELLOW_WIN:
      JOptionPane.showMessageDialog(frame,
          "GAME OVER: Yellow player won.press \"START\" button to restart.");
      break;
    case RED_WIN:
      JOptionPane.showMessageDialog(frame,
          "GAME OVER: Red player won.press \"START\" button to restart.");
      break;
    default:
      break;
    }
  }

  /**
   * @param turn
   *          current player's color
   */
  @Override
  public void changeTurn(Color turn) {
    notice.append("It's " + turn + "'s turn\n");
  }

  private static class RButton extends JButton {
    private static final long serialVersionUID = 4374851841327447783L;
    final int row;
    final int col;

    RButton(int row, int col) {
      this.row = row;
      this.col = col;
      Dimension size = getPreferredSize();
      size.width = size.height = Math.max(size.width, size.height);
      setPreferredSize(size);
      setContentAreaFilled(false);
      this.setBackground(java.awt.Color.WHITE);
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