import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import templates.Editable;
import templates.MyButton;
import templates.MyComboBox;
import templates.MyFrame;
import templates.MyPanel;
import templates.MyTextField;
import templates.RoundedBorder;

//-------------------------------------------------------------------------------------------------------------
// Panel for data creation and edit
//-------------------------------------------------------------------------------------------------------------
public class DataCreation extends MyPanel implements Editable{
	private JPanel maindcPanel, entrydataPanel, topPanel, heightPanel;
	private JPanel weightPanel, datelabelPanel, timePanel, bodyTemperaturePanel, buttonPanel;
	private JLabel errorLabel;
	private MyTextField theight, tweight, ttemp;
	private JButton returnBtn, saveBtn;
	private MyButton cancel;
	private UtilDateModel model;
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	private JComboBox<String> timeSelector;
	
	private String[] timeList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00"
								, "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
	private String time = "";
	private FileWriter writer;
	private Scanner reader;
	private File file = new File("Database.txt");
	
	private CardLayout crd;
	private JPanel cardPanel;
	
	private final int SIZE = 6;
	
	//for determining edit or create (used in ConfirmButtonActionListener)
	private int id = -1; //-1 = create, else = edit
	
	public DataCreation(CardLayout c, JPanel panel) {
		crd = c;
		cardPanel = panel;
		
		maindcPanel = new JPanel();
		maindcPanel.setLayout(new BorderLayout());

		Color foregroundColor = new Color(51,51,51);
		Color backgroundColor = Color.WHITE;
		returnBtn = new MyButton("Back", foregroundColor, backgroundColor, 60, 50);
		returnBtn.addActionListener(new CancelButtonListener());
		foregroundColor = new Color(14, 156, 255);
		
		saveBtn = new MyButton("Save", foregroundColor, backgroundColor, 60, 50);
		saveBtn.addActionListener(new ConfirmButtonListener());

		topPanel = this.topPanelReturn("Entry", "Save", returnBtn, saveBtn, "actionPanel", "returnPanel");

		//date picker
		model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		// Resize Button
		datePicker.getComponent(1).setPreferredSize(new Dimension(20,40));
		datePicker.getComponent(1).setBackground(Color.WHITE);
		datePicker.getComponent(1).setFocusable(false);

		// Resize TextField
		JFormattedTextField textField = datePicker.getJFormattedTextField();
		textField.setPreferredSize(new Dimension(280, 40));
        textField.setBorder(new RoundedBorder(10));
        textField.setForeground(new Color(51,51,51));
        textField.setBackground(Color.WHITE);
        textField.setOpaque(true);
		textField.setFont(new Font(null, Font.PLAIN, 14));

		//Label panels
		heightPanel = this.addTextFieldPanel("Height", "cm", (280-20)/2, 40);
		heightPanel.setPreferredSize(new Dimension((280-20)/2, 80));
		weightPanel = this.addTextFieldPanel("Weight", "kg", (280-20)/2, 40 );
		weightPanel.setPreferredSize(new Dimension((280-20)/2, 80));
		datelabelPanel = this.addTextFieldPanel("Date", "", 280, 40 );
		datelabelPanel.setPreferredSize(new Dimension(280, 80));
		timePanel = this.addTextFieldPanel("Time", "", 280, 40);
		timePanel.setPreferredSize(new Dimension(280, 80));
		bodyTemperaturePanel = this.addTextFieldPanel("Body Temperature", "\u00B0C", 280, 40);
		bodyTemperaturePanel.setPreferredSize(new Dimension(280, 80));

		//Entry Elements
		theight = new MyTextField("", (280-20)/2, 40);
		theight.setFont(new Font(null, Font.PLAIN, 14));
		tweight = new MyTextField("", (280-20)/2, 40);
		tweight.setFont(new Font(null, Font.PLAIN, 14));
		timeSelector = new MyComboBox(timeList, time, 280, 40);
		timeSelector.setBackground(Color.WHITE);

		ttemp = new MyTextField("", 280, 40);
		cancel = new MyButton("Reset", Color.WHITE, new Color(252, 124, 124));
		cancel.setPreferredSize(new Dimension(280, 40));
		cancel.setFont(new Font(null, Font.BOLD, 16));
		cancel.addActionListener(new CancelButtonListener());
		
		errorLabel = new JLabel();
		errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		errorLabel.setVisible(false);
		errorLabel.setForeground(Color.red);

		buttonPanel = MyPanel.addPanel(new FlowLayout(), cancel, 300, 70);
    	buttonPanel.setOpaque(false);
		
		topPanel.add(returnBtn,BorderLayout.WEST);
		topPanel.add(saveBtn,BorderLayout.EAST);

		//add element to label panel
		heightPanel.add(theight,BorderLayout.CENTER);
		weightPanel.add(tweight,BorderLayout.CENTER);
		datelabelPanel.add(datePicker,BorderLayout.CENTER);
		timePanel.add(timeSelector,BorderLayout.CENTER);
		bodyTemperaturePanel.add(ttemp,BorderLayout.CENTER);

		entrydataPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
		entrydataPanel.setPreferredSize(new Dimension(MyFrame.WIDTH, MyFrame.HEIGHT-50));
		entrydataPanel.add(heightPanel);
		entrydataPanel.add(weightPanel);
		entrydataPanel.add(datelabelPanel);
		entrydataPanel.add(timePanel);
		entrydataPanel.add(bodyTemperaturePanel);
		entrydataPanel.add(errorLabel);
		entrydataPanel.add(buttonPanel);

		maindcPanel.add(topPanel,BorderLayout.NORTH);
		maindcPanel.add(entrydataPanel,BorderLayout.CENTER);

		add(maindcPanel);
		setBackground(Color.WHITE);
    	setOpaque(true);
		setPreferredSize(new Dimension(360, 500));

	}
	
