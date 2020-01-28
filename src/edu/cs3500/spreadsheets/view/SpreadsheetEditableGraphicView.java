package edu.cs3500.spreadsheets.view;

import java.awt.Dimension;

import javax.swing.JFrame;

import edu.cs3500.spreadsheets.model.ViewOnlyModel;

/**
 * An editable graphic view for spreadsheet. Contains a tool bar for user to edit the content of
 * cell.
 */
public class SpreadsheetEditableGraphicView extends AbstractSpreadsheetView {


  /**
   * constructor for SpreadsheetEditableGraphicView.
   *
   * @param model a viewonly model.
   */
  public SpreadsheetEditableGraphicView(ViewOnlyModel model) {
    super(new EditablePanel(new ScrollableGridPanel(new GridPanel(model))));
    this.setTitle(model.getName());
    this.setSize(1500, 750);
    this.setMinimumSize(new Dimension(1000, 500));
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    this.add(panel);

    this.pack();

    resetFocus();

  }





}
