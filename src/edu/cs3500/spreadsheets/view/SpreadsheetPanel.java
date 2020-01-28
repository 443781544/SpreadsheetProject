package edu.cs3500.spreadsheets.view;

import java.awt.event.ActionListener;

import javax.swing.JComponent;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * an abstract class for spreadsheet panel.
 */
public abstract class SpreadsheetPanel extends JComponent {

  public abstract void setScroll(Coord c);

  /**
   * To realize the function from a spreadsheet.
   *
   * @param features functions of the spreadsheet.
   */
  public abstract void addFeatures(Features features);


  /**
   * this is to force the view to have a method to set up actions for buttons. All the buttons must
   * be given this action listener. Thus our Swing-based implementation of this interface will
   * already have such a method.
   *
   * @param listener ActionListener
   */
  public abstract void addActionListener(ActionListener listener);


  /**
   * Get the string which user inputs.
   *
   * @return the string that user input.
   * @throws UnsupportedOperationException if the view is not editable.
   */
  public abstract String getInputString() throws UnsupportedOperationException;

  /**
   * mutate the exist string to the given string.
   *
   * @param s string that need to be set.
   * @throws UnsupportedOperationException if the view is not editable.
   */
  public abstract void setInputString(String s) throws UnsupportedOperationException;

  /**
   * To get the coordinate of the selected cell.
   *
   * @return the coordinate.
   * @throws UnsupportedOperationException if the view is not editable.
   */
  public abstract Coord getSelectCoord() throws UnsupportedOperationException;


  /**
   * To set the coordinate of the selected cell.
   *
   * @param c coordinate.
   * @throws UnsupportedOperationException if the view is not editable.
   */
  public abstract void setSelectedCoord(Coord c) throws UnsupportedOperationException;

  /**
   * Reset the focus on the appropriate part of the view that has the keyboard listener attached to
   * it, so that keyboard events will still flow through.
   */
  public abstract void resetFocus();

  /**
   * To display the view once again.
   */
  public abstract void redraw();
}
