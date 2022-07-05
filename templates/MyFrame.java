package templates;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyFrame extends JFrame {

	public final static int WIDTH = 360;
	public final static int HEIGHT = 740;

    public MyFrame(String string, JPanel panel) {
		
        this.setTitle(string);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.getContentPane().add(panel);
		this.setIconImage(new ImageIcon("src/images/favicon.png").getImage());
		this.setSize(new Dimension(MyFrame.WIDTH, MyFrame.HEIGHT));
		this.setResizable(false);
		this.setVisible(true);
    }
}
