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
            button.setOnAction(e -> { // Event listener beim Klicken
                puzzle.buttonBewegen(button);
                main.benachrichtigen();
            });
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

                // Setzt die Hintergrundfarbe des letzten Buttons anders
                String hintergrundFarbe = (index == gridgroesse * gridgroesse - 1) ? "#222222" : "#555555";

                String buttonStyle = "-fx-background-color: " + hintergrundFarbe + "; " + // Hintergrundfarbe
                        "-fx-text-fill: #00AAAA; "; // Cyan

                // Wenn es der letzte Button ist, keine Rahmen
                if (index == gridgroesse * gridgroesse - 1) {
                    buttonStyle += ""; // Keine Rahmen
                } else {
                    buttonStyle += "-fx-border-color: #888888 #444444 #222222 #666666; " + // Hellgrau, Mittelgrau,
                                                                                           // Dunkelgrau,
                    // Sehr Dunkelgrau
                            "-fx-border-width: 2px; " + // Breite des Rahmens
                            "-fx-border-style: solid; "; // Stil des Rahmens
                }

                buttonStyle += "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.6), 5, 0.5, 2, 2);"; // Effekt für "3d
                // Rahmen"
                buttons.get(index).setStyle(buttonStyle);
                gridPane.add(buttons.get(index), spalteAkt, reiheAkt);
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
