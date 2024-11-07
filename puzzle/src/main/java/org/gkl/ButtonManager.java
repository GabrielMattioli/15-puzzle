package org.gkl;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class ButtonManager {
    private GridPane gridPane;
    private Puzzle puzzle;
    private ArrayList<Button> buttons;
    private Main main;

    public ButtonManager(GridPane gridPane, Puzzle puzzle, Main main) {
        this.gridPane = gridPane;
        this.puzzle = puzzle;
        this.main = main;
        this.buttons = puzzle.getButtons();
        buttonsErstellen(puzzle.getGridGroesse());
        buttonsEinfuegen(puzzle.getGridGroesse());
    }

    // Erstellt Buttons und fügt sie in die ArrayList "buttons" ein
    public void buttonsErstellen(int gridgroesse) {
        buttons.clear();
        for (int i = 0; i < gridgroesse * gridgroesse; i++) {
            Button button = new Button(Integer.toString(i + 1));
            button.setOnAction(e -> {
                puzzle.buttonBewegen(button);
                main.benachrichtigen();
            }); // Event listener beim Klicken
            buttons.add(button);
        }
    }

    // Buttons in das GridPane einfügen
    public void buttonsEinfuegen(int gridgroesse) {
        for (int reiheAkt = 0; reiheAkt < gridgroesse; reiheAkt++) {
            for (int spalteAkt = 0; spalteAkt < gridgroesse; spalteAkt++) {
                int index = reiheAkt * gridgroesse + spalteAkt;
                buttons = puzzle.getButtons();
                // Eigenschaften der Buttons
                buttons.get(index).setFont(new Font("Elephant", 27));
                buttons.get(index).setPrefWidth(Main.getQUADRAT_GROESSE());
                buttons.get(index).setPrefHeight((Main.getQUADRAT_GROESSE()));
                buttons.get(index).setStyle("-fx-background-color: #555555; -fx-text-fill: #00AAAA;");
                gridPane.add(buttons.get(index),spalteAkt,reiheAkt);
            }
        }
    }

    // Speichert die ArrayLists
    public String arraySpeichern() {
        StringBuilder string = new StringBuilder();
        for (Button button : buttons) {
            string.append(button.getText()).append(", ");
        }
        if (string.length() > 0) {
            string.setLength(string.length() - 2);
        }
        return string.toString();
    }
}
