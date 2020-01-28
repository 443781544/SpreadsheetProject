package edu.cs3500.spreadsheets.model.formula;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cellvalue.CellValue;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * formulas that refer to entire columns of data, no matter how many items are in them.
 */
public class ColumnRefFormula extends ReferenceFormula implements Formula {
  private int fromCol;
  private int toCol;
  private boolean absCol1;
  private boolean absCol2;

  /**
   * Constructor for ColumnRefFormula.
   * @param i1 from column
   * @param i2 to column
   * @param absCol1 is from column absolute reference?
   * @param absCol2 is to column it absolute reference?
   */
  public ColumnRefFormula(int i1, int i2, boolean absCol1, boolean absCol2) {
    super(null, absCol1, absCol2);
    this.fromCol = i1;
    this.toCol = i2;
    this.absCol1 = absCol1;
    this.absCol2 = absCol2;
  }


  @Override
  public CellValue getCellValue(Map<Coord, CellValue> acc, Map<Coord, Cell> grid) {
    coords = getCoords(grid);
    return super.getCellValue(acc, grid);
  }

  @Override
  public String toString(Map<Coord, Cell> grid) {
    coords = getCoords(grid);
    return super.toString(grid);
  }

  @Override
  public boolean checkCyclicReference(List<Coord> l, Set<Coord> noCycles, Map<Coord, Cell> grid) {
    coords = getCoords(grid);

    return super.checkCyclicReference(l, noCycles, grid);
  }

  @Override
  public <R> R accept(FunctionFormula.FormulaVisitor<R> visitor, Map acc, Map<Coord, Cell> grid) {
    coords = getCoords(grid);

    return super.accept(visitor, acc, grid);

  }

  private List<Coord> getCoords(Map<Coord, Cell> grid) {
    List<Coord> coords = new ArrayList<>();

    for (int i = fromCol; i <= toCol; i++) {
      for (Coord c : grid.keySet()) {
        if (c.col == i) {
          coords.add(c);
        }
      }
    }
    return coords;
  }

  @Override
  public Formula moveRefTo(int col, int row) {
    if (!absCol1) {
      fromCol += col;
    }
    if (!absCol2) {
      toCol += col;
    }

    if (fromCol < 1 || toCol < 1) {
      throw new IllegalArgumentException("Reference does not exist.");
    }
    return new ColumnRefFormula(fromCol, toCol, absCol1, absCol2);
  }

  @Override
  public String getRowContent() {
    String s = "";
    if (absCol1) {
      s += "$";
    }
    s += Coord.colIndexToName(fromCol);

    if (fromCol == toCol) {
      return s;
    }

    s += ":";

    if (absCol2) {
      s += "$";
    }
    s += Coord.colIndexToName(toCol);
    return s;
  }


}
