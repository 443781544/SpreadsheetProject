package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.ViewOnlyModel;

/**
 *  The class defines a public enum ViewType with three possible values:
 *  Editable, ViewOnly and SaveLoad.
 *  It offers a static factory method create(ViewType type) that returns
 *  an instance of (an appropriate subclass of) GraphicView,
 *  depending on the value of the parameter.
 */
public class SpreadsheetViewCreator {
  /**
   * GameType with three possible values:
   * BASIC, RELAXED and TRIPEAKS.
   * Representing PyramidSolitaireModels.
   */
  public enum ViewType {
    Editable, ViewOnly, SaveLoad;
  }

  /**
   * returns an instance of (an appropriate subclass of) PyramidSolitaireModel.
   * @param type enum GameType
   * @return
   */
  public static SpreadsheetView create(ViewOnlyModel model, ViewType type) {


    switch (type) {
      case Editable:
        return new SpreadsheetEditableGraphicView(model);
      case ViewOnly:
        return new SpreadsheetGraphicView(model);
      case SaveLoad:
        return new SpreadsheetSaveLoadGraphicView(model);
      default:
        return null;
    }


  }
}

