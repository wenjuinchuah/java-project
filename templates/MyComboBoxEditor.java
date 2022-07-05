package templates;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxEditor;

public class MyComboBoxEditor extends BasicComboBoxEditor {
    private final JLabel label = new JLabel();
    private final JPanel panel = new JPanel();
    private Object selectedItem;

    public MyComboBoxEditor() {

        label.setFont(new Font(null, Font.PLAIN, 14));
        label.setForeground(new Color(51,51,51));

        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.add(label);
        panel.setBackground(Color.WHITE);
        panel.setOpaque(true);
    }

    public Component getEditorComponent() {
        return this.panel;
    }

    public Object getItem() {
        return this.selectedItem.toString();
    }

    public void setItem(Object item) {
        this.selectedItem = item;
        label.setText(item.toString());
    }

}