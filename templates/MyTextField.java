package templates;

import javax.swing.*;
import java.awt.*;

public class MyTextField extends JTextField {
    public MyTextField(String text, int width, int height) {
        this.setText(text);
        this.setFont(new Font("", Font.PLAIN, 14));
        this.setPreferredSize(new Dimension(width, height));
        this.setBorder(new RoundedBorder(10));
        this.setForeground(new Color(51,51,51));
        this.setBackground(Color.WHITE);
        this.setOpaque(true);
    }
}
