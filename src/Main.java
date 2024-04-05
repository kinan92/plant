import boundary.ChoosePlantFrame;
import boundary.mainGUI;
import entity.File;

public class Main {

	public static void main(String[] args) {
		new mainGUI();
	        File file= new File();
	        String x= "i am in the file ";
	        file.writeObjectToFile(x);
	   
	       

	}

}
