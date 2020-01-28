package edu.cs3500.spreadsheets.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTextField;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.view.listener.ButtonListener;

/**
 * an editable panel.
 */
public class EditablePanel extends PanelDecorator {

  private JButton confirmButton;
  private JButton rejectButton;
  private JTextField textField;

  private Coord selectedCoord;

  /**
   * Constructor for EditablePanel.
   * @param panel a SpreadsheetPanel.
   */
  public EditablePanel(SpreadsheetPanel panel) {
    super(panel);


    JToolBar toolBar = new JToolBar();

    this.setLayout(new BorderLayout());
    this.add(toolBar, BorderLayout.NORTH);
    this.add(panel, BorderLayout.CENTER);

    confirmButton = new JButton("✓");

    confirmButton.setFont(new Font("Font", Font.BOLD, 16));
    confirmButton.setActionCommand("Confirm Button");
    toolBar.add(confirmButton);

    rejectButton = new JButton("×");
    rejectButton.setFont(new Font("Font", Font.BOLD, 16));
    rejectButton.setActionCommand("Reject Button");
    toolBar.add(rejectButton);


    textField = new JTextField(100);
    toolBar.add(textField);

    //default selected Coord
    setSelectedCoord(new Coord(1, 1));

  }

  @Override
  public void addFeatures(Features features) {
    Map<String, Runnable> buttonClickedMap = new HashMap<String, Runnable>();
    ButtonListener buttonListener = new ButtonListener();
    buttonClickedMap.put("Confirm Button", () -> {
      features.confirm();
    });
    buttonClickedMap.put("Reject Button", () -> {
      features.reject();

    });
    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    this.addActionListener(buttonListener);


    panel.addFeatures(features);

  }

  public void addActionListener(ActionListener actionListener) {
    confirmButton.addActionListener(actionListener);
    rejectButton.addActionListener(actionListener);
  }

  @Override
  public String getInputString() {
    return textField.getText();
  }

  @Override
  public void setInputString(String s) {
    textField.setText(s);
  }

  @Override
  public Coord getSelectCoord() {
    return selectedCoord;
  }

  @Override
  public void setSelectedCoord(Coord c) {
    selectedCoord = new Coord(c.col, c.row);
    panel.setSelectedCoord(selectedCoord);
  }


}
