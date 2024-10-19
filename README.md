# 15-Puzzle (Schiebepuzzle)

Dieses Projekt implementiert das klassische 15-Puzzle (auch bekannt als Schiebepuzzle) als JavaFX-Anwendung.

## Projektbeschreibung

Das 15-Puzzle ist ein Schiebepuzzle, bei dem 15 nummerierte Quadrate in einem 4x4-Raster angeordnet sind. Ziel des Spiels ist es, die Quadrate durch Verschieben in die richtige Reihenfolge zu bringen.

Besonderheiten dieser Implementierung:
- Variable Größe des Puzzles (3x3 bis 8x8)
- Zeitmessung und Zugzählung
- Steuerung per Maus und Tastatur

## Voraussetzungen

Um dieses Projekt auszuführen, benötigen Sie:

- Java Development Kit (JDK) 11 oder höher
- Maven
- JavaFX (wird über Maven automatisch heruntergeladen)

## Projekt ausführen

1. Klonen Sie das Repository oder laden Sie den Quellcode herunter.
2. Öffnen Sie ein Terminal und navigieren Sie zum Projektverzeichnis.
3. Führen Sie den folgenden Befehl aus, um das Projekt zu kompilieren und zu starten:

   ```
   mvn javafx:run
   ```

   Dieser Befehl wird alle notwendigen Abhängigkeiten herunterladen, das Projekt kompilieren und die Anwendung starten.

## Spielanleitung

- Verwenden Sie die Pfeiltasten oder die Maus, um die Quadrate zu verschieben.
- Drücken Sie [ENTER], um das Puzzle neu zu mischen.
- Drücken Sie [3] bis [8], um die Größe des Puzzles zu ändern.
- Drücken Sie [ESC], um das Spiel zu beenden.

## Entwickler

Dieses Projekt wurde von Gabriel Mattioli entwickelt, inspiriert durch ein Programm, das Richard Kuzlik während seiner Studienzeit erstellt hat.

## Lizenz

Dieses Projekt steht unter der [MIT-Lizenz](https://opensource.org/licenses/MIT).
