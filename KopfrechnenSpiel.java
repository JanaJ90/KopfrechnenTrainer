import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class KopfrechnenSpiel extends JFrame {
    private int level;
    private JLabel aufgabeLabel;
    private JLabel timerLabel;
    private JPanel spielfeld;
    private Random random = new Random();
    private Timer spielTimer;
    private int verbleibendeZeit = 60;
    private int richtigeAntwort;

    // Konstruktor
    public KopfrechnenSpiel(int level) {
        this.level = level;
    //Fenster Eintellungen
        setTitle("Kopfrechentrainer - Level " + level);
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); 
        setLocationRelativeTo(null);
    //Initiallisieren und positionieren das Aufgabe-Label
        aufgabeLabel = new JLabel("", SwingConstants.CENTER);
        aufgabeLabel.setBounds(150, 20, 600, 50);
        aufgabeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        //Aufgabe-Label hinzufügen
        add(aufgabeLabel);
    // Initialisiere und positioniere das Timer-Label    
        timerLabel = new JLabel("Zeit: " + verbleibendeZeit, SwingConstants.CENTER);
        timerLabel.setBounds(750, 20, 120, 50);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 32));
        //Timer-Label hinzufügen
        add(timerLabel);
    //Erstellen das Spielfeld-Panel    
        spielfeld = new JPanel();
        spielfeld.setLayout(null);
        spielfeld.setBounds(0, 80, 900, 500);
        spielfeld.setOpaque(false);
        //Spielfeld-Panel hinzufügen
        add(spielfeld); 
     // Setze das Zähler-Label für die Blasen (z. B. zur Anzeige von korrekten und falschen Antworten)       
        Blase.setCounterLabel(aufgabeLabel);
    //Setze das Zähler zurück
        Blase.resetCounter();
    //Generiere die erste Aufgabe
        generiereAufgabe();
    //Starte den Timer
        starteTimer();
    //Macht Fenster sichtbar    
        setVisible(true);
    }
    //Startet den Timer und zeigt verbliebende Zeit
    private void starteTimer() {
        spielTimer = new Timer(1000, e -> {
            verbleibendeZeit--;
            timerLabel.setText("Zeit: " + verbleibendeZeit);
            //wenn Zeit abgelaufen ist schließt sich die SpielFenster, Ergebnis Fenster macht sich auf
            if (verbleibendeZeit <= 0) {
                spielTimer.stop();
                dispose();
                ergebnisFensterAnzeigen();
            }
        });
        spielTimer.start();
    }
    // Aufbau das ErgebnisFenster
    private void ergebnisFensterAnzeigen() {
        JFrame ergebnisFenster = new JFrame("Spiel beendet");
        ergebnisFenster.setSize(400, 250);
        ergebnisFenster.setLayout(new BorderLayout());
        ergebnisFenster.setLocationRelativeTo(null);
        //Überschrift mit Glückwunsch Nachricht
        JLabel nachricht = new JLabel("Gut gemacht!", SwingConstants.CENTER);
        nachricht.setFont(new Font("Arial", Font.BOLD, 24));
        ergebnisFenster.add(nachricht, BorderLayout.NORTH);
        //Anzeige der Richtigen und die Falschen Antworten
        JLabel ergebnisLabel = new JLabel("Richtig: " + Blase.getRichtigeAntworten() + " | Falsch: " + Blase.getFalscheAntworten(), SwingConstants.CENTER);
        ergebnisLabel.setFont(new Font("Arial", Font.BOLD, 20));
        ergebnisFenster.add(ergebnisLabel, BorderLayout.CENTER);
        //Panel für die Actions-Buttons
        JPanel buttonPanel = new JPanel();
        
        JButton wiederholenButton = new JButton("Wiederholen");
        wiederholenButton.addActionListener(e -> {
            ergebnisFenster.dispose();
            new KopfrechnenSpiel(level);
        });
        
        JButton hauptmenueButton = new JButton("Hauptmenü");
        hauptmenueButton.addActionListener(e -> {
            ergebnisFenster.dispose();
            new KopfrechnenStart();
        });
        //Füge die Buttons dem Panel hinzu und plaziere die im ErgebnisFenster
        buttonPanel.add(wiederholenButton);
        buttonPanel.add(hauptmenueButton);
        ergebnisFenster.add(buttonPanel, BorderLayout.SOUTH);
        //Mach Fenster sichtbar
        ergebnisFenster.setVisible(true);
    }
// Logik zum Aufgaben generieren
    public void generiereAufgabe() {
        int zahl1 = random.nextInt((int) Math.pow(10, level)) + 1;
        int zahl2 = random.nextInt((int) Math.pow(10, level)) + 1;
        char[] operatoren = {'+', '-', '*', '/'};
        char operator = operatoren[random.nextInt(operatoren.length)];
        
        if (operator == '/') {
            while (zahl2 == 0 || zahl1 % zahl2 != 0) {//wenn die Zahl = 0 ist und wenn  der Rest kein ganzen zahl ist, nochmal random
                zahl1 = random.nextInt((int) Math.pow(10, level)) + 1;
                zahl2 = random.nextInt((int) Math.pow(10, level)) + 1;
            }
            richtigeAntwort = zahl1 / zahl2;
        } else if (operator == '-') {
            while (zahl1 < zahl2) { // wenn die erste Zahl <  als Zahl zwei, mache nochmal random
                zahl1 = random.nextInt((int) Math.pow(10, level)) + 1;//Math.pow rechnen x(10) hoch n(level) 
                zahl2 = random.nextInt((int) Math.pow(10, level)) + 1;
            }
            richtigeAntwort = zahl1 - zahl2;
        } else if (operator == '+') {
            richtigeAntwort = zahl1 + zahl2;
        } else {  // Multiplikation
            richtigeAntwort = zahl1 * zahl2;
        }
        // Setze die Aufgabe in Aufgabe-Label
        aufgabeLabel.setText(zahl1 + " " + operator + " " + zahl2 + " = ?");
        // Erzeuge 3 Antworten
        int[] antworten = {
            richtigeAntwort, 
            richtigeAntwort + random.nextInt(50) + 1, 
            richtigeAntwort - random.nextInt(50) - 1
        };
        //Antwort Möglichkeiten werden gemischt
        for (int i = antworten.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = antworten[i];
            antworten[i] = antworten[j];
            antworten[j] = temp;
        }
        //Aktualisiert das Spielfeld
        spielfeld.removeAll();// Entfernt 
        spielfeld.repaint();// Fügt neue zu
        //Schleife um neue Blasen zu erstellen
        for (int i = 0; i < 3; i++) {
            Blase blase = new Blase(antworten[i], i * 250 + 100, spielfeld, richtigeAntwort, this);
            blase.setFont(new Font("Arial", Font.BOLD, 16)); // Noch kleinere Schrift für sehr lange Zahlen
            blase.setPreferredSize(new Dimension(220, 100)); // Noch größere Blasen
            blase.setHorizontalAlignment(SwingConstants.CENTER);
            spielfeld.add(blase);
        }
    }
}
