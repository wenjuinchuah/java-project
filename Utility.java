import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
	static SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
	static DecimalFormat df = new DecimalFormat();
	
	public static Date dateFormat(String date) {
		Date output = null;
		try {
			return sdformat.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}
	
	public static float calculateBMI(String height, String weight) {
		df.setMaximumFractionDigits(1);
		df.setMinimumFractionDigits(1);
		float output = Float.parseFloat(weight) / ((Float.parseFloat(height)/100) * (Float.parseFloat(height)/100));
		output = Float.parseFloat(df.format(output));
		return output;
	}
}
