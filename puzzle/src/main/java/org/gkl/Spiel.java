package org.gkl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Spiel extends Application {

    private static final int gridGroesse = 5;
    private static final int quadratGroesse = 100;

    // verschachtelte Array für X und Y werte
    private Rectangle[][] quadraten = new Rectangle[gridGroesse][gridGroesse];

    private int reiheAkt = 0;
    private int spalteAkt = 0;

    @Override
    public void start(Stage primaryStage) {
        // gridpane erstellen
        GridPane gridPane = new GridPane();

        Scene scene = new Scene(gridPane, gridGroesse * quadratGroesse, gridGroesse * quadratGroesse);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Schiebepuzzle");
        primaryStage.show();

        // Würfeln initialisieren
        for (int reihe = 0; reihe < gridGroesse; reihe++) {
            for (int spalte = 0; spalte < gridGroesse; spalte++) {
                Rectangle quadrat = new Rectangle(quadratGroesse, quadratGroesse);
                // if reihe == reiheAkt und spalte == spalteAkt, dann Farbe rot, sonst blau
                quadrat.setFill(reihe == reiheAkt && spalte == spalteAkt ? Color.RED : Color.BLUE);
                quadrat.setStroke(Color.BLACK);
                quadraten[reihe][spalte] = quadrat;
                gridPane.add(quadrat, reihe, spalte);
            }
        }
        // ohne das, Funktionieren die Tastatuureingaben nicht
        gridPane.requestFocus();

        // Input von Tastatur
        scene.setOnKeyPressed(event -> {

            switch (event.getCode()) {
                case UP:
                    quadratBewegen(0, -1);
                    break;
                case DOWN:
                    quadratBewegen(0, 1);
                    break;
                case LEFT:
                    quadratBewegen(-1, 0);
                    break;
                case RIGHT:
                    quadratBewegen(1, 0);
                    break;
                case ESCAPE:
                    Platform.exit();
                    break;
            }

        });
    }

    public void quadratBewegen(int rowChange, int colChange) {
        // holt Farbe der aktuelle Stelle
        Color farbeAktuell = (Color) quadraten[reiheAkt][spalteAkt].getFill();
        // position aktualisieren
        reiheAkt += rowChange;
        spalteAkt += colChange;

        // nächste Stelle darf nicht außerhalb des gridPanes sein
        if (!(reiheAkt < 0 | spalteAkt < 0 | reiheAkt > gridGroesse - 1 | spalteAkt > gridGroesse - 1)) {
            // holt die Farbe der nächste Stelle
            Color farbeNext = (Color) quadraten[reiheAkt][spalteAkt].getFill();
            // Farbe wechseln
            quadraten[reiheAkt][spalteAkt].setFill(farbeAktuell);
            quadraten[reiheAkt - rowChange][spalteAkt - colChange].setFill(farbeNext);
        } else {
            // position zurücksetzen
            reiheAkt -= rowChange;
            spalteAkt -= colChange;
        }
    }
}
