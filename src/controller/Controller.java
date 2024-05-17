package controller;

import boundary.*;
import entity.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import boundary.MainMenu;
import java.util.Random;

import javax.swing.*;

public class Controller {
	private ArrayList<Plant> listOfPlants = new ArrayList<>();
	private Plant plant;
	private MainMenu window;
	MainFrame mainFrame;
	ArrayList<PlantType> plantTypes = new ArrayList<>();
	ArrayList<Pot> pots = new ArrayList<>();
	private Timer waterDecreaseTimer;
	private Timer ageTimer;
	private boolean isPaused = false;
	private Random random = new Random();

	public Controller() {
		//this.window = new MainMenu(this);
		mainFrame = new MainFrame(this);
		mainFrame.addMainMenu();
		loadPlantTypes();
		loadPots();
		test();
		/*Chinese Money Plant
		plant = new Plant("TestPlanta", 0, "images/plants/moneyplant.png",50, );
		listOfPlants.add(plant);*/
		startWaterDecreaseTimer();
		startAgeTimer();
	}

	/**
	 * Creates a new plant object based on the choice of the user
	 * called by a boundary class
	 * @param i int, the index of the chosen plant in the GUI
	 * @author Petri Närhi
	 * */
	public void createPlant(int plantNumber, int potNumber) {
		PlantType type = plantTypes.get(plantNumber);
		String name;
		do {
			name = JOptionPane.showInputDialog("Give your plant a name!");
		} while (name == null || name.isEmpty());
		int initialWaterLevel = random.nextInt(21) * 5; //divisible by 5 so the watering will work as intended
		if (initialWaterLevel == 0) {
			initialWaterLevel = 5;
		}
		LocalDateTime dateAndTime = LocalDateTime.now();

		Plant newPlant = new Plant(name, 0, initialWaterLevel, type, PlantStateEnum.little, dateAndTime, pots.get(potNumber)); //ny planta är alltid liten
		listOfPlants.add(newPlant);
		plant = newPlant;
		System.out.println("New plant! " + plant);
		showPlantView();
		mainFrame.getPlantView().updatePlantDetails(plant);
	}


