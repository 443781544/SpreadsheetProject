package edu.cs3500.spreadsheets.view;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;

abstract class AbstractSpreadsheetView extends JFrame implements SpreadsheetView {
  SpreadsheetPanel panel;

  public AbstractSpreadsheetView(SpreadsheetPanel panel) {
    this.panel = panel;

  }

  @Override
  public void render() {
    this.setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    panel.addFeatures(features);
  }

  @Override
  public void addActionListener(ActionListener actionListener) {
    panel.addActionListener(actionListener);
  }

  @Override
  public String getInputString() {
    return panel.getInputString();
  }

  @Override
  public void setInputString(String s) {
    panel.setInputString(s);
  }

  @Override
  public Coord getSelectCoord() {
    return panel.getSelectCoord();
  }

  @Override
  public void setSelectedCoord(Coord c) {
    panel.setSelectedCoord(c);
  }


  @Override
  public void resetFocus() {
    panel.resetFocus();
  }

  @Override
  public void showMessage(String m) {
    JOptionPane.showMessageDialog(null, m,
            "Message", JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(null, error,
            "Error", JOptionPane.ERROR_MESSAGE);
  }
}
