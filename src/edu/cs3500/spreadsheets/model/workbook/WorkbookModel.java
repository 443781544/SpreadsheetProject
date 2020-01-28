package edu.cs3500.spreadsheets.model.workbook;

import java.util.ArrayList;
import java.util.List;

import edu.cs3500.spreadsheets.model.SpreadsheetModel;

/**
 * A workbook model.
 */
public class WorkbookModel implements Workbook {
  List<SpreadsheetModel> workbook;

  /**
   * Construct the Workbook.
   */
  public WorkbookModel() {
    workbook = new ArrayList<>();
  }

  @Override
  public void addWorksheet(SpreadsheetModel model) {
    workbook.add(model);
  }


  @Override
  public SpreadsheetModel getSpreadsheet(int i) {
    //java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
    return workbook.get(i);
  }

  @Override
  public SpreadsheetModel getSpreadsheet(String fileName) {
    for (SpreadsheetModel model : workbook) {
      if (model.getName().equals(fileName)) {
        return model;
      }
    }
    return null;
  }

  @Override
  public int getNumSheets() {
    return workbook.size();
  }

}
