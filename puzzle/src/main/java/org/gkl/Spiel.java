package org.gkl;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Spiel extends Application {
    private static final int viereckGroesse = 50;
    private static final int reiheAnz = 4;
    private static final int spaltenAnz = 4;

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(0);
        gridPane.setVgap(0);

        for (int reihe = 0; reihe < reiheAnz; reihe++) {
            for (int spalte = 0; spalte < spaltenAnz; spalte++) {
                Rectangle viereck = new Rectangle(viereckGroesse, viereckGroesse, Color.LIGHTGRAY);
                Text nummer = new Text(String.valueOf(reihe * spaltenAnz + spalte + 1));
                StackPane stackPane = new StackPane(viereck, nummer);
                gridPane.add(stackPane, spalte, reihe);
            }
        }

        Rectangle viereckLeer = new Rectangle(viereckGroesse, viereckGroesse, Color.WHITE);
        StackPane paneLeer = new StackPane(viereckLeer);
        gridPane.add(paneLeer, spaltenAnz - 1, reiheAnz - 1);

        Scene scene = new Scene(gridPane, spaltenAnz * viereckGroesse, reiheAnz * viereckGroesse);
        primaryStage.setTitle("Schiebepuzzle");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
