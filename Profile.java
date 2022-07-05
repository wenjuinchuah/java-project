import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import templates.MyPanel;
import templates.Displayable;
import templates.Editable;
import templates.MyButton;
import templates.MyTextField;
import templates.Retrievable;
import templates.MyComboBox;
import templates.MyTextArea;

public class Profile extends MyPanel implements Displayable, Retrievable{
	
	private JLabel namePicLabel, subTitleLabel;
	private JPanel cardPanel, mainPanel, topPanel, bloodTypePanel, healthConditionPanel, profilePanel, profilerefreshPanel;
	
	private File file;
	private Scanner reader;
	private FileWriter writer;
	
	private CardLayout crd;
	
	private ProfileCreation pc;
	
	private final int LABEL_HEIGHT = 40;

	private JTextField tnameComponent;
	private JComboBox<String> bloodSelector;
	private JTextArea thealthConditionComponent;
	private String userName="Abu Bakar", userBlood="A", userHealth="Normal";
	
	public Profile(){
		
		//create database file
		createDB();
	
		//container
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		//show edit profile card-using profile creation
		cardPanel = new JPanel();
		cardPanel.setPreferredSize(new Dimension(360, 740));
		cardPanel.setMaximumSize(new Dimension(360, 740));
		
		//header
		topPanel = this.topPanel("Profile", "Edit Profile", "EditUserProfile", "UserProfile");
		Color backgroundColor = new Color(244, 244, 244);
		Color foregroundColor = new Color(14, 156, 255);
		MyButton topBtn = new MyButton("Edit Profile", foregroundColor, backgroundColor, 88, 21);
		topBtn.setFont(new Font(null, Font.PLAIN, 16));
		topBtn.setPreferredSize(new Dimension(120, 40));
		topBtn.addActionListener(new EditProfileButtonListener());

		profilerefreshPanel= new JPanel();
		
		crd = new CardLayout();
		pc = new ProfileCreation();
		
		topPanel.add(topBtn,BorderLayout.EAST);
		
		//head
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(profilerefreshPanel, BorderLayout.CENTER);	

		mainPanel.setName("m");
		pc.setName("pc");

		cardPanel.setLayout(crd);
		//show main panel first
		cardPanel.add(mainPanel, mainPanel.getName());
		cardPanel.add(pc, pc.getName());

		add(cardPanel);
		setBackground(Color.WHITE);
        setOpaque(true);
        
        mainPanel.addComponentListener(new ComponentAdapter() {
        	public void componentShown(ComponentEvent evt) {
        		displayData();
        	}
        });
		
		displayData();
	
	}
	
	private class ProfileCreation extends MyPanel implements Editable{
		private JLabel  name, blood, healthCondition, errorLabel;
		private JPanel topPanel, mainpcPanel, profilepcPanel;
		
		private JButton returnBtn, saveBtn;
		private String[] bloodtype = {"AB", "A", "B", "O"};
	
		public ProfileCreation(){
			mainpcPanel = new JPanel();
			mainpcPanel.setLayout(new BorderLayout());

			Color foregroundColor = new Color(51,51,51);
			Color backgroundColor = Color.WHITE;
			returnBtn = new MyButton("Back", foregroundColor, backgroundColor, 60, 50);
			returnBtn.addActionListener(new CancelButtonListener());

			foregroundColor = new Color(14, 156, 255);

			saveBtn = new MyButton("Save", foregroundColor, backgroundColor, 60, 50);
			saveBtn.addActionListener(new ConfirmButtonListener());

			topPanel = this.topPanelReturn("Edit Profile", "Save",returnBtn, saveBtn, "actionPanel", "returnPanel");

			name = new JLabel("Name");
			name.setFont(new Font(null, Font.PLAIN, 16));
			name.setPreferredSize(new Dimension(280, 40));
			name.setForeground(new Color(51,51,51));
			name.setLayout(new BorderLayout());
			tnameComponent = new MyTextField(userName, 280, 40);
			tnameComponent.setFont(new Font(null, Font.PLAIN, 14));


			blood = new JLabel("Blood Type");
			blood.setFont(new Font(null, Font.PLAIN, 16));
			blood.setPreferredSize(new Dimension(280, 40));
			blood.setForeground(new Color(51,51,51));
			blood.setLayout(new BorderLayout());

			bloodSelector = new MyComboBox(bloodtype, userBlood, 280, 40);;
			bloodSelector.setFont(new Font(null, Font.PLAIN, 14));

			healthCondition = new JLabel("Health Conditions");
			healthCondition.setFont(new Font(null, Font.PLAIN, 16));
			healthCondition.setPreferredSize(new Dimension(280, 40));
			healthCondition.setForeground(new Color(51,51,51));
			healthCondition.setLayout(new BorderLayout());
			thealthConditionComponent = new MyTextArea(userHealth, 280, 250);
			
			errorLabel = new JLabel();
			errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			errorLabel.setVisible(false);
			errorLabel.setForeground(Color.red);

			topPanel.add(returnBtn,BorderLayout.WEST);
			topPanel.add(saveBtn,BorderLayout.EAST);
        	profilepcPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
			
			mainpcPanel.add(topPanel,BorderLayout.NORTH);
			profilepcPanel.add(name);
			profilepcPanel.add(tnameComponent);
			profilepcPanel.add(blood);
			profilepcPanel.add(bloodSelector);
			profilepcPanel.add(healthCondition);
			profilepcPanel.add(thealthConditionComponent);
			profilepcPanel.add(errorLabel);
			
			mainpcPanel.add(profilepcPanel,BorderLayout.CENTER);
			
			add(mainpcPanel);

			setBorder(new EmptyBorder(0, 0, 0, 0));
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		}
		
