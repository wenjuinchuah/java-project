package templates;

import javax.swing.*;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class MyComboBox extends JComboBox {
    public MyComboBox(String[] entryList, String currentEntry, int width, int height) {

        for (String item : entryList) {
            this.addItem(item);
        }
        this.setUI(ColorArrowUI.createUI(this));
        this.setEditable(true);
        this.setEditor(new MyComboBoxEditor());
        this.setSelectedItem(currentEntry);
        this.setPreferredSize(new Dimension(width, height));
        this.setBorder(new RoundedBorder(10));
        this.setForeground(new Color(51,51,51));
        this.setBackground(Color.WHITE);
        this.setOpaque(true);
    }

    public static class ColorArrowUI extends BasicComboBoxUI {
        public static ComboBoxUI createUI(JComponent c) {
            return new ColorArrowUI();
        }

        @Override protected JButton createArrowButton() {
            return new BasicArrowButton (
                    BasicArrowButton.SOUTH,
                    Color.WHITE, Color.WHITE,
                    new Color(51,51,51), Color.WHITE);
        }
    }
}
