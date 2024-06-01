package entity;

import controller.Controller;

import java.io.*;
import java.util.ArrayList;

/**
 * The FileManager class handles reading and writing files
 * as well as being an autosave thread
 * @author Petri Närhi
 * */
public class FileManager extends Thread {
	private Controller controller;
	private String plantsFile = "files\\plants.dat";

	/**
	 * Constructor for FileManager
	 * initializes controller variable
	 * @param controller Controller
	 * @author Petri Närhi
	 * */
	public FileManager(Controller controller) {
		this.controller = controller;
	}

	/**
	 * Run-method for the thread part of the class
	 * Saves user data every 60 seconds
	 * @author Petri Närhi
	 * */
	@Override
	public void run() {
		while (!Thread.interrupted() ){
			try {
				sleep(60000);
				controller.saveUserData();
			} catch (InterruptedException e) {}
		}
	}

	/**
	 * Reads PlantType information from a TextFile and creates PlantType objects from the information
	 * creates ArrayList of these objects
	 * @return plantTypes ArrayList
	 * @author Elvira Grubb (main)
	 * @author Petri Närhi (edits & refactor)
	 */
	public ArrayList<PlantType> loadPlantTypes()
	{
		try {
			BufferedReader br = new BufferedReader( new FileReader("files/plantTypes.txt"));
			PlantType plantType;
			ArrayList<PlantType> plantTypes = new ArrayList<>();
			String string = br.readLine();

			while(string != null) {
				String[] plantInformation;
				plantInformation = string.split( "," );

				plantType = new PlantType(plantInformation[0], plantInformation[1], plantInformation[2], plantInformation[3], plantInformation[4], plantInformation[5], plantInformation[6], plantInformation[7], plantInformation[8], plantInformation[9], plantInformation[10]);
				plantTypes.add(plantType);
				string = br.readLine();
			}
			br.close();
			return plantTypes;
		} catch( IOException e ) {
			System.out.println( "readPlantType: " + e );
			return null;
		}
	}

	/**
	 * Reads pots from a TextFile and creates pot objects from the information
	 * creates ArrayList of these objects
	 * @return pots ArrayList
	 * @author Elvira Grubb (main)
	 * @author Petri Närhi (edits & refactor)
	 */
	public ArrayList<Pot> loadPots()
	{
		try {
			BufferedReader br = new BufferedReader( new FileReader("files/pot.txt"));
			Pot pot;
			ArrayList<Pot> pots = new ArrayList<>();
			String string = br.readLine();

			while(string != null) {
				String[] potInformation;
				potInformation = string.split( "," );

				pot = new Pot(potInformation[0], potInformation[1], potInformation[2]);
				pots.add(pot);
				string = br.readLine();
			}
			br.close();
			return pots;
		} catch( IOException e ) {
			System.out.println( "readPlantType: " + e );
			return null;
		}
	}

	/**
	 * Writes plants to file
	 * Takes the list of plants from controller and writes them to a dat-file
	 * using buffered object output stream
	 * @param listOfPlants ArrayList of plants
	 * @author Petri Närhi
	 * */
	public void writePlantsToFile(ArrayList<Plant> listOfPlants) throws IOException
	{
		try (ObjectOutputStream oos = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream(plantsFile)))) {
			oos.writeInt(listOfPlants.size());
			for (Plant p : listOfPlants) {
				oos.writeObject(p);
			}
			oos.flush();
		}
	}

	/**
	 * Reads plants from file
	 * Reads list of plants from a dat-file using buffered object input stream
	 * @return listOfPlants ArrayList of plants
	 * @author Petri Närhi
	 * */
	public ArrayList<Plant> readPlantsFromFile() throws IOException
	{
		ArrayList<Plant> listOfPlants = new ArrayList<>();
		try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(plantsFile))) ) {
			int n = ois.readInt();
			for (int i = 0; i < n; i++) {
				try {
					listOfPlants.add((Plant) ois.readObject());
				} catch(ClassNotFoundException e) {}
			}
		}
		return listOfPlants;
	}
}
