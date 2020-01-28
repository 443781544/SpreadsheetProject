package edu.cs3500.spreadsheets.view;


import javax.swing.JFrame;
import edu.cs3500.spreadsheets.model.ViewOnlyModel;

/**
 * To represent the visible spreadsheet.
 */
public class SpreadsheetGraphicView extends AbstractSpreadsheetView {


  /**
   * To construct SpreadsheetGraphicView
   * set the size and name of the visible canvas.
   * @param model SpreadsheetModel
   */
  public SpreadsheetGraphicView(ViewOnlyModel model) {
    super(new ScrollableGridPanel(new GridPanel(model)));
    this.setTitle(model.getName());
    this.setSize(1500, 750);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


    this.add(panel);


    this.pack();


  }
}
