package edu.cs3500.spreadsheets.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SimpleSpreadSheetBuilder;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.ViewOnlyModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.view.SpreadsheetView;
import edu.cs3500.spreadsheets.view.SpreadsheetTextualView;
import edu.cs3500.spreadsheets.view.SpreadsheetViewCreator;

/**
 * Provides a controller for interacting with the spreadsheet in order to realize several functions
 * in the spreadsheet.
 */
public class Controller implements IController, Features {
  private SpreadsheetModel model;
  private SpreadsheetView view;
  private SpreadsheetViewCreator.ViewType viewType;

  /**
   * To construct the controller.
   *
   * @param m        SpreadsheetModel
   * @param viewType view types
   */
  public Controller(SpreadsheetModel m, SpreadsheetViewCreator.ViewType viewType) {
    this.model = m;

    this.viewType = viewType;

    this.view = SpreadsheetViewCreator.create(new ViewOnlyModel(model), viewType);

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

    if (fileName != null) {

      File file = new File(fileName);

      BufferedReader br;
      try {
        br = new BufferedReader(new FileReader(file));
      } catch (FileNotFoundException e) {
        view.showErrorMessage("File Not Exist");
        return;
      }


      WorksheetReader.WorksheetBuilder<SpreadsheetModel> builder =
              new SimpleSpreadSheetBuilder(fileName);

      SpreadsheetModel newModel = WorksheetReader.read(builder, br);
      Controller c = new Controller(newModel, viewType);


      try {
        c.run();
      } catch (IOException e) {
        this.view.showErrorMessage(e.getMessage());
      }
    }

  }

  @Override
  public void switchWorksheets(int index) {
    //do nothing
  }

  @Override
  public void resizeCellCol(int i, int size) {
    model.resizeCol(i, size);
    view.repaint();
  }

  @Override
  public void resizeCellRow(int i, int size) {
    model.resizeRow(i, size);
    view.repaint();
  }

  @Override
  public void smartMoveCell(Coord from, Coord to) {

    //catch error
    try {
      model.moveCellTo(from, to);
    } catch (IllegalArgumentException e) {
      view.showErrorMessage(e.getMessage());
    }


  }
}
