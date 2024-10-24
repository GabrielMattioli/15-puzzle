package org.gkl;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Spiel extends Application {
    public static int gridGroesse = 4; // Standart auf 3 setzen
    private static final int quadratGroesse = 100;
    ArrayList<Button> buttons = new ArrayList<>();
    int index;
    private Button buttonLeer;
    private int aktuelleIndex;
    GridPane gridPane = new GridPane();
    String reihenfolgeRichtig;
    String reigenfolgePruefen;
    int zuegeZaehler;
    int sekunden = 0;
    private Timeline timeline;
    BorderPane borderPane = new BorderPane();
    Scene scene = new Scene(borderPane, 640, 480);

    @Override
    public void start(Stage primaryStage) {
        // Erstellung von Elementen
        Label titelSpiel = new Label("GKL Puzzle");
        titelSpiel.setTextFill(Color.web("#AA0000"));
        titelSpiel.setFont(new Font("Elephant", 22));
        // Erstellung der Zeit Elementen
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            sekunden++;
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        // topPane
        borderPane.setTop(titelSpiel);
        BorderPane.setAlignment(titelSpiel, Pos.CENTER);
        // centerPane
        borderPane.setCenter(gridPane);
        gridPane.setAlignment(Pos.CENTER);
        // bottomPane
        BorderPane.setAlignment(gridPane, Pos.CENTER);
        Label tastenkombinationen = new Label(" [ENTER] = Mischen   [3]..[8]   [ESC] = Beenden");
        tastenkombinationen.setFont(new Font("Elephant", 20));
        tastenkombinationen.setTextFill(Color.web("#AA0000"));
        // Rectangle für die Hintergrundfarbe
        Rectangle hintergrundBottomPane = new Rectangle();
        hintergrundBottomPane.setFill(Color.web("#AAAAAA"));
        hintergrundBottomPane.widthProperty().bind(borderPane.widthProperty());
        hintergrundBottomPane.heightProperty().bind(tastenkombinationen.heightProperty());

        StackPane bottomPane = new StackPane();
        bottomPane.getChildren().addAll(hintergrundBottomPane, tastenkombinationen);

        borderPane.setBottom(bottomPane);

        BorderPane.setAlignment(tastenkombinationen, Pos.CENTER);
        gridFuellen(gridGroesse);
        // Setzt die Hintergrundfarbe Schwarz
        borderPane.setStyle("-fx-background-color: #000000;");
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
            case UP -> {
                nummerTauschen(0, 1);
                benachrichtigen();
            }
            case DOWN -> {
                nummerTauschen(0, -1);
                benachrichtigen();
            }
            case LEFT -> {
                nummerTauschen(1, 0);
                benachrichtigen();
            }
            case RIGHT -> {
                nummerTauschen(-1, 0);
                benachrichtigen();
            }
            case ENTER -> {
                puzzleMischen();
                gridPane.getChildren().clear();
                buttonsEinfuegen();
                gridPane.requestFocus();
            }
            case DIGIT3 -> {
                gridFuellen(3);
                puzzleMischen();
            }
            case DIGIT4 -> {
                gridFuellen(4);
                puzzleMischen();
            }
            case DIGIT5 -> {
                gridFuellen(5);
                puzzleMischen();
            }
            case DIGIT6 -> {
                gridFuellen(6);
                puzzleMischen();
            }
            case DIGIT7 -> {
                gridFuellen(7);
                puzzleMischen();
            }
            case DIGIT8 -> {
                gridFuellen(8);
                puzzleMischen();
            }
            case ESCAPE -> Platform.exit();
        }
    }

    // Erstellt Buttons und fügt sie in die ArrayList "buttons" ein
    private void buttonsErstellen() {
        buttons.clear();
        for (int i = 0; i < gridGroesse * gridGroesse; i++) {
            Button button = new Button(Integer.toString(i + 1));
            button.setOnAction(e -> {
                buttonBewegen(button);
                gewinnPruefen();
            }); // Event listener beim Klicken
            buttons.add(button);
        }
    }

    // Buttons in das GridPane einfügen
    private void buttonsEinfuegen() {
        for (int reiheAkt = 0; reiheAkt < gridGroesse; reiheAkt++) {
            for (int spalteAkt = 0; spalteAkt < gridGroesse; spalteAkt++) {
                index = reiheAkt * gridGroesse + spalteAkt;
                // Eigenschaften der Buttons
                buttons.get(index).setFont(new Font("Elephant", 28));
                buttons.get(index).setPrefWidth(quadratGroesse);
                buttons.get(index).setPrefHeight(quadratGroesse);
                buttons.get(index).setStyle("-fx-background-color: #555555; -fx-text-fill: #00AAAA;");
                gridPane.add(buttons.get(index), spalteAkt, reiheAkt);
            }
        }
    }

    // Der angeklickte Button wird mit dem leeren Button getauscht
    private void buttonBewegen(Button button) {
        // leere Button finden
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).getText().isEmpty()) {
                buttonLeer = buttons.get(i);
                break;
            }
        }
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
            zuegeZaehler++;

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
                case 1 -> nummerTauschen(0, 1);
                case 2 -> nummerTauschen(0, -1);
                case 3 -> nummerTauschen(1, 0);
                case 4 -> nummerTauschen(-1, 0);
            }
        }
        zuegeZaehler = 0;
        startTimer();
    }

    // Ändert die Größe des Grids
    private void gridFuellen(int groesse) {
        gridPane.getChildren().clear();
        gridGroesse = groesse;
        buttonsErstellen();
        buttonsEinfuegen();
        buttonLeer = buttons.get(buttons.size() - 1);
        buttonLeer.setText("");
        // Speichert die Zahlen der ButtonArayList für die Gewinnprüfung
        reihenfolgeRichtig = arraySpeichern(buttons);
        puzzleMischen();
    }

    // Prüft ob die Zahlen geordnet sind
    private boolean gewinnPruefen() {
        // Speichert die Zahlen er ButtonArayList
        reigenfolgePruefen = arraySpeichern(buttons);
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
        scene.getRoot().requestFocus();
        if (gewinnPruefen()) {
            stopTimer();
            Alert gewonnen = new Alert(Alert.AlertType.INFORMATION);
            gewonnen.setTitle("Gewinnmeldung");
            if (sekunden > 60) {
                int minuten = sekunden / 60;
                int uebrigeSekunden = sekunden % 60;
                if (sekunden > 120) {
                    gewonnen.setHeaderText(
                            "Züge: " + zuegeZaehler + ", Zeit: " + minuten + " Minuten und " + uebrigeSekunden
                                    + " Sekunden");
                } else {
                    gewonnen.setHeaderText("Züge: " + zuegeZaehler + ", Zeit: " + minuten + " Minute und "
                            + uebrigeSekunden + " Sekunden");
                }
            } else {
                gewonnen.setHeaderText("Züge: " + zuegeZaehler + ", Zeit: " + sekunden + " Sekunden");
            }
            gewonnen.showAndWait();
        }
    }

    private void startTimer() {
        timeline.play();
    }

    private void stopTimer() {
        timeline.stop();
    }
}
