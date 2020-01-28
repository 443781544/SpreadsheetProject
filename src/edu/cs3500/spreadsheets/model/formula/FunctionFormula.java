package edu.cs3500.spreadsheets.model.formula;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import edu.cs3500.spreadsheets.model.cellvalue.BooleanCellValue;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cellvalue.CellValue;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cellvalue.DoubleCellValue;
import edu.cs3500.spreadsheets.model.cellvalue.StringCellValue;

/**
 * To represent a formula with the content of function.
 */
public class FunctionFormula implements Formula {
  private final String funcName;
  protected final List<Formula> formulas;

  /**
   * Identify the function of the given formula.
   *
   * @param funcName the name of the given function
   * @param l        list of formulas
   */
  public FunctionFormula(String funcName, List<Formula> l) {
    if (funcName == null || l == null) {
      throw new IllegalArgumentException("null");
    }

    this.funcName = funcName;

    this.formulas = l;

  }


  @Override
  public CellValue getCellValue(Map<Coord, CellValue> acc, Map<Coord, Cell> grid) {
    if (formulas.size() == 0) {
      throw new IllegalArgumentException("#N/A");
    }
    if (checkCyclicReference(new ArrayList<>(), new HashSet<>(), grid)) {
      throw new IllegalArgumentException("#REF!");
    }


    switch (funcName) {
      case "SUM":
        return new DoubleCellValue(this.accept(new Sum(), acc, grid));


      case "PRODUCT":
        return new DoubleCellValue(this.accept(new Product(), acc, grid));


      case "<":
        return new BooleanCellValue(this.accept(new LessThan(), acc, grid));

      case "CONCAT":
        return new StringCellValue(this.accept(new Concat(), acc, grid));

      default:
        throw new IllegalArgumentException("#NAME?");
    }

  }

