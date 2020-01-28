package edu.cs3500.spreadsheets.model.formula;

import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cellvalue.CellValue;
import edu.cs3500.spreadsheets.model.Coord;


/**
 * To represent a formula content in the cell. A formula is one of: a value. a reference to a
 * rectangular region of cells in the spreadsheet. a function applied to one or more formulas as its
 * arguments.
 */
public interface Formula {



  /**
   * To get the value of the formula in the cell using accumulator.
   *
   * @param acc accumulator that record the cell value that has been evaluated.
   * @return the cellvalue of the formula.
   */
  CellValue getCellValue(Map<Coord, CellValue> acc, Map<Coord, Cell> grid);

  String toString(Map<Coord, Cell> grid);

  /**
   * Check whether the formula refer to itself.
   * @param l list of Coord visited.
   * @return if the formula is cyclic reference.
   */
  boolean checkCyclicReference(List<Coord> l, Set<Coord> noCycles, Map<Coord, Cell> grid);

  <R> R accept(FunctionFormula.FormulaVisitor<R> visitor, Map acc, Map<Coord, Cell> grid);

  Formula moveRefTo(int col, int row);

  String getRowContent();
}
