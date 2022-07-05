package templates;

import javax.swing.*;
import java.awt.*;

public class MyTextArea extends JTextArea {
    public MyTextArea(String text, int width, int height) {
        this.setText(text);
        this.setFont(new Font(null, Font.PLAIN, 16));
        this.setPreferredSize(new Dimension(width, height));
        this.setLineWrap(true);
        this.setBorder(new RoundedBorder(10));
        this.setForeground(new Color(51,51,51));
        this.setBackground(Color.WHITE);
        this.setOpaque(true);
    }


}
