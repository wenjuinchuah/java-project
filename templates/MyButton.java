package templates;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalButtonUI;

public class MyButton extends JButton implements ChangeListener {
    public final static int BTN_WIDTH = 280;
    public final static int BTN_HEIGHT = 50;
	private final Color selectedCOLOR = new Color(14,156,255);

    public MyButton(String text, Color foregroundColor, Color backgroundColor) {
        this.setText(text);
		this.setPreferredSize(new Dimension(MyButton.BTN_WIDTH, MyButton.BTN_HEIGHT));
		this.setFont(new Font("null", Font.PLAIN, 20));
		this.setBorder(new RoundedBorder(10));
		this.setForeground(foregroundColor);
		this.setBackground(backgroundColor);
		this.setFocusPainted(false);
		this.setBorderPainted(false);
		this.addChangeListener(this);
    }

	public MyButton(String text, Color foregroundColor, Color backgroundColor, int width, int height) {
        this.setText(text);
		this.setHorizontalTextPosition(JButton.CENTER);
		this.setVerticalTextPosition(JButton.BOTTOM);
		this.setPreferredSize(new Dimension(width, height));
		this.setFont(new Font("null", Font.PLAIN, 12));
		this.setBorder(new RoundedBorder(10));
		this.setForeground(foregroundColor);
		this.setBackground(backgroundColor);
		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
		this.setBorderPainted(false);
		this.setUI(new MetalButtonUI() {
			protected Color getDisabledTextColor() {
				return selectedCOLOR;
			}
		});
		this.addChangeListener(this);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
	}
    
}
