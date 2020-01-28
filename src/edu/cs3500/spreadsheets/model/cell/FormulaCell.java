package edu.cs3500.spreadsheets.model.cell;


import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import edu.cs3500.spreadsheets.model.cellvalue.CellValue;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.formula.Formula;

/**
 * To represent a cell that contain a formula{@link Formula}.
 */
public class FormulaCell implements Cell {

  private Formula f;
  private Coord c;

  /**
   * The constructor of a formula content in the cell.
   *
   * @param f the formula in the cell
   * @param c the coordinate of the cell
   */
  public FormulaCell(Formula f, Coord c) {
    if (f == null || c == null) {
      throw new IllegalArgumentException("formulaCell constructor null");
    }
    this.f = f;
    this.c = c;
  }

  @Override
  public CellValue getValue(Map<Coord, Cell> grid, Map<Coord, CellValue> acc) {

    return f.getCellValue(acc, grid);
  }

  @Override
  public boolean checkCyclicReference(List<Coord> l, Set<Coord> noCycles, Map<Coord, Cell> grid) {
    return f.checkCyclicReference(l, noCycles, grid);
  }


  @Override
  public String getRowContent() {
    return c.toString() + " =" + f.getRowContent();
  }

  @Override
  public Cell moveTo(int col, int row) {
    int x = c.col + col;
    int y = c.row + row;
    Coord coord = new Coord(x, y);

    return new FormulaCell(f.moveRefTo(col, row), coord);
  }


  @Override
  public String toString(Map<Coord, Cell> grid) {
    return f.toString(grid);
  }

  @Override
  public boolean equals(Object that) {
    if (!(that instanceof FormulaCell)) {
      return false;
    } else {
      FormulaCell c = (FormulaCell) that;
      return this.f.equals(c.f) && this.c.equals(c.c);
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(f, c);
  }
}


