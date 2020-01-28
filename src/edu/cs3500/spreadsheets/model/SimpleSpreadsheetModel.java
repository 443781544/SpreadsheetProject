package edu.cs3500.spreadsheets.model;


import java.util.HashMap;
import java.util.Map;

import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.FormulaCell;
import edu.cs3500.spreadsheets.model.cell.ValueCell;
import edu.cs3500.spreadsheets.model.formula.Formula;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.ToFormulaVisitor;

/**
 * The model of simple spreadsheet. The model support three types of values. The model support
 * references to cells and to finite rectangular regions of cells. The model support various
 * formulas. The model is able to support editing cells’ contents, regardless of a cell’s location
 * or prior contents.
 */
public class SimpleSpreadsheetModel implements SpreadsheetModel {

  protected Map<Coord, Cell> grid;

  String name;

  Map<Integer, Integer> cellColSize;
  Map<Integer, Integer> cellRowSize;

  /**
   * Construct the SimpleSpreadsheetModel.
   */
  public SimpleSpreadsheetModel() {
    grid = new HashMap<>();
    cellColSize = new HashMap<>();
    cellRowSize = new HashMap<>();
    name = "Spreadsheet";
  }

  /**
   * Construct the SimpleSpreadsheetModel.
   */
  public SimpleSpreadsheetModel(String s) {
    this();
    name = s;
  }

  @Override
  public void setCell(int x, int y, String s) {

    Cell c;
    if (s == null) {
      return;
    } else if (s.startsWith("=")) {


      Sexp sexp = Parser.parse(s.substring(1));
      //c = sexp.accept(new ToCellVisitor(this));
      Formula f = sexp.accept(new ToFormulaVisitor(null));
      c = new FormulaCell(f, new Coord(x, y));
    } else {
      c = new ValueCell(s, new Coord(x, y));

    }


    grid.put(new Coord(x, y), c);


  }

  @Override
  public void removeCell(Coord c) {
    grid.remove(c);


  }

  @Override
  public Cell getCellAt(int x, int y) {

    return grid.get(new Coord(x, y));
  }

  @Override
  public Cell getCellAt(Coord c) {
    return grid.get(c);
  }

  @Override
  public String getCell(int x, int y) {
    Map m = new HashMap<>(grid);

    return getCellAt(x, y).toString(m);
  }

  @Override
  public String getCellContent(int x, int y) {
    return getCellAt(x, y).getRowContent();
  }


  @Override
  public Map<Coord, Cell> getGrid() {
    Map m = new HashMap<>(grid);
    return m;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public int getRowSize(int i) {
    if (cellRowSize.containsKey(i)) {
      return cellRowSize.get(i);
    }
    return 25;
  }

  @Override
  public int getColSize(int i) {
    if (cellColSize.containsKey(i)) {
      return cellColSize.get(i);
    }
    return 75;
  }

  @Override
  public void resizeCol(int i, int size) {
    if (size > 5) {
      cellColSize.put(i, size);
    }
  }

  @Override
  public void resizeRow(int i, int size) {
    if (size > 5) {
      cellRowSize.put(i, size);
    }
  }

  @Override
  public Map<Integer, Integer> getAllRowSize() {
    Map m = new HashMap<>(cellRowSize);
    return m;
  }

  @Override
  public Map<Integer, Integer> getAllColSize() {
    Map m = new HashMap<>(cellColSize);
    return m;
  }

  @Override
  public void moveCellTo(Coord from, Coord to) {
    Cell c = getCellAt(from);

    if (c != null) {
      Cell newCell = c.moveTo(to.col - from.col, to.row - from.row);
      grid.put(to, newCell);
    }
  }


}
