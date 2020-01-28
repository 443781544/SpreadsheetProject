package edu.cs3500.spreadsheets.view;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Stroke;
import java.awt.BasicStroke;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.ViewOnlyModel;
import edu.cs3500.spreadsheets.view.listener.ButtonListener;

/**
 * To represent a Grid Panel of a spread sheet model without slides.
 */
public final class GridPanel extends SpreadsheetPanel {

  //int gridWidth = 20;
  //int gridHeight = 30;

  private int i;
  private int j;


  private int right = 0;
  private int down = 0;

  private SpreadsheetModel model;

  private Coord hightlightCoord;

  private boolean resizingCol = false;
  private boolean resizingRow = false;
  private int resizingCellIndex;
  private int resizeCellwidth;

  /**
   * To construct the GridPanel and set the size of the spread sheet. * @param model
   * SpreadsheetModel
   */
  public GridPanel(ViewOnlyModel model) {
    this.model = model;

    this.setPreferredSize(new Dimension(1500, 750));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    int height = 0;
    i = 0;

    int hlX = 0;
    int hlY = 0;
    int hlCellWidth = 0;
    int hlCellHeight = 0;
    while (height < this.getHeight()) {

      int cellHeight;
      if (i == 0) {
        cellHeight = model.getRowSize(0);
      } else {
        cellHeight = model.getRowSize(i + right);
      }

      int width = 0;
      j = 0;
      while (width < this.getWidth()) {
        int cellWidth;
        if (j == 0) {
          cellWidth = model.getColSize(0);
        } else {
          cellWidth = model.getColSize(j + down);
        }

        if (i == 0 && j > 0) {
          g2d.setColor(new Color(194, 194, 163));
          g2d.fillRect(width, height, cellWidth, cellHeight);
          g2d.setColor(Color.BLACK);

          String s = Coord.colIndexToName(j + down);
          s = trimText(s, cellWidth);
          drawCenteredString(g, s,
                  new Rectangle(width, height, cellWidth, cellHeight),
                  new Font("bold", Font.BOLD, 16));
        } else if (j == 0 && i > 0) {
          g2d.setColor(new Color(194, 194, 163));
          g2d.fillRect(width, height, cellWidth, cellHeight);
          g2d.setColor(Color.BLACK);

          String s = String.valueOf(i + right);
          drawCenteredString(g, s,
                  new Rectangle(width, height, cellWidth, cellHeight),
                  new Font("bold", Font.BOLD, 16));

        } else if (i != 0 && j != 0) {
          if (model.getCellAt(j + down, i + right) != null) {
            g2d.setColor(Color.BLACK);

            String s = model.getCell(j + down, i + right);
            s = trimText(s, cellWidth);

            drawCenteredString(g, s,
                    new Rectangle(width, height, cellWidth, cellHeight),
                    new Font("default", Font.PLAIN, 14));

          }
        } else {
          g2d.setColor(new Color(194, 194, 163));
          g2d.fillRect(width, height, cellWidth, cellHeight);
        }

        g2d.setColor(Color.BLACK);
        g2d.drawRect(width, height, cellWidth, cellHeight);


        if (hightlightCoord != null) {
          if (j > 0 && i > 0 &&
                  hightlightCoord.col == j + down && hightlightCoord.row == i + right) {
            hlX = width;
            hlY = height;
            hlCellWidth = cellWidth;
            hlCellHeight = cellHeight;

          }
        }


        width += cellWidth;
        j++;
      }
      height += cellHeight;
      i++;
    }

    if (hightlightCoord != null) {
      g2d.setColor(Color.red);
      float thickness = 2;
      Stroke oldStroke = g2d.getStroke();
      g2d.setStroke(new BasicStroke(thickness));
      g2d.drawRect(hlX, hlY, hlCellWidth, hlCellHeight);
      g2d.setStroke(oldStroke);
    }


  }

  private String trimText(String context, int width) {
    return context.substring(0, Math.min(context.length(), width / 8));
  }


  protected void setHighlightCoord(Coord c) {
    hightlightCoord = c;
  }