  @Override
  public boolean checkCyclicReference(List<Coord> l, Set<Coord> noCycles, Map<Coord, Cell> grid) {
    for (Formula f : formulas) {
      if (f.checkCyclicReference(l, noCycles, grid)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor, Map acc, Map<Coord, Cell> grid) {
    return visitor.visitFunctionFormula(formulas, acc, grid);
  }

  @Override
  public Formula moveRefTo(int col, int row) {
    ArrayList<Formula> newFormulas = new ArrayList<>();
    for (Formula f : formulas) {
      newFormulas.add(f.moveRefTo(col, row));
    }
    return new FunctionFormula(funcName, newFormulas);

  }

  @Override
  public String getRowContent() {
    StringBuilder s = new StringBuilder();
    s.append("(" + funcName + " ");
    for (int i = 0; i < formulas.size(); i++) {
      s.append(formulas.get(i).getRowContent());
      if (i != formulas.size() - 1) {
        s.append(" ");
      }
    }
    s.append(") ");
    return s.toString();
  }

  @Override
  public String toString(Map<Coord, Cell> grid) {
    try {
      return getCellValue(new HashMap<>(), grid).toString();
    } catch (IllegalArgumentException e) {
      return e.getMessage();
    }
  }

  @Override
  public boolean equals(Object that) {
    if (!(that instanceof FunctionFormula)) {
      return false;
    } else {
      FunctionFormula c = (FunctionFormula) that;
      return this.funcName.equals(c.funcName)
              && this.formulas.equals(c.formulas);
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(funcName, formulas);
  }


  /**
   * Visitor pattern for computing value of formula.
   *
   * @param <T> the return type of value.
   */
  public interface FormulaVisitor<T> {
    T visitValueFormula(CellValue cv, Map acc, Map grid);

    T visitReferenceFormula(List<Coord> coords, Map acc, Map grid);

    T visitFunctionFormula(List<Formula> f, Map acc, Map grid);


  }

  class Sum implements FormulaVisitor<Double> {

    @Override
    public Double visitValueFormula(CellValue cv, Map acc, Map grid) {

      try {
        double d = Double.parseDouble(cv.toString());
        return d;
      } catch (NumberFormatException e) {
        return 0.0;
      }
    }

    @Override
    public Double visitReferenceFormula(List<Coord> coords, Map acc, Map grid) {
      List<CellValue> cv = coordsToCvs(coords, acc, grid);
      double d = 0.0;
      for (CellValue c : cv) {
        try {
          double dd = Double.parseDouble(c.toString());
          d += dd;
        } catch (NumberFormatException e) {
          //do nothing
        }
      }

      return d;
    }

    @Override
    public Double visitFunctionFormula(List<Formula> f, Map acc, Map grid) {
      Double d = 0.0;
      for (Formula formula : f) {
        d += formula.accept(this, acc, grid);
      }
      return d;
    }
  }

  class Product implements FormulaVisitor<Double> {

    @Override
    public Double visitValueFormula(CellValue cv, Map acc, Map grid) {
      try {
        double d = Double.parseDouble(cv.toString());
        return d;
      } catch (NumberFormatException e) {
        return 1.0;
      }

    }

    @Override
    public Double visitReferenceFormula(List<Coord> coords, Map acc, Map grid) {
      List<CellValue> cv = coordsToCvs(coords, acc, grid);
      double d = 1.0;

      for (CellValue c : cv) {
        try {
          double dd = Double.parseDouble(c.toString());
          d *= dd;
        } catch (NumberFormatException e) {
          //do nothing
        }
      }

      return d;

    }

    @Override
    public Double visitFunctionFormula(List<Formula> f, Map acc, Map grid) {
      Double d = 1.0;

      for (Formula formula : f) {
        d *= formula.accept(this, acc, grid);
      }
      return d;
    }
  }

  class LessThan implements FormulaVisitor<Boolean> {

    @Override
    public Boolean visitValueFormula(CellValue cv, Map acc, Map grid) {
      return false;
    }

    @Override
    public Boolean visitReferenceFormula(List<Coord> coords, Map acc, Map grid) {
      return false;
    }

    @Override
    public Boolean visitFunctionFormula(List<Formula> f, Map acc, Map grid) {
      if (f.size() != 2) {
        throw new IllegalArgumentException("#ERROR!");
      }
      double n1;
      double n2;
      try {
        n1 = Double.parseDouble(f.get(0).getCellValue(acc, grid).toString());
        n2 = Double.parseDouble(f.get(1).getCellValue(acc, grid).toString());
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("#ERROR!");
      } catch (NullPointerException e) {
        throw new IllegalArgumentException("");
      }

      return n1 < n2;
    }
  }

  class Concat implements FormulaVisitor<String> {

    @Override
    public String visitValueFormula(CellValue cv, Map acc, Map grid) {
      return cv.toString();
    }

    @Override
    public String visitReferenceFormula(List<Coord> coords, Map acc, Map grid) {
      List<CellValue> cv = coordsToCvs(coords, acc, grid);
      StringBuilder s = new StringBuilder();

      for (CellValue c : cv) {
        s.append(c.toString());
      }

      return s.toString();
    }

    @Override
    public String visitFunctionFormula(List<Formula> f, Map acc, Map grid) {
      StringBuilder s = new StringBuilder();
      for (Formula formula : f) {
        s.append(formula.accept(this, acc, grid));
      }
      return s.toString();
    }
  }

  private List<CellValue> coordsToCvs(List<Coord> coords,
                                      Map<Coord, CellValue> acc, Map<Coord, Cell> grid) {
    List<CellValue> l = new ArrayList<>();
    for (int i = 0; i < coords.size(); i++) {
      if (acc.containsKey(coords.get(i))) {
        //acc
        l.add(acc.get(coords.get(i)));
      } else {
        try {
          CellValue cv = grid.get(coords.get(i)).getValue(grid, acc);
          l.add(cv);
          acc.put(coords.get(i), cv);
        } catch (NullPointerException e) {
          //donothing
        }
      }
    }


    return l;

  }
}
