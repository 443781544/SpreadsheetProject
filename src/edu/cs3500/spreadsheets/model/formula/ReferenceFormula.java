package edu.cs3500.spreadsheets.model.formula;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cellvalue.CellValue;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * To represent a formula with the content of reference.
 */
public class ReferenceFormula implements Formula {
  protected List<Coord> coords;

  private Coord from;
  private Coord to;

  private boolean absoluteCol;
  private boolean absoluteRow;
  private boolean absoluteCol2;
  private boolean absoluteRow2;


  /**
   * the constructor of the a formula with the content of reference.
   * @param c the coordinate of the cell
   * @param absoluteCol is col absolute?
   * @param absoluteRow is row absolute?
   */
  public ReferenceFormula(Coord c, boolean absoluteCol, boolean absoluteRow) {
    this.from = c;
    this.absoluteCol = absoluteCol;
    this.absoluteRow = absoluteRow;

    List<Coord> coords = new ArrayList<>();
    coords.add(c);
    this.coords = coords;
  }

  /**
   * the constructor of the a formula with the content of reference.
   * @param coord from coord
   * @param coord1 to coord
   * @param absoluteCol1 is col1 absolute?
   * @param absoluteRow1 is row1 absolute?
   * @param absoluteCol2 is col2 absolute?
   * @param absoluteRow2 is row2 absolute?
   */
  public ReferenceFormula(Coord coord, Coord coord1, boolean absoluteCol1,
                          boolean absoluteRow1, boolean absoluteCol2, boolean absoluteRow2) {
    this.from = coord;
    this.to = coord1;
    this.absoluteCol = absoluteCol1;
    this.absoluteRow = absoluteRow1;
    this.absoluteCol2 = absoluteCol2;
    this.absoluteRow2 = absoluteRow2;

    List<Coord> coords = new ArrayList<>();
    for (int i = coord.col; i <= coord1.col; i++) {
      for (int j = coord.row; j <= coord1.row; j++) {
        coords.add(new Coord(i, j));
      }
    }
    this.coords = coords;

  }


  @Override
  public CellValue getCellValue(Map<Coord, CellValue> acc, Map<Coord, Cell> grid) {
    if (coords.size() == 1) {

      if (checkCyclicReference(new ArrayList<>(), new HashSet<>(), grid)) {
        throw new IllegalArgumentException("#REF!");
      }
      return getCellValue(acc, 0, grid);
    }
    throw new IllegalArgumentException("#VALUE!");

  }

  protected CellValue getCellValue(Map<Coord, CellValue> acc, int i, Map<Coord, Cell> grid) {

    if (acc.containsKey(coords.get(i))) {
      return acc.get(coords.get(i));
    }

    Cell c = grid.get(coords.get(i));

    CellValue cv = null;
    if (c != null) {
      cv = c.getValue(grid, acc);
      acc.put(coords.get(i), cv);
    }


    return cv;

  }


  @Override
  public boolean checkCyclicReference(List<Coord> l, Set<Coord> noCycles, Map<Coord, Cell> grid) {
    for (Coord coord : coords) {

      List<Coord> list = new ArrayList<>(l);

      if (list.contains(coord)) {
        return true;
      } else {
        Cell c = grid.get(coord);
        list.add(coord);

        if (!noCycles.contains(coord) && c != null) {
          boolean cycle = c.checkCyclicReference(list, noCycles, grid);

          if (cycle) {
            return true;
          } else {
            noCycles.add(coord);
          }

        }

      }
    }
    return false;
  }

  @Override
  public <R> R accept(FunctionFormula.FormulaVisitor<R> visitor, Map acc, Map<Coord, Cell> grid) {
    return visitor.visitReferenceFormula(coords, acc, grid);
  }

  @Override
  public Formula moveRefTo(int col, int row) {

    if (to == null) {
      int c = from.col;
      int r = from.row;
      if (!absoluteCol) {
        c += col;
      }
      if (!absoluteRow) {
        r += row;
      }
      return new ReferenceFormula(new Coord(c, r), absoluteCol, absoluteRow);

    }
    int c1 = from.col;
    int r1 = from.row;
    int c2 = to.col;
    int r2 = to.col;
    if (!absoluteCol) {
      c1 += col;
    }
    if (!absoluteRow) {
      r1 += row;
    }
    if (!absoluteCol2) {
      c2 += col;
    }
    if (!absoluteRow2) {
      r2 += row;
    }
    return new ReferenceFormula(new Coord(c1, r1), new Coord(c2, r2), absoluteCol, absoluteRow,
            absoluteCol2, absoluteRow2);
  }

  @Override
  public String getRowContent() {
    String s = "";
    if (absoluteCol) {
      s += "$";
    }
    s += Coord.colIndexToName(from.col);

    if (absoluteRow) {
      s += "$";
    }
    s += from.row;

    if (to == null) {
      return s;
    }

    s += ":";

    if (absoluteCol2) {
      s += "$";
    }
    s += Coord.colIndexToName(to.col);

    if (absoluteRow2) {
      s += "$";
    }
    s += to.row;


    return s;
  }


  @Override
  public String toString(Map<Coord, Cell> grid) {
    try {
      return getCellValue(new HashMap<>(), grid).toString();
    } catch (IllegalArgumentException e) {
      return e.getMessage();
    } catch (NullPointerException e) {
      return "";
    }

  }

  @Override
  public boolean equals(Object that) {
    if (!(that instanceof FunctionFormula)) {
      return false;
    } else {
      ReferenceFormula c = (ReferenceFormula) that;
      return toListEqual(this.coords, c.coords);
    }
  }

  private boolean toListEqual(List<Coord> l1, List<Coord> l2) {
    for (int i = 0; i < l1.size(); i++) {
      if (!l1.get(i).equals(l2.get(i))) {
        return false;
      }
    }
    return l2.size() == l1.size();
  }


  @Override
  public int hashCode() {
    return Objects.hash(coords);
  }


}
