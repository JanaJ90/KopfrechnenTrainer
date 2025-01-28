import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Timer;
import java.util.Random;

class Blase extends JLabel {
    private int wert;
    private Timer timer;
    private static int richtigeAntworten = 0;
    private static int falscheAntworten = 0;
    private static JLabel counterLabel;
    private KopfrechnenSpiel spielInstance;
    
    public static void setCounterLabel(JLabel label) {
        counterLabel = label;
    }
    
    public static void resetCounter() {
        richtigeAntworten = 0;
        falscheAntworten = 0;
        updateCounter();
    }
    
    public static void updateCounter() {
        if (counterLabel != null) {
            counterLabel.setText("Richtig: " + richtigeAntworten + " | Falsch: " + falscheAntworten);
        }
    }
    
    public static int getRichtigeAntworten() {
        return richtigeAntworten;
    }
    
    public static int getFalscheAntworten() {
        return falscheAntworten;
    }
    
    public Blase(int wert, int x, JPanel spielfeld, int richtigeAntwort, KopfrechnenSpiel spiel) {
        super(new ImageIcon("bubble.png"));
        this.wert = wert;
        this.spielInstance = spiel;
        setBounds(x, 0, 50, 50);
        setText(String.valueOf(wert));
        setHorizontalTextPosition(CENTER);
        setVerticalTextPosition(CENTER);
        setForeground(Color.BLACK);
        setFont(new Font("Arial", Font.BOLD, 24));
        spielfeld.add(this);
        spielfeld.repaint();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                überprüfeAntwort(spielfeld, richtigeAntwort);
            }
        });

        timer = new Timer(100, new ActionListener() { // Blasen fallen jetzt langsamer
            @Override
            public void actionPerformed(ActionEvent e) {
                setLocation(getX(), getY() + 1);
                if (getY() > 330) {
                    spielfeld.remove(Blase.this);
                    spielfeld.repaint();
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    private void überprüfeAntwort(JPanel spielfeld, int richtigeAntwort) {
        if (wert == richtigeAntwort) {
            richtigeAntworten++;
        } else {
            falscheAntworten++;
        }
        updateCounter();
        spielfeld.removeAll();
        spielfeld.repaint();
        spielInstance.generiereAufgabe();
    }
}
