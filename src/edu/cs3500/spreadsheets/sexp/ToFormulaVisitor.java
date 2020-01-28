package edu.cs3500.spreadsheets.sexp;

import java.util.ArrayList;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.formula.CrossReferenceFormula;
import edu.cs3500.spreadsheets.model.formula.Formula;
import edu.cs3500.spreadsheets.model.formula.FunctionFormula;
import edu.cs3500.spreadsheets.model.formula.ReferenceFormula;
import edu.cs3500.spreadsheets.model.formula.ValueFormula;
import edu.cs3500.spreadsheets.model.cellvalue.BooleanCellValue;
import edu.cs3500.spreadsheets.model.cellvalue.DoubleCellValue;
import edu.cs3500.spreadsheets.model.cellvalue.StringCellValue;
import edu.cs3500.spreadsheets.model.formula.ColumnRefFormula;
import edu.cs3500.spreadsheets.model.workbook.Workbook;

/**
 * An function object for processing any {@link Sexp}ressions. return a formula {@link Formula}.
 */
public class ToFormulaVisitor implements SexpVisitor<Formula> {

  Workbook workbook;

  /**
   * Constructor ToFormulaVisitor.
   *
   * @param workbook a workbookModel.
   */

  public ToFormulaVisitor(Workbook workbook) {
    this.workbook = workbook;
  }

  @Override
  public Formula visitBoolean(boolean b) {
    return new ValueFormula(new BooleanCellValue(b));
  }

  @Override
  public Formula visitNumber(double d) {
    return new ValueFormula(new DoubleCellValue(d));
  }

  @Override
  public Formula visitSList(List<Sexp> l) {
    String functionName = "";
    List<Formula> lForm = new ArrayList<>();
    List<Sexp> lSexp = new ArrayList<>(l);


    if (l.size() > 0) {
      functionName = l.get(0).toString();
      lSexp.remove(0);
    }


    for (Sexp sexp : lSexp) {
      lForm.add(sexp.accept(this));
    }


    return new FunctionFormula(functionName, lForm);
  }

  @Override
  public Formula visitSymbol(String s) {


    try {

      if (s.indexOf('!') == -1) {
        return stringToRefFormula(s);

      } else {
        String fileName = s.substring(0, s.indexOf('!'));
        String content = s.substring(s.indexOf('!') + 1);
        return new CrossReferenceFormula(workbook.getSpreadsheet(fileName),
                stringToRefFormula(content));
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid Cell Content");
    }

  }

  private ReferenceFormula stringToRefFormula(String s) {
    if (s.indexOf(':') == -1) {

      boolean absoluteCol = absoluteCol(s);
      boolean absoluteRow = absoluteRow(s);
      s = s.replaceAll("\\$", "");

      if (s.matches("^[a-zA-Z]*$")) {
        return new ColumnRefFormula(Coord.colNameToIndex(s), Coord.colNameToIndex(s),
                absoluteCol, absoluteCol);
      }


      String[] part = s.split("(?<=\\D)(?=\\d)");


      int x = Coord.colNameToIndex(part[0]);
      int y = Integer.parseInt(part[1]);

      return new ReferenceFormula(new Coord(x, y), absoluteCol, absoluteRow);

    } else {
      String s1 = s.substring(0, s.indexOf(':'));
      String s2 = s.substring(s.indexOf(':') + 1);

      boolean absoluteCol1 = absoluteCol(s1);
      boolean absoluteRow1 = absoluteRow(s1);
      s1 = s1.replaceAll("\\$", "");

      boolean absoluteCol2 = absoluteCol(s2);
      boolean absoluteRow2 = absoluteRow(s2);
      s2 = s2.replaceAll("\\$", "");


      //two strings are only Alphabet, which means a column references
      if (s1.matches("^[a-zA-Z]*$") && s2.matches("^[a-zA-Z]*$")) {
        return new ColumnRefFormula(Coord.colNameToIndex(s1), Coord.colNameToIndex(s2),
                absoluteCol1, absoluteCol2);
      }

      String[] s1s = s1.split("(?<=\\D)(?=\\d)");
      String[] s2s = s2.split("(?<=\\D)(?=\\d)");


      int s1x = Coord.colNameToIndex(s1s[0]);
      int s1y = Integer.parseInt(s1s[1]);

      int s2x = Coord.colNameToIndex(s2s[0]);
      int s2y = Integer.parseInt(s2s[1]);


      return new ReferenceFormula(new Coord(s1x, s1y), new Coord(s2x, s2y),
              absoluteCol1, absoluteRow1, absoluteCol2, absoluteRow2);

    }
  }

  private boolean absoluteCol(String s) {
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == '$') {
        if (Character.isLetter(s.charAt(i + 1))) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean absoluteRow(String s) {
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == '$') {
        if (Character.isDigit(s.charAt(i + 1))) {
          return true;
        }
      }
    }
    return false;
  }


  @Override
  public Formula visitString(String s) {
    return new ValueFormula(new StringCellValue(s));
  }

}
