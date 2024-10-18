package org.gkl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Spiel extends Application {

    private static final int gridGroesse = 3;
    private static final int quadratGroesse = 100;
    Button button;
    List<Button> buttons = new ArrayList<>();
    int index;
    private Button buttonLeer;

    @SuppressWarnings("incomplete-switch")
    @Override
    public void start(Stage primaryStage) {

        // Erstellung von Elemente
        Label titelSpiel = new Label("GKL Puzzle");
        titelSpiel.setTextFill(Color.RED);

        // Erstellung von Panes
        GridPane gridPane = new GridPane();
        BorderPane borderPane = new BorderPane();

        // Bereiche der borderPane organisieren
        // top
        borderPane.setTop(titelSpiel);
        borderPane.setAlignment(titelSpiel, Pos.CENTER);
        // center
        borderPane.setCenter(gridPane);
        gridPane.setAlignment(Pos.CENTER);
        // bottom
        BorderPane.setAlignment(gridPane, Pos.CENTER);
        borderPane.setBottom(new Label("Tastenkombinationen"));

        // Erstellung der Buttons
        for (int i = 0; i < gridGroesse * gridGroesse; i++) {
            Button button = new Button(Integer.toString(i + 1));
            // Button selbst soll so groß sein
            button.setPrefWidth(quadratGroesse);
            button.setPrefHeight(quadratGroesse);
            // Zahl des Buttons soll so groß sein
            button.setStyle("-fx-font-size: 20px;");

            button.setOnAction(e -> buttonBewegen(button));// Eventlistener beim Klicken
            buttons.add(button);
        }

        // Buttons random organisieren
        Collections.shuffle(buttons);

        // Buttons ins gridPane einfügen
        for (int reiheAkt = 0; reiheAkt < gridGroesse; reiheAkt++) {
            for (int spalteAkt = 0; spalteAkt < gridGroesse; spalteAkt++) {
                index = reiheAkt * gridGroesse + spalteAkt;
                gridPane.add(buttons.get(index), reiheAkt, spalteAkt);
            }
        }

        buttonLeer = buttons.get(0);
        buttonLeer.setText("");

        gridPane.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    buttonBewegen(button);
                    break;
                case DOWN:
                    buttonBewegen(button);
                    break;
                case LEFT:
                    buttonBewegen(button);
                    break;
                case RIGHT:
                    buttonBewegen(button);
                    break;
                case ESCAPE:
                    Platform.exit();
                    break;
            }

        });

        // Das Fenster muss 640x480 Groß sein
        Scene scene = new Scene(borderPane, 640, 480);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Schiebepuzzle");
        primaryStage.show();
    }

    private void buttonBewegen(Button button) {
        if (buttonNeben(button, buttonLeer)) {
            String buttonZahl = button.getText();
            button.setText(buttonLeer.getText());
            buttonLeer.setText(buttonZahl);
            buttonLeer = button;
        }
    }

    private boolean buttonNeben(Button buttonZahl, Button buttonLeer) {
        int indexZahl = buttonZahl.getParent().getChildrenUnmodifiable().indexOf(buttonZahl);
        int indexLeer = buttonLeer.getParent().getChildrenUnmodifiable().indexOf(buttonLeer);
        int reiheIndexZahl = indexZahl / gridGroesse;
        int spalteIndexZahl = indexZahl % gridGroesse;
        int reiheIndexLeer = indexLeer / gridGroesse;
        int spalteIndexLeer = indexLeer % gridGroesse;
        boolean selbeReihe = reiheIndexZahl == reiheIndexLeer && Math.abs(indexZahl - indexLeer) == 1;
        boolean selbeSpalte = spalteIndexZahl == spalteIndexLeer && Math.abs(indexZahl - indexLeer) == 1;
        // Liefert true, wenn die Zahlen neben einander und in die selbe Spalte sind
        return selbeReihe || selbeSpalte || Math.abs(indexZahl - indexLeer) == gridGroesse;
    }
}
