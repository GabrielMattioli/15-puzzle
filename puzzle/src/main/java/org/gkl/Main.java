package org.gkl;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

public class Main extends Application {
    public static int gridGroesse = 4; // Standardgröße des Grids
    private static final int QUADRAT_GROESSE = 100;
    private int sekunden;
    private Timeline timeline;
    private final BorderPane borderPane = new BorderPane();
    private final GridPane gridPane = new GridPane();
    private final Scene scene = new Scene(borderPane, 640, 480);
    private Puzzle puzzle;
    private ButtonManager buttonManager;

    public static int getQUADRAT_GROESSE() {
        return QUADRAT_GROESSE;
    }

    @Override
    public void start(Stage primaryStage) {
        setupGUI(primaryStage);
        setupSpiel();
    }

    private void setupGUI(Stage primaryStage) {
        Label titelSpiel = new Label("GKL Puzzle");
        titelSpiel.setTextFill(Color.web("#AA0000"));
        titelSpiel.setFont(new Font("Elephant", 22));

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> sekunden++));
        timeline.setCycleCount(Timeline.INDEFINITE);

        borderPane.setTop(titelSpiel);
        BorderPane.setAlignment(titelSpiel, Pos.CENTER);

        borderPane.setCenter(gridPane);
        gridPane.setAlignment(Pos.CENTER);

        Label tastenkombinationen = new Label(" [ENTER] = Mischen   [3]..[8]   [ESC] = Beenden");
        tastenkombinationen.setFont(new Font("Elephant", 20));
        tastenkombinationen.setTextFill(Color.web("#AA0000"));

        Rectangle hintergrundBottomPane = new Rectangle();
        hintergrundBottomPane.setFill(Color.web("#AAAAAA"));
        hintergrundBottomPane.widthProperty().bind(borderPane.widthProperty());
        hintergrundBottomPane.heightProperty().bind(tastenkombinationen.heightProperty());

        StackPane bottomPane = new StackPane();
        bottomPane.getChildren().addAll(hintergrundBottomPane, tastenkombinationen);
        borderPane.setBottom(bottomPane);
        BorderPane.setAlignment(tastenkombinationen, Pos.CENTER);

        borderPane.setStyle("-fx-background-color: #000000;");
        scene.setOnKeyPressed(this::handleKeyPress);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Schiebe Puzzle");
        primaryStage.show();
        gridPane.requestFocus();
    }

    private void setupSpiel() {
        puzzle = new Puzzle(gridGroesse); // buttons sind in Puzzle
        sekunden = puzzle.getSekunden();
        buttonManager = new ButtonManager(gridPane, puzzle);
        gridFuellen(gridGroesse);       
        

    }

    @SuppressWarnings("incomplete-switch")
    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP -> {
                puzzle.nummerTauschen(0, 1);
                buttonManager.aktualisiereButtons();
                benachrichtigen();
            }
            case DOWN -> {
                puzzle.nummerTauschen(0, -1);
                buttonManager.aktualisiereButtons();
                benachrichtigen();
            }
            case LEFT -> {
                puzzle.nummerTauschen(1, 0);
                buttonManager.aktualisiereButtons();
                benachrichtigen();
            }
            case RIGHT -> {
                puzzle.nummerTauschen(-1, 0);
                buttonManager.aktualisiereButtons();
                benachrichtigen();
            }
            case ENTER -> {
                puzzle.mischen();
                buttonManager.aktualisiereButtons();
                gridPane.requestFocus();
            }
            case DIGIT3 -> {
                gridFuellen(3);
                puzzle.mischen();
            }
            case DIGIT4 -> {
                gridFuellen(4);
                puzzle.mischen();
            }
            case DIGIT5 -> {
                gridFuellen(5);
                puzzle.mischen();
            }
            case DIGIT6 -> {
                gridFuellen(6);
                puzzle.mischen();
            }
            case DIGIT7 -> {
                gridFuellen(7);
                puzzle.mischen();
            }
            case DIGIT8 -> {
                gridFuellen(8);
                puzzle.mischen();
            }
            case ESCAPE -> Platform.exit();
        }
    }
    // Ändert die Größe des Grids
    private void gridFuellen(int groesse) {
        gridPane.getChildren().clear();
        gridGroesse = groesse;
        buttonManager.buttonsErstellen();
        buttonManager.buttonsEinfuegen();
        puzzle.setButtonLeer(puzzle.getButtons().get(puzzle.getButtons().size() - 1));
        puzzle.getButtonLeer().setText(" ");
        // Speichert die Zahlen der buttons ArrayList für die Gewinnprüfung
        puzzle.setReihenfolgeRichtig(buttonManager.arraySpeichern());
        puzzle.mischen();
    }

    private void benachrichtigen() {
        scene.getRoot().requestFocus();
        if (puzzle.gewinnPruefen()) {
            puzzle.stopTimer();
            Alert gewonnen = new Alert(Alert.AlertType.INFORMATION);
            gewonnen.setTitle("Gewinnmeldung");
            if (sekunden > 60) {
                int minuten = sekunden / 60;
                int uebrigeSekunden = sekunden % 60;
                if (sekunden > 120) {
                    gewonnen.setHeaderText(
                            "Züge: " + puzzle.getZuegeZaehler() + ", Zeit: " + minuten + " Minuten und " + uebrigeSekunden
                                    + " Sekunden");
                } else {
                    gewonnen.setHeaderText("Züge: " + puzzle.getZuegeZaehler() + ", Zeit: " + minuten + " Minute und "
                            + uebrigeSekunden + " Sekunden");
                }
            } else {
                gewonnen.setHeaderText("Züge: " + puzzle.getZuegeZaehler() + ", Zeit: " + sekunden + " Sekunden");
            }
            gewonnen.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
