package edu.cs3500.spreadsheets.view;

import java.awt.Dimension;

import javax.swing.JFrame;


import edu.cs3500.spreadsheets.model.ViewOnlyModel;

/**
 * a graohicview with saveload feature.
 * contains a menuBar that allow users to save and load a spreadsheet.
 */
public class SpreadsheetSaveLoadGraphicView extends AbstractSpreadsheetView  {


  /**
   * Constructor for SpreadsheetSaveLoadGraphicView.
   * @param model view only model.
   */
  public SpreadsheetSaveLoadGraphicView(ViewOnlyModel model) {
    super(new SaveLoadPanel(new EditablePanel(new ScrollableGridPanel(new GridPanel(model)))));

    this.setTitle(model.getName());
    this.setSize(1500, 750);
    this.setMinimumSize(new Dimension(1000, 500));
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    this.add(panel);
    this.pack();
    resetFocus();
  }



}
