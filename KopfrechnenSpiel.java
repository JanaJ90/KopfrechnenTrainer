import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.Timer;

public class KopfrechnenSpiel extends JFrame {
    private int level;
    private JLabel aufgabeLabel;
    private JLabel timerLabel;
    private JPanel spielfeld;
    private Random random = new Random();
    private Timer spielTimer;
    private int verbleibendeZeit = 60;
    private int richtigeAntwort;

    public KopfrechnenSpiel(int level) {
        this.level = level;
        setTitle("Kopfrechentrainer - Level " + level);
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        aufgabeLabel = new JLabel("", SwingConstants.CENTER);
        aufgabeLabel.setBounds(150, 20, 600, 50);
        aufgabeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        add(aufgabeLabel);
        
        timerLabel = new JLabel("Zeit: " + verbleibendeZeit, SwingConstants.CENTER);
        timerLabel.setBounds(750, 20, 120, 50);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 32));
        add(timerLabel);
        
        spielfeld = new JPanel();
        spielfeld.setLayout(null);
        spielfeld.setBounds(0, 80, 900, 500);
        spielfeld.setOpaque(false);
        add(spielfeld);
        
        Blase.setCounterLabel(aufgabeLabel);
        Blase.resetCounter();
        generiereAufgabe();
        starteTimer();
        
        setVisible(true);
    }

    private void starteTimer() {
        spielTimer = new Timer(1000, e -> {
            verbleibendeZeit--;
            timerLabel.setText("Zeit: " + verbleibendeZeit);
            if (verbleibendeZeit <= 0) {
                spielTimer.stop();
                dispose();
                ergebnisFensterAnzeigen();
            }
        });
        spielTimer.start();
    }

    private void ergebnisFensterAnzeigen() {
        JFrame ergebnisFenster = new JFrame("Spiel beendet");
        ergebnisFenster.setSize(400, 250);
        ergebnisFenster.setLayout(new BorderLayout());
        ergebnisFenster.setLocationRelativeTo(null);
        
        JLabel nachricht = new JLabel("Gut gemacht!", SwingConstants.CENTER);
        nachricht.setFont(new Font("Arial", Font.BOLD, 24));
        ergebnisFenster.add(nachricht, BorderLayout.NORTH);
        
        JLabel ergebnisLabel = new JLabel("Richtig: " + Blase.getRichtigeAntworten() + " | Falsch: " + Blase.getFalscheAntworten(), SwingConstants.CENTER);
        ergebnisLabel.setFont(new Font("Arial", Font.BOLD, 20));
        ergebnisFenster.add(ergebnisLabel, BorderLayout.CENTER);
        
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
        
        buttonPanel.add(wiederholenButton);
        buttonPanel.add(hauptmenueButton);
        ergebnisFenster.add(buttonPanel, BorderLayout.SOUTH);
        
        ergebnisFenster.setVisible(true);
    }

    public void generiereAufgabe() {
        int zahl1 = random.nextInt((int) Math.pow(10, level)) + 1;
        int zahl2 = random.nextInt((int) Math.pow(10, level)) + 1;
        char[] operatoren = {'+', '-', '*', '/'};
        char operator = operatoren[random.nextInt(operatoren.length)];
        
        if (operator == '/') {
            while (zahl2 == 0 || zahl1 % zahl2 != 0) {
                zahl1 = random.nextInt((int) Math.pow(10, level)) + 1;
                zahl2 = random.nextInt((int) Math.pow(10, level)) + 1;
            }
            richtigeAntwort = zahl1 / zahl2;
        } else if (operator == '-') {
            while (zahl1 < zahl2) {
                zahl1 = random.nextInt((int) Math.pow(10, level)) + 1;
                zahl2 = random.nextInt((int) Math.pow(10, level)) + 1;
            }
            richtigeAntwort = zahl1 - zahl2;
        } else {
            richtigeAntwort = operator == '+' ? zahl1 + zahl2 : zahl1 * zahl2;
        }
        
        aufgabeLabel.setText(zahl1 + " " + operator + " " + zahl2 + " = ?");
        
        int[] antworten = {richtigeAntwort, richtigeAntwort + random.nextInt(50) + 1, richtigeAntwort - random.nextInt(50) - 1};
        
        for (int i = antworten.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = antworten[i];
            antworten[i] = antworten[j];
            antworten[j] = temp;
        }

        spielfeld.removeAll();
        spielfeld.repaint();
        for (int i = 0; i < 3; i++) {
            Blase blase = new Blase(antworten[i], i * 250 + 100, spielfeld, richtigeAntwort, this);
            blase.setFont(new Font("Arial", Font.BOLD, 16)); // Noch kleinere Schrift für sehr lange Zahlen
            blase.setPreferredSize(new Dimension(220, 100)); // Noch größere Blasen
            blase.setHorizontalAlignment(SwingConstants.CENTER);
            spielfeld.add(blase);
        }
    }
}
