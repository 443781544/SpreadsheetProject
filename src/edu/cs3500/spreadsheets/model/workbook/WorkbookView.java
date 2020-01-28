package edu.cs3500.spreadsheets.model.workbook;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ViewOnlyModel;
import edu.cs3500.spreadsheets.view.EditablePanel;
import edu.cs3500.spreadsheets.view.GridPanel;
import edu.cs3500.spreadsheets.view.SaveLoadPanel;
import edu.cs3500.spreadsheets.view.ScrollableGridPanel;
import edu.cs3500.spreadsheets.view.SpreadsheetPanel;
import edu.cs3500.spreadsheets.view.SpreadsheetView;

/**
 * A Spreadsheet view to show workbook.
 */
public class WorkbookView extends JFrame implements SpreadsheetView {

  JTabbedPane tab;

  ArrayList<SpreadsheetPanel> panels;

  int currentPanel;

  /**
   * Constructor for SpreadsheetSaveLoadGraphicView.
   *
   * @param model view only model.
   */
  public WorkbookView(Workbook model) {
    super();
    //TODO:
    //this.setTitle(model.getName());
    this.setSize(1500, 750);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


    panels = new ArrayList<>();
    currentPanel = 0;

    tab = new JTabbedPane(JTabbedPane.BOTTOM);

    for (int i = 0; i < model.getNumSheets(); i++) {
      SpreadsheetPanel p =
              new SaveLoadPanel(
                      new EditablePanel(
                              new ScrollableGridPanel(
                                      new GridPanel(
                                              new ViewOnlyModel(model.getSpreadsheet(i))))));

      tab.add(p, model.getSpreadsheet(i).getName());
      panels.add(p);
    }

    this.add(tab, BorderLayout.SOUTH);

    this.pack();
  }

  @Override
  public void render() {
    this.setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    for (SpreadsheetPanel c : panels) {
      c.addFeatures(features);
    }

    tab.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        int index = tab.getSelectedIndex();
        currentPanel = index;
        features.switchWorksheets(index);
      }
    });
  }

  @Override
  public void addActionListener(ActionListener listener) {
    //do nothing.
  }

  @Override
  public String getInputString() throws UnsupportedOperationException {
    return panels.get(currentPanel).getInputString();
  }

  @Override
  public void setInputString(String s) throws UnsupportedOperationException {
    panels.get(currentPanel).setInputString(s);
  }

  @Override
  public Coord getSelectCoord() throws UnsupportedOperationException {
    return panels.get(currentPanel).getSelectCoord();
  }

  @Override
  public void setSelectedCoord(Coord c) throws UnsupportedOperationException {
    panels.get(currentPanel).setSelectedCoord(c);
  }

  @Override
  public void resetFocus() {
    panels.get(currentPanel).resetFocus();
  }

  @Override
  public void repaint() {
    panels.get(currentPanel).repaint();
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
