package edu.cs3500.spreadsheets.view;

import java.awt.event.ActionListener;


import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;


abstract class PanelDecorator extends SpreadsheetPanel {
  protected SpreadsheetPanel panel;

  public PanelDecorator(SpreadsheetPanel newPanel) {
    panel = newPanel;
  }

  @Override
  public void setScroll(Coord c) {
    panel.setScroll(c);
  }

  @Override
  public void addFeatures(Features features) {
    panel.addFeatures(features);
  }

  @Override
  public void addActionListener(ActionListener listener) {
    panel.addActionListener(listener);
  }

  @Override
  public String getInputString() throws UnsupportedOperationException {
    return panel.getInputString();
  }

  @Override
  public void setInputString(String s) throws UnsupportedOperationException {
    panel.setInputString(s);
  }

  @Override
  public Coord getSelectCoord() throws UnsupportedOperationException {
    return panel.getSelectCoord();
  }

  @Override
  public void setSelectedCoord(Coord c) throws UnsupportedOperationException {
    panel.setSelectedCoord(c);
  }

  @Override
  public void resetFocus() {
    panel.resetFocus();
  }

  @Override
  public void redraw() {
    this.repaint();
    panel.repaint();
  }
}
