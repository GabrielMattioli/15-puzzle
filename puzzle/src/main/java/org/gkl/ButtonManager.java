package org.gkl;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import java.util.ArrayList;

public class ButtonManager {
    private GridPane gridPane;
    private Puzzle puzzle;
    private ArrayList<Button> buttons;
    private int gridGroesse;

    public ButtonManager(GridPane gridPane, Puzzle puzzle) {
        this.gridPane = gridPane;
        this.puzzle = puzzle;
        this.gridGroesse = puzzle.getGridGroesse();
        this.buttons = puzzle.getButtons();
        puzzleStarten();
    }

    private void puzzleStarten() {
        buttonsErstellen();
        buttonsEinfuegen();
    }

    // Erstellt Buttons und fügt sie in die ArrayList "buttons" ein
    public void buttonsErstellen() {
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

    public void buttonsEinfuegen() {
        for (int reiheAkt = 0; reiheAkt < gridGroesse; reiheAkt++) {
            for (int spalteAkt = 0; spalteAkt < gridGroesse; spalteAkt++) {
                int index = reiheAkt * gridGroesse + spalteAkt;
                Button button = buttons.get(index);
                button.setFont(new Font("Elephant", 28));
                button.setPrefWidth(Main.getQUADRAT_GROESSE());
                button.setPrefHeight(Main.getQUADRAT_GROESSE());
                button.setStyle("-fx-background-color: #555555; -fx-text-fill: #00AAAA;");
                gridPane.add(button, spalteAkt, reiheAkt);
            }
        }
    }

    public void aktualisiereButtons() {
        gridPane.getChildren().clear();
        buttonsEinfuegen();
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
