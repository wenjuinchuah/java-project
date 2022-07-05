import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import templates.*;

public class Data extends MyPanel implements Displayable{
	private JPanel mainPanel, dataPanel, entryPanel, topPanel, historyPanel;
	public JPanel cardPanel;
	private JScrollPane scrollableEntryPane;
	private MyButton topBtn, sortBtn, ascdes;
	private JLabel totalEntry;
	private File file;
	private FileWriter writer;
	private Scanner reader;
	
	public CardLayout crd;
	
	public DataCreation dc;
	private Charts chartPanel;
	private Profile profile;
	
	private int displayMode = 0; // 0 = default, 1 = date ascending, 2 = date descending, 3 = weight ascending; 4 = weight descending; 5 = BMI ascending; 6 = BMI descending
	
	private final int SIZE = 6; //the size of 1 entry (how many lines)

	
	public Data(Profile p) {
		//create database file
		createDB();
		
		profile = p;
		
		// topPanel
        topPanel = this.topPanel("Body Mass Index", "Add Entry", "AddNewEntry", "Analytics");
		Color backgroundColor = new Color(244, 244, 244);
		Color foregroundColor = new Color(14, 156, 255);
		topBtn = new MyButton("Add Entry", foregroundColor, backgroundColor, 88, 21);
		topBtn.setFont(new Font(null, Font.PLAIN, 16));
		topBtn.setPreferredSize(new Dimension(120, 40));
		topBtn.addActionListener(new AddDataButtonListener());
		
		// historyPanel
		totalEntry = new JLabel("Total Entries: " + getEntriesCount());
		totalEntry.setFont(new Font(null, Font.BOLD, 19));
		totalEntry.setPreferredSize(new Dimension(200, 40));
		totalEntry.setBorder(new EmptyBorder(0, 20, 0 ,0));

		ascdes = new MyButton("  Asc", new Color(128, 128, 128), new Color(244, 244, 244));
		ascdes.setIcon(new ImageIcon(MyPanel.resize("src/images/sort.png", 12, 12)));
		ascdes.setFont(new Font(null, Font.PLAIN, 14));
		ascdes.setHorizontalAlignment(JButton.RIGHT);
		ascdes.setPreferredSize(new Dimension(80, 40));
		ascdes.setOpaque(false);
		ascdes.addActionListener(new AscDesButtonListener());

		sortBtn = new MyButton("Default", new Color(128, 128, 128), new Color(244, 244, 244));
		sortBtn.setHorizontalAlignment(JButton.LEFT);
		sortBtn.setFont(new Font(null, Font.PLAIN, 14));
		sortBtn.setPreferredSize(new Dimension(80, 40));
		sortBtn.setOpaque(false);
		sortBtn.addActionListener(new SortButtonListener());
		
		JPanel sortPanel = new JPanel(new BorderLayout());
		sortPanel.add(ascdes, BorderLayout.WEST);
		sortPanel.add(new JLabel(":"), BorderLayout.CENTER);
		sortPanel.add(sortBtn, BorderLayout.EAST);

		historyPanel = new JPanel(new BorderLayout());
		historyPanel.add(totalEntry, BorderLayout.WEST);
		historyPanel.add(sortPanel, BorderLayout.EAST);
		historyPanel.setPreferredSize(new Dimension(360, 40));

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		cardPanel = new JPanel();
		cardPanel.setPreferredSize(new Dimension(360, 740));
		cardPanel.setMaximumSize(new Dimension(360, 740));

		chartPanel = new Charts();
		chartPanel.setBackground(Color.WHITE);
		chartPanel.setOpaque(true);
		chartPanel.setLayout(new GridLayout(0,1));

		dataPanel = new JPanel();
		dataPanel.setPreferredSize(new Dimension(360, 400));
		dataPanel.setMaximumSize(new Dimension(360, 700));

		entryPanel = new JPanel();
		entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.Y_AXIS));
		scrollableEntryPane = new JScrollPane(entryPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollableEntryPane.setPreferredSize(new Dimension(330, 240));
		scrollableEntryPane.setBorder(null);
		
		crd = new CardLayout();
		dc = new DataCreation(crd, cardPanel);
		
		topPanel.add(topBtn,BorderLayout.EAST);
;
		dataPanel.add(historyPanel);
		dataPanel.add(scrollableEntryPane);
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(chartPanel,BorderLayout.CENTER);
		mainPanel.add(dataPanel,BorderLayout.SOUTH);
	
		mainPanel.setName("m");
		dc.setName("dc");
		
		cardPanel.setLayout(crd);
		cardPanel.add(mainPanel, mainPanel.getName());
		cardPanel.add(dc, dc.getName());
		
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
	
	//-------------------------------------------------------------------------------------------------------------
	// Class for the entry (the block in the scroll pane)
	//-------------------------------------------------------------------------------------------------------------
	private class Entry extends MyPanel{
		private JPanel buttonPanel, infoPanel, whPanel, iconPanel;
		private JButton edit, delete;
		private JLabel dateLabel, bmiLabel, heightLabel, weightLabel;
		private int id;
		
		public Entry(Struct s) {
			id = Integer.parseInt(s.getData("id"));
			buttonPanel = new JPanel(new BorderLayout());
			buttonPanel.setBackground(Color.white);
			infoPanel = new JPanel(new BorderLayout());
			infoPanel.setBorder(new RoundedBorder(10));

			edit = new JButton();
			edit.setIcon(new ImageIcon(MyPanel.resize("src/images/edit-pencil.png", 16, 16)));
			edit.setBorderPainted(false);
			edit.setFocusPainted(false);
			edit.setVerticalAlignment(JButton.TOP);
			edit.setBackground(Color.WHITE);
			edit.setPreferredSize(new Dimension(20,60));

			delete = new JButton();
			delete.setIcon(new ImageIcon(MyPanel.resize("src/images/delete-bin.png", 16, 16)));
			delete.setBorderPainted(false);
			delete.setFocusPainted(false);
			delete.setVerticalAlignment(JButton.TOP);
			delete.setBackground(Color.WHITE);
			delete.setPreferredSize(new Dimension(20,60));
		
			edit.addActionListener(new EditButtonListener(this.getID()));
			edit.addChangeListener(e -> {
				edit.setCursor(new Cursor(Cursor.HAND_CURSOR));
			});

			delete.addActionListener(new DeleteButtonListener(this.getID()));
			delete.addChangeListener(e -> {
				delete.setCursor(new Cursor(Cursor.HAND_CURSOR));
			});
			
			float bmi = 0;
			bmi = Utility.calculateBMI(s.getData("height"), s.getData("weight"));
			String[] date = s.getData("date").split("-");
			String newDate = date[0] + "/" + date[1] + "/" + date[2];
			try {
				dateLabel = new JLabel(s.getDaysOfWeek(newDate) + " " + date[0] + "/" + date[1]);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			dateLabel.setFont(new Font(null, Font.PLAIN, 16));
			dateLabel.setPreferredSize(new Dimension(80, 50));	

			bmiLabel = new JLabel(Float.toString(bmi));
			bmiLabel.setFont(new Font(null, Font.BOLD, 21));
			bmiLabel.setHorizontalAlignment(JLabel.CENTER);
			bmiLabel.setPreferredSize(new Dimension(10, 50));

			heightLabel = new JLabel("H: " + s.getData("height") + "cm");
			heightLabel.setFont(new Font(null, Font.PLAIN, 12));
			heightLabel.setPreferredSize(new Dimension(60, 14));

			weightLabel = new JLabel("W: " + s.getData("weight") + "kg");
			weightLabel.setFont(new Font(null, Font.PLAIN, 12));
			weightLabel.setPreferredSize(new Dimension(60, 14));

			whPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
			whPanel.add(heightLabel);
			whPanel.add(weightLabel);
			whPanel.setPreferredSize(new Dimension(60, 60));
			whPanel.setBackground(Color.WHITE);
			
			iconPanel = MyPanel.addPanel(new FlowLayout(),whPanel, edit, delete, 120, 60);

			infoPanel.add(dateLabel, BorderLayout.WEST);
			infoPanel.add(bmiLabel, BorderLayout.CENTER);
			infoPanel.add(iconPanel,BorderLayout.EAST);
			infoPanel.setPreferredSize(new Dimension(290, 60));
			infoPanel.setOpaque(true);
			infoPanel.setBackground(Color.WHITE);

			add(infoPanel);
			this.revalidate();
		}
		
		public int getID() {
			return this.id;
		}
	}
	
	
	//button listener to add new entry
	private class AddDataButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {	
			crd.show(cardPanel, "dc");
		}
	}
	
	//button listener to edit entry
	private class EditButtonListener implements ActionListener{
		
		private int id;
		
		public EditButtonListener(int i){
			id = i;
		}

		public void actionPerformed(ActionEvent e) {
			dc.setID(id);
			dc.edit();
		}
	}
	
	//button listener to delete entry
	private class DeleteButtonListener implements ActionListener{
		private int id = 0;
		private String text = "";
		
		public DeleteButtonListener(int num){
			id = num;
		}
		
		public void actionPerformed(ActionEvent e) {
			String temp = "";
			try {
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
				
				//skip 1 entry
				for(int i = 0; i < SIZE; i ++) {
					reader.nextLine();
				}
				
				while(reader.hasNextLine()) {
					text += reader.nextLine() + "\n";
				}
				
				writer = new FileWriter("Database.txt");
				writer.write(text);
				
				reader.close();
				writer.close();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			displayData();
		}
		
	}
	
	//button listener to choose sort category
	private class SortButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			switch(displayMode) {
				case 0:
					displayMode = 1;
					sortBtn.setText("Date");
					break;
				case 1:
					displayMode = 3;
					sortBtn.setText("Weight");
					break;
				case 2:
					displayMode = 4;
					sortBtn.setText("Weight");
					break;
				case 3:
					displayMode = 5;
					sortBtn.setText("BMI");
					break;
				case 4:
					displayMode = 6;
					sortBtn.setText("BMI");
					break;
				case 5:
				case 6:
					displayMode = 0;
					ascdes.setText("Asc");
					sortBtn.setText("None");
					break;
			}
			displayData();
		}
	}
	
	//button listener to choose sort by ascending or descending
	private class AscDesButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			switch(displayMode) {
				case 0:
					break;
				case 1:
					displayMode = 2;
					ascdes.setText("Desc");
					break;
				case 2:
					displayMode = 1;
					ascdes.setText("  Asc");
					break;
				case 3:
					displayMode = 4;
					ascdes.setText("Desc");
					break;
				case 4:
					displayMode = 3;
					ascdes.setText("  Asc");
					break;
				case 5:
					displayMode = 6;
					ascdes.setText("Desc");
					break;
				case 6:
					displayMode = 5;
					ascdes.setText("  Asc");
					break;
			}
			displayData();
		}
	}
	

	//-------------------------------------------------------------------------------------------------------------
	// Panel for the chart
	//-------------------------------------------------------------------------------------------------------------
	private class Charts extends JPanel {
		float[] sortedBMI;
		Date[] sortedDate;
		
		public Charts() {
		}
		
		public Charts(float[] bmi, Date[] date){
			sortedBMI = bmi;
			sortedDate = date;
			initUI();
		}
		
		private void initUI() {
			XYDataset dataset = createDataset();
			JFreeChart chart = createChart(dataset);
			
			ChartPanel chartPanel = new ChartPanel(chart);
			chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			chartPanel.setBackground(Color.WHITE);
			chartPanel.setPreferredSize(new Dimension(MyFrame.WIDTH, 300));
			chartPanel.setMaximumSize(new Dimension(MyFrame.WIDTH, 300));
			add(chartPanel);
		}
		
		private XYDataset createDataset() {
			TimeSeries data = new TimeSeries(profile.getData("first_name"));
			TimeSeriesCollection dataset = new TimeSeriesCollection();
			dataset.addSeries(data);
			
			for(int i = 0; i < getEntriesCount(); i++) {
				data.addOrUpdate(new Day(sortedDate[i]), sortedBMI[i]);
			}
	        return dataset;
		}
		
		private JFreeChart createChart(XYDataset dataset) {
	        JFreeChart chart = ChartFactory.createXYLineChart("", "", "", dataset, PlotOrientation.VERTICAL, true, false, false);

	        XYPlot plot = chart.getXYPlot();

	        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
	        renderer.setSeriesPaint(0, new Color(252, 124, 124));
	        renderer.setDefaultStroke(new BasicStroke(2.0f));

			DateAxis d = new DateAxis();
	        plot.setDomainAxis(d);
	        d.setDateFormatOverride(new SimpleDateFormat("dd/MM"));

	        plot.setRenderer(renderer);
	        plot.setBackgroundPaint(Color.white);

	        plot.setRangeGridlinesVisible(true);
	        plot.setRangeGridlinePaint(Color.GRAY);

	        plot.setDomainGridlinesVisible(true);
	        plot.setDomainGridlinePaint(Color.GRAY);

	        chart.getLegend().setFrame(BlockBorder.NONE);

			chart.setTitle(new TextTitle("BMI against Date", new Font(null, Font.PLAIN, 14)));
			chart.setPadding(new RectangleInsets(0,0,10,0));
	        return chart;
	    }
	}
	
	//display entry
	public void displayData() {
		
		int count = getEntriesCount();
		int[] ids = new int[count];	//array of user id(num)
		String h, w, d, t, te;
		Struct[] list = new Struct[count]; // array of Struct which constructor has all user details
		Struct[] sortedList = new Struct[count];
		float[] sortedBMI = new float[count];
		Date[] sortedDate = new Date[count];

		entryPanel.removeAll(); //remove everything from the panel
		chartPanel.removeAll(); 
		
		totalEntry.setText("Total Entries: "+getEntriesCount()); //refreshing the counter
		
		try {
			reader = new Scanner(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		//read all text in database.txt
		for(int i = 0; i < count; i++) {
			h = reader.nextLine();
			w = reader.nextLine();
			d = reader.nextLine();
			t = reader.nextLine();
			te = reader.nextLine();
			reader.nextLine();
			
			ids[i] = i;	//user id(num)
			list[i] = new Struct(i, h, w, d, t, te); //Store the struct in each array
			sortedDate[i] = Utility.dateFormat(d);
			sortedBMI[i] = Utility.calculateBMI(h, w);
		}
		
		sortedList = Sorting.sortDateAsc(list);
		
		reader.close();
		
		switch(displayMode) {
			case 0: //default 
				break;
			case 1: //date ascending
				sortedList = Sorting.sortDateAsc(list);
				break;
			case 2: //date descending,
				sortedList = Sorting.sortDateDes(list);
				break;
			case 3: //weight ascending 
				sortedList = Sorting.sortWeightAsc(list);
				break;
			case 4: //weight descending
				sortedList = Sorting.sortWeightDes(list);
				break;
			case 5: //BMI ascending
				sortedList = Sorting.sortBMIAsc(list);
				break;
			case 6: //BMI descending
				sortedList = Sorting.sortBMIDes(list);
				break;
		}
		
		//sorting date and time for charts
		for(int i = 0; i<count-1; i++) {
			Date tempD;
			float tempBMI;
			int smallest = i;
			for(int x = i+1; x < count; x++) {
				
				if(sortedDate[smallest].compareTo(sortedDate[x]) > 0) 
					smallest = x;
				
			}
			tempD = sortedDate[i];
			sortedDate[i] = sortedDate[smallest];
			sortedDate[smallest] = tempD;
			
			tempBMI = sortedBMI[i];
			sortedBMI[i] = sortedBMI[smallest];
			sortedBMI[smallest] = tempBMI;
		}
		
		for(Struct data: sortedList){  
			entryPanel.add(new Entry(data));  
		}  

		chartPanel.add(new Charts(sortedBMI, sortedDate));

		reader.close();
		
		chartPanel.revalidate();
		chartPanel.repaint();
		entryPanel.revalidate();
		entryPanel.repaint();	
	}
		
	//get entry count from the database.txt
	private int getEntriesCount() {
		int count = 0;
		
		try {
			reader = new Scanner(file);
			while(reader.hasNextLine() ) {
				if(reader.nextLine() == "") {
					count++;
				}
			}
			reader.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
		
		return count;
	}

	//Creating database (txt file)
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
