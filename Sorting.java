import java.util.Date;

public class Sorting {
	static int count;
	
	static Struct temp;
	static int tempid;
		
	public static Struct[] sortDateAsc(Struct[] list) {
		count = list.length;
		
		for(int i = 0; i<count-1; i++) {
			int smallest = i;
			for(int x = i+1; x < count; x++) {
				Date d1 = Utility.dateFormat(list[smallest].getData("date"));
				Date d2 = Utility.dateFormat(list[x].getData("date"));
				if(d1.compareTo(d2) > 0) 
					smallest = x;
				
			}
			temp = list[i];
			list[i] = list[smallest];
			list[smallest] = temp;
		}
		
		return list;
	}
	
	public static Struct[] sortDateDes(Struct[] list) {
		count = list.length;

		for(int i = 0; i<count-1; i++) {
			int biggest = i;
			for(int x = i+1; x < count; x++) {
				Date d1 = Utility.dateFormat(list[biggest].getData("date"));
				Date d2 = Utility.dateFormat(list[x].getData("date"));
				if(d1.compareTo(d2) < 0) 
					biggest = x;
				
			}
			temp = list[i];
			list[i] = list[biggest];
			list[biggest] = temp;
		}
		
		return list;
	}
	
	public static Struct[] sortWeightAsc(Struct[] list) {
		count = list.length;
		
		for(int i = 0; i<count-1; i++) {
			int smallest = i;
			for(int x = i+1; x < count; x++) {
				String w1 = list[smallest].getData("weight");
				String w2 = list[x].getData("weight");
				if(Float.parseFloat(w1) > Float.parseFloat(w2)) 
					smallest = x;
				
			}
			temp = list[i];
			list[i] = list[smallest];
			list[smallest] = temp;
		}
		
		return list;
	}
	public static Struct[] sortWeightDes(Struct[] list) {
		count = list.length;

		for(int i = 0; i<count-1; i++) {
			int biggest = i;
			for(int x = i+1; x < count; x++) {
				String w1 = list[biggest].getData("weight");
				String w2 = list[x].getData("weight");
				if(Float.parseFloat(w1) < Float.parseFloat(w2)) 
					biggest = x;
				
			}
			temp = list[i];
			list[i] = list[biggest];
			list[biggest] = temp;

		}
		
		return list;
	}
	public static Struct[] sortBMIAsc(Struct[] list) {
		count = list.length;
		
		for(int i = 0; i<count-1; i++) {
			int smallest = i;
			for(int x = i+1; x < count; x++) {
				float bmi1 = Utility.calculateBMI(list[smallest].getData("height"), list[smallest].getData("weight"));
				float bmi2 = Utility.calculateBMI(list[x].getData("height"), list[x].getData("weight"));
				if(bmi1 > bmi2) 
					smallest = x;
				
			}
			temp = list[i];
			list[i] = list[smallest];
			list[smallest] = temp;
			
		}
		
		return list;
	}
	public static Struct[] sortBMIDes(Struct[] list) {
		count = list.length;
		
		for(int i = 0; i<count-1; i++) {
			int smallest = i;
			for(int x = i+1; x < count; x++) {
				float bmi1 = Utility.calculateBMI(list[smallest].getData("height"), list[smallest].getData("weight"));
				float bmi2 = Utility.calculateBMI(list[x].getData("height"), list[x].getData("weight"));
				if(bmi1 < bmi2) 
					smallest = x;
				
			}
			temp = list[i];
			list[i] = list[smallest];
			list[smallest] = temp;

		}
		
		return list;
	}
}
