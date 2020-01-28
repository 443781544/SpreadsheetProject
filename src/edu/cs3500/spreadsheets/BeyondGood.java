package edu.cs3500.spreadsheets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

import edu.cs3500.spreadsheets.controller.Controller;
import edu.cs3500.spreadsheets.controller.IController;
import edu.cs3500.spreadsheets.model.workbook.CrossReferenceSpreadsheetModel;
import edu.cs3500.spreadsheets.model.workbook.Workbook;
import edu.cs3500.spreadsheets.model.workbook.WorkbookController;
import edu.cs3500.spreadsheets.model.workbook.WorkbookModel;
import edu.cs3500.spreadsheets.model.workbook.WorkbookView;
import edu.cs3500.spreadsheets.view.SpreadsheetViewCreator;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SimpleSpreadSheetBuilder;
import edu.cs3500.spreadsheets.model.SimpleSpreadsheetModel;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.ViewOnlyModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.view.SpreadsheetView;
import edu.cs3500.spreadsheets.view.SpreadsheetGraphicView;
import edu.cs3500.spreadsheets.view.SpreadsheetTextualView;

/**
 * The main class for our program.
 */
public class BeyondGood {
  /**
   * The main entry point.
   *
   * @param args any command-line arguments
   */
  public static void main(String[] args) {
    try {

      if (args.length > 1) {
        File file = new File(args[1]);
        BufferedReader br = new BufferedReader(new FileReader(file));

        WorksheetReader.WorksheetBuilder<SpreadsheetModel> builder =
                new SimpleSpreadSheetBuilder(args[1]);
        SpreadsheetModel model = WorksheetReader.read(builder, br);

        if (args.length == 4 && args[0].equals("-in") && args[2].equals("-eval")) {

          String[] part = args[3].split("(?<=\\D)(?=\\d)");

          int x = Coord.colNameToIndex(part[0]);
          int y = Integer.parseInt(part[1]);

          System.out.print(model.getCell(x, y));

        } else if (args.length == 4 && args[0].equals("-in") && args[2].equals("-save")) {

          ViewOnlyModel vm = new ViewOnlyModel(model);


          File newFile = new File(args[3]);
          Appendable app = new PrintWriter(newFile);
          SpreadsheetView view = new SpreadsheetTextualView(vm, app);
          view.render();
          ((PrintWriter) app).close();
        } else if (args.length == 3 && args[0].equals("-in") && args[2].equals("-gui")) {

          ViewOnlyModel vm = new ViewOnlyModel(model);

          SpreadsheetView view = new SpreadsheetGraphicView(vm);
          view.render();

        } else if (args.length == 3 && args[0].equals("-in") && args[2].equals("-edit")) {



          IController controller =
                  new Controller(model, SpreadsheetViewCreator.ViewType.SaveLoad);

          controller.run();


        } else {
          System.out.println("invalid args");
        }
      } else if (args.length == 1) {
        SpreadsheetModel model = new SimpleSpreadsheetModel();
        ViewOnlyModel vm = new ViewOnlyModel(model);


        if (args[0].equals("-gui")) {
          SpreadsheetView view = new SpreadsheetGraphicView(vm);
          view.render();
        } else if (args[0].equals("-edit")) {

          IController controller =
                  new Controller(model,
                          SpreadsheetViewCreator.ViewType.SaveLoad);

          controller.run();

        } else if (args[0].equals("-workbook")) {
          Workbook wb = new WorkbookModel();
          wb.addWorksheet(new CrossReferenceSpreadsheetModel(wb));
          wb.addWorksheet(new CrossReferenceSpreadsheetModel(wb));

          WorkbookView view = new WorkbookView(wb);

          WorkbookController c = new WorkbookController(wb, view);

          c.run();
        }


      } else {
        System.out.println("invalid args");
      }


    } catch (FileNotFoundException e) {
      System.out.println("File not Found");
    } catch (Exception e) {
      e.printStackTrace();
    }


  }


}
/////////