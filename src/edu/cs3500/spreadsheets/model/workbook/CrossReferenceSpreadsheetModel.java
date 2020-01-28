package edu.cs3500.spreadsheets.model.workbook;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SimpleSpreadsheetModel;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.FormulaCell;
import edu.cs3500.spreadsheets.model.cell.ValueCell;
import edu.cs3500.spreadsheets.model.formula.Formula;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.ToFormulaVisitor;

/**
 * A spreadsheet model that allow cross reference.
 */
public class CrossReferenceSpreadsheetModel extends SimpleSpreadsheetModel {
  Workbook workbook;

  /**
   * Constructor for CrossReferenceSpreadsheetModel.
   * @param workbook a workbook model.
   */
  public CrossReferenceSpreadsheetModel(Workbook workbook) {
    super();
    this.workbook = workbook;
  }


  @Override
  public void setCell(int x, int y, String s) {

    Cell c;
    if (s == null) {
      return;
    } else if (s.startsWith("=")) {


      Sexp sexp = Parser.parse(s.substring(1));
      //c = sexp.accept(new ToCellVisitor(this));
      Formula f = sexp.accept(new ToFormulaVisitor(workbook));
      c = new FormulaCell(f, new Coord(x, y));
    } else {
      c = new ValueCell(s, new Coord(x, y));

    }

    grid.put(new Coord(x, y), c);

  }


}
