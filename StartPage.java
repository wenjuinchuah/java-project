import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import templates.MyButton;
import templates.MyFrame;
import templates.MyPanel;


public class StartPage extends JPanel implements ActionListener{
	private JPanel panel, logoPanel, btnPanel;
	private JButton start;
	private JLabel label;
	private CardLayout crd;
	private BoxLayout box;
	
	public StartPage() {
		panel = new JPanel(new BorderLayout());
		
		// Create health diary label using JLabel ImageIcon
		label = new JLabel(new ImageIcon("src/images/logo.png"));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		start = new MyButton("Let's start", Color.WHITE, new Color(51, 51, 51));
		start.setAlignmentX(Component.CENTER_ALIGNMENT);
		start.addActionListener(this);

		logoPanel = MyPanel.addPanel(new BorderLayout(), label, BorderLayout.CENTER, MyFrame.WIDTH, MyFrame.HEIGHT - MyButton.BTN_HEIGHT - 273);
		btnPanel = MyPanel.addPanel(new FlowLayout(), start, MyFrame.WIDTH, 200);
		panel.add(logoPanel, BorderLayout.NORTH);
		panel.add(btnPanel, BorderLayout.CENTER);
		
		
		box = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(box);
		panel.setOpaque(false);
		crd = new CardLayout();

		add(panel);
		
		setLayout(crd);
		
		setPreferredSize(new Dimension(360, 740));
	}
	
	public void actionPerformed(ActionEvent e) {
		this.removeAll();
		add(new PageSelector());
		this.validate();
	}

	// Set background gradient color
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth(), h = getHeight();
        Color color1 = new Color(7,153,255);
        Color color2 = new Color(0,81,175);
        GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}