		public class ConfirmButtonListener implements ActionListener{
			
			public void actionPerformed(ActionEvent e) {
				
				if(inputValidation()) {
					try {
							String text = "";
							text += tnameComponent.getText() + "\n";
							text += (String)bloodSelector.getSelectedItem() + "\n";
							
							//if health condition is blank (can be allowed blank)
							if(thealthConditionComponent.getText().equals("")) {
								text += "Normal\n\n";
							}else {
								text += thealthConditionComponent.getText() + "\n\n";
							}
							//write into file
							writer = new FileWriter("Profile.txt");
							writer.write(text);
							
							reader.close();
						
						writer.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					//reset value to null
					resetValue();
					
					crd.show(cardPanel, "m");
				}
			}
			
		}
		
		public class CancelButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				//reset value to null
				resetValue();
				
				crd.show(cardPanel, "m");
			}
		}

		public void edit() {
			errorLabel.setVisible(false);
			crd.show(cardPanel, "pc");

			ArrayList<String> b = new ArrayList<>();
			b.add("AB"); b.add("A"); b.add("B"); b.add("O");		
			
			try {
				reader = new Scanner(file);
				
				while(reader.hasNextLine() ) {
					//put the existing data into the textbox
					tnameComponent.setText(reader.nextLine());
					bloodSelector.setSelectedIndex(b.indexOf(reader.nextLine()));
					thealthConditionComponent.setText(reader.nextLine());
					if(reader.nextLine() == "") {
						break;
					}
				}
			
				reader.close();

			} catch (FileNotFoundException e1){
				e1.printStackTrace();
			}
		}
		
		public void resetValue() {
			tnameComponent.setText("");
			bloodSelector.setSelectedIndex(0);
			thealthConditionComponent.setText("");
		}

		public boolean inputValidation() {
			String namePattern = "[A-Za-z'/ ]+";
			
			Boolean nameValidation = Pattern.matches(namePattern, tnameComponent.getText());
			
			if(tnameComponent.getText().equals("")) {
				errorLabel.setVisible(true);
				errorLabel.setText("Name Cannot Be Blank!");
				return false;
			}else if(!nameValidation){
				errorLabel.setVisible(true);
				errorLabel.setText("Name Cannot Contain Special Symbol or Numbers!");
				return false;
			}else {
				return true;
			}
		}
	}

	private class EditProfileButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			pc.edit();
		}
		
	}
	
	public void displayData() {
		profilerefreshPanel.removeAll();
	
		try {
			reader = new Scanner(file);

			while(reader.hasNextLine() ) {
				//put the existing data into the textbox
				userName=reader.nextLine();
				userBlood= reader.nextLine();
				userHealth=reader.nextLine();
				if(reader.nextLine() == "") {
					break;
				}
			}
			//body top
			namePicLabel = new JLabel(userName);
			namePicLabel.setFont(new Font(null, Font.BOLD, 20));
			namePicLabel.setPreferredSize(new Dimension(300, 150));
			namePicLabel.setIcon(new ImageIcon(MyPanel.resize("src/images/user-profile.png", 100, 100)));
			namePicLabel.setIconTextGap(10);

			//body 
			bloodTypePanel = this.addDisplayPanel("Blood Type", userBlood, 300, LABEL_HEIGHT, false);
			healthConditionPanel = this.addDisplayPanel("Health Condition", userHealth, 300, 250, true);

			subTitleLabel = new JLabel("Additional Details");
			subTitleLabel.setFont(new Font(null, Font.BOLD, 18));
			subTitleLabel.setPreferredSize(new Dimension(300, 20));
			subTitleLabel.setHorizontalAlignment(JLabel.LEFT);
			subTitleLabel.setBorder(new CompoundBorder(subTitleLabel.getBorder(), new EmptyBorder(0, 5, 0, 0)));

			profilePanel = MyPanel.addPanel(new FlowLayout(FlowLayout.CENTER, 5, 20), namePicLabel, String.valueOf(FlowLayout.CENTER), 300, 600);
			profilePanel.add(bloodTypePanel);
			profilePanel.add(subTitleLabel);
			profilePanel.add(healthConditionPanel);
			profilePanel.setPreferredSize(new Dimension(300, 700));

			profilerefreshPanel.add(profilePanel);

			reader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			profilerefreshPanel.revalidate();
			profilerefreshPanel.repaint();
		
	}
	
	public String getData(String category) {
		String cat = category;
		String name, blood, health, output = "";
		String[] first_name;
		
		try {
			reader = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		name = reader.nextLine();
		first_name = name.split(" ");
		blood = reader.nextLine();
		health = reader.nextLine();
		reader.close();
		
		switch(cat) {
			case "name":
				output = name;
				break;
			case "first_name":
				output = first_name[0];
				break;
			case "blood":
				output = blood;
				break;
			case "health":
				output = health;
				break;
		}
		return output;
	}

	
	public void createDB() {
		try {
			file = new File("Profile.txt");
			if (file.createNewFile()) {
		        System.out.println("File created: " + file.getName());
						writer = new FileWriter("Profile.txt");
						writer.write(userName + "\n");
						writer.write(userBlood + "\n");
						writer.write(userHealth + "\n\n");

						writer.close();
			} else {
		        System.out.println("File already exists in Profile.");
		
			}
			
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}
