package org.gkl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Spiel extends Application {
    private static final int viereckAnz = 4;
    private static final int quadratGroesse = 100;

    @Override
    public void start(Stage primaryStage) {

        // gridpane erstllen und zentralisieren
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        // Beispiel Element für Bewegung
        Rectangle element = new Rectangle(50, 50);
        element.setFill(Color.BLUE);

        gridPane.add(element, 1, 1);

        // Gridpane in Scene einfügen
        Scene scene = new Scene(gridPane, viereckAnz * quadratGroesse, viereckAnz * quadratGroesse);

        // input von Pfeile annehmen
        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            // Anzahl Pixels für Bewegung
            double schritteAnz = 10;

            switch (keyCode) {
                case UP:
                    element.setTranslateY(element.getTranslateY() + schritteAnz * -1);
                    break;
                case LEFT:
                    element.setTranslateX(element.getTranslateX() + schritteAnz * -1);
                    break;
                case DOWN:
                    element.setTranslateY(element.getTranslateY() + schritteAnz);
                    break;
                case RIGHT:
                    element.setTranslateX(element.getTranslateX() + schritteAnz);
                    break;
                case ESCAPE:
                    Platform.exit();
                    break;
            }
        });
        primaryStage.setTitle("Schiebepuzzle");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
