package org.gkl;

import javafx.animation.Timeline;
import javafx.scene.control.Button;
import java.util.ArrayList;

public class Puzzle {
    private int gridGroesse;
    private ArrayList<Button> buttons;
    private int zuegeZaehler;
    public int sekunden;
    private int aktuelleIndex;
    private Button buttonLeer;
    private Timeline timeline;
    private String reihenfolgeRichtig;
    private String reihenfolgePruefen;

    public Puzzle(int gridGroesse) {
        this.gridGroesse = gridGroesse;
        this.buttons = new ArrayList<>();
        this.zuegeZaehler = 0;
        this.sekunden = 0;
        this.timeline = new Timeline();
    }

    public int getSekunden() {
        return sekunden;
    }

    public int getGridGroesse() {
        return gridGroesse;
    }

    public void setButtonLeer(Button buttonLeer) {
        this.buttonLeer = buttonLeer;
    }

    public void setReihenfolgeRichtig(String reihenfolgeRichtig) {
        this.reihenfolgeRichtig = reihenfolgeRichtig;
    }

    public int getZuegeZaehler() {
        return zuegeZaehler;
    }

    public ArrayList<Button> getButtons() {
        return buttons;
    }

    // Ändert die Position des leeren Buttons
    public void nummerTauschen(int xSpalte, int xReihe) {
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

    // Der angeklikte Button wird mit dem leeren Button getauscht
    public void buttonBewegen(Button button) {
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

    public void mischen() {
        ArrayList<Integer> letzteRichtungen = new ArrayList<>();
        letzteRichtungen.clear();
        for (int bewegungen = 0; bewegungen < 60; bewegungen++) {
            int randomRichtung = (int) (Math.random() * 4 + 1);
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

    public boolean gewinnPruefen() {
        reihenfolgePruefen = arraySpeichern(buttons);
        return reihenfolgeRichtig.equals(reihenfolgePruefen);
    }

    public String arraySpeichern(ArrayList<Button> buttonListe) {
        StringBuilder string = new StringBuilder();
        for (Button button : buttonListe) {
            string.append(button.getText()).append(", ");
        }
        if (string.length() > 0) {
            string.setLength(string.length() - 2);
        }
        return string.toString();
    }

    public void startTimer() {
        timeline.play();
    }

    public void stopTimer() {
        timeline.stop();
    }

    public Button getButtonLeer() {
        return buttons.get(buttons.size() - 1);
    }
}