	//Button listener for save button
	private class ConfirmButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			if(inputValidation()) {
				Date selectedDate = (Date) datePicker.getModel().getValue();
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				String reportDate = df.format(selectedDate);
				
				try {
					if(id == -1) { //if its create new entry
						writer = new FileWriter("Database.txt", true);
						writer.write(theight.getText() + "\n");
						writer.write(tweight.getText() + "\n");
						writer.write(reportDate + "\n");
						writer.write(timeSelector.getSelectedItem() + "\n");
						writer.write(ttemp.getText() + "\n\n");
						writer.close();
					}else { //if its edit entry
						
						String text = "", temp = "";
						
						//rewrite the whole file
						reader = new Scanner(file);
						for(int i = 0; i < id; i++) {
							while(reader.hasNextLine() ) {
								temp = reader.nextLine();
								if(temp == "") {
									break;
								}
								text += temp + "\n";
							}
							text += "\n";
						}
						
						text += theight.getText() + "\n";
						text += tweight.getText() + "\n";
						text += reportDate + "\n";
						text += timeSelector.getSelectedItem() + "\n";
						text += ttemp.getText() + "\n\n";
						
						//skip 1 entry
						for(int i = 0; i < SIZE; i ++) {
							reader.nextLine();
						}
						
						while(reader.hasNextLine()) {
							text += reader.nextLine() + "\n";
						}
							
						//write into file
						writer = new FileWriter("Database.txt");
						writer.write(text);
						
						id = -1;
						datePicker.getComponent(1).setEnabled(true);

						reader.close();
					}
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
	
	//button listener for back
	private class CancelButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			id = -1;
			datePicker.getComponent(1).setEnabled(true);

			//reset value to null
			resetValue();
			
			if (e.getSource() == returnBtn){
				crd.show(cardPanel, "m");
			}
		}
	}
	
	public void edit() {
		String temp = "";
		int year, month, day;
		
		ArrayList<String> t = new ArrayList<>();
		t.add("0:00"); t.add("1:00"); t.add("2:00"); t.add("3:00"); t.add("4:00"); t.add("5:00"); t.add("6:00");
		t.add("7:00"); t.add("8:00"); t.add("9:00"); t.add("10:00"); t.add("11:00"); t.add("12:00"); t.add("13:00");
		t.add("14:00"); t.add("15:00"); t.add("16:00"); t.add("17:00"); t.add("18:00"); t.add("19:00"); t.add("20:00");
		t.add("21:00"); t.add("22:00"); t.add("23:00");

		crd.show(cardPanel, "dc");	
		datePicker.getComponent(1).setEnabled(false);
	
		try {
			reader = new Scanner(file);
			for(int i = 0; i < id; i++) {
				while(reader.hasNextLine() ) {
					if(reader.nextLine() == "") {
						break;
					}
				}
			}
			
			//put the existing data into the textbox
			theight.setText(reader.nextLine());
			tweight.setText(reader.nextLine());
			
			//for date
			temp = reader.nextLine();
			String date[] = temp.split("-", 0);
			day = Integer.parseInt(date[0]);
			month = Integer.parseInt(date[1]);
			year = Integer.parseInt(date[2]);
			model.setDate(year, month-1, day);
			model.setSelected(true);
			
			timeSelector.setSelectedIndex(t.indexOf(reader.nextLine()));
			ttemp.setText(reader.nextLine());
			
			
			reader.close();
			
		} catch (FileNotFoundException e1){
			e1.printStackTrace();
		}
	}
	
	public void resetValue() {
		theight.setText("");
		tweight.setText("");
		ttemp.setText("");
		datePicker.getJFormattedTextField().setText("");
		timeSelector.setSelectedIndex(0);
		errorLabel.setVisible(false);
	}

	public boolean inputValidation() {
		String floatPattern = "[0-9.]+";
		String temp = "";
		Date inputDate = (Date) datePicker.getModel().getValue();
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String reportDate = df.format(inputDate);
		
		Boolean heightValidation = Pattern.matches(floatPattern, theight.getText());
		Boolean weightValidation = Pattern.matches(floatPattern, tweight.getText());
		Boolean tempValidation = Pattern.matches(floatPattern, ttemp.getText());
		

		//validate same date
		if(id==-1)
		{
			try {
			
				reader = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			while(reader.hasNextLine() ) {
				temp = reader.nextLine();
				if(temp.equals(reportDate)) {
					errorLabel.setVisible(true);
					errorLabel.setText("Duplicate Date! Delete Entry or Select Another Date");
					return false;
				}
			}
		}
		
		
		//if got empty input
		if(theight.getText().equals("") || tweight.getText().equals("") || datePicker.getModel().getValue() == null || ttemp.getText().equals("")) {
			errorLabel.setVisible(true);
			errorLabel.setText("Please Enter All The Required Data!");
			return false;
		}else if((!heightValidation)||(Float.parseFloat(theight.getText())>300)){ //if height is not an int or float
			errorLabel.setVisible(true);
			errorLabel.setText("Please Enter Proper Height Value!");
			return false;
		}else if((!weightValidation) || (Float.parseFloat(tweight.getText())>700)) { //if weight is not an int or float
			errorLabel.setVisible(true);
			errorLabel.setText("Please Enter Proper Weight Value!");
			return false;
		}else if((!tempValidation) ||(Float.parseFloat(ttemp.getText())>40)){ //if body temperature is not an int or float
			errorLabel.setVisible(true);
			errorLabel.setText("Please Enter Proper Body Temperature Value!");
			return false;
		}else {
			return true;
		}
	}
	
	public void setID(int i) {
		id = i;
	}

}