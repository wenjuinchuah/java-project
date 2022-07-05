package templates;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class MyPanel extends JPanel implements ActionListener {
	public void setDefault() {
		this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(MyFrame.WIDTH, MyFrame.HEIGHT));
		this.setBackground(new Color(244,244,244));
    }

	
	public void setDefault(int width, int height) {
		this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(width, height));
    }
	
	
    public static JPanel addPanel(LayoutManager layout, JLabel label, String position, int width, int height) {
		JPanel panel = new JPanel(layout);
		panel.add(label, position);
		panel.setOpaque(false);
		panel.setPreferredSize(new Dimension(width, height));
		return panel;
	}

	public static JPanel addPanel(LayoutManager layout, JButton btn, int width, int height) {
		JPanel panel = new JPanel(layout);
		panel.add(btn);
		panel.setOpaque(false);
		panel.setPreferredSize(new Dimension(width, height));
		return panel;
	}

	public static JPanel addPanel(LayoutManager layout,JPanel whPanel, JButton btn1, JButton btn2, int width, int height) {
		JPanel panel = new JPanel(layout);
		panel.add(whPanel);
		panel.add(btn1);
		panel.add(btn2);
		panel.setOpaque(false);
		panel.setBackground(Color.white);
		panel.setPreferredSize(new Dimension(width, height));
		return panel;
	}

	public JPanel addDisplayPanel(String textLabel, String text, int width, int height, boolean isLargeDisplay) {
		//title
		JLabel label1 = new JLabel(textLabel);
		label1.setFont(new Font(null, Font.PLAIN, 14));
		label1.setForeground(new Color(128,128,128));

		//user detail
		JLabel label2 = new JLabel(text);
		label2.setFont(new Font(null, Font.PLAIN, 14));
		label2.setForeground(new Color(51,51,51));

		JPanel panel;

		if (!isLargeDisplay) {
			label2.setHorizontalAlignment(JLabel.RIGHT);

			panel = MyPanel.addPanel(new BorderLayout(), label1, BorderLayout.WEST, 200, height);
			panel.add(label2, BorderLayout.EAST);
		} 
		//Health condition
		else {
			label2.setVerticalAlignment(JLabel.TOP);
			label2.setBorder(new CompoundBorder(label2.getBorder(), new EmptyBorder(5,0,0,5)));

			panel = MyPanel.addPanel(new BorderLayout(), label1, BorderLayout.NORTH, width, 40);
			panel.add(label2, BorderLayout.CENTER);
		}

		panel.setPreferredSize(new Dimension(width, height));
		panel.setBorder(new RoundedBorder(10));
		panel.setBackground(Color.WHITE);
		panel.setOpaque(true);

		return panel;
	}

	public JPanel addTextFieldPanel(String textLabel, String textUnit, int width, int height) {
		JLabel label1 = new JLabel(textLabel);
		label1.setFont(new Font(null, Font.PLAIN, 16));
		label1.setPreferredSize(new Dimension(width, height));
		label1.setForeground(new Color(51,51,51));
		label1.setLayout(new BorderLayout());

		JLabel label2 = new JLabel(textUnit);
		label2.setFont(new Font(null, Font.PLAIN, 14));
		label2.setForeground(new Color(51,51,51));
		label2.setHorizontalAlignment(JLabel.RIGHT);
		label2.setPreferredSize(new Dimension(40, 40));
		label2.setBorder(new EmptyBorder(0, 0, 0, 10));

		label1.add(label2, BorderLayout.EAST);

		JPanel panel = MyPanel.addPanel(new BorderLayout(), label1, BorderLayout.NORTH, width, 40);
		panel.setOpaque(false);

		return panel;
	}

	public JPanel addComboBoxPanel(String textLabel, String[] entryList, String userEntry, int width, int height) {
		JLabel label1 = new JLabel(textLabel);
		label1.setFont(new Font(null, Font.PLAIN, 16));
		label1.setPreferredSize(new Dimension(width, height));
		label1.setForeground(new Color(51,51,51));

		MyComboBox comboBox = new MyComboBox(entryList, userEntry, width, height);
		comboBox.setFont(new Font(null, Font.PLAIN, 14));

		JPanel panel = MyPanel.addPanel(new BorderLayout(), label1, BorderLayout.NORTH, width, 40);
		panel.add(comboBox, BorderLayout.CENTER);
		panel.setPreferredSize(new Dimension(width, 80));
		panel.setOpaque(false);

		return panel;
	}

	public JPanel addComboBoxBtnPanel(String textLabel, int width, int height) {
		JLabel label1 = new JLabel(textLabel);
		label1.setFont(new Font(null, Font.PLAIN, 18));
		label1.setPreferredSize(new Dimension(width, height));
		label1.setForeground(new Color(51,51,51));

		JPanel panel = MyPanel.addPanel(new BorderLayout(), label1, BorderLayout.WEST, 200, height);
		panel.setPreferredSize(new Dimension(width, 80));
		panel.setOpaque(false);

		return panel;
	}

	public JPanel addHistory(String[][] data, String returnPanel) {
		JLabel dateLabel = new JLabel(data[0][0]);
		dateLabel.setFont(new Font(null, Font.PLAIN, 16));
		dateLabel.setPreferredSize(new Dimension(100, 50));

		JLabel bmiLabel = new JLabel(data[0][1]);
		bmiLabel.setFont(new Font(null, Font.BOLD, 18));
		bmiLabel.setPreferredSize(new Dimension(100, 50));

		JLabel heightLabel = new JLabel("H: " + data[0][2] + "cm");
		heightLabel.setFont(new Font(null, Font.PLAIN, 12));
		heightLabel.setPreferredSize(new Dimension(120, 12));

		JLabel weightLabel = new JLabel("W: " + data[0][3] + "kg");
		weightLabel.setFont(new Font(null, Font.PLAIN, 12));
		weightLabel.setPreferredSize(new Dimension(80, 12));

		JPanel heightWeightPanel = new JPanel(new BorderLayout());
		heightWeightPanel.add(heightLabel, BorderLayout.NORTH);
		heightWeightPanel.add(weightLabel, BorderLayout.CENTER);
		heightWeightPanel.setPreferredSize(new Dimension(60, 50));
		heightWeightPanel.setBackground(Color.WHITE);

		JButton iconBtn = new JButton();
		iconBtn.setIcon(new ImageIcon(MyPanel.resize("src/images/caret-right.png", 8, 14)));
		iconBtn.setBorderPainted(false);
		iconBtn.setFocusPainted(false);
		iconBtn.setBackground(Color.WHITE);

		//group height weight and > as a panel
		JPanel iconPanel = MyPanel.addPanel(new BorderLayout(), iconBtn, 80, 50);
		iconPanel.add(heightWeightPanel, BorderLayout.WEST);

		//one row entry 
		JPanel perEntryPanel = new JPanel(new BorderLayout());
		perEntryPanel.setPreferredSize(new Dimension(280, 50));
		perEntryPanel.setBackground(Color.WHITE);
		perEntryPanel.setBorder(new RoundedBorder(10));
		perEntryPanel.setOpaque(true);
		perEntryPanel.add(dateLabel, BorderLayout.WEST);
		perEntryPanel.add(bmiLabel, BorderLayout.CENTER);
		perEntryPanel.add(iconPanel, BorderLayout.EAST);
		return perEntryPanel;
	}

	//All top panel
	public JPanel topPanel(String title, String btnTitle, String actionPanel, String returnPanel) {
		final int labelHEIGHT = 50;
		// topPanel
		//title
		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(new Font(null, Font.BOLD, 19));
		titleLabel.setPreferredSize(new Dimension(180, labelHEIGHT));
		titleLabel.setBorder(new CompoundBorder(titleLabel.getBorder(), new EmptyBorder(0,20,0,0)));

		JPanel panel = MyPanel.addPanel(new BorderLayout(), titleLabel, BorderLayout.WEST, MyFrame.WIDTH / 2, labelHEIGHT);
		panel.setBackground(Color.WHITE);
        panel.setOpaque(true);

		return panel;
	}

	public JPanel topPanelReturn(String title, String editTitle, JButton returnBtn, JButton saveBtn, String actionPanel, String returnPanel) {
		final int labelHEIGHT = 50;
		 Color backgroundColor = Color.WHITE;

		// topPanel
		returnBtn.setIcon(new ImageIcon(MyPanel.resize("src/images/return.png", 8, 14)));
		returnBtn.setFont(new Font(null, Font.PLAIN, 19));
		returnBtn.setVerticalAlignment(JButton.BOTTOM);
		returnBtn.setVerticalTextPosition(JButton.CENTER);
		returnBtn.setHorizontalTextPosition(JButton.RIGHT);
		returnBtn.setIconTextGap(5);
		returnBtn.setPreferredSize(new Dimension(100, labelHEIGHT));

		//Add New Entry label
		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(new Font(null, Font.BOLD, 19));
		titleLabel.setPreferredSize(new Dimension(100, labelHEIGHT));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setVerticalAlignment(JLabel.BOTTOM);
		titleLabel.setBorder(new CompoundBorder(titleLabel.getBorder(), new EmptyBorder(0,0,12,0)));

		saveBtn.setFont(new Font(null, Font.PLAIN, 16));
		saveBtn.setVerticalAlignment(JButton.BOTTOM);
		saveBtn.setPreferredSize(new Dimension(100, labelHEIGHT));

		//Add New Entry top label- compress into panel
		JPanel panel = MyPanel.addPanel(new BorderLayout(), titleLabel, BorderLayout.CENTER, WIDTH / 3, labelHEIGHT);
		panel.setPreferredSize(new Dimension(MyFrame.WIDTH, labelHEIGHT));
		panel.setBackground(backgroundColor);
		panel.setOpaque(true);

		return panel;
	}

	public static Image resize(String location, int width, int height) {
		return new ImageIcon(location).getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
