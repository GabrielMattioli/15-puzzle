package org.gkl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    public static int gridGroesse = 3; // Standart auf 3 setzen
    private static int quadratGroesse = 100;
    Button button;
    ArrayList<Button> buttons = new ArrayList<>();
    int index;
    private Button buttonLeer;
    private int aktuelleIndex;
    GridPane gridPane = new GridPane();
    String reihenfolgeRichtig;
    String reigenfolgePruefen;
    int zugeZaehler;

    @Override
    public void start(Stage primaryStage) {
        // Erstellung von Elementen
        Label titelSpiel = new Label("GKL Puzzle");
        titelSpiel.setTextFill(Color.RED);
        titelSpiel.setFont(new Font(22));
        // Erstellung von Panes
        BorderPane borderPane = new BorderPane();
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
        gridFuellen(gridGroesse);
        // Erstellung der Buttons
        // Buttons in GridPane einfügen
        // Puzzle mischen
        puzzleMischen();
        // Das Fenster muss immer 640x480 Groß sein
        Scene scene = new Scene(borderPane, 640, 480);
        scene.setOnKeyPressed(this::handleKeyPress);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Schiebe Puzzle");
        primaryStage.show();
        // Fokussiert auf das gridPane damit die Tastatureingaben funktionieren
        gridPane.requestFocus();
    }

    @SuppressWarnings("incomplete-switch")
    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                nummerTauschen(0, 1);
                benachrichtigen();
                break;
            case DOWN:
                nummerTauschen(0, -1);
                benachrichtigen();
                break;
            case LEFT:
                nummerTauschen(1, 0);
                benachrichtigen();
                break;
            case RIGHT:
                nummerTauschen(-1, 0);
                benachrichtigen();
                break;
            case ENTER:
                puzzleMischen();
                gridPane.getChildren().clear();
                buttonsEinfuegen();
                gridPane.requestFocus();
                break;
            case DIGIT3:
                gridFuellen(3);
                puzzleMischen();
                break;
            case DIGIT4:
                gridFuellen(4);
                puzzleMischen();
                break;
            case DIGIT5:
                gridFuellen(5);
                puzzleMischen();
                break;
            case DIGIT6:
                gridFuellen(6);
                puzzleMischen();
                break;
            case DIGIT7:
                gridFuellen(7);
                puzzleMischen();
                break;
            case DIGIT8:
                gridFuellen(8);
                puzzleMischen();
                break;
            case ESCAPE:
                Platform.exit();
                break;
        }
    }

    // Erstellt Buttons und fügt sie in die ArrayList "buttons" ein
    private void buttonsErstellen() {
        buttons.clear();
        for (int i = 0; i < gridGroesse * gridGroesse; i++) {
            Button button = new Button(Integer.toString(i + 1));
            // button soll so groß sein
            button.setPrefWidth(quadratGroesse);
            button.setPrefHeight(quadratGroesse);
            // Zahl des Buttons soll 20px sein
            button.setStyle("-fx-font-size: 20px;");
            button.setOnAction(e -> buttonBewegen(button));
            buttons.add(button);
        }
    }

    // Buttons in das GridPane einfügen
    private void buttonsEinfuegen() {
        for (int reiheAkt = 0; reiheAkt < gridGroesse; reiheAkt++) {
            for (int spalteAkt = 0; spalteAkt < gridGroesse; spalteAkt++) {
                index = reiheAkt * gridGroesse + spalteAkt;
                gridPane.add(buttons.get(index), reiheAkt, spalteAkt);
            }
        }
    }

    // Der angeklickte Button wird mit dem leeren Button getauscht
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

    // Ändert die Position des leeren Buttons
    private void nummerTauschen(int xSpalte, int xReihe) {
        // Neue Indes des leeren Button finden
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).getText().isEmpty()) {
                aktuelleIndex = i;
                break;
            }
        }
        int aktuelleReihe = aktuelleIndex / gridGroesse;
        int aktuelleSpalte = aktuelleIndex % gridGroesse;
        int neueReihe = aktuelleReihe + xReihe;
        int neueSpalte = aktuelleSpalte + xSpalte;
        // prüft ob die neue Position innerhalb des Grids liegt
        if ((neueReihe >= 0) && (neueReihe < gridGroesse) && (neueSpalte >= 0) && (neueSpalte < gridGroesse)) {
            int neuerIndex = neueReihe * gridGroesse + neueSpalte;
            // Tauscht die Zahlen
            Button ausgewaehlterButton = buttons.get(aktuelleIndex);
            Button zielButton = buttons.get(neuerIndex);
            String tempText = ausgewaehlterButton.getText();
            ausgewaehlterButton.setText(zielButton.getText());
            zielButton.setText(tempText);
            // Addiert 1 zu der Zähler
            zugeZaehler++;

            // Aktualisiert die Position des leeren Buttons
            aktuelleIndex = neuerIndex;
        }
    }

    public void puzzleMischen() {
        ArrayList<Integer> letzteRichtungen = new ArrayList<>();
        letzteRichtungen.clear();
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
        zugeZaehler = 0;
    }

    // Ändert die Größe des Grids
    private void gridFuellen(int groesse) {
        gridPane.getChildren().clear();
        gridGroesse = groesse;
        buttonsErstellen();
        buttonsEinfuegen();
        buttonLeer = buttons.get(0);
        buttonLeer.setText("");
        // Speichert die Zahlen der ButtonArayList für die Gewinnprüfung
        reihenfolgeRichtig = arraySpeichern(buttons);
    }

    // Prüft ob die Zahlen geordnet sind
    private boolean gewinnPruefen() {
        reigenfolgePruefen = arraySpeichern(buttons);
        // Speichert die Zahlen er ButtonArayList
        return reihenfolgeRichtig.equals(reigenfolgePruefen);
    }

    // Speichert die ArrayLists
    private String arraySpeichern(ArrayList<Button> buttonListe) {
        StringBuilder string = new StringBuilder();
        for (Button button : buttonListe) {
            string.append(button.getText()).append(", ");
        }
        if (string.length() > 0) {
            string.setLength(string.length() - 2);
        }
        return string.toString();
    }

    // Schickt eine PopUp wenn das Spiel gewonnen ist
    private void benachrichtigen() {
        if (gewinnPruefen()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Geschafft!");
            alert.setHeaderText("Züge: " + zugeZaehler + ", Zeit: " + letzteZeit);
            alert.setContentText("Sie haben das Puzzle geschafft!");
            alert.showAndWait();
        }
    }
}
