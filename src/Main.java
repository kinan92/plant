import boundary.mainGUI;
import entity.File;

public class Main {

	public static void main(String[] args) {
		  System.out.println("hello");
	        mainGUI maingui = new mainGUI();
	        File file= new File();
	        String x= "i am in the file ";
	        file.writeObjectToFile(x);
	   
	       

	}

}
