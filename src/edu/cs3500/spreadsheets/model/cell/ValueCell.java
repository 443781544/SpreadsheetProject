package edu.cs3500.spreadsheets.model.cell;


import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import edu.cs3500.spreadsheets.model.cellvalue.BooleanCellValue;
import edu.cs3500.spreadsheets.model.cellvalue.CellValue;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cellvalue.DoubleCellValue;
import edu.cs3500.spreadsheets.model.cellvalue.StringCellValue;


/**
 * To represent a value content in a cell {@link CellValue}.
 */

public class ValueCell implements Cell {

  private final String rawContent;

  private final CellValue value;

  private Coord c;

  /**
   * the constructor of a  value content in a cell.
   *
   * @param s the content in a cell
   * @param c the coordinate of the cell
   */
  public ValueCell(String s, Coord c) {
    CellValue value1;
    if (s == null || c == null) {
      throw new IllegalArgumentException("ValueCell constructor null");
    }

    if (s.equals("true")) {
      value1 = new BooleanCellValue(true);
    } else if (s.equals("false")) {
      value1 = new BooleanCellValue(false);
    } else {
      try {

        double d = Double.parseDouble(s);
        value1 = new DoubleCellValue(d);

      } catch (NumberFormatException e) {
        value1 = new StringCellValue(s);
      }
    }

    this.value = value1;
    this.rawContent = s;
    this.c = c;
  }


  @Override
  public String getRowContent() {
    return c.toString() + " " + rawContent;
  }

  @Override
  public Cell moveTo(int col, int row) {
    int x = c.col + col;
    int y = c.row + row;
    Coord coord = new Coord(x, y);
    return new ValueCell(rawContent, coord);
  }

  @Override
  public CellValue getValue(Map<Coord, Cell> grid, Map<Coord, CellValue> acc) {
    return value;
  }

  @Override
  public boolean checkCyclicReference(List<Coord> l, Set<Coord> noCycles, Map<Coord, Cell> grid) {
    return false;
  }

  @Override
  public String toString(Map<Coord, Cell> grid) {
    return value.toString();
  }


  @Override
  public boolean equals(Object that) {
    if (!(that instanceof ValueCell)) {
      return false;
    } else {
      ValueCell c = (ValueCell) that;
      return this.value.equals(c.value) && this.c.equals(c.c);
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, c);
  }
}

