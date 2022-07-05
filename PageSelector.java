import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import templates.MyButton;
import templates.MyFrame;
import templates.MyPanel;

public class PageSelector extends JPanel{
	
	private BoxLayout grd;
	private JPanel  middle, bottom;
	private JButton home, data, profile;
	private CardLayout middlecrd;
	private HomePage h;
	private Data d;
	private Profile p;
	
	public PageSelector() {
		grd = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(grd);
		
		//middle 
		middle = new JPanel();
		middlecrd = new CardLayout();	
		middle.setLayout(middlecrd);
		p = new Profile();
		d = new Data(p);
		h = new HomePage(p);
		p.setName("p");
		d.setName("d");
		h.setName("h");
		middle.add(h, h.getName());
		middle.add(d, d.getName());
		middle.add(p, p.getName());
		
		//bottom
		Color defaultColor = new Color(51, 51, 51);
		//home button
		home = new MyButton("Home", defaultColor, Color.WHITE, 90, 40);
		home.setIcon(new ImageIcon(MyPanel.resize("src/images/home.png", 16, 16)));
		home.setDisabledIcon(new ImageIcon(MyPanel.resize("src/images/home-selected.png", 16, 16)));
		home.setEnabled(false);
		home.addActionListener(new HomeButtonListener());

		//data button
		data = new MyButton("Analytics", defaultColor, Color.WHITE, 90, 40);
		data.setIcon(new ImageIcon(MyPanel.resize("src/images/pulse.png", 16, 16)));
		data.addActionListener(new DataButtonListener());
		
		profile = new MyButton("Profile", defaultColor, Color.WHITE, 90, 40);
		profile.setIcon(new ImageIcon(MyPanel.resize("src/images/user.png", 16, 16)));	
		profile.addActionListener(new ProfileButtonListener());
		
		bottom = MyPanel.addPanel(new FlowLayout(), home, 100, 50);
		bottom.add(MyPanel.addPanel(new FlowLayout(), data, 100, 50));
		bottom.add(MyPanel.addPanel(new FlowLayout(), profile, 100, 50));
		bottom.setPreferredSize(new Dimension(MyFrame.WIDTH, 50));
		bottom.setBorder((BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(196,196,196))));
		bottom.setBackground(Color.WHITE);
		bottom.setOpaque(true);
		
		p.addComponentListener(new ComponentAdapter() {
        	public void componentShown(ComponentEvent evt) {
        		p.displayData();
        	}
        });
		
		d.addComponentListener(new ComponentAdapter() {
        	public void componentShown(ComponentEvent evt) {
        		d.displayData();
        	}
        });
		
		h.addComponentListener(new ComponentAdapter() {
        	public void componentShown(ComponentEvent evt) {
        		h.displayData();
        	}
        });
		
		//add(up);
		add(middle);
		add(bottom);
	}
	
	//show middle card layout when nav bar is clicked
	private class HomeButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			middlecrd.show(middle, "h");

			home.setDisabledIcon(new ImageIcon(MyPanel.resize("src/images/home-selected.png", 16, 16)));
			home.setEnabled(false);

			data.setIcon(new ImageIcon(MyPanel.resize("src/images/pulse.png", 16, 16)));
			data.setEnabled(true);

			profile.setIcon(new ImageIcon(MyPanel.resize("src/images/user.png", 16, 16)));
			profile.setEnabled(true);
		}
	}
	
	private class DataButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			middlecrd.show(middle, "d");

			home.setIcon(new ImageIcon(MyPanel.resize("src/images/home.png", 16, 16)));
			home.setEnabled(true);
			
			data.setDisabledIcon(new ImageIcon(MyPanel.resize("src/images/pulse-selected.png", 16, 16)));
			data.setEnabled(false);
			
			profile.setIcon(new ImageIcon(MyPanel.resize("src/images/user.png", 16, 16)));
			profile.setEnabled(true);
		}
	}
	
	private class ProfileButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			middlecrd.show(middle, "p");
		
			home.setIcon(new ImageIcon(MyPanel.resize("src/images/home.png", 16, 16)));
			home.setEnabled(true);
			
			data.setIcon(new ImageIcon(MyPanel.resize("src/images/pulse.png", 16, 16)));
			data.setEnabled(true);
			
			profile.setDisabledIcon(new ImageIcon(MyPanel.resize("src/images/user-selected.png", 16, 16)));
			profile.setEnabled(false);
		}
	}
}
