package entity;

import java.io.*;
import java.util.ArrayList;

public class FileManager {
	private Object objectFromTheFile = null;
	private String fileName = "files\\data.dat";
	private FileOutputStream datafile;
	private ObjectOutputStream myWriter;
	private ObjectInputStream readObjectFromFile;

	public FileManager() {
		 
		try {
			datafile = new FileOutputStream(fileName);
			myWriter = new ObjectOutputStream(datafile);
			readObjectFromFile = new ObjectInputStream(new FileInputStream(fileName));

		} catch (FileNotFoundException e) {
			System.out.println("File Not Found " + e);
		} catch (IOException e) {

			e.printStackTrace();
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

				plantType = new PlantType(plantInformation[0], plantInformation[1], plantInformation[2], plantInformation[3], plantInformation[4], plantInformation[5], plantInformation[6], plantInformation[7]);
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

	public void creatAFileIFNotExist() {
		try {

			datafile.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found " + e);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeObjectToFile(Object obj) {
		
			try {

				myWriter.writeObject(obj);
				myWriter.flush();// Flushes the stream. This will write any bufferedoutput bytes.
			//	myWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	
	}

	public Object readObjectFromFile() {	
	
			try {
				objectFromTheFile = readObjectFromFile.readObject();
				readObjectFromFile.close();
			} catch (EOFException e) {
			    // End of file reached unexpectedly
			    System.err.println("End of file reached unexpectedly.");
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		
		return objectFromTheFile;

	}

}
