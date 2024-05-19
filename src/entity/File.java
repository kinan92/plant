package entity;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class File  {
	private Object objectFromTheFile = null;
	private String fileName = "files\\data.dat";
	private FileOutputStream datafile;
	private ObjectOutputStream myWriter;
	private ObjectInputStream readObjectFromFile;

	public File() {
		 
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