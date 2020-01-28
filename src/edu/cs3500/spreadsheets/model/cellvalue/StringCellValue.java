package edu.cs3500.spreadsheets.model.cellvalue;

import java.util.Objects;


/**
 * A CellValue that contains a String.
 */
public class StringCellValue implements CellValue {
  private final String s;


  /**
   * Construct StringCellValue.
   *
   * @param s String.
   */
  public StringCellValue(String s) {
    this.s = s;
  }

  @Override
  public String toString() {
    return "\""
            + this.s.replace("\\", "\\\\")
            .replace("\"", "\\\"")
            + "\"";
  }

  @Override
  public boolean equals(Object that) {
    if (!(that instanceof StringCellValue)) {
      return false;
    } else {
      StringCellValue c = (StringCellValue) that;
      return this.s.equals(c.s);
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(s);
  }

  @Override
  public String getRowContent() {
    return s;
  }
}
