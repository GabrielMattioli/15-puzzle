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
    private static final int QUADRAT_GROESSE = 100;
    private int sekunden;
    private Timeline timeline;
    private final BorderPane borderPane = new BorderPane();
    private final GridPane gridPane = new GridPane();
    private final Scene scene = new Scene(borderPane, 640, 480);
    private Puzzle puzzle;
    private ButtonManager buttonManager;

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
        puzzle = new Puzzle(3, this); // Standartgroesse ist 3
        sekunden = puzzle.getSekunden();
        buttonManager = new ButtonManager(gridPane, puzzle);
        gridFuellen(puzzle.getGridGroesse());
    }

    @SuppressWarnings("incomplete-switch")
    private void handleKeyPress(KeyEvent event) {
    	int neueGroesse;
        switch (event.getCode()) {
            case UP:
                puzzle.nummerTauschen(0, 1);
                benachrichtigen();
            
            case DOWN:
                puzzle.nummerTauschen(0, -1);
                benachrichtigen();
            
            case LEFT:
                puzzle.nummerTauschen(1, 0);
                benachrichtigen();
            
            case RIGHT:
                puzzle.nummerTauschen(-1, 0);
                benachrichtigen();
            
            case ENTER:
                puzzle.mischen();
                gridPane.requestFocus();
            
            case DIGIT3:
            	neueGroesse = Integer.parseInt(event.getText());
                puzzle.setGridGroesse(neueGroesse);
                gridFuellen(puzzle.getGridGroesse());
                gridPane.requestFocus();
                
            case DIGIT4:
            	neueGroesse = Integer.parseInt(event.getText());
                puzzle.setGridGroesse(neueGroesse);
                gridFuellen(puzzle.getGridGroesse());
                gridPane.requestFocus();
                
            case DIGIT5:
            	neueGroesse = Integer.parseInt(event.getText());
                puzzle.setGridGroesse(neueGroesse);
                gridFuellen(puzzle.getGridGroesse());
                gridPane.requestFocus();
                
            case DIGIT6:
            	neueGroesse = Integer.parseInt(event.getText());
                puzzle.setGridGroesse(neueGroesse);
                gridFuellen(puzzle.getGridGroesse());
                gridPane.requestFocus();
                
            case DIGIT7:
            	neueGroesse = Integer.parseInt(event.getText());
            	puzzle.setGridGroesse(neueGroesse);
            	gridFuellen(puzzle.getGridGroesse());
            	gridPane.requestFocus();
            	
            case DIGIT8:
                neueGroesse = Integer.parseInt(event.getText());
                puzzle.setGridGroesse(neueGroesse);
                gridFuellen(puzzle.getGridGroesse());
                gridPane.requestFocus();
            
            case ESCAPE:
            	Platform.exit();
    
        }    
    }
    // Ändert die Größe des Grids
    private void gridFuellen(int groesse) {
        gridPane.getChildren().clear();
        buttonManager.buttonsErstellen(groesse);
        buttonManager.buttonsEinfuegen(groesse);
        puzzle.setButtonLeer(puzzle.getButtons().get(puzzle.getButtons().size() - 1));
        puzzle.getButtonLeer().setText("");
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

    // Getters und Setters

    public static int getQUADRAT_GROESSE() {
        return QUADRAT_GROESSE;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public static void main(String[] args) {
        launch(args);
    }

    
}
