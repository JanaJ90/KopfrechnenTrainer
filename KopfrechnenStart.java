// KopfrechnenStart.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class KopfrechnenStart extends JFrame {
    private JButton startButton;
    private JButton level1Button;
    private JButton level2Button;
    private JButton level3Button;
    private JLabel backgroundLabel;
    private int selectedLevel = -1;

    public KopfrechnenStart() {
        setTitle("Kopfrechentrainer");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        backgroundLabel = new JLabel(new ImageIcon("background.jpg"));
        backgroundLabel.setBounds(0, 0, 600, 400);

        startButton = new JButton("Start");
        startButton.setBounds(250, 250, 100, 40);
        startButton.addActionListener(e -> {
            if (selectedLevel != -1) {
                new KopfrechnenSpiel(selectedLevel);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Bitte ein Level auswählen!");
            }
        });
        
        level1Button = createLevelButton("Level 1 (1-10)", 1, 50);
        level2Button = createLevelButton("Level 2 (1-100)", 2, 240);
        level3Button = createLevelButton("Level 3 (1-1000)", 3, 430);
        
        add(startButton);
        add(level1Button);
        add(level2Button);
        add(level3Button);
        add(backgroundLabel);

        setVisible(true);
    }
    
    private JButton createLevelButton(String text, int level, int x) {
        JButton button = new JButton(text);
        button.setBounds(x, 300, 120, 40);
        button.addActionListener(e -> {
            selectedLevel = level;
            level1Button.setBackground(null);
            level2Button.setBackground(null);
            level3Button.setBackground(null);
            button.setBackground(Color.GREEN);
        });
        return button;
    }

    public static void main(String[] args) {
        new KopfrechnenStart();
    }
}


