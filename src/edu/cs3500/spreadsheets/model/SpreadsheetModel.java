package edu.cs3500.spreadsheets.model;

import java.util.Map;

import edu.cs3500.spreadsheets.model.cell.Cell;

/**
 * The model of a spreadsheet.
 */
public interface SpreadsheetModel {

  /**
   * Returns the Cell at the specified col and row, 1-indexed numeric value..
   *
   * @param x col .
   * @param y row.
   * @return the cell at the given position, or <code>null</code> if no cell is there
   */
  Cell getCellAt(int x, int y);

  /**
   * Returns the Cell at the specified coordinate.
   *
   * @param c coordinate.
   * @return the cell at the given position, or <code>null</code> if no cell is there
   */
  Cell getCellAt(Coord c);

  String getCell(int x, int y);

  String getCellContent(int x, int y);

  /**
   * set the cell at the given coordinates and fills in its raw contents.
   *
   * @param x    the column of the new cell (1-indexed)
   * @param y    the row of the new cell (1-indexed)
   * @param data the raw contents of the new cell: may be {@code null}, or any string. Strings
   *             beginning with an {@code =} character should be treated as formulas; all other
   *             strings should be treated as number or boolean values if possible, and string
   *             values otherwise.
   * @throws IllegalArgumentException if data is invalid.
   */
  void setCell(int x, int y, String data) throws IllegalArgumentException;


  /**
   * remove the cell at the given coordinates.
   *
   * @param c coordinate.
   */
  void removeCell(Coord c);


  /**
   * Returns width of the spreadsheet.
   *
   * @return width.
   */
  Map<Coord, Cell> getGrid();


  String getName();

  void setName(String name);

  int getRowSize(int i);

  int getColSize(int i);

  void resizeCol(int i, int size);

  void resizeRow(int i, int size);

  Map<Integer, Integer> getAllRowSize();

  Map<Integer, Integer> getAllColSize();

  void moveCellTo(Coord from, Coord to);
}

