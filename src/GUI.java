import java.awt.*;
import javax.swing.*;

/**
 * O interfata grafica pentru a afisa mai lizibil anumite
 * mesaje pe ecran
 */
public class GUI extends JFrame {
    String textToDisplay;
    public GUI() {
    }

    /**
     * Metoda prin care se construieste aspectul ferestrei
     */
    public void displayGUI() {
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());

        JTextArea jta = new JTextArea(50, 50);
        jta.setBackground(new Color(200, 200, 200));
        jta.setForeground(Color.BLACK);
        jta.setPreferredSize(new Dimension(850, 200));
        jta.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        cp.add(jta);

        setTitle("Afisare Log");
        setSize(900, 600);
        setVisible(true);

        StringBuilder msg = new StringBuilder();
        msg.append(textToDisplay);
        jta.setText(msg.toString());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public String getTextToDisplay() {
        return textToDisplay;
    }

    public void setTextToDisplay(String textToDisplay) {
        this.textToDisplay = textToDisplay;
    }
}