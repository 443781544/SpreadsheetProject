package edu.cs3500.spreadsheets.model;

/**
 * To build a Workbook.
 */
public class WorkbookBuilder
        implements WorksheetReader.WorksheetBuilder<SpreadsheetModel> {

  @Override
  public WorksheetReader.WorksheetBuilder<SpreadsheetModel> createCell(
          int col, int row, String contents) {
    return null;
  }

  @Override
  public SpreadsheetModel createWorksheet() {
    return null;
  }

  @Override
  public WorksheetReader.WorksheetBuilder<SpreadsheetModel> setRowSize(int i, int size) {
    return null;
  }

  @Override
  public WorksheetReader.WorksheetBuilder<SpreadsheetModel> setColSize(int i, int size) {
    return null;
  }
}
