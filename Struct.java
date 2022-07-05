import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import templates.Retrievable;

//Use to hold data for sorting and display purposes
public class Struct implements Retrievable{
	private int id;
	private String weight, height, date, time, temp;
	
	public Struct(int i, String h, String w, String d, String t, String te){
		id = i;
		height = h;
		weight = w;
		date = d;
		time = t;
		temp = te;
	}

	public String getData(String category) {
		String output = "";
		
		switch(category) {
			case "id":
				output = Integer.toString(id);
				break;
			case "height":
				output = height;
				break;
			case "weight":
				output = weight;
				break;
			case "date":
				output = date;
				break;
			case "time":
				output = time;
				break;
			case "temp":
				output = temp;
				break;
		}
		return output;
	}

	public String getDaysOfWeek(String date) throws ParseException {
		Calendar c = Calendar.getInstance();
		Date newDate = new SimpleDateFormat("dd/M/yyyy").parse(date);
		c.setTime(newDate);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

		switch (dayOfWeek) {
			case 1:
				return "Sun";
			case 2:
				return "Mon";
			case 3:
				return "Tue";
			case 4:
				return "Wed";
			case 5:
				return "Thu";
			case 6:
				return "Fri";
			case 7:
				return "Sat";
		}
		return "";
	}
}

