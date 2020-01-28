package edu.cs3500.spreadsheets.model.cellvalue;

import java.util.Objects;


/**
 * A CellValue that contains a double.
 */
public class DoubleCellValue implements CellValue {
  private final double d;

  /**
   * Construct DoubleCellValue.
   *
   * @param d double.
   */
  public DoubleCellValue(double d) {
    this.d = d;
  }


  @Override
  public String toString() {
    return String.format("%f", d);
  }

  @Override
  public boolean equals(Object that) {
    if (!(that instanceof DoubleCellValue)) {
      return false;
    } else {
      DoubleCellValue c = (DoubleCellValue) that;
      return this.d == c.d;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(d);
  }

  @Override
  public String getRowContent() {
    return String.valueOf(d);
  }
}
