package edu.cs3500.spreadsheets.model.cellvalue;

import java.util.Objects;

/**
 * A CellValue that contains a boolean.
 */
public class BooleanCellValue implements CellValue {
  private final boolean b;

  /**
   * Construct BooleanCellValue.
   *
   * @param b boolean.
   */
  public BooleanCellValue(boolean b) {
    this.b = b;
  }


  @Override
  public String toString() {
    return Boolean.toString(b);
  }

  @Override
  public boolean equals(Object that) {
    if (!(that instanceof BooleanCellValue)) {
      return false;
    } else {
      BooleanCellValue c = (BooleanCellValue) that;
      return this.b == c.b;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(b);
  }


  @Override
  public String getRowContent() {
    return toString();
  }
}
