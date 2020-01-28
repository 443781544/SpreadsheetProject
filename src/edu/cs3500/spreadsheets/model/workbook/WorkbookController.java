package edu.cs3500.spreadsheets.model.workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.controller.IController;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.ViewOnlyModel;
import edu.cs3500.spreadsheets.view.SpreadsheetTextualView;
import edu.cs3500.spreadsheets.view.SpreadsheetView;

/**
 * Controller for workbook model.
 */
public class WorkbookController implements IController, Features {
  private Workbook workbook;
  SpreadsheetView view;

  SpreadsheetModel model;

  /**
   * Constructor for WorkbookController.
   * @param workbook a workbook model.
   * @param view a spreadsheet view.
   */
  public WorkbookController(Workbook workbook, SpreadsheetView view) {
    this.workbook = workbook;
    this.view = view;

    model = workbook.getSpreadsheet(0);

    view.addFeatures(this);
  }

  @Override
  public void run() throws IOException {
    view.render();
  }


  @Override
  public void confirm() {
    String content = view.getInputString();

    Coord c = view.getSelectCoord();

    try {
      model.setCell(c.col, c.row, content);
    } catch (IllegalArgumentException e) {
      view.showErrorMessage(e.getMessage());
    }


    view.repaint();
    //set focus back to main frame so that keyboard events work
    view.resetFocus();
  }

  @Override
  public void reject() {
    Coord c = view.getSelectCoord();

    Cell cell = model.getCellAt(c.col, c.row);
    if (cell != null) {
      String s = model.getCellAt(c.col, c.row).getRowContent();
      s = s.substring(s.indexOf(' ') + 1);
      view.setInputString(s);
      view.resetFocus();
    }

  }

  @Override
  public void selectCell(Coord c) {
    view.setSelectedCoord(c);
    Cell cell = model.getCellAt(c.col, c.row);

    if (cell != null) {
      String s = cell.getRowContent();
      s = s.substring(s.indexOf(' ') + 1);
      view.setInputString(s);
    } else {
      view.setInputString("");
    }
    view.repaint();
    view.resetFocus();
  }

  @Override
  public void moveSelectCellUp() {
    Coord c = view.getSelectCoord();
    if (c.row > 1) {
      view.setSelectedCoord(new Coord(c.col, c.row - 1));
    }
    view.repaint();
  }

  @Override
  public void moveSelectCellDown() {
    Coord c = view.getSelectCoord();
    view.setSelectedCoord(new Coord(c.col, c.row + 1));
    view.repaint();

  }

  @Override
  public void moveSelectCellLeft() {
    Coord c = view.getSelectCoord();
    if (c.col > 1) {
      view.setSelectedCoord(new Coord(c.col - 1, c.row));
    }
    view.repaint();
  }

  @Override
  public void moveSelectCellRight() {
    Coord c = view.getSelectCoord();
    view.setSelectedCoord(new Coord(c.col + 1, c.row));
    view.repaint();
  }

  @Override
  public void deleteCellContent() {
    Coord c = view.getSelectCoord();
    model.removeCell(c);
    view.repaint();

  }

  @Override
  public void saveFile() {
    File file = new File(model.getName());

    try {
      Appendable app = new PrintWriter(file);
      SpreadsheetView view = new SpreadsheetTextualView(new ViewOnlyModel(model), app);
      view.render();
      ((PrintWriter) app).close();
      this.view.showMessage("File Saved");
    } catch (FileNotFoundException e) {
      this.view.showErrorMessage("file not found");
    } catch (IOException e) {
      this.view.showErrorMessage(e.getMessage());
    }

  }

  @Override
  public void loadFile(String fileName) {
    //TODO:

  }

  @Override
  public void switchWorksheets(int index) {
    model = workbook.getSpreadsheet(index);
  }

  @Override
  public void resizeCellCol(int i, int size) {
    model.resizeCol(i, size);
  }

  @Override
  public void resizeCellRow(int i, int size) {
    model.resizeRow(i, size);
  }

  @Override
  public void smartMoveCell(Coord from, Coord to) {
    try {
      model.moveCellTo(from, to);
    } catch (IllegalArgumentException e) {
      view.showErrorMessage(e.getMessage());
    }
  }

}
