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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Spiel extends Application {

    public static final int gridGroesse = 3;
    private static final int quadratGroesse = 100;
    Button button;
    List<Button> buttons = new ArrayList<>();
    int index;
    private Button buttonLeer;
    private int aktuelleIndex = 0;
    GridPane gridPane = new GridPane();

    @Override
    public void start(Stage primaryStage) {
        // Erstellung von Elementen
        Label titelSpiel = new Label("GKL Puzzle");
        titelSpiel.setTextFill(Color.RED);
        titelSpiel.setFont(new Font(22));

        // Erstellung von Panes
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
        Text tastenkombinationen = new Text(" [ENTER] = Mischen   [3]..[8]   [ESC] = Beenden");
        tastenkombinationen.setFont(new Font(20));
        tastenkombinationen.setFill(Color.RED);
        borderPane.setBottom(tastenkombinationen);
        BorderPane.setAlignment(tastenkombinationen, Pos.CENTER);

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
        buttonsEinfuegen();

        buttonLeer = buttons.get(0);
        buttonLeer.setText("");

        puzzleMischen();

        // Das Fenster muss 640x480 Groß sein
        Scene scene = new Scene(borderPane, 640, 480);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Schiebe Puzzle");
        primaryStage.show();

        // Fokussiertauf das gridPane damit die Tastatureingaben funktionieren
        gridPane.requestFocus();

    }

    @SuppressWarnings("incomplete-switch")
    private void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                nummerTauschen(0, -1);
                break;
            case DOWN:
                nummerTauschen(0, 1);
                break;
            case LEFT:
                nummerTauschen(-1, 0);
                break;
            case RIGHT:
                nummerTauschen(1, 0);
                break;
            case ENTER:
                puzzleMischen();
                gridPane.getChildren().clear();
                buttonsEinfuegen();
                gridPane.requestFocus();
                break;
            case ESCAPE:
                Platform.exit();
                break;
        }
    }

    // Buttons ins GridPane einfügen
    private void buttonsEinfuegen() {
        for (int reiheAkt = 0; reiheAkt < gridGroesse; reiheAkt++) {
            for (int spalteAkt = 0; spalteAkt < gridGroesse; spalteAkt++) {
                index = reiheAkt * gridGroesse + spalteAkt;
                gridPane.add(buttons.get(index), reiheAkt, spalteAkt);
            }
        }
    }

    private void buttonBewegen(Button button) {
        if (istButtonNeben(button, buttonLeer)) {
            String buttonZahl = button.getText();
            button.setText(buttonLeer.getText());
            buttonLeer.setText(buttonZahl);
            buttonLeer = button;
        }
    }

    private boolean istButtonNeben(Button buttonZahl, Button buttonLeer) {
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

    private void nummerTauschen(int dx, int dy) {
        int currentRow = aktuelleIndex / gridGroesse;
        int currentCol = aktuelleIndex % gridGroesse;

        int neueReihe = currentRow + dy;
        int neueSpalte = currentCol + dx;

        // prüft ob die neue Position innerhalb des Grids liegt
        if (neueReihe >= 0 && neueReihe < gridGroesse && neueSpalte >= 0 && neueSpalte < gridGroesse) {
            int neuerIndex = neueReihe * gridGroesse + neueSpalte;

            // Tauscht die Zahlen
            Button ausgewaehlterButton = buttons.get(aktuelleIndex);
            Button zielButton = buttons.get(neuerIndex);

            String tempText = ausgewaehlterButton.getText();
            ausgewaehlterButton.setText(zielButton.getText());
            zielButton.setText(tempText);

            // Aktualisiert die Position des leeren Buttons
            aktuelleIndex = neuerIndex;
        }
    }

    public void puzzleMischen() {
        ArrayList<Integer> letzteRichtungen = new ArrayList<>();

        for (int bewegungen = 0; bewegungen < 60; bewegungen++) {
            // 1=oben, 2=unten, 3=links, 4=rechts
            int randomRichtung = (int) (Math.random() * (4) + 1);

            letzteRichtungen.add(randomRichtung);
            switch (randomRichtung) {
                case 1:
                    nummerTauschen(0, 1);
                    break;
                case 2:
                    nummerTauschen(0, -1);
                    break;
                case 3:
                    nummerTauschen(1, 0);
                    break;
                case 4:
                    nummerTauschen(-1, 0);
                    break;
            }
        }
    }
}
