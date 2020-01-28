package edu.cs3500.spreadsheets.model.cell;


import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.cs3500.spreadsheets.model.cellvalue.CellValue;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * To represent a cell in the spreadsheet. An individual spreadsheet cell may: contain a value.
 * contain a formula.
 */
public interface Cell {

  /**
   * To get the value of every single cell.
   *
   * @return the cellvalue of each cell.
   */
  CellValue getValue(Map<Coord, Cell> grid, Map<Coord, CellValue> acc);

  boolean checkCyclicReference(List<Coord> l, Set<Coord> noCycles, Map<Coord, Cell> grid);


  String toString(Map<Coord, Cell> grid);

  /**
   * To get the raw content of every single cell.
   *
   * @return the a String of raw content of each cell.
   */
  String getRowContent();


  Cell moveTo(int col, int row);
}

