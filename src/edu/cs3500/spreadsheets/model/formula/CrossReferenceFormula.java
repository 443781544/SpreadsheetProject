package edu.cs3500.spreadsheets.model.formula;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cellvalue.CellValue;

/**
 * Represent cross-reference between cells of multiple sheets.
 */
public class CrossReferenceFormula implements Formula {

  private SpreadsheetModel model;
  private ReferenceFormula rf;

  /**
   * Constructor for CrossReferenceFormula.
   *
   * @param model the spreadsheet model it is referencing.
   * @param rf reference formula.
   */
  public CrossReferenceFormula(SpreadsheetModel model, ReferenceFormula rf) {
    this.rf = rf;
    this.model = model;
  }

  @Override
  public CellValue getCellValue(Map<Coord, CellValue> acc, Map<Coord, Cell> grid) {

    return rf.getCellValue(acc, model.getGrid());
  }

  @Override
  public String toString(Map<Coord, Cell> grid) {
    try {
      if (model == null) {
        return "No";
      }
      return getCellValue(new HashMap<>(), model.getGrid()).toString();
    } catch (IllegalArgumentException e) {
      return e.getMessage();
    } catch (NullPointerException e) {
      return "";
    }

  }

  @Override
  public boolean checkCyclicReference(List<Coord> l, Set<Coord> noCycles, Map<Coord, Cell> grid) {
    //TODO
    try {
      return false;
    } catch (StackOverflowError e) {
      return true;
    }

  }

  @Override
  public <R> R accept(FunctionFormula.FormulaVisitor<R> visitor, Map acc, Map<Coord, Cell> grid) {
    return rf.accept(visitor, acc, model.getGrid());
  }

  @Override
  public Formula moveRefTo(int col, int row) {
    Formula f = rf.moveRefTo(col, row);
    return new CrossReferenceFormula(model, (ReferenceFormula) f);
  }

  @Override
  public String getRowContent() {
    return model.getName() + "!" + rf.getRowContent();

  }


}
