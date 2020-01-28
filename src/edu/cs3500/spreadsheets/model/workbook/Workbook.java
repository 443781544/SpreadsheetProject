package edu.cs3500.spreadsheets.model.workbook;

import edu.cs3500.spreadsheets.model.SpreadsheetModel;

/**
 * A workbook that contains multiple spreadsheet.
 */
public interface Workbook {
  void addWorksheet(SpreadsheetModel model);

  SpreadsheetModel getSpreadsheet(int i);

  SpreadsheetModel getSpreadsheet(String fileName);

  int getNumSheets();
}
