import java.io.File;
import java.io.IOException;

import javax.swing.UIManager;

import templates.MyFrame;

public class Project{
	private static File file;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			
			file = new File("Database.txt");
			// if user data exist
			if (file.createNewFile()) {
				new MyFrame("Health Diary", new StartPage());
			} 
			 // use another frame
			 // for presentation purpose, PageSelector() is replaced to StartPage()
			 else {
			 	new MyFrame("Health Diary", new StartPage());
			 }
		 } catch (IOException e) {
		 	System.out.println("An error occurred.");
		 	e.printStackTrace();
		 } catch (Exception e) {
			e.printStackTrace(); 
		}
	}
}
