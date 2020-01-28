package edu.cs3500.spreadsheets.model;


import java.util.Map;

import edu.cs3500.spreadsheets.model.cell.Cell;

/**
 * a ViewOnlyModel to ensure no mutate.
 */
public class ViewOnlyModel implements SpreadsheetModel {
  SpreadsheetModel model;

  /**
   * To construct the ViewOnlyModel.
   *
   * @param spreadsheet the SpreadsheetModel
   */
  public ViewOnlyModel(SpreadsheetModel spreadsheet) {
    this.model = spreadsheet;
  }

  @Override
  public Cell getCellAt(int x, int y) {
    return model.getCellAt(x, y);
  }

  @Override
  public Cell getCellAt(Coord c) {
    return model.getCellAt(c);
  }

  @Override
  public String getCell(int x, int y) {
    return model.getCell(x, y);
  }

  @Override
  public String getCellContent(int x, int y) {
    return model.getCellContent(x, y);
  }

  @Override
  public void setCell(int x, int y, String s) {
    throw new UnsupportedOperationException("View Only");
  }

  @Override
  public void removeCell(Coord c) {
    throw new UnsupportedOperationException("View Only");
  }

  @Override
  public Map<Coord, Cell> getGrid() {
    return model.getGrid();
  }

  @Override
  public String getName() {
    return model.getName();
  }

  @Override
  public void setName(String name) {
    throw new UnsupportedOperationException("View Only");
  }

  @Override
  public int getRowSize(int i) {
    return model.getRowSize(i);
  }

  @Override
  public int getColSize(int i) {
    return model.getColSize(i);
  }

  @Override
  public void resizeCol(int i, int size) {
    throw new UnsupportedOperationException("View Only");
  }

  @Override
  public void resizeRow(int i, int size) {
    throw new UnsupportedOperationException("View Only");
  }

  @Override
  public Map<Integer, Integer> getAllRowSize() {
    return model.getAllRowSize();
  }

  @Override
  public Map<Integer, Integer> getAllColSize() {
    return model.getAllColSize();
  }

  @Override
  public void moveCellTo(Coord from, Coord to) {
    throw new UnsupportedOperationException("View Only");
  }


}