  /**
   * To ensure text appear in the center of each grid.
   *
   * @param g    graphic
   * @param text the text inside each grid.
   * @param rect the grid.
   * @param font the front of the text.
   */
  private void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
    FontMetrics metrics = g.getFontMetrics(font);
    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
    g.setFont(font);
    g.drawString(text, x, y);

  }


  protected void addMouseListener(Features f) {

    MouseListener l = new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        //nothing
      }

      @Override
      public void mousePressed(MouseEvent e) {


        int height = model.getRowSize(0);
        for (int a = 1; a < i; height += model.getRowSize(a + right), a++) {
          int width = model.getColSize(0);
          for (int b = 1; b < j; width += model.getColSize(b + down), b++) {
            int widthMin = width;
            int widthMax = widthMin + model.getColSize(b + down);

            int heightMin = height;
            int heightMax = heightMin + model.getRowSize(a + right);
            if (e.getX() < widthMax && e.getX() > widthMin &&
                    e.getY() < heightMax && e.getY() > heightMin) {
              f.selectCell(new Coord(b + down, a + right));

            }

          }
        }


        int x = e.getX() / model.getColSize(0);
        int y = e.getY() / model.getRowSize(0);

        height = model.getRowSize(0);
        for (int a = 1; a < i; height += model.getRowSize(a + right), a++) {
          int width = model.getColSize(0);
          for (int b = 1; b < j; width += model.getColSize(b + down), b++) {
            int w = width + model.getColSize(b + down);
            int h = height + model.getRowSize(a + right);
            if (Math.abs(e.getX() - w) <= 5 && y == 0) {
              resizingCol = true;
              resizingCellIndex = b;
              resizeCellwidth = width;
            } else if (Math.abs(e.getY() - h) <= 5 && x == 0) {
              resizingRow = true;
              resizingCellIndex = a;
              resizeCellwidth = height;
            }

          }
        }


      }

      @Override
      public void mouseReleased(MouseEvent e) {
        resizingCol = false;
        resizingRow = false;
        /*
        if (e.getButton() == MouseEvent.BUTTON3) {
          //TODO: not done
          rightClickPopupMenu(f, e.getX(), e.getY());
        }

         */


      }

      @Override
      public void mouseEntered(MouseEvent e) {
        //nothing
      }

      @Override
      public void mouseExited(MouseEvent e) {
        //nothing
      }
    };
    this.addMouseListener(l);


    //MouseMotion Listener
    MouseMotionListener ml = new MouseMotionListener() {

      @Override
      public void mouseDragged(MouseEvent e) {
        if (resizingCol) {
          f.resizeCellCol(resizingCellIndex, e.getX() - resizeCellwidth);
        }
        if (resizingRow) {
          f.resizeCellRow(resizingCellIndex, e.getY() - resizeCellwidth);
        }
      }

      @Override
      public void mouseMoved(MouseEvent e) {
        //do nothing.

      }
    };

    this.addMouseMotionListener(ml);
  }

  private void rightClickPopupMenu(Features f, int x, int y) {

    JPopupMenu menu = new JPopupMenu();
    JMenuItem m1;
    JMenuItem m2;
    JMenuItem m3;
    JMenuItem m4;
    m1 = new JMenuItem("insert row");
    m2 = new JMenuItem("insert column");
    m3 = new JMenuItem("delete row");
    m4 = new JMenuItem("delete column");
    menu.add(m1);
    menu.add(m2);
    menu.add(m3);
    menu.add(m4);
    menu.show(GridPanel.this, x, y);

    Map<String, Runnable> buttonClickedMap = new HashMap<String, Runnable>();
    ButtonListener buttonListener = new ButtonListener();
    buttonClickedMap.put("insert row", () -> {
      System.out.println("insert row");
    });

    buttonClickedMap.put("insert column", () -> {
      System.out.println("insert column");
    });
    buttonClickedMap.put("delete row", () -> {
      System.out.println("delete row");
    });
    buttonClickedMap.put("delete column", () -> {
      System.out.println("delete column");
    });


    buttonListener.setButtonClickedActionMap(buttonClickedMap);

    m1.addActionListener(buttonListener);
    m2.addActionListener(buttonListener);
    m3.addActionListener(buttonListener);
    m4.addActionListener(buttonListener);


  }

  @Override
  public void setScroll(Coord c) {
    right = c.col - 1;
    down = c.row - 1;
  }

  @Override
  public void addFeatures(Features features) {
    this.addMouseListener(features);

  }

  @Override
  public void addActionListener(ActionListener listener) {
    //no action
  }

  @Override
  public String getInputString() throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setInputString(String s) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  @Override
  public Coord getSelectCoord() throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setSelectedCoord(Coord c) throws UnsupportedOperationException {
    setHighlightCoord(c);
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void redraw() {
    this.repaint();
  }
}
