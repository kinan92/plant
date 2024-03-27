package entity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.function.Consumer;

public class File {
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

	public void creatAFileIFNotExsest() {
		try {

			datafile.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found " + e);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void writeObjectToFile(Object obj) {
		Thread fileThread = new Thread(() -> {
			try {

				myWriter.writeObject(obj);
				myWriter.flush();// Flushes the stream. This will write any bufferedoutput bytes.
			//	myWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
		fileThread.start();
	}

	public Object readObjectFromFile(Consumer<Object> callback) {

		Thread fileThread = new Thread(() -> {
			try {
				//readObjectFromFile = new ObjectInputStream(new FileInputStream(fileName));
				// Read object from the file
				 while ((objectFromTheFile = readObjectFromFile.readObject()) != null) {
		                callback.accept(objectFromTheFile);
		            }
			//	readObjectFromFile.close();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		});

		fileThread.start();
		return objectFromTheFile;

	}

}
