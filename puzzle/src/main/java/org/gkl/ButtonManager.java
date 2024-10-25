package org.gkl;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class ButtonManager {
    private GridPane gridPane;
    private Puzzle puzzle;
    private ArrayList<Button> buttons;
    private int gridGroesse;
    private Button buttonLeer;

    public ButtonManager(GridPane gridPane, Puzzle puzzle) {
        this.gridPane = gridPane;
        this.puzzle = puzzle;
        this.gridGroesse = puzzle.getGridGroesse();
        this.buttons = puzzle.getButtons();
        puzzleStarten();
        this.buttonLeer = buttons.get(buttons.size() - 1);
    }

    private void puzzleStarten() {
        buttonsErstellen(gridGroesse);
        buttonsEinfuegen(gridGroesse);
    }

    // Erstellt Buttons und fügt sie in die ArrayList "buttons" ein
    public void buttonsErstellen(int gridGroesse) {
        buttons.clear();
        for (int i = 0; i < gridGroesse * gridGroesse; i++) {
            Button button = new Button(Integer.toString(i + 1));
            button.setOnAction(e -> {
                puzzle.buttonBewegen(button);
                puzzle.gewinnPruefen();
            }); // Event listener beim Klicken
            buttons.add(button);
        }
    }

    // Buttons in das GridPane einfügen
    public void buttonsEinfuegen(int gridGroesse) {
        for (int reiheAkt = 0; reiheAkt < gridGroesse; reiheAkt++) {
            for (int spalteAkt = 0; spalteAkt < gridGroesse; spalteAkt++) {
                int index = reiheAkt * gridGroesse + spalteAkt;
                buttons = puzzle.getButtons();
                // Eigenschaften der Buttons
                buttons.get(index).setFont(new Font("Elephant", 28));
                buttons.get(index).setPrefWidth(Main.getQUADRAT_GROESSE());
                buttons.get(index).setPrefHeight((Main.getQUADRAT_GROESSE()));
                buttons.get(index).setStyle("-fx-background-color: #555555; -fx-text-fill: #00AAAA;");
                gridPane.add(buttons.get(index),spalteAkt,reiheAkt);
            }
        }
    }

    public void aktualisiereButtons() {
        gridPane.getChildren().clear();
        buttonsEinfuegen(gridGroesse);
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
