package edu.cs3500.spreadsheets.view;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ViewOnlyModel;

/**
 * To add raw contents of each cell to appendable in order to read.
 */
public class SpreadsheetTextualView implements SpreadsheetView {

  private final ViewOnlyModel model;
  private final Appendable app;

  /**
   * To construct the SpreadsheetTextualView and throw the exceptions of null model.
   *
   * @param model SpreadsheetModel
   * @param app   appendable
   */
  public SpreadsheetTextualView(ViewOnlyModel model, Appendable app) {
    if (model == null || app == null) {
      throw new IllegalArgumentException("SpreadsheetTextualView constructor input null");
    }

    this.model = model;
    this.app = app;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();

    if (!model.getAllColSize().isEmpty()) {
      for (Map.Entry entry : model.getAllColSize().entrySet()) {
        s.append(entry.getKey());
        s.append(",");
        s.append(entry.getValue());
        s.append(";");
      }
      s.append("\n");
    }

    if (!model.getAllRowSize().isEmpty()) {
      for (Map.Entry entry : model.getAllRowSize().entrySet()) {
        s.append(entry.getKey());
        s.append(",");
        s.append(entry.getValue());
        s.append(";");
      }
      s.append("\n");
    }

    for (Cell c : model.getGrid().values()) {
      s.append(c.getRowContent());
      s.append("\n");
    }
    return s.toString();
  }

  @Override
  public void render() throws IOException {
    this.app.append(this.toString());
  }

  @Override
  public void addFeatures(Features features) {
    // this view will not include any features.
  }


  @Override
  public void addActionListener(ActionListener listener) {
    // this view will not include buttons.
  }


  @Override
  public String getInputString() {
    return null;
  }

  @Override
  public void setInputString(String s) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Coord getSelectCoord() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setSelectedCoord(Coord c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void resetFocus() {
    // no need resetfocus becase focus didn't change.
  }

  @Override
  public void repaint() {
    // no need to display the view again.
  }


  @Override
  public void showMessage(String m) {
    // no message need to be showed.
  }

  @Override
  public void showErrorMessage(String error) {
    // no error message need to be showed.
  }
}
