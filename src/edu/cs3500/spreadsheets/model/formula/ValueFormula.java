package edu.cs3500.spreadsheets.model.formula;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cellvalue.CellValue;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * To represent a formula with the content of value {@link CellValue}.
 */
public class ValueFormula implements Formula {
  private final CellValue cv;

  /**
   * the constructor of a value formula.
   *
   * @param cv cell value.
   */
  public ValueFormula(CellValue cv) {
    this.cv = cv;
  }


  @Override
  public CellValue getCellValue(Map<Coord, CellValue> acc, Map<Coord, Cell> grid) {
    return cv;
  }

  @Override
  public boolean checkCyclicReference(List<Coord> l, Set<Coord> noCycles, Map<Coord, Cell> grid) {
    return false;
  }

  @Override
  public <R> R accept(FunctionFormula.FormulaVisitor<R> visitor, Map acc, Map<Coord, Cell> grid) {
    return visitor.visitValueFormula(cv, acc, grid);
  }

  @Override
  public Formula moveRefTo(int col, int row) {
    return this;
  }

  @Override
  public String getRowContent() {
    return cv.getRowContent();
  }

  @Override
  public String toString(Map<Coord, Cell> grid) {
    return cv.toString();
  }

  @Override
  public boolean equals(Object that) {
    if (!(that instanceof ValueFormula)) {
      return false;
    } else {
      ValueFormula c = (ValueFormula) that;
      return this.cv.equals(c.cv);
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(cv);
  }

}
