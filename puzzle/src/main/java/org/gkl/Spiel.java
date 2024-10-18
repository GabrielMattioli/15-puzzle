package org.gkl;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Random;

public class Spiel extends Application {
    @Override
    public void start(Stage primaryStage) {
        int reiheAnz = 10;
        int spalteAnz = 10;
        int gridHoehe = 300;
        int gridBreite = 300;

        GridPane grid = new GridPane();
        Random rand = new Random();

        for (int reihe = 0; reihe < reiheAnz; reihe++) {
            for (int spalte = 0; spalte < spalteAnz; spalte++) {
                Rectangle rec = new Rectangle();
                rec.setWidth(23);
                rec.setHeight(23);
                GridPane.setRowIndex(rec, reihe);
                GridPane.setColumnIndex(rec, spalte);
                grid.getChildren().add(rec);
            }
        }

        Scene scene = new Scene(grid, gridHoehe, gridBreite);
        primaryStage.setTitle("Schiebepuzzle");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
