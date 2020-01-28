package edu.cs3500.spreadsheets.model;

/**
 * To build a simple spreadsheet {@link SimpleSpreadsheetModel}.
 */
public class SimpleSpreadSheetBuilder
        implements WorksheetReader.WorksheetBuilder<SpreadsheetModel> {

  private final SpreadsheetModel model;

  /**
   * Constructor of SimpleSpreadSheetBuilder.
   */
  public SimpleSpreadSheetBuilder(String name) {
    model = new SimpleSpreadsheetModel(name);
  }

  @Override
  public WorksheetReader.WorksheetBuilder<SpreadsheetModel> createCell(
          int col, int row, String contents) {

    model.setCell(col, row, contents);
    return this;
  }

  @Override
  public SpreadsheetModel createWorksheet() {

    return model;
  }

  @Override
  public WorksheetReader.WorksheetBuilder<SpreadsheetModel> setRowSize(int i, int size) {
    model.resizeRow(i, size);
    return this;
  }

  @Override
  public WorksheetReader.WorksheetBuilder<SpreadsheetModel> setColSize(int i, int size) {
    model.resizeCol(i, size);
    return this;
  }
}