	private void startAgeTimer(){
		if (ageTimer == null){
			ageTimer = new Timer(1000, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!isPaused){
						incrementAgeForALlPlants();
					}
				}
			});
			ageTimer.start();
		}

		if (waterDecreaseTimer == null){
			waterDecreaseTimer = new Timer(6000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!isPaused){
						decreaseWaterLevelForAllPlants();
					}
				}
			});
			waterDecreaseTimer.start();
		}
	}

	/**
	 * @author Elvira Grubb
	 * Test class to make sure the planttypes are read correctly. Will be deleted when PlantType class is done
	 */
	private void test()
	{
		for (PlantType pt : plantTypes)
		{
			System.out.println(pt.getPlantTypeName());
			System.out.println(pt.getPlantTypeNameAlternative());
			System.out.println(pt.getGrownPlantImage());
			System.out.println(pt.getPlantImageButton());
			System.out.println(pt.getPlantInformation());
			System.out.println();
		}
	}

	public void startWaterDecreaseTimer(){
		waterDecreaseTimer = new Timer(60000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				plant.decreaseWaterLevel(1);
			}
		});
		waterDecreaseTimer.start();
	}

	public void stopWaterDecreaseTimer(){
		if (waterDecreaseTimer != null){
			waterDecreaseTimer.stop();
			waterDecreaseTimer = null;
		}
	}

	public void waterPlant(){
		plant.waterPlant();
		mainFrame.getPlantView().updatePlantDetails(plant);

	}
	// Gets the current plant water level
	public int getPlantWaterLevel(){
		return plant.getWaterLevel();
	}

	private void notifyTimeSkipped(int hours){
		System.out.println("Time skipped by " + hours + " hours.");
	}
	public void choosePlantFrame()
	{
		ArrayList<ImageIcon> plantImage = new ArrayList<>();
		ArrayList<ImageIcon> plantImageHover = new ArrayList<>();
		ArrayList<ImageIcon> potImage = new ArrayList<>();
		ArrayList<ImageIcon> potImageHover = new ArrayList<>();


		for (PlantType pt : plantTypes)
		{
			plantImage.add(pt.getPlantImageButton());
			plantImageHover.add(pt.getPlantImageButtonHover());
		}

		for (Pot p : pots)
		{
			potImage.add(p.getPotButton());
			potImageHover.add(p.getPotButtonHoverImage());
		}

		mainFrame.addChoosePlantPanel(plantImage, plantImageHover, potImage, potImageHover);
	}

	public void showMainMenu()
	{
		mainFrame.addMainMenu();
	}

	/**
	 * @author Elvira Grubb
	 * Reads PlantType information from a TextFile and creates PlantType objects from the information
	 */
	private void loadPlantTypes()
	{
		try {
			BufferedReader br = new BufferedReader( new FileReader("files/plantTypes.txt"));
			PlantType plantType;
			String string = br.readLine();

			while(string != null) {
				String[] plantInformation;
				plantInformation = string.split( "," );

				plantType = new PlantType(plantInformation[0], plantInformation[1], plantInformation[2], plantInformation[3], plantInformation[4], plantInformation[5], plantInformation[6], plantInformation[7]);
				plantTypes.add(plantType);
				string = br.readLine();
			}
			br.close();
		} catch( IOException e ) {
			System.out.println( "readPlantType: " + e );
		}
	}

	private void loadPots()
	{
		try {
			BufferedReader br = new BufferedReader( new FileReader("files/pot.txt"));
			Pot pot;
			String string = br.readLine();

			while(string != null) {
				String[] potInformation;
				potInformation = string.split( "," );

				pot = new Pot(potInformation[0], potInformation[1], potInformation[2]);
				pots.add(pot);
				string = br.readLine();
			}
			br.close();
		} catch( IOException e ) {
			System.out.println( "readPlantType: " + e );
		}
	}

	/**
	 * Reads image file paths from a text file and returns an arraylist
	 * can be used regardless of type of pictures
	 * @param filename a String of the file path
	 * @return arraylist of images
	 * @author Petri Närhi
	 * */
	public ArrayList<ImageIcon> getImageListFromFile(String filename)
	{
		ArrayList<ImageIcon> imageList = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader( new FileReader(filename));
			ImageIcon image;
			String imagePath = br.readLine();

			while(imagePath != null) {
				image = new ImageIcon(imagePath);
				imageList.add(image);
				imagePath = br.readLine();
			}
			br.close();
		} catch( IOException e ) {
			System.out.println( "getImageList: " + e );
		}
		return imageList;
	}

	public void skipTime(int hours){
		if (hours <= 0){
			System.out.println("Skipped time requires a positive number of hours");
			return;
		}
		LocalDateTime newCreationTime = plant.getDateAndTime().minusHours(hours);
		plant.setDateAndTime(newCreationTime);
		int ageIncrement = hours / 24;
		plant.incrementAge(ageIncrement);
		plant.decreaseWaterLevel(1);
		plant.updateState();
		mainFrame.getPlantView().updateElapsedTime();
		mainFrame.getPlantView().updatePlantDetails(plant);
		notifyTimeSkipped(hours);
	}



	public ArrayList<PlantType> getPlantTypes()
	{
		return plantTypes;
	}

	public void showPlantView()
	{
		mainFrame.addPlantView();
	}

	public void incrementAgeForALlPlants(){
		for (int i = 0; i < listOfPlants.size(); i++){
			Plant plant = listOfPlants.get(i);
			if(plant != null){
				plant.incrementAge(1);
			}
		}
	}

	public void decreaseWaterLevelForAllPlants(){
		for (int i = 0; i < listOfPlants.size(); i++){
			Plant plant = listOfPlants.get(i);
			if(plant != null) {
				plant.decreaseWaterLevel(1);
			}
		}
	}

	/**
	 * Gets the current plant
	 * to show the current plant that is shown in PlantView
	 * and to be able to use the plant's methods in various classes
	 * @return Plant
	 * @author Petri Närhi
	 * */
	public Plant getPlant() {
		return plant;
	}

	/**
	 * Sets the current plant
	 * to change the current plant that is shown in PlantView
	 * @param plant the plant to replace the current plant
	 * @author Petri Närhi
	 * */
	public void setPlant(Plant plant) {
		this.plant = plant;
	}
}