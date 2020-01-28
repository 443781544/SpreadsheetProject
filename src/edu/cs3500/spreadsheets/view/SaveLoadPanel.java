package edu.cs3500.spreadsheets.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenuItem;
import javax.swing.JMenuBar;

import javax.swing.JMenu;
import javax.swing.JOptionPane;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.view.listener.ButtonListener;
import edu.cs3500.spreadsheets.view.listener.KeyboardListener;


/**
 * SaveLoadPanel.
 */
public class SaveLoadPanel extends PanelDecorator {
  private JMenuItem saveFile;
  private JMenuItem loadFile;

  private JMenuItem copy;
  private JMenuItem paste;

  private Coord c;

  /**
   * Constructor for SaveLoadPanel.
   *
   * @param panel a spreadsheet panel.
   */
  public SaveLoadPanel(SpreadsheetPanel panel) {
    super(panel);

    JMenuBar menuBar = new JMenuBar();

    JMenu menu1 = new JMenu("File");
    menu1.setFont(new Font("Font", Font.BOLD, 14));

    menuBar.add(menu1);

    saveFile = new JMenuItem("Save");

    loadFile = new JMenuItem("Load");

    menu1.add(saveFile);
    menu1.add(loadFile);

    JMenu menu2 = new JMenu("Edit");
    menu2.setFont(new Font("Font", Font.BOLD, 14));

    menuBar.add(menu2);
    copy = new JMenuItem("Copy");
    paste = new JMenuItem("Paste");

    menu2.add(copy);
    menu2.add(paste);

    this.setLayout(new BorderLayout());
    this.add(menuBar, BorderLayout.NORTH);
    this.add(panel, BorderLayout.CENTER);
  }

  @Override
  public void addFeatures(Features features) {
    panel.addFeatures(features);

    Map<String, Runnable> buttonClickedMap = new HashMap<String, Runnable>();
    ButtonListener buttonListener = new ButtonListener();
    buttonClickedMap.put("Save", () -> {
      features.saveFile();
    });

    buttonClickedMap.put("Load", () -> {
      String s = JOptionPane.showInputDialog("File name:");
      features.loadFile(s);
    });

    buttonClickedMap.put("Copy", new Runnable() {
      @Override
      public void run() {
        c = getSelectCoord();
      }
    });

    buttonClickedMap.put("Paste", new Runnable() {
      @Override
      public void run() {
        if (c != null && !c.equals(SaveLoadPanel.this.getSelectCoord())) {
          features.smartMoveCell(c, SaveLoadPanel.this.getSelectCoord());
          features.selectCell(SaveLoadPanel.this.getSelectCoord());
          repaint();
        }
      }
    });


    buttonListener.setButtonClickedActionMap(buttonClickedMap);

    saveFile.addActionListener(buttonListener);
    loadFile.addActionListener(buttonListener);

    copy.addActionListener(buttonListener);
    paste.addActionListener(buttonListener);

    Map<Character, Runnable> keyTypes = new HashMap<Character, Runnable>();
    Map<Integer, Runnable> keyPresses = new HashMap<Integer, Runnable>();
    Map<Integer, Runnable> keyReleases = new HashMap<Integer, Runnable>();

    keyPresses.put(KeyEvent.VK_UP, () -> {
      features.moveSelectCellUp();
    });
    keyPresses.put(KeyEvent.VK_DOWN, () -> {
      features.moveSelectCellDown();
    });
    keyPresses.put(KeyEvent.VK_LEFT, () -> {
      features.moveSelectCellLeft();
    });
    keyPresses.put(KeyEvent.VK_RIGHT, () -> {
      features.moveSelectCellRight();
    });
    keyPresses.put(KeyEvent.VK_DELETE, () -> {
      features.deleteCellContent();
    });
    keyPresses.put(KeyEvent.VK_BACK_SPACE, () -> {
      features.deleteCellContent();
    });


    KeyListener k = new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.isControlDown()) {
          if (e.getKeyCode() == 67) {
            c = SaveLoadPanel.this.getSelectCoord();
          }
          if (e.getKeyCode() == 86) {
            if (c != null && !c.equals(SaveLoadPanel.this.getSelectCoord())) {
              features.smartMoveCell(c, SaveLoadPanel.this.getSelectCoord());
              features.selectCell(SaveLoadPanel.this.getSelectCoord());
              repaint();
            }
          }
        }
      }
    };
    this.addKeyListener(k);


    KeyboardListener kbd = new KeyboardListener();
    kbd.setKeyTypedMap(keyTypes);
    kbd.setKeyPressedMap(keyPresses);
    kbd.setKeyReleasedMap(keyReleases);
    this.addKeyListener(kbd);
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }


}
