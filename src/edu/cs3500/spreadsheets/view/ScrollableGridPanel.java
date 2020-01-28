package edu.cs3500.spreadsheets.view;

import java.awt.BorderLayout;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseWheelListener;

import javax.swing.JScrollBar;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * To represent a GridPanel with both horizontal and vertical sliders to make the spreadsheet
 * scrollable.
 */
public class ScrollableGridPanel extends PanelDecorator {



  private JScrollBar vBar;
  private JScrollBar hBar;


  /**
   * To construct the ScrollableGridPanel. To initialize and set the value to the panel and
   * sliders.
   *
   * @param gPanel the spreadsheet panel.
   */
  public ScrollableGridPanel(SpreadsheetPanel gPanel) {
    super(gPanel);


    this.setLayout(new BorderLayout());
    this.add(gPanel, BorderLayout.CENTER);


    vBar = new JScrollBar(JScrollBar.VERTICAL, 0, 50, 0, 500);

    hBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 50, 0, 500);




    this.add(vBar, BorderLayout.EAST);
    this.add(hBar, BorderLayout.SOUTH);


  }


  @Override
  public void addFeatures(Features features) {
    panel.addFeatures(features);

    setScrollbar(hBar);

    setScrollbar(vBar);

    MouseWheelListener l = e -> {
      if (e.isShiftDown()) {
        if (e.getWheelRotation() < 0) {
          hBar.setValue(hBar.getValue() - 1);
        } else {
          hBar.setValue(hBar.getValue() + 1);
        }
      } else {
        if (e.getWheelRotation() < 0) {
          vBar.setValue(vBar.getValue() - 1);
        } else {
          vBar.setValue(vBar.getValue() + 1);
        }
      }
    };

    this.addMouseWheelListener(l);
  }

  private void setScrollbar(JScrollBar sBar) {
    AdjustmentListener vListener = e -> {
      panel.setScroll(new Coord(vBar.getValue() + 1, hBar.getValue() + 1));

      if (sBar.getValue() + sBar.getVisibleAmount() == sBar.getMaximum()) {

        sBar.setMaximum(sBar.getMaximum() + 1);
      }

      this.repaint();
    };

    sBar.addAdjustmentListener(vListener);
  }


}
