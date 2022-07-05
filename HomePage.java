import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import templates.*;

public class HomePage extends MyPanel implements Displayable{
    private JPanel cardPanel, mainPanel, namerefreshPanel;
    private CardLayout crd;
	
	private DataCreation dc;
    private final int TEXT_HEIGHT = 120;

    private File file;
	private Profile profile;
	public HomePage(Profile p) {
    	profile = p;
    	//create database file
		createDB();
		
		//container
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

        //show add entry card-using data creation
		cardPanel = new JPanel();
		cardPanel.setPreferredSize(new Dimension(360, 740));
		cardPanel.setMaximumSize(new Dimension(360, 740));
        
		namerefreshPanel= new JPanel();

        //advertisement label
        JLabel sliderLabel = new JLabel(new ImageIcon(MyPanel.resize("src/images/img1.jpg", 300, 150)));
        sliderLabel.setPreferredSize(new Dimension(300, 150));
        sliderLabel.setBorder(new RoundedBorder(10));

        //add label to panel
        JPanel sliderPanel = MyPanel.addPanel(new BorderLayout(), sliderLabel, BorderLayout.CENTER, 300, 150);

        // text as label
        JLabel textLabel = new JLabel("Stay healthy with Health Diary");
        textLabel.setPreferredSize(new Dimension(300, 20));
        textLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setVerticalAlignment(JLabel.BOTTOM);

        //image as label
        JLabel imgLabel = new JLabel(new ImageIcon(MyPanel.resize("src/images/img2.png", 280, 220)));
        imgLabel.setPreferredSize(new Dimension(300, 250));

        //insert image label to panel
        JPanel imgPanel = MyPanel.addPanel(new BorderLayout(), imgLabel, BorderLayout.NORTH, 300, 250);

        //bottom panel contain advertisement and picture panels
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 25));
        bottomPanel.add(sliderPanel, BorderLayout.NORTH);
        bottomPanel.add(textLabel, BorderLayout.CENTER);
        bottomPanel.add(imgPanel, BorderLayout.SOUTH);
        bottomPanel.setOpaque(false);

        crd = new CardLayout();
        dc = new DataCreation(crd, cardPanel);

        mainPanel.add(namerefreshPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel, BorderLayout.CENTER);

        mainPanel.setName("m");
        dc.setName("dc");

		cardPanel.setLayout(crd);
		//show main panel first
		cardPanel.add(mainPanel, mainPanel.getName());
		cardPanel.add(dc, dc.getName());

		add(cardPanel);
        setBackground(Color.WHITE);
        setOpaque(true);
        
        addComponentListener(new ComponentAdapter() {
        	public void componentShown(ComponentEvent evt) {
        		displayData();
        	}
        });
		
		displayData();
    }

    private class AddButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {	
			crd.show(cardPanel, "dc");
        }
    }
    
    //refresh mainPanel
    public void displayData() {
		namerefreshPanel.removeAll();
			
        //Welcome label
        JLabel textLabel = new JLabel("<html>Welcome,<br/>" + profile.getData("first_name") + "</html>");
        textLabel.setPreferredSize(new Dimension(200, TEXT_HEIGHT));
        textLabel.setFont(new Font(null, Font.BOLD, 30));
        textLabel.setForeground(new Color(51,51,51));
        textLabel.setHorizontalAlignment(JLabel.LEFT);
        textLabel.setBorder(new EmptyBorder(0,10,0,0));


        //Add Entry button
        MyButton addBtn = new MyButton("Add", Color.WHITE, new Color(51, 51, 51));
        addBtn.setPreferredSize(new Dimension(100, 40));
        addBtn.setIcon(new ImageIcon(MyPanel.resize("src/images/plus.png", 16, 16)));
        addBtn.setHorizontalTextPosition(JButton.RIGHT);
        addBtn.setIconTextGap(5);
        addBtn.addActionListener(new AddButtonListener());


        //top label container
        namerefreshPanel.setBackground(Color.WHITE);
        namerefreshPanel.setPreferredSize(new Dimension(360, TEXT_HEIGHT));
        namerefreshPanel.add(textLabel);
        namerefreshPanel.add(addBtn);
        
        namerefreshPanel.revalidate();
        namerefreshPanel.repaint();
    
	}

	public void createDB() {
		try {
			file = new File("Database.txt");
			if (file.createNewFile()) {
		        System.out.println("File created: " + file.getName());
			} else {
		        System.out.println("File already exists in Data.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

}

