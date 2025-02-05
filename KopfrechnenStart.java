// KopfrechnenStart.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class KopfrechnenStart extends JFrame {
    private JButton startButton;
    private JButton level1Button;
    private JButton level2Button;
    private JButton level3Button;
    private JLabel backgroundLabel;
    private int selectedLevel = -1;
    private List<JButton> levelButtons = new ArrayList<>();

    // Konstruktor
    public KopfrechnenStart() {
        setTitle("Kopfrechentrainer");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

    //Hintergrund initialisieren und positionieren
        backgroundLabel = new JLabel(new ImageIcon("background.jpg"));
        backgroundLabel.setBounds(0, 0, 600, 400);
    //Hintergrund zuerst hinzufügen, damit andere Komponenten drüber liegen
        add(backgroundLabel);

    //Startbutton erstellen
        startButton = new JButton("Start");
    //Positionieren
        startButton.setBounds(250, 250, 100, 40);
    //ActionListener hinzufügen
        startButton.addActionListener(e -> {
        //Prüfen ob ein Level ausgewählt wurde
            if (selectedLevel != -1) {
               // wenn ja startet das Spiel
                new KopfrechnenSpiel(selectedLevel);
                dispose();//Aktueler Fenster wird geschlossen
            //wenn nein kommt Fehlermeldung
            } else {
                JOptionPane.showMessageDialog(null, "Bitte ein Level auswählen!");
            }
        });
        //StartButtom hinzufügen
        add(startButton);

    //Level-Buttons erstellen
        level1Button = createLevelButton("Level 1 (1-10)", 1, 50);
        level2Button = createLevelButton("Level 2 (1-100)", 2, 240);
        level3Button = createLevelButton("Level 3 (1-1000)", 3, 430);
        
    //Level-Buttons hinzufügen    
        add(level1Button);
        add(level2Button);
        add(level3Button);
        
    //Fenster wird sichtbar
        setVisible(true);
    }
    
    private JButton createLevelButton(String text, int level, int x) {
        JButton button = new JButton(text);
        button.setBounds(x, 300, 120, 40);
        button.addActionListener(e -> {
            selectedLevel = level;
            // alle Level-Buttons auf Standartfarbe setzen
            for (JButton btn : levelButtons) {
                btn.setBackground(null);
            }
            // den aktuellen Butten mit grün hervorheben
            button.setBackground(Color.GREEN);

            // Ohne for-Schleife:
            // level1Button.setBackground(null);
            // level2Button.setBackground(null);
            // level3Button.setBackground(null);
            // button.setBackground(Color.GREEN);
        });
        return button;
    }
    //Die Hauptmethode, die Anwendung startet
    public static void main(String[] args) {
        // startet die GUI 
        // new KopfrechnenStart();

         // Starte die GUI im Event-Dispatch-Thread für thread-sichere Swing-Anwendungen
         SwingUtilities.invokeLater(KopfrechnenStart::new);

    }
}


